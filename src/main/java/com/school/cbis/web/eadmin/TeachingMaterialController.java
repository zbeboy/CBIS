package com.school.cbis.web.eadmin;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.TeacherFillTaskContentRecord;
import com.school.cbis.domain.tables.records.TeacherFillTaskTemplateRecord;
import com.school.cbis.domain.tables.records.TeachingMaterialContentRecord;
import com.school.cbis.domain.tables.records.TeachingMaterialTemplateRecord;
import com.school.cbis.service.*;
import com.school.cbis.util.RandomUtils;
import com.school.cbis.vo.eadmin.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-06-04.
 */
@Controller
public class TeachingMaterialController {

    private final Logger log = LoggerFactory.getLogger(TeachingMaterialController.class);

    @Resource
    private TeachingMaterialTemplateService teachingMaterialTemplateService;

    @Resource
    private TeachingMaterialHeadService teachingMaterialHeadService;

    @Resource
    private TeachingMaterialContentService teachingMaterialContentService;

    @Resource
    private TeachingMaterialInfoService teachingMaterialInfoService;

    @Resource
    private TeachTaskTitleService teachTaskTitleService;

    @Resource
    private TeachTaskContentService teachTaskContentService;

    @Resource
    private UsersService usersService;

    @Resource
    private Wordbook wordbook;


    /**
     * 教材模板列表
     *
     * @param teachingMaterialTemplateListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingMaterialTemplateList")
    public String teachingMaterialTemplateList(TeachingMaterialTemplateListVo teachingMaterialTemplateListVo, ModelMap modelMap) {
        modelMap.addAttribute("teachingMaterialTemplateListVo", teachingMaterialTemplateListVo);
        return "/administrator/eadmin/teachingmaterialtemplatelist";
    }

    /**
     * 教材模板列表数据
     *
     * @param teachingMaterialTemplateListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingMaterialTemplateData")
    @ResponseBody
    public AjaxData<TeachingMaterialTemplateListVo> teacherFillTemplateData(TeachingMaterialTemplateListVo teachingMaterialTemplateListVo) {
        AjaxData<TeachingMaterialTemplateListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            Result<Record5<Integer, String, Timestamp, String, String>> record5s = teachingMaterialTemplateService.findByTieIdAndPage(teachingMaterialTemplateListVo, tieId);
            if (record5s.isNotEmpty()) {
                List<TeachingMaterialTemplateListVo> list = record5s.into(TeachingMaterialTemplateListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teachingMaterialTemplateListVo.getPageNum());
                paginationData.setPageSize(teachingMaterialTemplateListVo.getPageSize());
                paginationData.setTotalDatas(teachingMaterialTemplateService.findByTieIdAndPageCount(teachingMaterialTemplateListVo, tieId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.fail();
            }
        } else {
            ajaxData.fail();
        }
        return ajaxData;
    }

    /**
     * 添加模板后选择教学任务书
     *
     * @param assignmentBookListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingMaterialTemplateSelect")
    public String teachingMaterialTemplateSelect(AssignmentBookListVo assignmentBookListVo, ModelMap modelMap) {
        if (!StringUtils.hasLength(assignmentBookListVo.getTeachTaskTerm())) {
            assignmentBookListVo.setTeachTaskTerm("");
        }
        modelMap.addAttribute("assignmentBookListVo", assignmentBookListVo);
        return "/administrator/eadmin/teachingmaterialtemplateselect";
    }

    /**
     * 添加教材申报模板
     *
     * @param teachingMaterialTemplate
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/addTeachingMaterialTemplate")
    public String addTeachingMaterialTemplate(TeachingMaterialTemplate teachingMaterialTemplate, ModelMap modelMap) {
        teachingMaterialTemplate.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        int templateId = 0;
        if (tieId > 0) {
            teachingMaterialTemplate.setCreateUser(usersService.getUserName());
            teachingMaterialTemplate.setTieId(tieId);
            //防止刷新时的误添加
            Result<TeachingMaterialTemplateRecord> records = teachingMaterialTemplateService.findByTieIdAndTitle(tieId, StringUtils.trimWhitespace(teachingMaterialTemplate.getTitle()));
            if (records.isEmpty()) {
                templateId = teachingMaterialTemplateService.saveAndReturnId(teachingMaterialTemplate);
            } else {
                return "redirect:/administrator/eadmin/teacherFillTemplateSelect";
            }

        } else {
            return "redirect:/administrator/eadmin/teacherFillTemplateSelect";
        }
        List<TeachTaskTitle> titles = teachTaskTitleService.findByTeachTaskInfoId(teachingMaterialTemplate.getTeachTaskInfoId());
        modelMap.addAttribute("titles", titles);
        modelMap.addAttribute("templateId", templateId);
        return "/administrator/eadmin/teachingmaterialtemplatetitleadd";
    }

    /**
     * 添加教材申报标题
     *
     * @param teachingMaterialHead
     * @return
     */
    @RequestMapping("/administrator/eadmin/addTeachingMaterialTemplateTitle")
    @ResponseBody
    public AjaxData addTeachingMaterialTemplateTitle(TeachingMaterialHead teachingMaterialHead) {
        teachingMaterialHead.setTitleVariable(RandomUtils.generateTitleVariable());
        int titleId = teachingMaterialHeadService.saveAndReturnId(teachingMaterialHead);
        teachingMaterialHead.setId(titleId);
        if (teachingMaterialHead.getIsAssignment() == 1) {
            //要查询标题title
            TeachTaskTitle teachTaskTitle = teachTaskTitleService.findById(teachingMaterialHead.getTeachTaskTitleId());
            if (!ObjectUtils.isEmpty(teachTaskTitle)) {
                teachingMaterialHead.setTitle(teachTaskTitle.getTitle());
            }
            //将教学任务书内容表中数据导入到教师内容表中
            List<TeachTaskContent> teachTaskContents = teachTaskContentService.findByTeachTaskTitleId(teachTaskTitle.getId());
            for (TeachTaskContent tc : teachTaskContents) {
                TeachingMaterialContent teachingMaterialContent = new TeachingMaterialContent();
                teachingMaterialContent.setContent(tc.getContent());
                teachingMaterialContent.setTeachingMaterialHeadId(titleId);
                teachingMaterialContent.setContentX(tc.getContentX());
                teachingMaterialContentService.save(teachingMaterialContent);
            }
        }
        return new AjaxData().success().obj(teachingMaterialHead);
    }

