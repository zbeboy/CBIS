package com.school.cbis.web.major;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.MajorRecord;
import com.school.cbis.plugin.jsgrid.JsGrid;
import com.school.cbis.service.*;
import com.school.cbis.vo.article.ArticleVo;
import com.school.cbis.vo.major.*;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-02-07.
 */
@Controller
public class MajorManagerController {

    private final Logger log = LoggerFactory.getLogger(MajorManagerController.class);

    @Resource
    private UsersService usersService;

    @Resource
    private MajorService majorService;

    @Resource
    private ArticleInfoService articleInfoService;

    @Resource
    private ArticleSubService articleSubService;

    @Resource
    private Wordbook wordbook;

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    @Resource
    private GradeService gradeService;

    /**
     * 专业管理界面
     *
     * @return
     */
    @RequestMapping("/maintainer/major/majorManager")
    public String majorManager(MajorVo majorVo, ModelMap modelMap) {
        modelMap.addAttribute("majorVo", majorVo);
        return "/maintainer/major/majorlist";
    }

    /**
     * 专业数据
     *
     * @param majorVo
     * @return
     */
    @RequestMapping("/maintainer/major/majorData")
    @ResponseBody
    public AjaxData<MajorVo> majorData(MajorVo majorVo) {
        AjaxData<MajorVo> ajaxData = new AjaxData<>();
        //通过用户类型获取系表ID
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<MajorVo> majorVos = new ArrayList<>();
        if (tieId > 0) {
            Result<Record2<Integer, String>> record2s = majorService.findByTieIdByPage(majorVo, tieId);
            if (record2s.isNotEmpty()) {
                majorVos = record2s.into(MajorVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(majorVo.getPageNum());
                paginationData.setPageSize(majorVo.getPageSize());
                paginationData.setTotalDatas(majorService.findByTieIdCount(majorVo, tieId));
                ajaxData.success().listData(majorVos).paginationData(paginationData);
            } else {
                ajaxData.fail().listData(majorVos);
            }
        } else {
            ajaxData.fail().listData(majorVos);
        }
        return ajaxData;
    }

    /**
     * 检验添加专业时 专业名
     * @param majorName
     * @return
     */
    @RequestMapping("/maintainer/major/validAddMajorName")
    @ResponseBody
    public Map<String, Object> validMajorName(@RequestParam("majorName") String majorName) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(majorName)) {
            //通过用户类型获取系表ID
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            if (tieId > 0) {
                MajorRecord majorRecord = majorService.findByMajorNameAndTieId(StringUtils.trimWhitespace(majorName), tieId);
                if (ObjectUtils.isEmpty(majorRecord)) {
                    map.put("ok", "");
                } else {
                    map.put("error", "该专业已存在!");
                }
            } else {
                map.put("error", "获取当前用户信息失败!");
            }
        } else {
            map.put("error", "参数异常!");
        }
        return map;
    }

    /**
     * 检验更新时 专业名
     * @param id
     * @param majorName
     * @return
     */
    @RequestMapping("/maintainer/major/validUpdateMajorName")
    @ResponseBody
    public Map<String,Object> validUpdateMajorName(@RequestParam("id") int id,@RequestParam("majorName") String majorName){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(majorName)) {
            //通过用户类型获取系表ID
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            if (tieId > 0) {
                MajorRecord majorRecord = majorService.findByMajorNameAndIdAndTieId(StringUtils.trimWhitespace(majorName), tieId,id);
                if (ObjectUtils.isEmpty(majorRecord)) {
                    map.put("ok", "");
                } else {
                    map.put("error", "该专业已存在!");
                }
            } else {
                map.put("error", "获取当前用户信息失败!");
            }
        } else {
            map.put("error", "参数异常!");
        }
        return map;
    }

    /**
     * 保存专业
     *
     * @param majorVo
     * @return
     */
    @RequestMapping(value = "/maintainer/major/saveMajor", method = RequestMethod.POST)
    public String saveMajor(MajorVo majorVo) {
        if (StringUtils.hasLength(majorVo.getMajorName())) {
            //通过用户类型获取系表ID
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            Major major = new Major();
            major.setMajorName(majorVo.getMajorName());
            major.setTieId(tieId);
            majorService.saveMajor(major);
        }
        return "redirect:/maintainer/major/majorManager";
    }

    /**
     * 更新专业
     *
     * @param majorVo
     * @return
     */
    @RequestMapping(value = "/maintainer/major/updateMajor", method = RequestMethod.POST)
    public String updateMajor(MajorVo majorVo) {
        Major major = majorService.findById(majorVo.getId());
        if (!ObjectUtils.isEmpty(major)&&StringUtils.hasLength(majorVo.getMajorName())) {
            major.setMajorName(majorVo.getMajorName());
            majorService.update(major);
        }
        return "redirect:/maintainer/major/majorManager";
    }

    /**
     * 删除专业
     *
     * @param majorVo
     * @return
     */
    @RequestMapping(value = "/maintainer/major/deleteMajor", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData deleteMajor(MajorVo majorVo) {
        AjaxData ajaxData = new AjaxData();
        List<Grade> grades = gradeService.findByMajorId(majorVo.getId());
        if (grades.isEmpty()) {
            majorService.deleteById(majorVo.getId());
            ajaxData.success().msg("删除专业成功!");
        } else {
            ajaxData.fail().msg("不能删除该专业,该专业已有对应的班级!");
        }
        return ajaxData;
    }

    /**
     * 专业简介界面
     *
     * @return
     */
    @RequestMapping("/maintainer/major/majorIntroduce")
    public String majorIntroduce(MajorIntroduceVo majorIntroduceVo,ModelMap modelMap) {
        modelMap.addAttribute("majorIntroduceVo",majorIntroduceVo);
        return "/maintainer/major/majorintroducelist";
    }

    /**
     * 简介数据
     *
     * @param majorIntroduceVo
     * @return
     */
    @RequestMapping("/maintainer/major/majorIntroduceData")
    @ResponseBody
    public AjaxData<MajorIntroduceVo> majorIntroduceData(MajorIntroduceVo majorIntroduceVo) {
        AjaxData<MajorIntroduceVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<MajorIntroduceVo> list = new ArrayList<>();
        if (tieId > 0) {
            Result<Record6<Integer,String, String, String, Timestamp, Byte>> record6s = majorService.findAllWithIntroduceByPage(majorIntroduceVo, tieId);
            if (record6s.isNotEmpty()) {
                list = record6s.into(MajorIntroduceVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(majorIntroduceVo.getPageNum());
                paginationData.setPageSize(majorIntroduceVo.getPageSize());
                paginationData.setTotalDatas(majorService.findAllWithIntroduceByPageCount(majorIntroduceVo, tieId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.success().listData(list);
            }
        } else {
            ajaxData.success().listData(list);
        }
        return ajaxData;
    }

    /**
     * 专业显示在首页
     * @param majorIntroduceVo
     * @return
     */
    @RequestMapping("/maintainer/major/updateMajorIntroduceShow")
    @ResponseBody
    public AjaxData updateMajorIntroduceShow(MajorIntroduceVo majorIntroduceVo){
        AjaxData ajaxData = new AjaxData<>();
        if(majorIntroduceVo.getId()>0){
            Major major = majorService.findById(majorIntroduceVo.getId());
            major.setIsShow(majorIntroduceVo.getIsShow());
            majorService.update(major);
            ajaxData.success().msg("更改显示成功!");
        } else {
            ajaxData.fail().msg("参数异常,更改显示失败!");
        }
        return ajaxData;
    }

    /**
     * 专业简介更新页面
     *
     * @param majorId
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/major/majorIntroduceUpdate")
    public String majorIntroduceUpdate(@RequestParam("majorId") int majorId, ModelMap modelMap) {
        Major major = majorService.findById(majorId);
        ArticleInfo articleInfo = new ArticleInfo();
        List<ArticleSub> articleSubs = new ArrayList<>();
        if (!StringUtils.isEmpty(major) && !StringUtils.isEmpty(major.getMajorIntroduceArticleInfoId())) {
            articleInfo = articleInfoService.findById(major.getMajorIntroduceArticleInfoId());
            modelMap.addAttribute("articleinfo", articleInfo);
            articleSubs = articleSubService.findByArticleInfoId(major.getMajorIntroduceArticleInfoId());
            modelMap.addAttribute("articlesubinfo", articleSubs);
            modelMap.addAttribute("major", major);
            modelMap.addAttribute("isShow", major.getIsShow() == 0 ? false : true);
        } else {
            modelMap.addAttribute("articleinfo", articleInfo);
            modelMap.addAttribute("articlesubinfo", articleSubs);
            modelMap.addAttribute("major", major);
            modelMap.addAttribute("isShow", false);
        }
        return "/maintainer/major/majorintroduceupdate";
    }

    /**
     * 专业文章显示
     *
     * @param modelMap
     * @param majorId  专业id
     * @param id       文章id
     * @return
     */
    @RequestMapping("/user/major/majorArticleShow")
    public String majorArticleShow(ModelMap modelMap, @RequestParam("majorId") int majorId, @RequestParam("id") int id) {
        /**
         * id = -2 专业教师
         */
        Major major = majorService.findById(majorId);
        if (!StringUtils.isEmpty(major)) {
            if (!StringUtils.isEmpty(major.getMajorIntroduceArticleInfoId()) && major.getMajorIntroduceArticleInfoId() == id) {//简介
                modelMap.addAttribute("navId", "navmajorintroduce");
            } else if (!StringUtils.isEmpty(major.getMajorTrainingGoalArticleInfoId()) && major.getMajorTrainingGoalArticleInfoId() == id) {//培养目标
                modelMap.addAttribute("navId", "navmajortraingoal");
            } else if (!StringUtils.isEmpty(major.getMajorTraitArticleInfoId()) && major.getMajorTraitArticleInfoId() == id) {//特色
                modelMap.addAttribute("navId", "navmajortrait");
            } else if (!StringUtils.isEmpty(major.getMajorForegoerArticleInfoId()) && major.getMajorForegoerArticleInfoId() == id) {//带头人
                modelMap.addAttribute("navId", "navmajorhead");
            } else if(id == -2){
                modelMap.addAttribute("navId", "navmajorteacher");
            }

            modelMap.addAttribute("majorintroduceid", major.getMajorIntroduceArticleInfoId());
            modelMap.addAttribute("majortraingoalid", major.getMajorTrainingGoalArticleInfoId());
            modelMap.addAttribute("majortraitid", major.getMajorTraitArticleInfoId());
            modelMap.addAttribute("majorheadid", major.getMajorForegoerArticleInfoId());
            modelMap.addAttribute("majorteacher", -2);
            modelMap.addAttribute("currentId", id);
            modelMap.addAttribute("currentMajorId", majorId);
            modelMap.addAttribute("currentMajorName", major.getMajorName());
        }

        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }

        List<MajorListVo> majorListVos = new ArrayList<>();
        Result<Record2<Integer, String>> record2s = majorService.findByTieIdToList(tieId);
        if (record2s.isNotEmpty()) {
            majorListVos = record2s.into(MajorListVo.class);
        }

        modelMap.addAttribute("majorList", majorListVos);

        return "/user/major/majorarticleshow";
    }

    /**
     * 专业数据
     *
     * @param modelMap
     * @param majorId  专业id
     * @param navId    导航id
     * @return
     */
    @RequestMapping("/user/major/articleMajorData")
    public String articleMajorData(ModelMap modelMap, @RequestParam("majorId") int majorId, String navId) {
        /**
         * id = -2 专业教师
         */
        Major major = majorService.findById(majorId);
        if (!StringUtils.isEmpty(major)) {
            int id = 0;
            if (navId.equals("navmajorintroduce") && !StringUtils.isEmpty(major.getMajorIntroduceArticleInfoId())) {//简介
                id = major.getMajorIntroduceArticleInfoId();
            } else if (navId.equals("navmajortraingoal") && !StringUtils.isEmpty(major.getMajorTrainingGoalArticleInfoId())) {//培养目标
                id = major.getMajorTrainingGoalArticleInfoId();
            } else if (navId.equals("navmajortrait") && !StringUtils.isEmpty(major.getMajorTraitArticleInfoId())) {//特色
                id = major.getMajorTraitArticleInfoId();
            } else if (navId.equals("navmajorhead") && !StringUtils.isEmpty(major.getMajorForegoerArticleInfoId())) {//带头人
                id = major.getMajorForegoerArticleInfoId();
            } else if(navId.equals("navmajorteacher")){
                id = -2;
            }

            modelMap.addAttribute("navId", navId);
            modelMap.addAttribute("majorintroduceid", major.getMajorIntroduceArticleInfoId());
            modelMap.addAttribute("majortraingoalid", major.getMajorTrainingGoalArticleInfoId());
            modelMap.addAttribute("majortraitid", major.getMajorTraitArticleInfoId());
            modelMap.addAttribute("majorheadid", major.getMajorForegoerArticleInfoId());
            modelMap.addAttribute("majorteacher", -2);
            modelMap.addAttribute("currentId", id);
            modelMap.addAttribute("currentMajorId", majorId);
            modelMap.addAttribute("currentMajorName", major.getMajorName());
        }

        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }

        List<MajorListVo> majorListVos = new ArrayList<>();
        Result<Record2<Integer, String>> record2s = majorService.findByTieIdToList(tieId);
        if (record2s.isNotEmpty()) {
            majorListVos = record2s.into(MajorListVo.class);
        }

        modelMap.addAttribute("majorList", majorListVos);

        return "/user/major/majorarticleshow";
    }

    /**
     * 专业文章数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/user/major/majorArticleShowData")
    @ResponseBody
    public Map<String, Object> majorArticleShowData(@RequestParam("id") int id) {
        Map<String, Object> map = new HashMap<>();
        Result<Record8<Integer, String, String, Integer, Timestamp, String, String, String>> record8s = articleInfoService.findByIdWithUsers(id);
        if (record8s.isNotEmpty()) {
            List<ArticleVo> articleVos = record8s.into(ArticleVo.class);
            List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(articleVos.get(0).getId());
            map.put("articleInfo", articleVos.get(0));
            map.put("articleSub", articleSubs);
        }
        return map;
    }

    /**
     * 专业文章老师数据
     *
     * @param majorId
     * @return
     */
    @RequestMapping("/user/major/majorArticleShowTeacherData")
    @ResponseBody
    public AjaxData<MajorTeacherVo> majorArticleShowTeacherData(@RequestParam("majorId") int majorId) {
        Result<Record3<String, String, String>> record3s = majorService.findByIdWithTeacher(majorId);
        if(record3s.isNotEmpty()){
            List<MajorTeacherVo> majorTeacherVos = record3s.into(MajorTeacherVo.class);
            return new AjaxData().success().listData(majorTeacherVos);
        } else {
            return new AjaxData().fail().msg("无数据!");
        }
    }

    /**
     * 专业带头人界面
     *
     * @return
     */
    @RequestMapping("/maintainer/major/majorHead")
    public String majorHead(ModelMap modelMap,MajorHeadVo majorHeadVo) {
        modelMap.addAttribute("majorHeadVo",majorHeadVo);
        return "/maintainer/major/majorheadlist";
    }

    /**
     * 专业带头人数据
     *
     * @param majorHeadVo
     * @return
     */
    @RequestMapping("/maintainer/major/majorHeadData")
    @ResponseBody
    public AjaxData<MajorHeadVo> majorHeadData(MajorHeadVo majorHeadVo) {
        AjaxData<MajorHeadVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<MajorHeadVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record5<Integer,String, String, String, Timestamp>> record5s = majorService.findAllWithHeadByPage(majorHeadVo, tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(MajorHeadVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(majorHeadVo.getPageNum());
                paginationData.setPageSize(majorHeadVo.getPageSize());
                paginationData.setTotalDatas(majorService.findAllWithHeadByPageCount(majorHeadVo, tieId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.success().listData(list);
            }
        } else {
            ajaxData.success().listData(list);
        }
        return ajaxData;
    }

    /**
     * 专业带头人更新页面
     *
     * @param majorId
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/major/majorHeadUpdate")
    public String majorHeadUpdate(@RequestParam("majorId") int majorId, ModelMap modelMap) {
        Major major = majorService.findById(majorId);
        ArticleInfo articleInfo = new ArticleInfo();
        List<ArticleSub> articleSubs = new ArrayList<>();
        if (!StringUtils.isEmpty(major) && !StringUtils.isEmpty(major.getMajorForegoerArticleInfoId())) {
            articleInfo = articleInfoService.findById(major.getMajorForegoerArticleInfoId());
            modelMap.addAttribute("articleinfo", articleInfo);
            articleSubs = articleSubService.findByArticleInfoId(major.getMajorForegoerArticleInfoId());
            modelMap.addAttribute("articlesubinfo", articleSubs);
        } else {
            modelMap.addAttribute("articleinfo", articleInfo);
            modelMap.addAttribute("articlesubinfo", articleSubs);
        }
        modelMap.addAttribute("majorId", majorId);
        return "/maintainer/major/majorheadupdate";
    }

    /**
     * 专业培养目标界面
     *
     * @return
     */
    @RequestMapping("/maintainer/major/majorTrainingGoal")
    public String majorTrainingGoal(ModelMap modelMap,MajorTrainingGoalVo majorTrainingGoalVo) {
        modelMap.addAttribute("majorTrainingGoalVo",majorTrainingGoalVo);
        return "/maintainer/major/majortraininggoallist";
    }

    /**
     * 专业培养目标数据
     *
     * @param majorTrainingGoalVo
     * @return
     */
    @RequestMapping("/maintainer/major/majorTrainingGoalData")
    @ResponseBody
    public AjaxData<MajorTrainingGoalVo> majorTrainingGoalData(MajorTrainingGoalVo majorTrainingGoalVo) {
        AjaxData<MajorTrainingGoalVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<MajorTrainingGoalVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record5<Integer,String, String, String, Timestamp>> record5s = majorService.findAllWithTrainingGoalByPage(majorTrainingGoalVo, tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(MajorTrainingGoalVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(majorTrainingGoalVo.getPageNum());
                paginationData.setPageSize(majorTrainingGoalVo.getPageSize());
                paginationData.setTotalDatas(majorService.findAllWithTrainingGoalByPageCount(majorTrainingGoalVo, tieId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.success().listData(list);
            }
        } else {
            ajaxData.success().listData(list);
        }
        return ajaxData;
    }

    /**
     * 专业培养目标更新页面
     *
     * @param majorId
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/major/majorTrainingGoalUpdate")
    public String majorTrainingGoalUpdate(@RequestParam("majorId") int majorId, ModelMap modelMap) {
        Major major = majorService.findById(majorId);
        ArticleInfo articleInfo = new ArticleInfo();
        List<ArticleSub> articleSubs = new ArrayList<>();
        if (!StringUtils.isEmpty(major) && !StringUtils.isEmpty(major.getMajorTrainingGoalArticleInfoId())) {
            articleInfo = articleInfoService.findById(major.getMajorTrainingGoalArticleInfoId());
            modelMap.addAttribute("articleinfo", articleInfo);
            articleSubs = articleSubService.findByArticleInfoId(major.getMajorTrainingGoalArticleInfoId());
            modelMap.addAttribute("articlesubinfo", articleSubs);
        } else {
            modelMap.addAttribute("articleinfo", articleInfo);
            modelMap.addAttribute("articlesubinfo", articleSubs);
        }
        modelMap.addAttribute("majorId", majorId);
        return "/maintainer/major/majortraininggoalupdate";
    }

    /**
     * 专业特色界面
     *
     * @return
     */
    @RequestMapping("/maintainer/major/majorTrait")
    public String majorTrait(ModelMap modelMap,MajorTraitVo majorTraitVo) {
        modelMap.addAttribute("majorTraitVo",majorTraitVo);
        return "/maintainer/major/majortraitlist";
    }

    /**
     * 专业特色数据
     *
     * @param majorTraitVo
     * @return
     */
    @RequestMapping("/maintainer/major/majorTraitData")
    @ResponseBody
    public AjaxData<MajorTraitVo> majorTraitData(MajorTraitVo majorTraitVo) {
        AjaxData<MajorTraitVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<MajorTraitVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record5<Integer,String, String, String, Timestamp>> record5s = majorService.findAllWithTraitByPage(majorTraitVo, tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(MajorTraitVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(majorTraitVo.getPageNum());
                paginationData.setPageSize(majorTraitVo.getPageSize());
                paginationData.setTotalDatas(majorService.findAllWithTraitByPageCount(majorTraitVo, tieId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.success().listData(list);
            }
        } else {
            ajaxData.success().listData(list);
        }
        return ajaxData;
    }

    /**
     * 专业特色更新页面
     *
     * @param majorId
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/major/majorTraitUpdate")
    public String majorTraitUpdate(@RequestParam("majorId") int majorId, ModelMap modelMap) {
        Major major = majorService.findById(majorId);
        ArticleInfo articleInfo = new ArticleInfo();
        List<ArticleSub> articleSubs = new ArrayList<>();
        if (!StringUtils.isEmpty(major) && !StringUtils.isEmpty(major.getMajorTraitArticleInfoId())) {
            articleInfo = articleInfoService.findById(major.getMajorTraitArticleInfoId());
            modelMap.addAttribute("articleinfo", articleInfo);
            articleSubs = articleSubService.findByArticleInfoId(major.getMajorTraitArticleInfoId());
            modelMap.addAttribute("articlesubinfo", articleSubs);
        } else {
            modelMap.addAttribute("articleinfo", articleInfo);
            modelMap.addAttribute("articlesubinfo", articleSubs);
        }
        modelMap.addAttribute("majorId", majorId);
        return "/maintainer/major/majortraitupdate";
    }

    /**
     * 专业简介展示
     * @param username
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/major/teachersResumeShow")
    public String teachersResumeShow( @RequestParam("username") String username,@RequestParam("majorId") int majorId, ModelMap modelMap) {
        modelMap.addAttribute("username",username);
        modelMap.addAttribute("majorId",majorId);
        return "/user/major/teachersresumeshow";
    }

    /**
     * 专业简介展示数据
     * @param username
     * @return
     */
    @RequestMapping("/user/major/teachersResumeShowData")
    @ResponseBody
    public AjaxData teachersResumeShowData( @RequestParam("username") String username,@RequestParam("majorId") int majorId) {
        Map<String,Object> map = new HashMap<>();
        Record record = articleInfoService.findByUsername(username);
        List<ArticleSub> articleSubs = new ArrayList<>();
        if(!ObjectUtils.isEmpty(record)){
            Users users = record.into(Users.class);
            //为保证用户信息安全，以下信息清空
            users.setPasswordResetKey(null);
            users.setPassword(null);
            users.setEmailCheckKey(null);
            users.setMobileCheckKey(null);
            users.setBirthday(null);
            users.setIdentityCard(null);
            users.setPoliticalLandscape(null);
            users.setNation(null);
            users.setPost(null);
            users.setEmail(null);
            users.setMobile(null);
            users.setSex(null);
            users.setReligiousBelief(null);
            map.put("userInfo", users);
            ArticleInfo articleInfo = record.into(ArticleInfo.class);
            map.put("articleInfo", articleInfo);
            articleSubs = articleSubService.findByArticleInfoId(articleInfo.getId());
        } else {
            map.put("userInfo", new Users());
            map.put("articleInfo", new ArticleInfo());
        }
        map.put("articleSub", articleSubs);
        Result<Record3<String, String, String>> record3s = majorService.findByIdWithTeacher(majorId);
        List<MajorTeacherVo> majorTeacherVos = new ArrayList<>();
        if(record3s.isNotEmpty()){
            majorTeacherVos = record3s.into(MajorTeacherVo.class);
        }
        map.put("majorTeacherVos",majorTeacherVos);
        return new AjaxData().success().mapData(map);
    }

}
