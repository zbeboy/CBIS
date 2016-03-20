package com.school.cbis.web.major;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.plugin.jsgrid.JsGrid;
import com.school.cbis.service.*;
import com.school.cbis.vo.article.ArticleVo;
import com.school.cbis.vo.major.*;
import com.school.cbis.vo.tie.TieElegantVo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.jooq.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by lenovo on 2016-02-07.
 */
@Controller
public class MajorManagerController {

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

    /**
     * 专业数据
     * @param majorVo
     * @return
     */
    @RequestMapping("/maintainer/majorData")
    @ResponseBody
    public Map<String, Object> majorData(MajorVo majorVo) {
        JsGrid<MajorVo> jsGrid = new JsGrid<>(new HashMap<>());
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorVo> majorVos = new ArrayList<>();
        if (tieId > 0) {
            Result<Record2<Integer, String>> record2s = majorService.findByTieIdByPage(majorVo, tieId);
            if (record2s.isNotEmpty()) {
                majorVos = record2s.into(MajorVo.class);
                jsGrid.loadData(majorVos, majorService.findByTieIdCount(majorVo, tieId));
            } else {
                jsGrid.loadData(majorVos, 0);
            }
        } else {
            jsGrid.loadData(majorVos, 0);
        }
        return jsGrid.getMap();
    }

    /**
     * 保存专业
     * @param majorVo
     * @return
     */
    @RequestMapping(value = "/maintainer/saveMajor", method = RequestMethod.POST)
    @ResponseBody
    public MajorVo saveMajor(MajorVo majorVo) {
        JsGrid<MajorVo> jsGrid = new JsGrid<>();
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        if (tieId > 0) {
            if (StringUtils.hasLength(majorVo.getMajorName())) {
                List<Major> majors = majorService.findByMajorName(majorVo.getMajorName());
                if (majors.isEmpty()) {
                    Major major = new Major();
                    major.setMajorName(majorVo.getMajorName());
                    major.setTieId(tieId);
                    majorService.saveMajor(major);
                    return jsGrid.insertItem(majorVo);
                }
            }
        }
        return null;
    }

    /**
     * 更新专业
     * @param majorVo
     * @return
     */
    @RequestMapping(value = "/maintainer/updateMajor", method = RequestMethod.POST)
    @ResponseBody
    public MajorVo updateMajor(MajorVo majorVo) {
        JsGrid<MajorVo> jsGrid = new JsGrid<>();
        Major major = majorService.findById(majorVo.getId());
        if (StringUtils.hasLength(majorVo.getMajorName())) {
            List<Major> majors = majorService.findByMajorName(majorVo.getMajorName());
            if (majors.isEmpty()) {
                major.setMajorName(majorVo.getMajorName());
                majorService.update(major);
                return jsGrid.updateItem(majorVo);
            }
        }
        return null;
    }

    /**
     * 删除专业
     * @param majorVo
     * @return
     */
    @RequestMapping(value = "/maintainer/deleteMajor", method = RequestMethod.POST)
    @ResponseBody
    public MajorVo deleteMajor(MajorVo majorVo) {
        JsGrid<MajorVo> jsGrid = new JsGrid<>();
        majorService.deleteById(majorVo.getId());
        return jsGrid.deleteItem(majorVo);
    }

