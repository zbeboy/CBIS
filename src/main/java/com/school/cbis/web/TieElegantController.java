package com.school.cbis.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.ArticleInfoRecord;
import com.school.cbis.domain.tables.records.ArticleSubRecord;
import com.school.cbis.domain.tables.records.TieElegantRecord;
import com.school.cbis.domain.tables.records.TieElegantTimeRecord;
import com.school.cbis.service.ArticleService;
import com.school.cbis.service.TieElegantService;
import com.school.cbis.service.UsersService;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.*;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2016-01-23.
 */
@Controller
public class TieElegantController {

    @Resource
    private ArticleService articleService;

    @Resource
    private UsersService usersService;

    @Resource
    private Wordbook wordbook;

    @Resource
    private TieElegantService tieElegantService;

    /**
     * 动态搜索标题
     *
     * @param search 搜索值
     * @return
     */
    @RequestMapping(value = "/admin/searchitems", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> tieElegantTitle(@RequestParam("search") String search) {
        Result<Record> records = usersService.getUsersInfoAll(usersService.getUserName());
        int tieId = 0;
        if (!StringUtils.isEmpty(records)) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);//获取id
            }
        }
        Result<Record3<Integer, String, String>> result = tieElegantService.searchItems(search, tieId);//得到所有与输入内容相近的标题与内容
        List<SearchData> searchDatas = new ArrayList<>();
        if (!StringUtils.isEmpty(result)) {
            for (Record r : result) {
                searchDatas.add(new SearchData(r.getValue(Tables.ARTICLE_INFO.BIG_TITLE), new String(r.getValue(Tables.ARTICLE_INFO.ARTICLE_CONTENT)).substring(0, 15) + "...",
                        "/admin/tieelegantupdate?id=" + r.getValue(Tables.ARTICLE_INFO.ID)));
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("results", searchDatas);
        return map;
    }

    /**
     * 当按搜索框中内容模糊搜索
     *
     * @param key 搜索中内容
     * @param map
     * @return
     */
    @RequestMapping(value = "/admin/moreresulttieelegant", method = RequestMethod.POST)
    public String moreResultTieElegant(@RequestParam("key") String key, ModelMap map) {
        Result<Record> records = usersService.getUsersInfoAll(usersService.getUserName());
        int tieId = 0;
        if (!StringUtils.isEmpty(records)) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Result<Record4<Integer, String, String, Timestamp>> record5s = tieElegantService.getTieElegantInfoByPage(key, 0, 10, tieId);
        List<TieElegantVo> list = new ArrayList<>();
        if (!StringUtils.isEmpty(record5s)) {
            for (Record r : record5s) {
                list.add(buildTieElegantVo(r.getValue(Tables.TIE_ELEGANT.ID), r.getValue(Tables.ARTICLE_INFO.BIG_TITLE),
                        r.getValue(Tables.USERS.USERNAME), r.getValue(Tables.ARTICLE_INFO.DATE)));
            }
        }
        int datas = tieElegantService.tieElegantInfoCount(key, tieId);

        map.addAttribute("pagination", new PaginationData(datas, 0, 10));
        map.addAttribute("elegant", list);
        map.addAttribute("startSearch", true);
        map.addAttribute("key", key);
        return "/admin/tieelegantlist";
    }

    /**
     * angularjs 动态分页数据显示
     *
     * @param key     当前搜索条件
     * @param pageNum 当前页
     * @return
     */
    @RequestMapping(value = "/admin/tieelegantlist")
    @ResponseBody
    public AjaxData backstageTieElegantList(@RequestParam("key") String key,
                                            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        AjaxData ajaxData = new AjaxData();
        Result<Record> records = usersService.getUsersInfoAll(usersService.getUserName());
        int tieId = 0;
        if (!StringUtils.isEmpty(records)) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        pageNum -= 1;
        if (pageNum < 0) {
            pageNum = 0;
        }
        Result<Record4<Integer, String, String, Timestamp>> record5s = tieElegantService.getTieElegantInfoByPage(key, pageNum * 10, 10, tieId);
        if (!StringUtils.isEmpty(record5s)) {
            ajaxData.setState(true);
            List<Object> list = new ArrayList<>();
            for (Record r : record5s) {
                list.add(buildTieElegantVo(r.getValue(Tables.ARTICLE_INFO.ID), r.getValue(Tables.ARTICLE_INFO.BIG_TITLE),
                        r.getValue(Tables.USERS.USERNAME), r.getValue(Tables.ARTICLE_INFO.DATE)));
            }
            ajaxData.setResult(list);
            int datas = tieElegantService.tieElegantInfoCount(key, tieId);
            ajaxData.setPaginationData(new PaginationData(datas, pageNum++, 10));
        } else {
            ajaxData.setState(false);
            ajaxData.setMsg("没有结果！");
        }
        return ajaxData;
    }

    /**
     * 系风采添加页面
     *
     * @return
     */
    @RequestMapping("/admin/tieelegantadd")
    public String tieElegantAdd() {
        return "/admin/tieelegantadd";
    }

    /**
     * 保存系风采
     * @param subData 风采数据
     * @return
     */
    @RequestMapping(value = "/admin/saveElegant", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData saveArticle(@RequestParam(value = "subData", required = false) String subData) {
        AjaxData data = null;
        try {
            data = new AjaxData();
            //Json转换成Java对象
            ArticleData[] articleDatas = new ObjectMapper().readValue(subData, ArticleData[].class);
            if (articleDatas.length > 0) {
                //插入信息到文章信息表
                int articleInfoId = 0;
                //查询文章类别
                int articleTypeId = wordbook.getArticleTypeMap().get(articleDatas[0].getArticleType());//文章类别ID
                ArticleInfoRecord articleInfoRecord = buildArticleInfoRecord(articleDatas[0].getTitle(), usersService.getUserName(),
                        articleTypeId, articleDatas[0].getSummary(), articleDatas[0].getPicPath());
                articleInfoId = articleService.saveArticleInfo(articleInfoRecord);

                //插入信息到文章子标题表
                if (articleInfoId > 0 && articleDatas.length > 1) {
                    List<ArticleSubRecord> articleSubRecordList = new ArrayList<>();
                    for (int i = 1; i < articleDatas.length; i++) {
                        ArticleSubRecord articleSubRecord = buildArticleSubRecord(articleDatas[i].getSubTitle(), articleDatas[i].getSubPage(),
                                null, articleInfoId, articleDatas[i].getRow());
                        articleSubRecordList.add(articleSubRecord);
                    }
                    if (!StringUtils.isEmpty(articleSubRecordList) && articleSubRecordList.size() > 0) {
                        articleService.saveArticleSub(articleSubRecordList);
                    }
                }

                //通过用户类型获取系表ID
                Result<Record> records = usersService.getUsersInfoAll(usersService.getUserName());
                int tieId = 0;
                if (!StringUtils.isEmpty(records)) {
                    for (Record r : records) {
                        tieId = r.getValue(Tables.TIE.ID);
                    }
                }

                if (articleDatas[0].getArticleType().equals(Wordbook.TIE_ELEGANT)) {//系风采
                    if (tieId > 0) {
                        int tieElegantTimeId = 0;
                        Result<TieElegantTimeRecord> tieElegantTimeRecords = tieElegantService.findByTime(new SimpleDateFormat("yyyy年MM月").format(new Date()));
                        for (TieElegantTimeRecord r : tieElegantTimeRecords) {
                            tieElegantTimeId = r.getValue(Tables.TIE_ELEGANT_TIME.ID);
                        }
                        if (tieElegantTimeId <= 0) {
                            TieElegantTimeRecord tieElegantTimeRecord = new TieElegantTimeRecord();
                            tieElegantTimeRecord.setTime(new SimpleDateFormat("yyyy年MM月").format(new Date()));
                            tieElegantTimeId = tieElegantService.saveTime(tieElegantTimeRecord);
                        }
                        TieElegantRecord tieElegantRecord = new TieElegantRecord();
                        tieElegantRecord.setTieId(tieId);
                        tieElegantRecord.setTieElegantArticleInfoId(articleInfoId);
                        tieElegantRecord.setTieElegantTimeId(tieElegantTimeId);
                        if (tieElegantService.saveTieElegant(tieElegantRecord)) {
                            data.setState(true);
                            data.setMsg("保存系风采成功，是否现在查看效果！");
                        } else {
                            data.setState(false);
                            data.setMsg("保存系风采失败！");
                        }
                    } else {
                        data.setState(false);
                        data.setMsg("获取用户信息失败！");
                    }
                }
            } else {
                data.setState(false);
                data.setMsg("文章信息为空！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 跳转更新页面
     * @param id 文章id
     * @param map
     * @return
     */
    @RequestMapping("/admin/tieelegantupdate")
    public String tieElegantUpdate(@RequestParam("id") int id, ModelMap map) {
        map.addAttribute("articleinfo", articleService.findById(id));
        List<ArticleSubRecord> list = new ArrayList<>();
        Result<ArticleSubRecord> records = articleService.getArticleSubs(id);
        if (records.size() > 0) {
            for (ArticleSubRecord r : records) {
                ArticleSubRecord articleSubRecord = new ArticleSubRecord();
                articleSubRecord.setId(r.getValue(Tables.ARTICLE_SUB.ID));
                articleSubRecord.setArticleInfoId(r.getValue(Tables.ARTICLE_SUB.ARTICLE_INFO_ID));
                articleSubRecord.setSubPhotoUrl(r.getValue(Tables.ARTICLE_SUB.SUB_PHOTO_URL));
                articleSubRecord.setRow(r.getValue(Tables.ARTICLE_SUB.ROW));
                articleSubRecord.setSubContent(r.getValue(Tables.ARTICLE_SUB.SUB_CONTENT));
                articleSubRecord.setSubTitle(r.getValue(Tables.ARTICLE_SUB.SUB_TITLE));
                list.add(articleSubRecord);
            }
        }
        map.addAttribute("articlesubinfo", list);
        return "/admin/tieelegantupdate";
    }

    /**
     * 删除子标题内容
     * @param id 子标题id
     * @return
     */
    @RequestMapping(value = "/admin/deleteTieElegantSub", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData deleteSubPage(@RequestParam("id") int id) {
        AjaxData ajaxData = new AjaxData();
        if (articleService.deleteArticleSub(id)) {
            ajaxData.setState(true);
            ajaxData.setMsg("删除子标题成功！");
        } else {
            ajaxData.setState(false);
            ajaxData.setMsg("删除子标题失败！");
        }
        return ajaxData;
    }

    /**
     * 删除文章图片
     * @param path 路径
     * @param id 文章id
     * @return
     */
    @RequestMapping(value = "/admin/deleteTieElegantImg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData deleteTieElegantImg(@RequestParam("path") String path, @RequestParam(value = "id", defaultValue = "0") int id) {
        AjaxData data = null;
        try {
            data = new AjaxData();
            if (id <= 0) {
                data.setState(false);
                data.setMsg("未找到文章信息！");
            }
            if (id > 0 && !StringUtils.isEmpty(path) && StringUtils.trimWhitespace(path).length() > 0) {
                if (FilesUtils.deleteFile(path)) {
                    if (tieElegantService.deleteTieElegantImg(id)) {
                        data.setState(true);
                        data.setMsg("删除图片成功！");
                    } else {
                        data.setState(false);
                        data.setMsg("删除图片失败！");
                    }
                } else {
                    data.setState(false);
                    data.setMsg("未找到图片！");
                }
            } else {
                data.setState(false);
                data.setMsg("删除图片失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            data.setState(false);
            data.setMsg("删除图片失败！");
        }
        return data;
    }

    /**
     * 更新系风采文章
     * @param subData
     * @param id 文章id
     * @return
     */
    @RequestMapping(value = "/admin/updateElegant", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData updateElegant(@RequestParam(value = "subData", required = false) String subData, @RequestParam(value = "id", defaultValue = "0") int id) {
        AjaxData data = null;
        try {
            if (id > 0) {
                data = new AjaxData();
                //Json转换成Java对象
                ArticleData[] articleDatas = new ObjectMapper().readValue(subData, ArticleData[].class);
                if (articleDatas.length > 0) {
                    ArticleInfoRecord articleInfoRecord = new ArticleInfoRecord();
                    articleInfoRecord.setBigTitle(articleDatas[0].getTitle());
                    articleInfoRecord.setArticleContent(articleDatas[0].getSummary());
                    articleInfoRecord.setArticlePhotoUrl(articleDatas[0].getPicPath());
                    if (articleService.updateArticleInfo(articleInfoRecord, id)) {//文章信息表

                        if (articleService.deleteArticleSubByArticleInfoId(id)) {//删除所有子标题
                            //插入信息到文章子标题表
                            if (articleDatas.length > 1) {
                                List<ArticleSubRecord> articleSubRecordList = new ArrayList<>();
                                for (int i = 1; i < articleDatas.length; i++) {
                                    ArticleSubRecord articleSubRecord = buildArticleSubRecord(articleDatas[i].getSubTitle(), articleDatas[i].getSubPage(),
                                            null, id, articleDatas[i].getRow());
                                    articleSubRecordList.add(articleSubRecord);
                                }
                                if (!StringUtils.isEmpty(articleSubRecordList) && articleSubRecordList.size() > 0) {
                                    articleService.saveArticleSub(articleSubRecordList);
                                }
                            }
                        }
                        data.setState(true);
                        data.setMsg("更新文章成功！");
                    }
                } else {
                    data.setState(false);
                    data.setMsg("文章信息为空！");
                }
            } else {
                data.setState(false);
                data.setMsg("获取文章信息失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }

    /**
     * 删除系风采
     * @param id 文章id
     * @return
     */
    @RequestMapping(value = "/admin/deleteTieElegant", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData deleteTieElegant(@RequestParam(value = "id", defaultValue = "0") int id) {
        AjaxData data = new AjaxData();
        if (id > 0) {
            if (tieElegantService.deleteTieElegant(id)) {
                articleService.deleteArticleSubByArticleInfoId(id);
                if (articleService.deleteArticleInfo(id)) {
                    data.setState(true);
                    data.setMsg("删除文章成功！");
                } else {
                    data.setState(false);
                    data.setMsg("删除文章失败！");
                }
            } else {
                data.setState(false);
                data.setMsg("删除文章失败！");
            }
        } else {
            data.setState(false);
            data.setMsg("获取文章信息失败！");
        }
        return data;
    }

    /**
     * @param id        系风采id
     * @param big_title 文章标题
     * @param username  用户名
     * @param date      日期
     * @return
     */
    private TieElegantVo buildTieElegantVo(int id, String big_title, String username, Timestamp date) {
        TieElegantVo tieElegantVo = new TieElegantVo();
        tieElegantVo.setId(id);
        tieElegantVo.setBigTitle(big_title);
        tieElegantVo.setUsername(username);
        tieElegantVo.setDate(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(date.getTime())));
        return tieElegantVo;
    }

    /**
     * 文章信息表实体
     *
     * @param title
     * @param username
     * @param typeId
     * @param summary
     * @param picPath
     * @return
     */
    private ArticleInfoRecord buildArticleInfoRecord(String title, String username, int typeId, String summary, String picPath) {
        ArticleInfoRecord articleInfoRecord = new ArticleInfoRecord();
        articleInfoRecord.setBigTitle(title);
        articleInfoRecord.setArticleWriter(username);
        articleInfoRecord.setArticleTypeId(typeId);
        articleInfoRecord.setArticleContent(summary);
        articleInfoRecord.setArticlePhotoUrl(picPath);
        return articleInfoRecord;
    }

    /**
     * 文章子标题实体
     *
     * @param subTitle
     * @param subContent
     * @param subPhotoUrl
     * @param articleInfoId
     * @param row
     * @return
     */
    private ArticleSubRecord buildArticleSubRecord(String subTitle, String subContent, String subPhotoUrl, int articleInfoId, int row) {
        ArticleSubRecord articleSubRecord = new ArticleSubRecord();
        articleSubRecord.setSubTitle(subTitle);
        articleSubRecord.setSubContent(subContent);
        articleSubRecord.setSubPhotoUrl(subPhotoUrl);//本次就在子内容中添加图片了
        articleSubRecord.setArticleInfoId(articleInfoId);
        articleSubRecord.setRow(row);
        return articleSubRecord;
    }
}
