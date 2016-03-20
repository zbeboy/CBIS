package com.school.cbis.web.tie;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.TieRecord;
import com.school.cbis.plugin.jsgrid.JsGrid;
import com.school.cbis.service.*;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.article.ArticleVo;
import com.school.cbis.vo.tie.*;
import org.jooq.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2016-02-03.
 */
@Controller
public class TieManagerController {

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
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    @Resource
    private TieElegantTimeService tieElegantTimeService;

    @Resource
    private TieNoticeService tieNoticeService;

    @Resource
    private TieNoticeTimeService tieNoticeTimeService;

    @Resource
    private TieNoticeAffixService tieNoticeAffixService;

    /**
     * 检验系名
     *
     * @param tieName
     * @return
     */
    @RequestMapping(value = "/maintainer/validTieName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validTieName(@RequestParam("tieId") int id,@RequestParam("tieName") String tieName) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(tieName)) {
            Result<TieRecord> records = tieService.findByTieName(id,tieName);

            if (records.isNotEmpty()) {
                map.put("error", "系名已存在!");
            } else {
                map.put("ok", "");
            }
        } else {
            map.put("error", "参数异常!");
        }
        return map;
    }

    /**
     * 更新系信息
     *
     * @param tieVo
     * @param result
     * @return
     */
    @RequestMapping(value = "/maintainer/updateTieInfo", method = RequestMethod.POST)
    public String updateTieInfo(@Valid TieVo tieVo, BindingResult result, ModelMap modelMap) {
        if (!result.hasErrors()) {
            Tie tie = tieService.findById(tieVo.getTieId());
            tie.setTieName(tieVo.getTieName());
            tie.setTieAddress(tieVo.getTieAddress());
            tie.setTiePhone(tieVo.getTiePhone());
            tie.setYardId(tieVo.getYard());
            tieService.update(tie);
        }
        return "redirect:/maintainer/tieManager";
    }

    /**
     * 系风采管理页面
     *
     * @return 页面地址
     */
    @RequestMapping(value = "/maintainer/tieElegant")
    public String tieElegant() {
        return "/maintainer/tieelegantlist";
    }

    /**
     * 加载系风采数据
     *
     * @param tieElegantVo
     * @return
     */
    @RequestMapping(value = "/maintainer/tieElegantData")
    @ResponseBody
    public Map<String, Object> tieElegantData(TieElegantVo tieElegantVo) {
        JsGrid<TieElegantVo> jsGrid = new JsGrid<>(new HashMap<>());
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<TieElegantVo> list = new ArrayList<>();
        if (tieId > 0) {
            Result<Record4<Integer, String, String, Timestamp>> record5s = tieElegantService.findByTieIdWithBigTitleAndPage(tieElegantVo, tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(TieElegantVo.class);
                jsGrid.loadData(list, tieElegantService.findByTieIdWithBigTitleAndCount(tieElegantVo, tieId));
            } else {
                jsGrid.loadData(list, 0);
            }
        } else {
            jsGrid.loadData(list, 0);
        }
        return jsGrid.getMap();
    }

    /**
     * 删除系风采
     *
     * @param id 文章id
     * @return
     */
    @RequestMapping(value = "/maintainer/deleteTieElegant", method = RequestMethod.POST)
    @ResponseBody
    public TieElegantVo deleteTieElegant(@RequestParam(value = "id") int id, String imgpath) {
        JsGrid<TieElegantVo> jsGrid = new JsGrid<>();
        try {
            ArticleInfo articleInfo = articleInfoService.findById(id);
            if (!StringUtils.isEmpty(articleInfo)) {
                tieElegantService.deleteById(id);
                articleSubService.deleteByArticleInfoId(id);
                articleInfoService.deleteById(id);
                FilesUtils.deleteFile(imgpath);
                TieElegantVo tieElegantVo = new TieElegantVo();
                tieElegantVo.setId(id);
                tieElegantVo.setBigTitle(articleInfo.getBigTitle());
                tieElegantVo.setUsername(usersService.getUserName());
                tieElegantVo.setDate(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(articleInfo.getDate().getTime())));
                return jsGrid.deleteItem(tieElegantVo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 系风采添加页面
     *
     * @return
     */
    @RequestMapping("/maintainer/tieElegantAdd")
    public String tieElegantAdd() {
        return "/maintainer/tieelegantadd";
    }


    /**
     * 跳转更新页面
     *
     * @param id       文章id
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/tieElegantUpdate")
    public String tieElegantUpdate(@RequestParam("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("articleinfo", articleInfoService.findById(id));
        List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(id);
        modelMap.addAttribute("articlesubinfo", articleSubs);
        return "/maintainer/tieelegantupdate";
    }

    /**
     * 系风采详细展示页
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/tieElegantShow")
    public String tieElegantShow(@RequestParam("id") int id, ModelMap modelMap) {
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

            Result<Record> records = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (records.isNotEmpty()) {
                for (Record r : records) {
                    tieId = r.getValue(Tables.TIE.ID);
                }
            }

            if (StringUtils.hasLength(articleVos.get(0).getArticlePhotoUrl())) {
                String[] paths = articleVos.get(0).getArticlePhotoUrl().split("/");
                String photo = "/" + paths[paths.length - 3] + "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
                articleVos.get(0).setArticlePhotoUrl(photo);
            }

            modelMap.addAttribute("articleInfo", articleVos.get(0));
            modelMap.addAttribute("articleSub", articleSubs);

            //左侧菜单
            Result<Record4<Integer, String, String, String>> record4s = tieElegantService.findByTieIdWithArticleOrderByDateDescAndPage(tieId, 0, 3);
            if (record4s.isNotEmpty()) {
                List<ArticleVo> menuList = record4s.into(ArticleVo.class);
                modelMap.addAttribute("menuList", menuList);
            } else {
                modelMap.addAttribute("menuList", null);
            }

        }
        return "/user/tieelegantshow";
    }

    /**
     * 系风采历史记录页面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/tieElegantTime")
    public String tieElegantTime(ModelMap modelMap, String bigTitle) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }

        Result<Record2<Integer, String>> record2s = tieElegantTimeService.findByBigTitleAndTieIdAndTimeDistinctId(
                bigTitle,
                tieId
        );

        List<TieElegantTime> tieElegantTimes = new ArrayList<>();

        if (record2s.isNotEmpty()) {

            tieElegantTimes = record2s.into(TieElegantTime.class);
        }

        modelMap.addAttribute("timeList", tieElegantTimes);

        //左侧菜单
        Result<Record4<Integer, String, String, String>> record4s = tieElegantService.findByTieIdWithArticleOrderByDateDescAndPage(tieId, 0, 3);
        if (record4s.isNotEmpty()) {
            List<ArticleVo> menuList = record4s.into(ArticleVo.class);
            modelMap.addAttribute("menuList", menuList);
        } else {
            modelMap.addAttribute("menuList", null);
        }

        modelMap.addAttribute("bigTitle", bigTitle);
        return "/user/tieeleganttimeshow";
    }

    /**
     * 对应时间下数据
     *
     * @param modelMap
     * @param id
     * @param bigTitle
     * @return
     */
    @RequestMapping("/user/tieElegantTimeDropData")
    @ResponseBody
    public AjaxData<TieElegantTimeVo> tieElegantTimeDropData(ModelMap modelMap, @RequestParam("id") int id, String bigTitle) {
        AjaxData<TieElegantTimeVo> ajaxData = new AjaxData();

        Result<Record3<Integer, String, Timestamp>> record3s =
                tieElegantService.findByTieElegantTimeIdOrBigTitleWithArticleOrderByDateDesc(id, bigTitle);
        List<TieElegantTimeVo> tieElegantTimeVos = record3s.into(TieElegantTimeVo.class);
        for (TieElegantTimeVo t : tieElegantTimeVos) {

            t.setDate(t.getDate().split(" ")[0]);

        }

        ajaxData.setState(true);
        ajaxData.setResult(tieElegantTimeVos);

        return ajaxData;
    }

    /**
     * 系文章管理页面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/tieIntroduceUpdate")
    public String tieIntroduceUpdate(ModelMap modelMap) {
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
            modelMap.addAttribute("articleinfo", articleInfoService.findById(articleInfoId));
            List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(articleInfoId);
            modelMap.addAttribute("articlesubinfo", articleSubs);
        } else {
            modelMap.addAttribute("articleinfo", new ArticleInfo());
            modelMap.addAttribute("articlesubinfo", null);
        }
        return "/maintainer/tieintroduceupdate";
    }

    /**
     * 系主任管理页面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tieHeadUpdate")
    public String tieHeadUpdate(ModelMap map) {
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
     * 系培养目标管理页面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tieTrainGoalUpdate")
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
     * 系特色管理页面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/tieItemUpdate")
    public String tieItemUpdate(ModelMap map) {
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

    /**
     * 系文章展示页面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/tieArticleShow")
    public String tieArticleShow(ModelMap modelMap, @RequestParam("id") int id) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        if (records.isNotEmpty()) {
            for (Record r : records) {
                if ( !StringUtils.isEmpty(r.getValue(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID)) && r.getValue(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID) == id) {
                    modelMap.addAttribute("navId", "navtieintroduce");
                } else if ( !StringUtils.isEmpty(r.getValue(Tables.TIE.TIE_PRINCIPAL_ARTICLE_INFO_ID)) && r.getValue(Tables.TIE.TIE_PRINCIPAL_ARTICLE_INFO_ID) == id) {
                    modelMap.addAttribute("navId", "navtiehead");
                } else if ( !StringUtils.isEmpty(r.getValue(Tables.TIE.TIE_TRAINING_GOAL_ARTICLE_INFO_ID)) && r.getValue(Tables.TIE.TIE_TRAINING_GOAL_ARTICLE_INFO_ID) == id) {
                    modelMap.addAttribute("navId", "navtiegoal");
                } else if ( !StringUtils.isEmpty(r.getValue(Tables.TIE.TIE_TRAIT_ARTICLE_INFO_ID)) && r.getValue(Tables.TIE.TIE_TRAIT_ARTICLE_INFO_ID) == id) {
                    modelMap.addAttribute("navId", "navtieitem");
                }

                modelMap.addAttribute("tieintroduceid", r.getValue(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID));
                modelMap.addAttribute("tieheadid", r.getValue(Tables.TIE.TIE_PRINCIPAL_ARTICLE_INFO_ID));
                modelMap.addAttribute("tiegoalid", r.getValue(Tables.TIE.TIE_TRAINING_GOAL_ARTICLE_INFO_ID));
                modelMap.addAttribute("tieitemid", r.getValue(Tables.TIE.TIE_TRAIT_ARTICLE_INFO_ID));
                modelMap.addAttribute("currentId", id);
            }
        }

        return "/user/tiearticleshow";
    }

    /**
     * 系风采文章数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/user/tieArticleShowData")
    @ResponseBody
    public Map<String, Object> tieArticleShowData(@RequestParam("id") int id) {
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
     * 系公告管理页面
     *
     * @return 页面地址
     */
    @RequestMapping(value = "/maintainer/tieNotice")
    public String tieNotice() {
        return "/maintainer/tienoticelist";
    }

    /**
     * 加载系公告数据
     *
     * @param tieNoticeVo
     * @return
     */
    @RequestMapping(value = "/maintainer/tieNoticeData")
    @ResponseBody
    public Map<String, Object> tieNoticeData(TieNoticeVo tieNoticeVo) {
        JsGrid<TieNoticeVo> jsGrid = new JsGrid<>(new HashMap<>());
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<TieNoticeVo> list = new ArrayList<>();
        if (tieId > 0) {
            Result<Record4<Integer, String, String, Timestamp>> record5s = tieNoticeService.findByTieIdWithBigTitleAndPage(tieNoticeVo, tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(TieNoticeVo.class);
                jsGrid.loadData(list, tieNoticeService.findByTieIdWithBigTitleAndCount(tieNoticeVo, tieId));
            } else {
                jsGrid.loadData(list, 0);
            }
        } else {
            jsGrid.loadData(list, 0);
        }
        return jsGrid.getMap();
    }

    /**
     * 删除系公告
     *
     * @param id 文章id
     * @return
     */
    @RequestMapping(value = "/maintainer/deleteTieNotice", method = RequestMethod.POST)
    @ResponseBody
    public TieNoticeVo deleteTieNotice(@RequestParam(value = "id") int id, String imgpath) {
        JsGrid<TieNoticeVo> jsGrid = new JsGrid<>();
        try {
            ArticleInfo articleInfo = articleInfoService.findById(id);
            if (!StringUtils.isEmpty(articleInfo)) {
                tieNoticeService.deleteById(id);
                articleSubService.deleteByArticleInfoId(id);
                articleInfoService.deleteById(id);
                FilesUtils.deleteFile(imgpath);
                TieNoticeVo tieNoticeVo = new TieNoticeVo();
                tieNoticeVo.setId(id);
                tieNoticeVo.setBigTitle(articleInfo.getBigTitle());
                tieNoticeVo.setUsername(usersService.getUserName());
                tieNoticeVo.setDate(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(articleInfo.getDate().getTime())));
                return jsGrid.deleteItem(tieNoticeVo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 系公告添加页面
     *
     * @return
     */
    @RequestMapping("/maintainer/tieNoticeAdd")
    public String tieNoticeAdd() {
        return "/maintainer/tienoticeadd";
    }

    /**
     * 跳转更新页面
     *
     * @param id       文章id
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/tieNoticeUpdate")
    public String tieNoticeUpdate(@RequestParam("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("articleinfo", articleInfoService.findById(id));
        List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(id);
        modelMap.addAttribute("articlesubinfo", articleSubs);
        List<TieNoticeAffix> tieNoticeAffices = tieNoticeAffixService.findByArticleInfoId(id);
        modelMap.addAttribute("tieNoticeAffix",tieNoticeAffices);
        return "/maintainer/tienoticeupdate";
    }

    /**
     * 系风采详细展示页
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/tieNoticeShow")
    public String tieNoticeShow(@RequestParam("id") int id, ModelMap modelMap) {
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

            Result<Record> records = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (records.isNotEmpty()) {
                for (Record r : records) {
                    tieId = r.getValue(Tables.TIE.ID);
                }
            }

            if (StringUtils.hasLength(articleVos.get(0).getArticlePhotoUrl())) {
                String[] paths = articleVos.get(0).getArticlePhotoUrl().split("/");
                String photo = "/" + paths[paths.length - 3] + "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
                articleVos.get(0).setArticlePhotoUrl(photo);
            }

            List<TieNoticeAffix> tieNoticeAffices = tieNoticeAffixService.findByArticleInfoId(id);
            modelMap.addAttribute("tieNoticeAffix",tieNoticeAffices);
            modelMap.addAttribute("articleInfo", articleVos.get(0));
            modelMap.addAttribute("articleSub", articleSubs);

        }
        return "/user/tienoticeshow";
    }

    /**
     * 系公告列表展示页面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/tieNoticeTime")
    public String tieNoticeTime(ModelMap modelMap, String bigTitle) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }

        Result<Record2<Integer, String>> record2s = tieNoticeTimeService.findByBigTitleAndTieIdAndTimeDistinctId(
                bigTitle,
                tieId
        );

        List<TieNoticeTime> tieNoticeTimes = new ArrayList<>();

        if (record2s.isNotEmpty()) {

            tieNoticeTimes = record2s.into(TieNoticeTime.class);
        }

        modelMap.addAttribute("timeList", tieNoticeTimes);

        modelMap.addAttribute("bigTitle", bigTitle);
        return "/user/tienoticetimeshow";
    }

    /**
     * 对应时间下数据
     *
     * @param modelMap
     * @param id
     * @param bigTitle
     * @return
     */
    @RequestMapping("/user/tieNoticeTimeDropData")
    @ResponseBody
    public AjaxData<TieNoticeTimeVo> tieNoticeTimeDropData(ModelMap modelMap, @RequestParam("id") int id, String bigTitle) {
        AjaxData<TieNoticeTimeVo> ajaxData = new AjaxData();

        Result<Record3<Integer, String, Timestamp>> record3s =
                tieNoticeService.findByTieNoticeTimeIdOrBigTitleWithArticleOrderByDateDesc(id, bigTitle);
        List<TieNoticeTimeVo> tieNoticeTimeVos = record3s.into(TieNoticeTimeVo.class);
        for (TieNoticeTimeVo t : tieNoticeTimeVos) {

            t.setDate(t.getDate().split(" ")[0]);

        }

        ajaxData.setState(true);
        ajaxData.setResult(tieNoticeTimeVos);

        return ajaxData;
    }
}