    /**
     * 专业简介界面
     *
     * @return
     */
    @RequestMapping("/maintainer/majorIntroduce")
    public String majorIntroduce(ModelMap modelMap) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorListVo> majorListVos = new ArrayList<>();
        MajorListVo majorListVo = new MajorListVo();
        majorListVo.setId(0);
        majorListVo.setMajorName("");
        majorListVos.add(majorListVo);
        Result<Record2<Integer, String>> record2s = majorService.findByTieIdToList(tieId);
        if (record2s.isNotEmpty()) {
            List<MajorListVo> majorListVoList = record2s.into(MajorListVo.class);
            majorListVos.addAll(majorListVoList);
        }
        modelMap.addAttribute("majors", majorListVos);
        return "/maintainer/majorintroducelist";
    }

    /**
     * 系简介数据
     *
     * @param majorIntroduceVo
     * @return
     */
    @RequestMapping("/maintainer/majorIntroduceData")
    @ResponseBody
    public Map<String, Object> majorIntroduceData(MajorIntroduceVo majorIntroduceVo) {
        JsGrid<MajorIntroduceVo> jsGrid = new JsGrid<>(new HashMap<>());
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorIntroduceVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record4<Integer, String, String, Timestamp>> record4s = majorService.findAllWithIntroduceByPage(majorIntroduceVo, tieId);
            if (record4s.isNotEmpty()) {
                list = record4s.into(MajorIntroduceVo.class);
                jsGrid.loadData(list,majorService.findAllWithIntroduceByPageCount(majorIntroduceVo, tieId));
            } else {
                jsGrid.loadData(list, 0);
            }
        } else {
            jsGrid.loadData(list, 0);
        }
        return jsGrid.getMap();
    }

    /**
     * 专业简介更新页面
     *
     * @param majorId
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/majorIntroduceUpdate")
    public String majorIntroduceUpdate(@RequestParam("majorId") int majorId, ModelMap modelMap) {
        Major major = majorService.findById(majorId);
        ArticleInfo articleInfo = new ArticleInfo();
        List<ArticleSub> articleSubs = new ArrayList<>();
        if (!StringUtils.isEmpty(major) && !StringUtils.isEmpty(major.getMajorIntroduceArticleInfoId())) {
            articleInfo = articleInfoService.findById(major.getMajorIntroduceArticleInfoId());
            modelMap.addAttribute("articleinfo", articleInfo);
            articleSubs = articleSubService.findByArticleInfoId(major.getMajorIntroduceArticleInfoId());
            modelMap.addAttribute("articlesubinfo", articleSubs);
        } else {
            modelMap.addAttribute("articleinfo", articleInfo);
            modelMap.addAttribute("articlesubinfo", articleSubs);
        }
        modelMap.addAttribute("majorId", majorId);
        return "/maintainer/majorintroduceupdate";
    }

    /**
     * 专业文章显示
     *
     * @param modelMap
     * @param majorId  专业id
     * @param id       文章id
     * @return
     */
    @RequestMapping("/user/majorArticleShow")
    public String majorArticleShow(ModelMap modelMap, @RequestParam("majorId") int majorId, @RequestParam("id") int id) {
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
            }

            modelMap.addAttribute("majorintroduceid", major.getMajorIntroduceArticleInfoId());
            modelMap.addAttribute("majortraingoalid", major.getMajorTrainingGoalArticleInfoId());
            modelMap.addAttribute("majortraitid", major.getMajorTraitArticleInfoId());
            modelMap.addAttribute("majorheadid", major.getMajorForegoerArticleInfoId());
            modelMap.addAttribute("currentId", id);
            modelMap.addAttribute("currentMajorId", majorId);
            modelMap.addAttribute("currentMajorName", major.getMajorName());
        }

        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }

        List<MajorListVo> majorListVos = new ArrayList<>();
        Result<Record2<Integer, String>> record2s = majorService.findByTieIdToList(tieId);
        if (record2s.isNotEmpty()) {
            majorListVos = record2s.into(MajorListVo.class);
        }

        modelMap.addAttribute("majorList", majorListVos);

        return "/user/majorarticleshow";
    }

    /**
     * 专业数据
     *
     * @param modelMap
     * @param majorId  专业id
     * @param navId    导航id
     * @return
     */
    @RequestMapping("/user/articleMajorData")
    public String articleMajorData(ModelMap modelMap, @RequestParam("majorId") int majorId, String navId) {
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
            }

            modelMap.addAttribute("navId", navId);
            modelMap.addAttribute("majorintroduceid", major.getMajorIntroduceArticleInfoId());
            modelMap.addAttribute("majortraingoalid", major.getMajorTrainingGoalArticleInfoId());
            modelMap.addAttribute("majortraitid", major.getMajorTraitArticleInfoId());
            modelMap.addAttribute("majorheadid", major.getMajorForegoerArticleInfoId());
            modelMap.addAttribute("currentId", id);
            modelMap.addAttribute("currentMajorId", majorId);
            modelMap.addAttribute("currentMajorName", major.getMajorName());
        }

        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }

        List<MajorListVo> majorListVos = new ArrayList<>();
        Result<Record2<Integer, String>> record2s = majorService.findByTieIdToList(tieId);
        if (record2s.isNotEmpty()) {
            majorListVos = record2s.into(MajorListVo.class);
        }

        modelMap.addAttribute("majorList", majorListVos);

        return "/user/majorarticleshow";
    }

    /**
     * 专业文章数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/user/majorArticleShowData")
    @ResponseBody
    public Map<String, Object> majorArticleShowData(@RequestParam("id") int id) {
        Map<String, Object> map = new HashMap<>();
        Result<Record7<Integer, String, String, Integer, Timestamp, String, String>> record7s = articleInfoService.findByIdWithUsers(id);
        if (record7s.isNotEmpty()) {
            List<ArticleVo> articleVos = record7s.into(ArticleVo.class);
            if (articleVos.get(0).getUserTypeId() == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {
                List<Teacher> teachers = teacherService.findByTeacherJobNumber(articleVos.get(0).getUsername());
                if (!teachers.isEmpty()) {
                    articleVos.get(0).setUserRealName(teachers.get(0).getTeacherName());
                }
            } else if (articleVos.get(0).getUserTypeId() == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT)) {
                List<Student> students = studentService.findByStudentNumber(articleVos.get(0).getUsername());
                if (!students.isEmpty()) {
                    articleVos.get(0).setUserRealName(students.get(0).getStudentName());
                }
            }

            List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(articleVos.get(0).getId());

            if (StringUtils.hasLength(articleVos.get(0).getArticlePhotoUrl())) {
                String[] paths = articleVos.get(0).getArticlePhotoUrl().split("/");
                String photo = "/" + paths[paths.length - 3] + "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
                articleVos.get(0).setArticlePhotoUrl(photo);
            }
            map.put("articleInfo", articleVos.get(0));
            map.put("articleSub", articleSubs);
        }
        return map;
    }

    /**
     * 专业带头人界面
     *
     * @return
     */
    @RequestMapping("/maintainer/majorHead")
    public String majorHead(ModelMap modelMap) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorListVo> majorListVos = new ArrayList<>();
        MajorListVo majorListVo = new MajorListVo();
        majorListVo.setId(0);
        majorListVo.setMajorName("");
        majorListVos.add(majorListVo);
        Result<Record2<Integer, String>> record2s = majorService.findByTieIdToList(tieId);
        if (record2s.isNotEmpty()) {
            List<MajorListVo> majorListVoList = record2s.into(MajorListVo.class);
            majorListVos.addAll(majorListVoList);
        }
        modelMap.addAttribute("majors", majorListVos);
        return "/maintainer/majorheadlist";
    }

    /**
     * 专业带头人数据
     *
     * @param majorHeadVo
     * @return
     */
    @RequestMapping("/maintainer/majorHeadData")
    @ResponseBody
    public Map<String, Object> majorHeadData(MajorHeadVo majorHeadVo) {
        JsGrid<MajorHeadVo> jsGrid = new JsGrid<>(new HashMap<>());
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorHeadVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record4<Integer, String, String, Timestamp>> record4s = majorService.findAllWithHeadByPage(majorHeadVo, tieId);
            if (record4s.isNotEmpty()) {
                list = record4s.into(MajorHeadVo.class);
                jsGrid.loadData(list,majorService.findAllWithHeadByPageCount(majorHeadVo, tieId));
            } else {
                jsGrid.loadData(list, 0);
            }
        } else {
            jsGrid.loadData(list, 0);
        }
        return jsGrid.getMap();
    }

    /**
     * 专业带头人更新页面
     *
     * @param majorId
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/majorHeadUpdate")
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
        return "/maintainer/majorheadupdate";
    }

    /**
     * 专业培养目标界面
     *
     * @return
     */
    @RequestMapping("/maintainer/majorTrainingGoal")
    public String majorTrainingGoal(ModelMap modelMap) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorListVo> majorListVos = new ArrayList<>();
        MajorListVo majorListVo = new MajorListVo();
        majorListVo.setId(0);
        majorListVo.setMajorName("");
        majorListVos.add(majorListVo);
        Result<Record2<Integer, String>> record2s = majorService.findByTieIdToList(tieId);
        if (record2s.isNotEmpty()) {
            List<MajorListVo> majorListVoList = record2s.into(MajorListVo.class);
            majorListVos.addAll(majorListVoList);
        }
        modelMap.addAttribute("majors", majorListVos);
        return "/maintainer/majortraininggoallist";
    }

    /**
     * 专业培养目标数据
     * @param majorTrainingGoalVo
     * @return
     */
    @RequestMapping("/maintainer/majorTrainingGoalData")
    @ResponseBody
    public Map<String, Object> majorTrainingGoalData(MajorTrainingGoalVo majorTrainingGoalVo) {
        JsGrid<MajorTrainingGoalVo> jsGrid = new JsGrid<>(new HashMap<>());
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorTrainingGoalVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record4<Integer, String, String, Timestamp>> record4s = majorService.findAllWithTrainingGoalByPage(majorTrainingGoalVo, tieId);
            if (record4s.isNotEmpty()) {
                list = record4s.into(MajorTrainingGoalVo.class);
                jsGrid.loadData(list,majorService.findAllWithTrainingGoalByPageCount(majorTrainingGoalVo, tieId));
            } else {
                jsGrid.loadData(list, 0);
            }
        } else {
            jsGrid.loadData(list, 0);
        }
        return jsGrid.getMap();
    }

    /**
     * 专业培养目标更新页面
     *
     * @param majorId
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/majorTrainingGoalUpdate")
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
        return "/maintainer/majortraininggoalupdate";
    }

    /**
     * 专业特色界面
     *
     * @return
     */
    @RequestMapping("/maintainer/majorTrait")
    public String majorTrait(ModelMap modelMap) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorListVo> majorListVos = new ArrayList<>();
        MajorListVo majorListVo = new MajorListVo();
        majorListVo.setId(0);
        majorListVo.setMajorName("");
        majorListVos.add(majorListVo);
        Result<Record2<Integer, String>> record2s = majorService.findByTieIdToList(tieId);
        if (record2s.isNotEmpty()) {
            List<MajorListVo> majorListVoList = record2s.into(MajorListVo.class);
            majorListVos.addAll(majorListVoList);
        }
        modelMap.addAttribute("majors", majorListVos);
        return "/maintainer/majortraitlist";
    }

    /**
     * 专业特色数据
     * @param majorTraitVo
     * @return
     */
    @RequestMapping("/maintainer/majorTraitData")
    @ResponseBody
    public Map<String, Object> majorTraitData(MajorTraitVo majorTraitVo) {
        JsGrid<MajorTraitVo> jsGrid = new JsGrid<>(new HashMap<>());
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorTraitVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record4<Integer, String, String, Timestamp>> record4s = majorService.findAllWithTraitByPage(majorTraitVo, tieId);
            if (record4s.isNotEmpty()) {
                list = record4s.into(MajorTraitVo.class);
                jsGrid.loadData(list,majorService.findAllWithTraitByPageCount(majorTraitVo, tieId));
            } else {
                jsGrid.loadData(list, 0);
            }
        } else {
            jsGrid.loadData(list, 0);
        }
        return jsGrid.getMap();
    }

    /**
     * 专业特色更新页面
     *
     * @param majorId
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/majorTraitUpdate")
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
        return "/maintainer/majortraitupdate";
    }

}