    /**
     * 更新教材申报标题
     *
     * @param teachingMaterialHead
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateTeachingMaterialTemplateTitle")
    @ResponseBody
    public AjaxData updateTeachingMaterialTemplateTitle(TeachingMaterialHead teachingMaterialHead) {
        TeachingMaterialHead update = teachingMaterialHeadService.findById(teachingMaterialHead.getId());
        if (!ObjectUtils.isEmpty(update)) {

            if (update.getIsAssignment() == 1 && update.getTeachTaskTitleId() != teachingMaterialHead.getTeachTaskTitleId()) {//如果是更换了教学任务书中标题
                teachingMaterialContentService.deleteByTeachingMaterialHeadId(update.getId());
                //重新将教学任务书内容表中数据导入到教师内容表中
                List<TeachTaskContent> teachTaskContents = teachTaskContentService.findByTeachTaskTitleId(teachingMaterialHead.getTeachTaskTitleId());
                for (TeachTaskContent tc : teachTaskContents) {
                    TeachingMaterialContent teachingMaterialContent = new TeachingMaterialContent();
                    teachingMaterialContent.setContent(tc.getContent());
                    teachingMaterialContent.setTeachingMaterialHeadId(update.getId());
                    teachingMaterialContent.setContentX(tc.getContentX());
                    teachingMaterialContentService.save(teachingMaterialContent);
                }
            } else if (update.getIsAssignment() == 0) {//如果不是教学任务书中标题，则清空
                List<TeachingMaterialContent> teachingMaterialContents = teachingMaterialContentService.findByTeachingMaterialHeadId(update.getId());
                for (TeachingMaterialContent tc : teachingMaterialContents) {
                    tc.setContent("");
                    teachingMaterialContentService.update(tc);
                }
            }

            update.setTitle(teachingMaterialHead.getTitle());
            update.setIsAssignment(teachingMaterialHead.getIsAssignment());
            update.setSort(teachingMaterialHead.getSort());
            update.setTeachTaskTitleId(teachingMaterialHead.getTeachTaskTitleId());
            teachingMaterialHeadService.update(update);

            if (teachingMaterialHead.getIsAssignment() == 1) {
                //要查询标题title
                TeachTaskTitle teachTaskTitle = teachTaskTitleService.findById(teachingMaterialHead.getTeachTaskTitleId());
                if (!ObjectUtils.isEmpty(teachTaskTitle)) {
                    update.setTitle(teachTaskTitle.getTitle());
                }
            }

            return new AjaxData().success().obj(update);
        } else {
            return new AjaxData().fail().msg("更新失败!");
        }

    }

    /**
     * 删除教材申报标题
     *
     * @param titleId
     * @return
     */
    @RequestMapping("/administrator/eadmin/deleteTeachingMaterialTemplateTitle")
    @ResponseBody
    public AjaxData deleteTeachingMaterialTemplateTitle(@RequestParam("id") int titleId) {
        TeachingMaterialHead teachingMaterialHead = teachingMaterialHeadService.findById(titleId);
        if (!ObjectUtils.isEmpty(teachingMaterialHead)) {
            teachingMaterialContentService.deleteByTeachingMaterialHeadId(titleId);
            teachingMaterialHeadService.deleteById(titleId);
            return new AjaxData().success().msg("删除成功!");
        } else {
            return new AjaxData().fail().msg("未找到该标题!");
        }
    }

