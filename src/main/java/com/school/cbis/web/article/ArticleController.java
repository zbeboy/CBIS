package com.school.cbis.web.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.ArticleData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.service.*;
import com.school.cbis.util.FilesUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016-02-24.
 */
@Controller
public class ArticleController {

    @Resource
    private UploadService upload;

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
    private MajorService majorService;

    /**
     * 保存文章
     *
     * @param subData 文章数据
     * @return
     */
    @RequestMapping(value = "/maintainer/saveArticle", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData saveArticle(@RequestParam(value = "subData") String subData,@RequestParam(value = "id") int majorId) {
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
                ArticleInfo articleInfo = new ArticleInfo();
                articleInfo.setBigTitle(articleDatas[0].getTitle());
                articleInfo.setArticleWriter(usersService.getUserName());
                articleInfo.setArticleTypeId(articleTypeId);
                articleInfo.setArticleContent(articleDatas[0].getSummary());
                articleInfo.setArticlePhotoUrl(articleDatas[0].getPicPath());
                articleInfoId = articleInfoService.save(articleInfo);

                //插入信息到文章子标题表
                if (articleInfoId > 0 && articleDatas.length > 1) {
                    List<ArticleSub> articleSubs = new ArrayList<>();
                    for (int i = 1; i < articleDatas.length; i++) {
                        ArticleSub articleSub = new ArticleSub();
                        articleSub.setSubTitle(articleDatas[i].getSubTitle());
                        articleSub.setArticleInfoId(articleInfoId);
                        articleSub.setSubContent(articleDatas[i].getSubPage());
                        articleSubs.add(articleSub);
                    }
                    if (!StringUtils.isEmpty(articleSubs) && articleSubs.size() > 0) {
                        articleSubService.save(articleSubs);
                    }
                }

                //通过用户类型获取系表ID
                Result<Record> records = usersService.findAll(usersService.getUserName());
                int tieId = 0;
                if (records.isNotEmpty()) {
                    for (Record r : records) {
                        tieId = r.getValue(Tables.TIE.ID);
                    }
                }

                Tie tie = tieService.findById(tieId);

                if (articleDatas[0].getArticleType().equals(Wordbook.TIE_ELEGANT)) {//系风采
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
                        tieElegantService.save(tieElegant);
                        data.setState(true);
                        data.setMsg("保存系风采成功，是否现在查看效果！");
                        data.setSingle(articleInfoId);

                    } else {
                        data.setState(false);
                        data.setMsg("获取用户信息失败！");
                    }
                } else if (articleDatas[0].getArticleType().equals(Wordbook.TIE_LEAD)) {//系主任
                    tie.setTiePrincipalArticleInfoId(articleInfoId);
                    tieService.update(tie);
                    data.setState(true);
                    data.setMsg("更新文章成功！");
                    data.setSingle(articleInfoId);

                } else if (articleDatas[0].getArticleType().equals(Wordbook.TIE_SUMMARY)) {//系简介
                    tie.setTieIntroduceArticleInfoId(articleInfoId);
                    tieService.update(tie);
                    data.setState(true);
                    data.setMsg("更新文章成功！");
                    data.setSingle(articleInfoId);
                } else if (articleDatas[0].getArticleType().equals(Wordbook.TIE_ITEM)) {//系特色
                    tie.setTieTraitArticleInfoId(articleInfoId);
                    tieService.update(tie);
                    data.setState(true);
                    data.setMsg("更新文章成功！");
                    data.setSingle(articleInfoId);
                } else if (articleDatas[0].getArticleType().equals(Wordbook.TIE_BRING_IN_GOAL)) {//系培养目标
                    tie.setTieTrainingGoalArticleInfoId(articleInfoId);
                    tieService.update(tie);
                    data.setState(true);
                    data.setMsg("更新文章成功！");
                    data.setSingle(articleInfoId);
                } else if (articleDatas[0].getArticleType().equals(Wordbook.MAJOR_LEAD)) {//专业带头人
                    Major major = majorService.findById(majorId);
                    if (!StringUtils.isEmpty(major)) {
                        major.setMajorForegoerArticleInfoId(articleInfoId);
                        majorService.update(major);
                        data.setState(true);
                        data.setMsg("更新文章成功！");
                        data.setSingle(articleInfoId);
                    } else {
                        data.setState(false);
                        data.setMsg("获取用户信息失败！");
                    }
                } else if (articleDatas[0].getArticleType().equals(Wordbook.MAJOR_SUMMARY)) {//专业简介
                    Major major = majorService.findById(majorId);
                    if (!StringUtils.isEmpty(major)) {
                        major.setMajorIntroduceArticleInfoId(articleInfoId);
                        majorService.update(major);
                        data.setState(true);
                        data.setMsg("更新文章成功！");
                        data.setSingle(articleInfoId);
                    } else {
                        data.setState(false);
                        data.setMsg("获取用户信息失败！");
                    }
                } else if (articleDatas[0].getArticleType().equals(Wordbook.MAJOR_BRING_IN_GOAL)) {//专业培养目标
                    Major major = majorService.findById(majorId);
                    if (!StringUtils.isEmpty(major)) {
                        major.setMajorTrainingGoalArticleInfoId(articleInfoId);
                        majorService.update(major);
                        data.setState(true);
                        data.setMsg("更新文章成功！");
                        data.setSingle(articleInfoId);
                    } else {
                        data.setState(false);
                        data.setMsg("获取用户信息失败！");
                    }
                } else if (articleDatas[0].getArticleType().equals(Wordbook.MAJOR_ITEM)) {//专业特色
                    Major major = majorService.findById(majorId);
                    if (!StringUtils.isEmpty(major)) {
                        major.setMajorTraitArticleInfoId(articleInfoId);
                        majorService.update(major);
                        data.setState(true);
                        data.setMsg("更新文章成功！");
                        data.setSingle(articleInfoId);
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
     * 更新文章
     *
     * @param subData
     * @param id      文章id
     * @return
     */
    @RequestMapping(value = "/maintainer/updateArticle", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData updateArticle(@RequestParam(value = "subData") String subData, @RequestParam(value = "id") int id) {
        AjaxData data = new AjaxData();
        try {
                //Json转换成Java对象
                ArticleData[] articleDatas = new ObjectMapper().readValue(subData, ArticleData[].class);
                if (articleDatas.length > 0) {

                    ArticleInfo articleInfo = articleInfoService.findById(id);
                    articleInfo.setBigTitle(articleDatas[0].getTitle());
                    articleInfo.setArticleContent(articleDatas[0].getSummary());
                    articleInfo.setArticlePhotoUrl(articleDatas[0].getPicPath());

                    articleInfoService.update(articleInfo);//文章信息表
                    //插入信息到文章子标题表
                    if (articleDatas.length > 1) {
                        List<ArticleSub> articleSubs = new ArrayList<>();
                        for (int i = 1; i < articleDatas.length; i++) {
                            ArticleSub articleSub = new ArticleSub();
                            articleSub.setSubTitle(articleDatas[i].getSubTitle());
                            articleSub.setSubContent(articleDatas[i].getSubPage());
                            articleSub.setArticleInfoId(id);
                            articleSubs.add(articleSub);
                        }
                        if (!StringUtils.isEmpty(articleSubs) && articleSubs.size() > 0) {
                            articleSubService.deleteByArticleInfoId(id);
                            articleSubService.save(articleSubs);
                        }
                    }
                    data.setState(true);
                    data.setMsg("更新文章成功！");
                    data.setSingle(id);
                }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }

    @RequestMapping("/maintainer/getArticle")
    public String getArticle(@RequestParam(value = "id", defaultValue = "0") int id, ModelMap map) {
        if (id > 0) {
            ArticleInfo articleInfo = articleInfoService.findById(id);
            if (!StringUtils.isEmpty(articleInfo)) {
                List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(articleInfo.getId());
                map.addAttribute("articleinfo", articleInfo);
                map.addAttribute("articlesubinfo", articleSubs);
            } else {
                map.addAttribute("articleinfo", new ArticleInfo());
                map.addAttribute("articlesubinfo", null);
            }
        } else {
            map.addAttribute("articleinfo", new ArticleInfo());
            map.addAttribute("articlesubinfo", null);
        }
        return "/maintainer/majorheadupdate";
    }

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
                ajaxData.setState(true);
            } else {
                ajaxData.setState(false);
                ajaxData.setMsg("获取文章信息有误!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            ajaxData.setState(false);
            ajaxData.setMsg("数据转换异常!");
        }
        return ajaxData;
    }

    /**
     * 上传图片
     *
     * @param multipartHttpServletRequest
     * @param request
     * @return 图片保存完整路径
     */
    @RequestMapping(value = "/maintainer/uploadPicture", method = RequestMethod.POST)
    @ResponseBody
    public String uploadPicture(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) {
        AjaxData data = new AjaxData();
        String lastPath = null;
        try {
            String realPath = request.getSession().getServletContext().getRealPath("/");
            lastPath = upload.upload(multipartHttpServletRequest, realPath + "files" + File.separator + multipartHttpServletRequest.getParameter("pathname"), request.getRemoteAddr());
            data.setState(true);
            data.setMsg(lastPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastPath;
    }

    /**
     * 删除硬盘中的图片
     *
     * @param path 真实图片路径
     * @return
     */
    @RequestMapping(value = "/maintainer/deletePictue", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData deletePictue(@RequestParam("path") String path) {
        AjaxData data = new AjaxData();
        try {
            if (!StringUtils.isEmpty(path) && StringUtils.trimWhitespace(path).length() > 0) {
                if (FilesUtils.deleteFile(path)) {
                    data.setState(true);
                    data.setMsg("删除图片成功！");
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
}
