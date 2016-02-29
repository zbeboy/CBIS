package com.school.cbis.web;

import com.school.cbis.data.AjaxData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.service.*;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.personal.TeacherVo;
import org.jooq.*;
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
import java.util.*;

/**
 * Created by lenovo on 2016-01-10.
 */
@Controller
public class BackstageController {

    @Resource
    private UploadService upload;

    @Resource
    private ArticleInfoService articleInfoService;

    @Resource
    private ArticleSubService articleSubService;

    @Resource
    private UsersService usersService;

    @Resource
    private TieService tieService;

    @Resource
    private YardService yardService;

    @Resource
    private MajorService majorService;

    @Resource
    private TeacherService teacherService;

    /**
     * 系管理界面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tieManager")
    public String tieManager(ModelMap map) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Tie tie = tieService.findById(tieId);
        List<Yard> yardList = yardService.findAll();
        Yard yard = new Yard();
        if (!yardList.isEmpty()) {
            for (int i = 0; i < yardList.size(); i++) {
                if (yardList.get(i).getId() == tie.getYardId()) {
                    yard = yardList.get(i);
                    yardList.remove(i);
                    break;
                }
            }
        }
        map.addAttribute("tie", tie);
        map.addAttribute("yardInfo", yard);
        map.addAttribute("yardList", yardList);
        return "/maintainer/tiemanager";
    }

    /**
     * 专业管理界面
     *
     * @return
     */
    @RequestMapping("/maintainer/majormanager")
    public String majorManager() {
        return "/maintainer/majorlist";
    }

    /**
     * 专业简介界面
     *
     * @return
     */
    @RequestMapping("/maintainer/majorintroduce")
    public String majorIntroduce() {
        return "/maintainer/majorintroducelist";
    }

    /**
     * 专业带头人界面
     * @return
     */
    @RequestMapping("/maintainer/majorhead")
    public String majorHead(){
        return "/maintainer/majorheadlist";
    }

    /**
     * 专业培养目标
     * @return
     */
    @RequestMapping("/maintainer/majortraininggoal")
    public String majorTrainingGoal(){
        return "/maintainer/majortraininggoallist";
    }

    /**
     * 专业特色
     * @return
     */
    @RequestMapping("/maintainer/majortrait")
    public String majorTrait(){
        return "/maintainer/majortraitlist";
    }

    /**
     * 系简介管理页面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tieintroduce")
    public String backstageTieIntroduce(ModelMap map) {
        //通过用户类型获取系表文章ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int articleInfoId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                if (!StringUtils.isEmpty(r.getValue(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID))) {
                    articleInfoId = r.getValue(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID);
                }
            }
        }
        if (articleInfoId > 0) {
            map.addAttribute("articleinfo", articleInfoService.findById(articleInfoId));
            List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(articleInfoId);
            map.addAttribute("articlesubinfo", articleSubs);
        } else {
            map.addAttribute("articleinfo", new ArticleInfo());
            map.addAttribute("articlesubinfo", null);
        }
        return "/maintainer/tieintroduceupdate";
    }

    /**
     * 系主任编辑页面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tiehead")
    public String backstageTieHead(ModelMap map) {
        //通过用户类型获取系表文章ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int articleInfoId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                if (!StringUtils.isEmpty(r.getValue(Tables.TIE.TIE_PRINCIPAL_ARTICLE_INFO_ID))) {
                    articleInfoId = r.getValue(Tables.TIE.TIE_PRINCIPAL_ARTICLE_INFO_ID);
                }
            }
        }
        if (articleInfoId > 0) {
            List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(articleInfoId);
            map.addAttribute("articleinfo", articleInfoService.findById(articleInfoId));
            map.addAttribute("articlesubinfo", articleSubs);
        } else {
            map.addAttribute("articlesubinfo", null);
            map.addAttribute("articleinfo", new ArticleInfo());
        }
        return "/maintainer/tieheadupdate";
    }

    /**
     * 系培养目标页面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tietraingoal")
    public String backstageTieTrainGoal(ModelMap map) {
        //通过用户类型获取系表文章ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int articleInfoId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                if (!StringUtils.isEmpty(r.getValue(Tables.TIE.TIE_TRAINING_GOAL_ARTICLE_INFO_ID))) {
                    articleInfoId = r.getValue(Tables.TIE.TIE_TRAINING_GOAL_ARTICLE_INFO_ID);
                }
            }
        }
        if (articleInfoId > 0) {
            List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(articleInfoId);
            map.addAttribute("articlesubinfo", articleSubs);
            map.addAttribute("articleinfo", articleInfoService.findById(articleInfoId));
        } else {
            map.addAttribute("articleinfo", new ArticleInfo());
            map.addAttribute("articlesubinfo", null);
        }
        return "/maintainer/tietraingoalupdate";
    }

    /**
     * 系特色
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tieitem")
    public String backstageTieItem(ModelMap map) {
        //通过用户类型获取系表文章ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int articleInfoId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                if (!StringUtils.isEmpty(r.getValue(Tables.TIE.TIE_TRAIT_ARTICLE_INFO_ID))) {
                    articleInfoId = r.getValue(Tables.TIE.TIE_TRAIT_ARTICLE_INFO_ID);
                }
            }
        }
        if (articleInfoId > 0) {
            List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(articleInfoId);
            map.addAttribute("articleinfo", articleInfoService.findById(articleInfoId));
            map.addAttribute("articlesubinfo", articleSubs);

        } else {
            map.addAttribute("articleinfo", new ArticleInfo());
            map.addAttribute("articlesubinfo", null);

        }
        return "/maintainer/tieitemupdate";
    }

    @RequestMapping("/maintainer/grademanager")
    public String gradeManager(ModelMap map){
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<Major> majors = majorService.findByTieId(tieId);
        map.addAttribute("majorNames",majors);
        return "/maintainer/gradelist";
    }

    @RequestMapping("/maintainer/usersmanager")
    public String usersManager(){
        return "/maintainer/studentlist";
    }

    @RequestMapping("/maintainer/studentmanager")
    public String studentManager(ModelMap map){
        return "/maintainer/studentlist";
    }

    @RequestMapping("/maintainer/teachermanager")
    public String teacherManager(ModelMap map){
        List<TeacherVo> teacherVos = new ArrayList<>();
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Result<Record5<Integer, String, String, Byte, String>> record5s = teacherService.findByTieId(tieId);
        if(record5s.isNotEmpty()){
            teacherVos = record5s.into(TeacherVo.class);
        }
        map.addAttribute("teachers",teacherVos);
        return "/maintainer/teacherlist";
    }

    /**
     * 上传图片
     *
     * @param multipartHttpServletRequest
     * @param request
     * @return 图片保存完整路径
     */
    @RequestMapping(value = "/maintainer/uploadpicture", method = RequestMethod.POST)
    @ResponseBody
    public String uploadTieElegantPicture(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) {
        AjaxData data = null;
        String lastPath = null;
        try {
            data = new AjaxData();
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
    @RequestMapping(value = "/maintainer/deletepictue", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData deleteTieElegantPicture(@RequestParam("path") String path) {
        AjaxData data = null;
        try {
            data = new AjaxData();
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