    /**
     * 更新教材申报模板信息
     *
     * @param templateId
     * @param title
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateTeachingMaterialTemplate")
    public String updateTeachingMaterialTemplate(@RequestParam("templateId") int templateId, @RequestParam("title") String title, ModelMap modelMap) {
        TeachingMaterialTemplate teachingMaterialTemplate = teachingMaterialTemplateService.findById(templateId);
        if (!ObjectUtils.isEmpty(teachingMaterialTemplate)) {
            teachingMaterialTemplate.setTitle(title);
            teachingMaterialTemplateService.update(teachingMaterialTemplate);
            modelMap.addAttribute("templateId", templateId);
            List<TeachTaskTitle> titles = teachTaskTitleService.findByTeachTaskInfoId(teachingMaterialTemplate.getTeachTaskInfoId());
            modelMap.addAttribute("titles", titles);
            return "/administrator/eadmin/teachingmaterialtemplatetitleupdate";
        } else {
            return "redirect:/administrator/eadmin/teachingMaterialTemplateList";
        }
    }

    /**
     * 检验添加时的模板名
     *
     * @param title
     * @return
     */
    @RequestMapping("/administrator/eadmin/validAddTeachingMaterialTemplateTitle")
    @ResponseBody
    public Map<String, Object> validAddTeachingMaterialTemplateTitle(@RequestParam("title") String title) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(title)) {
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            if (tieId > 0) {
                Result<TeachingMaterialTemplateRecord> records = teachingMaterialTemplateService.findByTieIdAndTitle(tieId, StringUtils.trimWhitespace(title));
                if (records.isEmpty()) {
                    map.put("ok", "");
                } else {
                    map.put("error", "模板名已存在!");
                }
            } else {
                map.put("error", "获取用户信息错误!");
            }

        } else {
            map.put("error", "请填写标题!");
        }
        return map;
    }

    /**
     * 检验更新时模板名
     *
     * @param templateId
     * @param title
     * @return
     */
    @RequestMapping("/administrator/eadmin/validUpdateTeachingMaterialTemplateTitle")
    @ResponseBody
    public Map<String, Object> validUpdateTeachingMaterialTemplateTitle(@RequestParam("templateId") int templateId, @RequestParam("title") String title) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(title)) {
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            if (tieId > 0) {
                Result<TeachingMaterialTemplateRecord> records = teachingMaterialTemplateService.findByTieIdAndTitleAndNeId(tieId, StringUtils.trimWhitespace(title), templateId);
                if (records.isEmpty()) {
                    map.put("ok", "");
                } else {
                    map.put("error", "模板名已存在!");
                }
            } else {
                map.put("error", "获取用户信息错误!");
            }

        } else {
            map.put("error", "请填写标题!");
        }
        return map;
    }

    /**
     * 更新时 教材申报模板标题数据
     *
     * @param templateId
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingMaterialTemplateTitleUpdateData")
    @ResponseBody
    public AjaxData<TeachingMaterialTemplateTitleUpdateVo> teachingMaterialTemplateTitleUpdateData(@RequestParam("templateId") int templateId) {
        Result<Record7<Integer, String, String, String, Integer, Byte, Integer>> record7s = teachingMaterialHeadService.findByTeachingMaterialTemplateIdWithTeachTaskInfoTitle(templateId);
        if (record7s.isNotEmpty()) {
            List<TeachingMaterialTemplateTitleUpdateVo> list = record7s.into(TeachingMaterialTemplateTitleUpdateVo.class);
            return new AjaxData<TeachingMaterialTemplateTitleUpdateVo>().success().listData(list);
        } else {
            return new AjaxData<TeachingMaterialTemplateTitleUpdateVo>().fail();
        }
    }

    /**
     * 教材申报模板信息
     *
     * @param teachingMaterialInfoListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingMaterialInfoList")
    public String teachingMaterialInfoList(TeachingMaterialInfoListVo teachingMaterialInfoListVo, ModelMap modelMap) {
        modelMap.addAttribute("teachingMaterialInfoListVo", teachingMaterialInfoListVo);
        return "/administrator/eadmin/teachingmaterialinfolist";
    }

    /**
     * 教材申报模板信息数据
     *
     * @param teachingMaterialInfoListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingMaterialInfoData")
    @ResponseBody
    public AjaxData<TeachingMaterialInfoListVo> teachingMaterialInfoData(TeachingMaterialInfoListVo teachingMaterialInfoListVo) {
        AjaxData<TeachingMaterialInfoListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            Result<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> record7s = teachingMaterialInfoService.findByTieIdAndPage(teachingMaterialInfoListVo, tieId);
            if (record7s.isNotEmpty()) {
                List<TeachingMaterialInfoListVo> list = record7s.into(TeachingMaterialInfoListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teachingMaterialInfoListVo.getPageNum());
                paginationData.setPageSize(teachingMaterialInfoListVo.getPageSize());
                paginationData.setTotalDatas(teachingMaterialInfoService.findByTieIdAndPageCount(teachingMaterialInfoListVo, tieId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.fail();
            }
        } else {
            ajaxData.fail();
        }
        return ajaxData;
    }

    /**
     * 添加教材申报模板信息界面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingMaterialInfoAdd")
    public String teachingMaterialInfoAdd(ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<TeachingMaterialTemplate> list = new ArrayList<>();
        if (tieId > 0) {
            int teachTypeId = wordbook.getTeachTypeMap().get(Wordbook.TEACH_TYPE_THEORY);
            Result<Record6<Integer,String,Timestamp,String,Integer,Integer>> records = teachingMaterialTemplateService.findByTieIdAndTeachTypeId(tieId,teachTypeId);
            if (records.isNotEmpty()) {
                list = records.into(TeachingMaterialTemplate.class);
            }
        }
        modelMap.addAttribute("template", list);
        return "/administrator/eadmin/teachingmaterialinfoadd";
    }

    /**
     * 添加教材申报模板模板信息
     *
     * @param addTeachingMaterialInfoVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/addTeachingMaterialInfo")
    public String addTeachingMaterialInfo(AddTeachingMaterialInfoVo addTeachingMaterialInfoVo) {
        try {
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            if (tieId > 0) {
                TeachingMaterialInfo teachingMaterialInfo = new TeachingMaterialInfo();
                teachingMaterialInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
                teachingMaterialInfo.setTitle(addTeachingMaterialInfoVo.getTitle());
                teachingMaterialInfo.setTeachingMaterialTemplateId(addTeachingMaterialInfoVo.getTeachingMaterialTemplateId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                java.util.Date startDate = sdf.parse(addTeachingMaterialInfoVo.getStartTime() + " 00:00:00");
                java.util.Date endDate = sdf.parse(addTeachingMaterialInfoVo.getEndTime() + " 23:59:59");
                teachingMaterialInfo.setStartTime(new Timestamp(startDate.getTime()));
                teachingMaterialInfo.setEndTime(new Timestamp(endDate.getTime()));
                teachingMaterialInfo.setUsersId(usersService.getUserName());
                teachingMaterialInfo.setTieId(tieId);
                teachingMaterialInfoService.save(teachingMaterialInfo);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/administrator/eadmin/teachingMaterialInfoList";
    }

    /**
     * 更新教材申报模板信息界面
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teachingMaterialInfoUpdate")
    public String teachingMaterialInfoUpdate(@RequestParam("id") int id, ModelMap modelMap) {
        TeachingMaterialInfo teachingMaterialInfo = teachingMaterialInfoService.findById(id);
        if (!ObjectUtils.isEmpty(teachingMaterialInfo)) {
            modelMap.addAttribute("teachingMaterialInfo", teachingMaterialInfo);
            return "/administrator/eadmin/teachingmaterialinfoupdate";
        } else {
            return "redirect:/administrator/eadmin/teachingMaterialInfoList";
        }
    }

    /**
     * 更新教材申报模板信息
     *
     * @param addTeachingMaterialInfoVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateTeachingMaterialInfo")
    public String updateTeachingMaterialInfo(AddTeachingMaterialInfoVo addTeachingMaterialInfoVo) {
        try {
            TeachingMaterialInfo teachingMaterialInfo = teachingMaterialInfoService.findById(addTeachingMaterialInfoVo.getId());
            teachingMaterialInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            teachingMaterialInfo.setTitle(addTeachingMaterialInfoVo.getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            java.util.Date startDate = sdf.parse(addTeachingMaterialInfoVo.getStartTime() + " 00:00:00");
            java.util.Date endDate = sdf.parse(addTeachingMaterialInfoVo.getEndTime() + " 23:59:59");
            teachingMaterialInfo.setStartTime(new Timestamp(startDate.getTime()));
            teachingMaterialInfo.setEndTime(new Timestamp(endDate.getTime()));
            teachingMaterialInfoService.update(teachingMaterialInfo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/administrator/eadmin/teachingMaterialInfoList";
    }

    /**
     * 教材申报列表
     *
     * @return
     */
    @RequestMapping("/teacher/eadmin/teachingMaterialList")
    public String teachingMaterialList() {
        return "/teacher/eadmin/teachingmateriallist";
    }

    /**
     * 教材申报列表数据
     *
     * @param teachingMaterialListVo
     * @return
     */
    @RequestMapping("/teacher/eadmin/teachingMaterialData")
    @ResponseBody
    public AjaxData<TeachingMaterialListVo> teachingMaterialData(TeachingMaterialListVo teachingMaterialListVo) {
        AjaxData<TeachingMaterialListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        boolean isTeacher = false;
        int tieId = 0;
        int teachTypeId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
            if (record.getValue(Tables.USERS.USER_TYPE_ID) == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {
                isTeacher = true;
                teachTypeId = wordbook.getTeachTypeMap().get(Wordbook.TEACH_TYPE_THEORY);
            }
        }

        if (tieId > 0 && isTeacher) {
            Result<Record9<Integer, Integer, Integer, String, Timestamp, Timestamp, String, String, String>> record9s =
                    teachingMaterialInfoService.findAllAndPage(teachingMaterialListVo, tieId, teachTypeId);
            if (record9s.isNotEmpty()) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                List<TeachingMaterialListVo> list = record9s.into(TeachingMaterialListVo.class);
                for (TeachingMaterialListVo r : list) {
                    if (current.after(r.getStartTime()) && current.before(r.getEndTime())) {
                        r.setOk(true);
                    }
                    r.setStartTimeString(r.getStartTime().toString());
                    r.setEndTimeString(r.getEndTime().toString());
                }
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teachingMaterialListVo.getPageNum());
                paginationData.setPageSize(teachingMaterialListVo.getPageSize());
                paginationData.setTotalDatas(teachingMaterialInfoService.findAllAndPageCount(teachingMaterialListVo, tieId, teachTypeId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.fail();
            }
        } else {
            ajaxData.fail();
        }
        return ajaxData;
    }

    /**
     * 教材申报内容列表
     *
     * @param templateId
     * @return
     */
    @RequestMapping("/teacher/eadmin/teachingMaterialContentList")
    public String teachingMaterialContentList(@RequestParam("templateId") int templateId, @RequestParam("teachingMaterialInfoId") int teachingMaterialInfoId, ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        boolean isTeacher = false;
        if (!ObjectUtils.isEmpty(record)) {
            if (record.getValue(Tables.USERS.USER_TYPE_ID) == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {
                isTeacher = true;
            }
        }

        if (isTeacher) {
            TeachingMaterialInfo teachingMaterialInfo = teachingMaterialInfoService.findById(teachingMaterialInfoId);
            if (!ObjectUtils.isEmpty(teachingMaterialInfo)) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                if (current.after(teachingMaterialInfo.getStartTime()) && current.before(teachingMaterialInfo.getEndTime())) {
                    List<TeachingMaterialHead> teachingMaterialHeads = teachingMaterialHeadService.findByTeachingMaterialTemplateId(templateId);
                    List<Integer> titleIds = new ArrayList<>();
                    if (!teachingMaterialHeads.isEmpty()) {
                        for (TeachingMaterialHead th : teachingMaterialHeads) {
                            titleIds.add(th.getId());
                        }
                        Result<TeachingMaterialContentRecord> records = teachingMaterialContentService.findInTeachingMaterialHeadId(titleIds);
                        if (records.isNotEmpty()) {
                            List<TeachingMaterialContent> list = records.into(TeachingMaterialContent.class);
                            modelMap.addAttribute("taskContent", list);
                        }
                    }
                    modelMap.addAttribute("taskTitle", teachingMaterialHeads);
                    modelMap.addAttribute("templateId", templateId);
                    modelMap.addAttribute("teachingMaterialInfo", teachingMaterialInfo);
                    return "/teacher/eadmin/teachingmaterialcontentlist";
                }
            }

        }
        return "redirect:/teacher/eadmin/teachingMaterialList";
    }

    /**
     * 教材申报内容页面
     *
     * @param x
     * @param templateId
     * @param modelMap
     * @return
     */
    @RequestMapping("/teacher/eadmin/teachingMaterialContentAdd")
    public String teacherFillTaskContentAdd(@RequestParam("x") int x, @RequestParam("templateId") int templateId, @RequestParam("teachingMaterialInfoId") int teachingMaterialInfoId, ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        boolean isTeacher = false;
        if (!ObjectUtils.isEmpty(record)) {
            if (record.getValue(Tables.USERS.USER_TYPE_ID) == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {
                isTeacher = true;
            }
        }
        if (isTeacher) {
            TeachingMaterialInfo teachingMaterialInfo = teachingMaterialInfoService.findById(teachingMaterialInfoId);
            if (!ObjectUtils.isEmpty(teachingMaterialInfo)) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                if (current.after(teachingMaterialInfo.getStartTime()) && current.before(teachingMaterialInfo.getEndTime())) {
                    List<TeachingMaterialHead> teachingMaterialHeads = teachingMaterialHeadService.findByTeachingMaterialTemplateId(templateId);
                    List<Integer> titleIds = new ArrayList<>();
                    if (!teachingMaterialHeads.isEmpty()) {
                        for (TeachingMaterialHead th : teachingMaterialHeads) {
                            titleIds.add(th.getId());
                        }
                        Result<TeachingMaterialContentRecord> records = teachingMaterialContentService.findInTeachingMaterialHeadIdAndContentX(titleIds, x);
                        if (records.isNotEmpty()) {
                            List<TeachingMaterialContent> list = records.into(TeachingMaterialContent.class);
                            modelMap.addAttribute("taskContent", list);
                        }
                    }
                    modelMap.addAttribute("taskTitle", teachingMaterialHeads);
                    modelMap.addAttribute("templateId", templateId);
                    modelMap.addAttribute("contentX", x);
                    modelMap.addAttribute("teachingMaterialInfo", teachingMaterialInfo);
                    return "/teacher/eadmin/teachingmaterialcontentadd";
                }
            }
        }
        return "redirect:/teacher/eadmin/teachingMaterialList";
    }

    /**
     * 保存填报内容
     *
     * @param request
     * @return
     */
    @RequestMapping("/teacher/eadmin/addTeachingMaterialContent")
    public String addTeachingMaterialContent(HttpServletRequest request) {
        int templateId = NumberUtils.toInt(request.getParameter("templateId"));
        int contentX = NumberUtils.toInt(request.getParameter("contentX"));
        int teachingMaterialInfoId = NumberUtils.toInt(request.getParameter("teachingMaterialInfoId"));

        Record record = usersService.findAll(usersService.getUserName());
        boolean isTeacher = false;
        if (!ObjectUtils.isEmpty(record)) {
            if (record.getValue(Tables.USERS.USER_TYPE_ID) == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {
                isTeacher = true;
            }
        }
        if (isTeacher) {
            TeachingMaterialInfo teachingMaterialInfo = teachingMaterialInfoService.findById(teachingMaterialInfoId);
            if (!ObjectUtils.isEmpty(teachingMaterialInfo)) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                if (current.after(teachingMaterialInfo.getStartTime()) && current.before(teachingMaterialInfo.getEndTime())) {
                    List<TeachingMaterialHead> teachingMaterialHeads = teachingMaterialHeadService.findByTeachingMaterialTemplateId(templateId);
                    for (TeachingMaterialHead th : teachingMaterialHeads) {
                        if (th.getIsAssignment() == 0) {
                            TeachingMaterialContentRecord teachingMaterialContentRecord = teachingMaterialContentService.findByTeachingMaterialHeadIdAndContentX(th.getId(), contentX);
                            if (!ObjectUtils.isEmpty(teachingMaterialContentRecord)) {//存在就更新
                                TeachingMaterialContent teachingMaterialContent = teachingMaterialContentRecord.into(TeachingMaterialContent.class);
                                teachingMaterialContent.setContent(request.getParameter(th.getTitleVariable()));
                                teachingMaterialContentService.update(teachingMaterialContent);
                            } else {
                                TeachingMaterialContent teachingMaterialContent = new TeachingMaterialContent();
                                teachingMaterialContent.setContent(request.getParameter(th.getTitleVariable()));
                                teachingMaterialContent.setTeacherId(record.getValue(Tables.TEACHER.ID));
                                teachingMaterialContent.setContentX(contentX);
                                teachingMaterialContent.setTeachingMaterialHeadId(th.getId());
                                teachingMaterialContentService.save(teachingMaterialContent);
                            }

                        }
                    }
                    return "redirect:/teacher/eadmin/teachingMaterialContentList?templateId=" + templateId + "&teachingMaterialInfoId="+teachingMaterialInfoId;
                }
            }
        }
        return "redirect:/teacher/eadmin/teachingMaterialList";

    }
}
