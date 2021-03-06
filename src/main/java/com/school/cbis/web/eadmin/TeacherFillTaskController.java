package com.school.cbis.web.eadmin;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.TeacherFillTaskContentRecord;
import com.school.cbis.domain.tables.records.TeacherFillTaskTemplateRecord;
import com.school.cbis.service.*;
import com.school.cbis.util.RandomUtils;
import com.school.cbis.vo.eadmin.*;
import org.apache.commons.lang3.CharEncoding;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
public class TeacherFillTaskController {

    private final Logger log = LoggerFactory.getLogger(TeacherFillTaskController.class);

    @Resource
    private TeacherFillTaskTemplateService teacherFillTaskTemplateService;

    @Resource
    private TeacherFillTaskHeadService teacherFillTaskHeadService;

    @Resource
    private TeacherFillTaskContentService teacherFillTaskContentService;

    @Resource
    private TeacherFillTaskInfoService teacherFillTaskInfoService;

    @Resource
    private TeachTaskTitleService teachTaskTitleService;

    @Resource
    private TeachTaskContentService teachTaskContentService;

    @Resource
    private UsersService usersService;

    @Resource
    private Wordbook wordbook;

    /**
     * 教师模板列表
     *
     * @param teacherFillTemplateListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherFillTemplateList")
    public String teacherFillTemplateList(TeacherFillTemplateListVo teacherFillTemplateListVo, ModelMap modelMap) {
        modelMap.addAttribute("teacherFillTemplateListVo", teacherFillTemplateListVo);
        return "/administrator/eadmin/teacherfilltemplatelist";
    }

    /**
     * 教师模板列表数据
     *
     * @param teacherFillTemplateListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherFillTemplateData")
    @ResponseBody
    public AjaxData<TeacherFillTemplateListVo> teacherFillTemplateData(TeacherFillTemplateListVo teacherFillTemplateListVo) {
        AjaxData<TeacherFillTemplateListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            teacherFillTemplateListVo.setTeachTypeId(wordbook.getTeachTypeMap().get(StringUtils.trimWhitespace(teacherFillTemplateListVo.getTeachType())));
            Result<Record5<Integer, String, Timestamp, String, String>> record5s = teacherFillTaskTemplateService.findByTieIdAndTeachTypeIdAndPage(teacherFillTemplateListVo, tieId);
            if (record5s.isNotEmpty()) {
                List<TeacherFillTemplateListVo> list = record5s.into(TeacherFillTemplateListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teacherFillTemplateListVo.getPageNum());
                paginationData.setPageSize(teacherFillTemplateListVo.getPageSize());
                paginationData.setTotalDatas(teacherFillTaskTemplateService.findByTieIdAndTeachTypeIdAndPageCount(teacherFillTemplateListVo, tieId));
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
    @RequestMapping("/administrator/eadmin/teacherFillTemplateSelect")
    public String teacherFillTemplateSelect(AssignmentBookListVo assignmentBookListVo, ModelMap modelMap) {
        if (!StringUtils.hasLength(assignmentBookListVo.getTeachTaskTerm())) {
            assignmentBookListVo.setTeachTaskTerm("");
        }
        modelMap.addAttribute("assignmentBookListVo", assignmentBookListVo);
        return "/administrator/eadmin/teacherfilltemplateselect";
    }

    /**
     * 检验添加时的模板名
     *
     * @param title
     * @return
     */
    @RequestMapping("/administrator/eadmin/validAddTeacherFillTaskTemplateTitle")
    @ResponseBody
    public Map<String, Object> validTeacherFillTaskTemplateTitle(@RequestParam("title") String title) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(title)) {
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            if (tieId > 0) {
                Result<TeacherFillTaskTemplateRecord> records = teacherFillTaskTemplateService.findByTieIdAndTitle(tieId, StringUtils.trimWhitespace(title));
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
    @RequestMapping("/administrator/eadmin/validUpdateTeacherFillTaskTemplateTitle")
    @ResponseBody
    public Map<String, Object> validUpdateTeacherFillTaskTemplateTitle(@RequestParam("templateId") int templateId, @RequestParam("title") String title) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(title)) {
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            if (tieId > 0) {
                Result<TeacherFillTaskTemplateRecord> records = teacherFillTaskTemplateService.findByTieIdAndTitleAndNeId(tieId, StringUtils.trimWhitespace(title), templateId);
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
     * 添加教师填写模板
     *
     * @param addTeacherFillTaskTemplateVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/addTeacherFillTaskTemplate")
    public String addTeacherFillTaskTemplate(AddTeacherFillTaskTemplateVo addTeacherFillTaskTemplateVo, ModelMap modelMap) {
        TeacherFillTaskTemplate teacherFillTaskTemplate = new TeacherFillTaskTemplate();
        teacherFillTaskTemplate.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        int templateId = 0;
        if (tieId > 0) {
            teacherFillTaskTemplate.setCreateUser(usersService.getUserName());
            teacherFillTaskTemplate.setTieId(tieId);
            teacherFillTaskTemplate.setTitle(addTeacherFillTaskTemplateVo.getTitle());
            teacherFillTaskTemplate.setTeachTaskInfoId(addTeacherFillTaskTemplateVo.getTeachTaskInfoId());
            //防止刷新时的误添加
            Result<TeacherFillTaskTemplateRecord> records = teacherFillTaskTemplateService.findByTieIdAndTitle(tieId, StringUtils.trimWhitespace(teacherFillTaskTemplate.getTitle()));
            if (records.isEmpty()) {
                templateId = teacherFillTaskTemplateService.saveAndReturnId(teacherFillTaskTemplate);
            } else {
                return "redirect:/administrator/eadmin/teacherFillTemplateSelect?teachType=" + addTeacherFillTaskTemplateVo.getTeachType();
            }

        } else {
            return "redirect:/administrator/eadmin/teacherFillTemplateSelect?teachType=" + addTeacherFillTaskTemplateVo.getTeachType();
        }
        List<TeachTaskTitle> titles = teachTaskTitleService.findByTeachTaskInfoId(teacherFillTaskTemplate.getTeachTaskInfoId());
        modelMap.addAttribute("titles", titles);
        modelMap.addAttribute("templateId", templateId);
        modelMap.addAttribute("teachType", addTeacherFillTaskTemplateVo.getTeachType());
        return "/administrator/eadmin/teacherfilltemplatetitleadd";
    }

    /**
     * 添加教师填报标题
     *
     * @param teacherFillTaskHead
     * @return
     */
    @RequestMapping("/administrator/eadmin/addTeacherFillTemplateTitle")
    @ResponseBody
    public AjaxData addTeacherFillTemplateTitle(TeacherFillTaskHead teacherFillTaskHead) {
        teacherFillTaskHead.setTitleVariable(RandomUtils.generateTitleVariable());
        int titleId = teacherFillTaskHeadService.saveAndReturnId(teacherFillTaskHead);
        teacherFillTaskHead.setId(titleId);
        if (teacherFillTaskHead.getIsAssignment() == 1) {
            //要查询标题title
            TeachTaskTitle teachTaskTitle = teachTaskTitleService.findById(teacherFillTaskHead.getTeachTaskTitleId());
            if (!ObjectUtils.isEmpty(teachTaskTitle)) {
                teacherFillTaskHead.setTitle(teachTaskTitle.getTitle());
            }
            //将教学任务书内容表中数据导入到教师内容表中
            List<TeachTaskContent> teachTaskContents = teachTaskContentService.findByTeachTaskTitleId(teachTaskTitle.getId());
            for (TeachTaskContent tc : teachTaskContents) {
                TeacherFillTaskContent teacherFillTaskContent = new TeacherFillTaskContent();
                teacherFillTaskContent.setContent(tc.getContent());
                teacherFillTaskContent.setTeacherFillTaskHeadId(titleId);
                teacherFillTaskContent.setContentX(tc.getContentX());
                teacherFillTaskContentService.save(teacherFillTaskContent);
            }
        }
        return new AjaxData().success().obj(teacherFillTaskHead);
    }

    /**
     * 更新教师填报标题
     *
     * @param teacherFillTaskHead
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateTeacherFillTemplateTitle")
    @ResponseBody
    public AjaxData updateTeacherFillTemplateTitle(TeacherFillTaskHead teacherFillTaskHead) {
        TeacherFillTaskHead update = teacherFillTaskHeadService.findById(teacherFillTaskHead.getId());
        if (!ObjectUtils.isEmpty(update)) {

            if (update.getIsAssignment() == 1 && update.getTeachTaskTitleId() != teacherFillTaskHead.getTeachTaskTitleId()) {//如果是更换了教学任务书中标题
                teacherFillTaskContentService.deleteByTeacherFillTaskHeadId(update.getId());
                //重新将教学任务书内容表中数据导入到教师内容表中
                List<TeachTaskContent> teachTaskContents = teachTaskContentService.findByTeachTaskTitleId(teacherFillTaskHead.getTeachTaskTitleId());
                for (TeachTaskContent tc : teachTaskContents) {
                    TeacherFillTaskContent teacherFillTaskContent = new TeacherFillTaskContent();
                    teacherFillTaskContent.setContent(tc.getContent());
                    teacherFillTaskContent.setTeacherFillTaskHeadId(update.getId());
                    teacherFillTaskContent.setContentX(tc.getContentX());
                    teacherFillTaskContentService.save(teacherFillTaskContent);
                }
            } else if (update.getIsAssignment() == 0) {//如果不是教学任务书中标题，则清空
                List<TeacherFillTaskContent> teacherFillTaskContents = teacherFillTaskContentService.findByTeacherFillTaskHeadId(update.getId());
                for (TeacherFillTaskContent tc : teacherFillTaskContents) {
                    tc.setContent("");
                    teacherFillTaskContentService.update(tc);
                }
            }

            update.setTitle(teacherFillTaskHead.getTitle());
            update.setIsAssignment(teacherFillTaskHead.getIsAssignment());
            update.setSort(teacherFillTaskHead.getSort());
            update.setTeachTaskTitleId(teacherFillTaskHead.getTeachTaskTitleId());
            teacherFillTaskHeadService.update(update);

            if (teacherFillTaskHead.getIsAssignment() == 1) {
                //要查询标题title
                TeachTaskTitle teachTaskTitle = teachTaskTitleService.findById(teacherFillTaskHead.getTeachTaskTitleId());
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
     * 删除教师填报标题
     *
     * @param titleId
     * @return
     */
    @RequestMapping("/administrator/eadmin/deleteTeacherFillTemplateTitle")
    @ResponseBody
    public AjaxData deleteTeacherFillTemplateTitle(@RequestParam("id") int titleId) {
        TeacherFillTaskHead teacherFillTaskHead = teacherFillTaskHeadService.findById(titleId);
        if (!ObjectUtils.isEmpty(teacherFillTaskHead)) {
            teacherFillTaskContentService.deleteByTeacherFillTaskHeadId(titleId);
            teacherFillTaskHeadService.deleteById(titleId);
            return new AjaxData().success().msg("删除成功!");
        } else {
            return new AjaxData().fail().msg("未找到该标题!");
        }
    }


    /**
     * 更新教师模板信息
     *
     * @param templateId
     * @param title
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateTeacherFillTaskTemplate")
    public String updateTeacherFillTaskTemplate(@RequestParam("templateId") int templateId, @RequestParam("title") String title, String teachType, ModelMap modelMap) {
        TeacherFillTaskTemplate teacherFillTaskTemplate = teacherFillTaskTemplateService.findById(templateId);
        if (!ObjectUtils.isEmpty(teacherFillTaskTemplate)) {
            teacherFillTaskTemplate.setTitle(title);
            teacherFillTaskTemplateService.update(teacherFillTaskTemplate);
            modelMap.addAttribute("templateId", templateId);
            List<TeachTaskTitle> titles = teachTaskTitleService.findByTeachTaskInfoId(teacherFillTaskTemplate.getTeachTaskInfoId());
            modelMap.addAttribute("titles", titles);
            modelMap.addAttribute("teachType", teachType);
            return "/administrator/eadmin/teacherfilltemplatetitleupdate";
        } else {
            return "redirect:/administrator/eadmin/teacherFillTemplateList?teachType=" + teachType;
        }
    }

    /**
     * 更新时 教师模板标题数据
     *
     * @param templateId
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherFillTemplateTitleUpdateData")
    @ResponseBody
    public AjaxData<TeacherFillTemplateTitleUpdateVo> TeacherFillTemplateTitleUpdateData(@RequestParam("templateId") int templateId) {
        Result<Record7<Integer, String, String, String, Integer, Byte, Integer>> record7s = teacherFillTaskHeadService.findByTeacherFillTaskTemplateIdWithTeachTaskInfoTitle(templateId);
        if (record7s.isNotEmpty()) {
            List<TeacherFillTemplateTitleUpdateVo> list = record7s.into(TeacherFillTemplateTitleUpdateVo.class);
            return new AjaxData<TeacherFillTemplateTitleUpdateVo>().success().listData(list);
        } else {
            return new AjaxData<TeacherFillTemplateTitleUpdateVo>().fail();
        }
    }

    /**
     * 教师模板信息
     *
     * @param teacherFillTaskInfoListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherFillTaskInfoList")
    public String teacherFillTaskInfoList(TeacherFillTaskInfoListVo teacherFillTaskInfoListVo, ModelMap modelMap) {
        modelMap.addAttribute("teacherFillTaskInfoListVo", teacherFillTaskInfoListVo);
        return "/administrator/eadmin/teacherfilltaskinfolist";
    }

    /**
     * 教师模板信息数据
     *
     * @param teacherFillTaskInfoListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherFillTaskInfoData")
    @ResponseBody
    public AjaxData<TeacherFillTaskInfoListVo> teacherFillTaskInfoData(TeacherFillTaskInfoListVo teacherFillTaskInfoListVo) {
        AjaxData<TeacherFillTaskInfoListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            teacherFillTaskInfoListVo.setTeachTypeId(wordbook.getTeachTypeMap().get(StringUtils.trimWhitespace(teacherFillTaskInfoListVo.getTeachType())));
            Result<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> record7s = teacherFillTaskInfoService.findByTieIdAndTeachTypeIdAndPage(teacherFillTaskInfoListVo, tieId);
            if (record7s.isNotEmpty()) {
                List<TeacherFillTaskInfoListVo> list = record7s.into(TeacherFillTaskInfoListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teacherFillTaskInfoListVo.getPageNum());
                paginationData.setPageSize(teacherFillTaskInfoListVo.getPageSize());
                paginationData.setTotalDatas(teacherFillTaskInfoService.findByTieIdAndTeachTypeIdAndPageCount(teacherFillTaskInfoListVo, tieId));
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
     * 添加教师模板信息界面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherFillTaskInfoAdd")
    public String teacherFillTaskInfoAdd(String teachType, ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<TeacherFillTaskTemplate> list = new ArrayList<>();
        if (tieId > 0) {
            int teachTypeId = wordbook.getTeachTypeMap().get(teachType);
            Result<Record6<Integer, String, Timestamp, String, Integer, Integer>> records = teacherFillTaskTemplateService.findByTieIdAndTeachTypeId(tieId, teachTypeId);
            if (records.isNotEmpty()) {
                list = records.into(TeacherFillTaskTemplate.class);
            }
        }
        modelMap.addAttribute("template", list);
        modelMap.addAttribute("teachType", teachType);
        return "/administrator/eadmin/teacherfilltaskinfoadd";
    }

    /**
     * 添加教师模板信息
     *
     * @param addTeacherFillTaskInfoVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/addTeacherFillTaskInfo")
    public String addTeacherFillTaskInfo(AddTeacherFillTaskInfoVo addTeacherFillTaskInfoVo) {
        String teachType = null;
        try {
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
            }
            if (tieId > 0) {
                TeacherFillTaskInfo teacherFillTaskInfo = new TeacherFillTaskInfo();
                teacherFillTaskInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
                teacherFillTaskInfo.setTitle(addTeacherFillTaskInfoVo.getTitle());
                teacherFillTaskInfo.setTeacherFillTaskTemplateId(addTeacherFillTaskInfoVo.getTeacherFillTaskTemplateId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                java.util.Date startDate = sdf.parse(addTeacherFillTaskInfoVo.getStartTime() + " 00:00:00");
                java.util.Date endDate = sdf.parse(addTeacherFillTaskInfoVo.getEndTime() + " 23:59:59");
                teacherFillTaskInfo.setStartTime(new Timestamp(startDate.getTime()));
                teacherFillTaskInfo.setEndTime(new Timestamp(endDate.getTime()));
                teacherFillTaskInfo.setUsersId(usersService.getUserName());
                teacherFillTaskInfo.setTieId(tieId);
                teacherFillTaskInfoService.save(teacherFillTaskInfo);
            }
            teachType = URLEncoder.encode(addTeacherFillTaskInfoVo.getTeachType(), CharEncoding.UTF_8);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/administrator/eadmin/teacherFillTaskInfoList?teachType=" + teachType;
    }

    /**
     * 更新教师模板信息界面
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherFillTaskInfoUpdate")
    public String teacherFillTaskInfoUpdate(@RequestParam("id") int id, String teachType, ModelMap modelMap) {
        TeacherFillTaskInfo teacherFillTaskInfo = teacherFillTaskInfoService.findById(id);
        if (!ObjectUtils.isEmpty(teacherFillTaskInfo)) {
            modelMap.addAttribute("teacherFillTaskInfo", teacherFillTaskInfo);
            modelMap.addAttribute("teachType", teachType);
            return "/administrator/eadmin/teacherfilltaskinfoupdate";
        } else {
            return "redirect:/administrator/eadmin/teacherFillTaskInfoList?teachType=" + teachType;
        }
    }

    /**
     * 更新教师模板信息
     *
     * @param addTeacherFillTaskInfoVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateTeacherFillTaskInfo")
    public String updateTeacherFillTaskInfo(AddTeacherFillTaskInfoVo addTeacherFillTaskInfoVo) {
        String teachType = null;
        try {
            TeacherFillTaskInfo teacherFillTaskInfo = teacherFillTaskInfoService.findById(addTeacherFillTaskInfoVo.getId());
            teacherFillTaskInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            teacherFillTaskInfo.setTitle(addTeacherFillTaskInfoVo.getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            java.util.Date startDate = sdf.parse(addTeacherFillTaskInfoVo.getStartTime() + " 00:00:00");
            java.util.Date endDate = sdf.parse(addTeacherFillTaskInfoVo.getEndTime() + " 23:59:59");
            teacherFillTaskInfo.setStartTime(new Timestamp(startDate.getTime()));
            teacherFillTaskInfo.setEndTime(new Timestamp(endDate.getTime()));
            teacherFillTaskInfoService.update(teacherFillTaskInfo);
            teachType = URLEncoder.encode(addTeacherFillTaskInfoVo.getTeachType(), CharEncoding.UTF_8);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.debug(addTeacherFillTaskInfoVo.getTeachType());
        return "redirect:/administrator/eadmin/teacherFillTaskInfoList?teachType=" + teachType;
    }

    /**
     * 教师填报列表
     *
     * @return
     */
    @RequestMapping("/teacher/eadmin/teacherReportList")
    public String teacherReportList(String teachType,ModelMap modelMap) {
        modelMap.addAttribute("teachType",teachType);
        return "/teacher/eadmin/teacherreportlist";
    }

    /**
     * 教师填报列表数据
     *
     * @param teacherReportListVo
     * @return
     */
    @RequestMapping("/teacher/eadmin/teacherReportData")
    @ResponseBody
    public AjaxData<TeacherReportListVo> teacherReportData(TeacherReportListVo teacherReportListVo) {
        AjaxData<TeacherReportListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        boolean isTeacher = false;
        int tieId = 0;
        int teachTypeId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
            if (record.getValue(Tables.USERS.USER_TYPE_ID) == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {
                isTeacher = true;
                teachTypeId = wordbook.getTeachTypeMap().get(teacherReportListVo.getTeachType());
            }
        }

        if (tieId > 0 && isTeacher) {
            Result<Record9<Integer, Integer, Integer, String, Timestamp, Timestamp, String, String, String>> record9s =
                    teacherFillTaskInfoService.findAllAndPage(teacherReportListVo, tieId, teachTypeId);
            if (record9s.isNotEmpty()) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                List<TeacherReportListVo> list = record9s.into(TeacherReportListVo.class);
                for (TeacherReportListVo r : list) {
                    if (current.after(r.getStartTime()) && current.before(r.getEndTime())) {
                        r.setOk(true);
                    }
                    r.setStartTimeString(r.getStartTime().toString());
                    r.setEndTimeString(r.getEndTime().toString());
                }
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teacherReportListVo.getPageNum());
                paginationData.setPageSize(teacherReportListVo.getPageSize());
                paginationData.setTotalDatas(teacherFillTaskInfoService.findAllAndPageCount(teacherReportListVo, tieId, teachTypeId));
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
     * 教师填报内容列表
     *
     * @param templateId
     * @return
     */
    @RequestMapping("/teacher/eadmin/teacherFillTaskContentList")
    public String teacherFillTaskContentList(@RequestParam("templateId") int templateId, @RequestParam("teacherTaskInfoId") int teacherTaskInfoId,String teachType, ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        boolean isTeacher = false;
        if (!ObjectUtils.isEmpty(record)) {
            if (record.getValue(Tables.USERS.USER_TYPE_ID) == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {
                isTeacher = true;
            }
        }

        if (isTeacher) {
            TeacherFillTaskInfo teacherFillTaskInfo = teacherFillTaskInfoService.findById(teacherTaskInfoId);
            if (!ObjectUtils.isEmpty(teacherFillTaskInfo)) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                if (current.after(teacherFillTaskInfo.getStartTime()) && current.before(teacherFillTaskInfo.getEndTime())) {
                    List<TeacherFillTaskHead> teacherFillTaskHeads = teacherFillTaskHeadService.findByTeacherFillTaskTemplateId(templateId);
                    List<Integer> titleIds = new ArrayList<>();
                    if (!teacherFillTaskHeads.isEmpty()) {
                        for (TeacherFillTaskHead th : teacherFillTaskHeads) {
                            titleIds.add(th.getId());
                        }
                        Result<TeacherFillTaskContentRecord> records = teacherFillTaskContentService.findInTeacherFillTaskHeadId(titleIds);
                        if (records.isNotEmpty()) {
                            List<TeacherFillTaskContent> list = records.into(TeacherFillTaskContent.class);
                            modelMap.addAttribute("taskContent", list);
                        }
                    }
                    modelMap.addAttribute("taskTitle", teacherFillTaskHeads);
                    modelMap.addAttribute("templateId", templateId);
                    modelMap.addAttribute("teacherFillTaskInfo", teacherFillTaskInfo);
                    modelMap.addAttribute("teachType",teachType);
                    return "/teacher/eadmin/teacherfilltaskcontentlist";
                }
            }

        }
        return "redirect:/teacher/eadmin/teacherReportList";
    }

    /**
     * 教师填报内容页面
     *
     * @param x
     * @param templateId
     * @param modelMap
     * @return
     */
    @RequestMapping("/teacher/eadmin/teacherFillTaskContentAdd")
    public String teacherFillTaskContentAdd(@RequestParam("x") int x, @RequestParam("templateId") int templateId, @RequestParam("teacherTaskInfoId") int teacherTaskInfoId, ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        boolean isTeacher = false;
        if (!ObjectUtils.isEmpty(record)) {
            if (record.getValue(Tables.USERS.USER_TYPE_ID) == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {
                isTeacher = true;
            }
        }
        if (isTeacher) {
            TeacherFillTaskInfo teacherFillTaskInfo = teacherFillTaskInfoService.findById(teacherTaskInfoId);
            if (!ObjectUtils.isEmpty(teacherFillTaskInfo)) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                if (current.after(teacherFillTaskInfo.getStartTime()) && current.before(teacherFillTaskInfo.getEndTime())) {
                    List<TeacherFillTaskHead> teacherFillTaskHeads = teacherFillTaskHeadService.findByTeacherFillTaskTemplateId(templateId);
                    List<Integer> titleIds = new ArrayList<>();
                    if (!teacherFillTaskHeads.isEmpty()) {
                        for (TeacherFillTaskHead th : teacherFillTaskHeads) {
                            titleIds.add(th.getId());
                        }
                        Result<TeacherFillTaskContentRecord> records = teacherFillTaskContentService.findInTeacherFillTaskHeadIdAndContentX(titleIds, x);
                        if (records.isNotEmpty()) {
                            List<TeacherFillTaskContent> list = records.into(TeacherFillTaskContent.class);
                            modelMap.addAttribute("taskContent", list);
                        }
                    }
                    modelMap.addAttribute("taskTitle", teacherFillTaskHeads);
                    modelMap.addAttribute("templateId", templateId);
                    modelMap.addAttribute("contentX", x);
                    modelMap.addAttribute("teacherFillTaskInfo", teacherFillTaskInfo);
                    return "/teacher/eadmin/teacherfilltaskcontentadd";
                }
            }
        }
        return "redirect:/teacher/eadmin/teacherReportList";
    }

    /**
     * 保存填报内容
     *
     * @param request
     * @return
     */
    @RequestMapping("/teacher/eadmin/addTeacherFillTaskContent")
    public String addTeacherFillTaskContent(HttpServletRequest request) {
        int templateId = NumberUtils.toInt(request.getParameter("templateId"));
        int contentX = NumberUtils.toInt(request.getParameter("contentX"));
        int teacherTaskInfoId = NumberUtils.toInt(request.getParameter("teacherTaskInfoId"));

        Record record = usersService.findAll(usersService.getUserName());
        boolean isTeacher = false;
        if (!ObjectUtils.isEmpty(record)) {
            if (record.getValue(Tables.USERS.USER_TYPE_ID) == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {
                isTeacher = true;
            }
        }
        if (isTeacher) {
            TeacherFillTaskInfo teacherFillTaskInfo = teacherFillTaskInfoService.findById(teacherTaskInfoId);
            if (!ObjectUtils.isEmpty(teacherFillTaskInfo)) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                if (current.after(teacherFillTaskInfo.getStartTime()) && current.before(teacherFillTaskInfo.getEndTime())) {
                    List<TeacherFillTaskHead> teacherFillTaskHeads = teacherFillTaskHeadService.findByTeacherFillTaskTemplateId(templateId);
                    for (TeacherFillTaskHead th : teacherFillTaskHeads) {
                        if (th.getIsAssignment() == 0) {
                            TeacherFillTaskContentRecord teacherFillTaskContentRecord = teacherFillTaskContentService.findByTeacherFillTaskHeadIdAndContentX(th.getId(), contentX);
                            if (!ObjectUtils.isEmpty(teacherFillTaskContentRecord)) {//存在就更新
                                TeacherFillTaskContent teacherFillTaskContent = teacherFillTaskContentRecord.into(TeacherFillTaskContent.class);
                                teacherFillTaskContent.setContent(request.getParameter(th.getTitleVariable()));
                                teacherFillTaskContentService.update(teacherFillTaskContent);
                            } else {
                                TeacherFillTaskContent teacherFillTaskContent = new TeacherFillTaskContent();
                                teacherFillTaskContent.setContent(request.getParameter(th.getTitleVariable()));
                                teacherFillTaskContent.setTeacherId(record.getValue(Tables.TEACHER.ID));
                                teacherFillTaskContent.setContentX(contentX);
                                teacherFillTaskContent.setTeacherFillTaskHeadId(th.getId());
                                teacherFillTaskContentService.save(teacherFillTaskContent);
                            }

                        }
                    }
                    return "redirect:/teacher/eadmin/teacherFillTaskContentList?templateId=" + templateId + "&teacherTaskInfoId=" + teacherTaskInfoId;
                }
            }
        }
        return "redirect:/teacher/eadmin/teacherReportList";

    }
}
