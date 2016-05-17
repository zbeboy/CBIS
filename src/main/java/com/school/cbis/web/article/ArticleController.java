package com.school.cbis.web.article;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.ArticleData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.service.*;
import com.school.cbis.util.FilesUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2016-02-24.
 */
@Controller
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Resource
    private TieService tieService;

    @Resource
    private ArticleInfoService articleInfoService;

    @Resource
    private ArticleSubService articleSubService;

    @Resource
    private UsersService usersService;

    @Resource
    private Wordbook wordbook;

    @Resource
    private TieElegantService tieElegantService;

    @Resource
    private TieElegantTimeService tieElegantTimeService;

    @Resource
    private TieNoticeService tieNoticeService;

    @Resource
    private TieNoticeTimeService tieNoticeTimeService;

    @Resource
    private MajorService majorService;

    @Resource
    private TieNoticeAffixService tieNoticeAffixService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    /**
     * 保存文章
     *
     * @param lastParam 文章数据
     * @return
     */
    @RequestMapping(value = "/student/saveArticle", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData saveArticle(@RequestParam(value = "lastParam") String lastParam) {
        AjaxData data = new AjaxData();
        JSONObject object = JSON.parseObject(lastParam);
        Object jsonArray = object.get("articleData");
        Object myParam = object.get("myParam");
        //Json转换成Java对象
        List<ArticleData> articleDatas = JSON.parseArray(jsonArray.toString(), ArticleData.class);
        if (!articleDatas.isEmpty()) {
            //插入信息到文章信息表
            int articleInfoId = 0;
            //查询文章类别
            int articleTypeId = wordbook.getArticleTypeMap().get(articleDatas.get(0).getArticleType());//文章类别ID
            ArticleInfo articleInfo = new ArticleInfo();
            articleInfo.setBigTitle(articleDatas.get(0).getBigTitle());
            articleInfo.setArticleWriter(usersService.getUserName());
            articleInfo.setArticleTypeId(articleTypeId);
            articleInfo.setArticleContent(articleDatas.get(0).getArticleContent());
            articleInfo.setArticlePhotoUrl(articleDatas.get(0).getArticlePhotoUrl());
            articleInfoId = articleInfoService.save(articleInfo);

            //插入信息到文章子标题表
            if (articleInfoId > 0 && !articleDatas.isEmpty()) {
                List<ArticleSub> articleSubs = new ArrayList<>();

                for (int i = 1; i < articleDatas.size(); i++) {
                    ArticleSub articleSub = new ArticleSub();
                    articleSub.setSubTitle(articleDatas.get(i).getSubTitle());
                    articleSub.setArticleInfoId(articleInfoId);
                    articleSub.setSubContent(articleDatas.get(i).getSubContent());
                    articleSubs.add(articleSub);
                }
                if (!StringUtils.isEmpty(articleSubs) && articleSubs.size() > 0) {
                    articleSubService.save(articleSubs);
                }
            }

            //通过用户类型获取系表ID
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if(!ObjectUtils.isEmpty(record)){
                tieId = record.getValue(Tables.TIE.ID);
            }

            Tie tie = tieService.findById(tieId);

            if (articleDatas.get(0).getArticleType().equals(Wordbook.TIE_ELEGANT)) {//系风采
                Map<String, Object> tieMap = (Map<String, Object>) JSON.parse(myParam.toString());
                if (tieId > 0) {
                    int tieElegantTimeId = 0;
                    List<TieElegantTime> tieElegantTimes = tieElegantTimeService.findByTime(new SimpleDateFormat("yyyy年MM月").format(new Date()));
                    if (StringUtils.isEmpty(tieElegantTimes) || tieElegantTimes.size() <= 0) {
                        TieElegantTime tieElegantTime = new TieElegantTime();
                        tieElegantTime.setTime(new SimpleDateFormat("yyyy年MM月").format(new Date()));
                        tieElegantTimeId = tieElegantTimeService.save(tieElegantTime);
                    } else {
                        tieElegantTimeId = tieElegantTimes.get(0).getId();
                    }

                    TieElegant tieElegant = new TieElegant();
                    tieElegant.setTieId(tieId);
                    tieElegant.setTieElegantArticleInfoId(articleInfoId);
                    tieElegant.setTieElegantTimeId(tieElegantTimeId);
                    tieElegant.setIsShow(Byte.parseByte(tieMap.get("isShow").toString()));
                    tieElegantService.save(tieElegant);
                    Map<String, Object> map = new HashMap<>();
                    map.put("single", articleInfoId);
                    data.success().msg("保存系风采成功，是否现在查看效果!").mapData(map);
                } else {
                    data.fail().msg("获取系信息失败!");
                }
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.TIE_LEAD)) {//系主任
                tie.setTiePrincipalArticleInfoId(articleInfoId);
                tieService.update(tie);
                Map<String, Object> map = new HashMap<>();
                map.put("single", articleInfoId);
                data.success().msg("更新文章成功!").mapData(map);

            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.TIE_SUMMARY)) {//系简介
                tie.setTieIntroduceArticleInfoId(articleInfoId);
                tieService.update(tie);
                Map<String, Object> map = new HashMap<>();
                map.put("single", articleInfoId);
                data.success().msg("更新文章成功!").mapData(map);
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.TIE_ITEM)) {//系特色
                tie.setTieTraitArticleInfoId(articleInfoId);
                tieService.update(tie);
                Map<String, Object> map = new HashMap<>();
                map.put("single", articleInfoId);
                data.success().msg("更新文章成功!").mapData(map);
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.TIE_BRING_IN_GOAL)) {//系培养目标
                tie.setTieTrainingGoalArticleInfoId(articleInfoId);
                tieService.update(tie);
                Map<String, Object> map = new HashMap<>();
                map.put("single", articleInfoId);
                data.success().msg("更新文章成功!").mapData(map);
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.TIE_NOTICE)) {//系公告
                Map<String, Object> tieMap = (Map<String, Object>) JSON.parse(myParam.toString());
                if (tieId > 0) {
                    int tieNoticeTimeId = 0;
                    List<TieNoticeTime> tieNoticeTimes = tieNoticeTimeService.findByTime(new SimpleDateFormat("yyyy年MM月").format(new Date()));
                    if (StringUtils.isEmpty(tieNoticeTimes) || tieNoticeTimes.size() <= 0) {
                        TieNoticeTime tieNoticeTime = new TieNoticeTime();
                        tieNoticeTime.setTime(new SimpleDateFormat("yyyy年MM月").format(new Date()));
                        tieNoticeTimeId = tieNoticeTimeService.save(tieNoticeTime);
                    } else {
                        tieNoticeTimeId = tieNoticeTimes.get(0).getId();
                    }

                    TieNotice tieNotice = new TieNotice();
                    tieNotice.setTieId(tieId);
                    tieNotice.setTieNoticeArticleInfoId(articleInfoId);
                    tieNotice.setTieNoticeTimeId(tieNoticeTimeId);
                    tieNotice.setIsShow(Byte.parseByte(tieMap.get("isShow").toString()));
                    tieNoticeService.save(tieNotice);
                    Map<String, Object> map = new HashMap<>();
                    map.put("single", articleInfoId);
                    data.success().msg("保存系公告成功，是否现在查看效果!").mapData(map);

                } else {
                    data.fail().msg("获取系信息失败!");
                }
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.MAJOR_LEAD)) {//专业带头人
                Map<String, Object> majorMap = (Map<String, Object>) JSON.parse(myParam.toString());
                Major major = majorService.findById(Integer.parseInt(majorMap.get("majorId").toString()));
                if (!StringUtils.isEmpty(major)) {
                    major.setMajorForegoerArticleInfoId(articleInfoId);
                    majorService.update(major);
                    Map<String, Object> map = new HashMap<>();
                    map.put("single", articleInfoId);
                    data.success().msg("更新文章成功!").mapData(map);
                } else {
                    data.fail().msg("获取专业信息失败!");
                }
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.MAJOR_SUMMARY)) {//专业简介
                Map<String, Object> majorMap = (Map<String, Object>) JSON.parse(myParam.toString());
                Major major = majorService.findById(Integer.parseInt(majorMap.get("majorId").toString()));
                if (!StringUtils.isEmpty(major)) {
                    major.setMajorIntroduceArticleInfoId(articleInfoId);
                    major.setIsShow(Byte.parseByte(majorMap.get("isShow").toString()));
                    majorService.update(major);
                    Map<String, Object> map = new HashMap<>();
                    map.put("single", articleInfoId);
                    data.success().msg("更新文章成功!").mapData(map);
                } else {
                    data.fail().msg("获取专业信息失败!");
                }
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.MAJOR_BRING_IN_GOAL)) {//专业培养目标
                Map<String, Object> majorMap = (Map<String, Object>) JSON.parse(myParam.toString());
                Major major = majorService.findById(Integer.parseInt(majorMap.get("majorId").toString()));
                if (!StringUtils.isEmpty(major)) {
                    major.setMajorTrainingGoalArticleInfoId(articleInfoId);
                    majorService.update(major);
                    Map<String, Object> map = new HashMap<>();
                    map.put("single", articleInfoId);
                    data.success().msg("更新文章成功!").mapData(map);
                } else {
                    data.fail().msg("获取专业信息失败!");
                }
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.MAJOR_ITEM)) {//专业特色
                Map<String, Object> majorMap = (Map<String, Object>) JSON.parse(myParam.toString());
                Major major = majorService.findById(Integer.parseInt(majorMap.get("majorId").toString()));
                if (!StringUtils.isEmpty(major)) {
                    major.setMajorTraitArticleInfoId(articleInfoId);
                    majorService.update(major);
                    Map<String, Object> map = new HashMap<>();
                    map.put("single", articleInfoId);
                    data.success().msg("更新文章成功!").mapData(map);
                } else {
                    data.fail().msg("获取专业信息失败!");
                }
            }else if (articleDatas.get(0).getArticleType().equals(Wordbook.USER_SUMMARY)) {//用户简介
                Map<String, Object> majorMap = (Map<String, Object>) JSON.parse(myParam.toString());
                Users users = usersService.findByUsername(majorMap.get("username").toString());
                if (!StringUtils.isEmpty(users)) {
                    users.setIntroduceArticleInfoId(articleInfoId);
                    usersService.update(users);
                    Map<String, Object> map = new HashMap<>();
                    map.put("single", articleInfoId);
                    data.success().msg("更新文章成功!").mapData(map);
                } else {
                    data.fail().msg("获取教师信息失败!");
                }
            }

            //处理附件
            if (Boolean.parseBoolean(object.get("openAffix").toString())) {
                if (StringUtils.hasLength(object.get("affixData").toString())) {
                    List<TieNoticeAffix> tieNoticeAffices = JSON.parseArray(object.get("affixData").toString(), TieNoticeAffix.class);
                    for (TieNoticeAffix t : tieNoticeAffices) {
                        t.setTieNoticeFileSize(FilesUtils.sizeToString(new File(t.getTieNoticeFileUrl()).length()));
                        t.setArticleInfoId(articleInfoId);
                        t.setFileUser(usersService.getUserName());
                        t.setFileType(t.getTieNoticeFileUrl().substring(t.getTieNoticeFileUrl().lastIndexOf(".") + 1));
                        tieNoticeAffixService.save(t);
                    }
                }
            }

        } else {
            data.fail().msg("文章信息为空!");
        }
        return data;
    }

    /**
     * 更新文章
     *
     * @param lastParam
     * @return
     */
    @RequestMapping(value = "/student/updateArticle", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData updateArticle(@RequestParam(value = "lastParam") String lastParam) {
        AjaxData data = new AjaxData();
        Map<String, Object> map = new HashMap<>();
        JSONObject object = JSON.parseObject(lastParam);
        Object jsonArray = object.get("articleData");
        Object myParam = object.get("myParam");
        List<ArticleData> articleDatas = JSON.parseArray(jsonArray.toString(), ArticleData.class);
        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(myParam.toString());
        if (!articleDatas.isEmpty()) {
            ArticleInfo articleInfo = articleInfoService.findById(Integer.parseInt(jsonMap.get("articleId").toString()));
            articleInfo.setBigTitle(articleDatas.get(0).getBigTitle());
            articleInfo.setArticleContent(articleDatas.get(0).getArticleContent());
            articleInfo.setArticlePhotoUrl(articleDatas.get(0).getArticlePhotoUrl());

            articleInfoService.update(articleInfo);//文章信息表
            //插入信息到文章子标题表
            List<ArticleSub> articleSubs = new ArrayList<>();
            for (int i = 1; i < articleDatas.size(); i++) {
                ArticleSub articleSub = new ArticleSub();
                articleSub.setSubTitle(articleDatas.get(i).getSubTitle());
                articleSub.setSubContent(articleDatas.get(i).getSubContent());
                articleSub.setArticleInfoId(Integer.parseInt(jsonMap.get("articleId").toString()));
                articleSubs.add(articleSub);
            }
            articleSubService.deleteByArticleInfoId(Integer.parseInt(jsonMap.get("articleId").toString()));
            if (!StringUtils.isEmpty(articleSubs) && articleSubs.size() > 0) {
                articleSubService.save(articleSubs);
            }

            if (articleDatas.get(0).getArticleType().equals(Wordbook.MAJOR_SUMMARY)) {//专业简介
                Map<String, Object> majorMap = (Map<String, Object>) JSON.parse(myParam.toString());
                Major major = majorService.findById(Integer.parseInt(majorMap.get("majorId").toString()));
                if (!StringUtils.isEmpty(major)) {
                    major.setIsShow(Byte.parseByte(majorMap.get("isShow").toString()));
                    majorService.update(major);
                } else {
                    data.fail().msg("获取专业信息失败!");
                }
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.TIE_ELEGANT)) {//系风采
                Map<String, Object> tieMap = (Map<String, Object>) JSON.parse(myParam.toString());
                TieElegant tieElegant = tieElegantService.findById(Integer.parseInt(tieMap.get("tieElegantId").toString()));
                if (!StringUtils.isEmpty(tieElegant)) {
                    tieElegant.setIsShow(Byte.parseByte(tieMap.get("isShow").toString()));
                    tieElegantService.update(tieElegant);
                } else {
                    data.fail().msg("获取系信息失败!");
                }
            } else if (articleDatas.get(0).getArticleType().equals(Wordbook.TIE_NOTICE)) {//系公告
                Map<String, Object> tieMap = (Map<String, Object>) JSON.parse(myParam.toString());
                TieNotice tieNotice = tieNoticeService.findById(Integer.parseInt(tieMap.get("tieNoticeId").toString()));
                if (!StringUtils.isEmpty(tieNotice)) {
                    tieNotice.setIsShow(Byte.parseByte(tieMap.get("isShow").toString()));
                    tieNoticeService.update(tieNotice);
                } else {
                    data.fail().msg("获取系信息失败!");
                }
            }

            if (Boolean.parseBoolean(object.get("openAffix").toString())) {
                tieNoticeAffixService.deleteByArticleInfoId(Integer.parseInt(jsonMap.get("articleId").toString()));
                if (StringUtils.hasLength(object.get("affixData").toString())) {
                    List<TieNoticeAffix> tieNoticeAffices = JSON.parseArray(object.get("affixData").toString(), TieNoticeAffix.class);
                    for (TieNoticeAffix t : tieNoticeAffices) {
                        t.setTieNoticeFileSize(FilesUtils.sizeToString(new File(t.getTieNoticeFileUrl()).length()));
                        t.setArticleInfoId(Integer.parseInt(jsonMap.get("articleId").toString()));
                        t.setFileUser(usersService.getUserName());
                        t.setFileType(t.getTieNoticeFileUrl().substring(t.getTieNoticeFileUrl().lastIndexOf(".") + 1));
                        tieNoticeAffixService.save(t);
                    }
                }
            }
            map.put("single", Integer.parseInt(jsonMap.get("articleId").toString()));
            data.success().msg("更新文章成功!").mapData(map);
        }
        return data;

    }

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/maintainer/deleteArticle", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData deleteArticle(@RequestParam(value = "id", defaultValue = "0") int id) {
        AjaxData ajaxData = new AjaxData();
        try {
            ArticleInfo articleInfo = articleInfoService.findById(id);
            if (!StringUtils.isEmpty(articleInfo)) {
                articleSubService.deleteByArticleInfoId(articleInfo.getId());
                articleInfoService.deleteById(articleInfo.getId());
                FilesUtils.deleteFile(articleInfo.getArticlePhotoUrl());
                ajaxData.success();
            } else {
                ajaxData.fail().msg("获取文章信息有误!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            ajaxData.fail().msg("数据转换异常!");
        }
        return ajaxData;
    }


}
