package com.school.cbis.web.eadmin;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.*;
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
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-05-28.
 */
@Controller
public class EAdminController {

    private final Logger log = LoggerFactory.getLogger(EAdminController.class);

    @Resource
    private TeachTaskInfoService teachTaskInfoService;

    @Resource
    private TeachTaskTitleService teachTaskTitleService;

    @Resource
    private TeachTaskContentService teachTaskContentService;

    @Resource
    private TeachTaskGradeCheckService teachTaskGradeCheckService;

    @Resource
    private TeacherFillTaskTemplateService teacherFillTaskTemplateService;

    @Resource
    private TeacherFillTaskHeadService teacherFillTaskHeadService;

    @Resource
    private TeacherFillTaskContentService teacherFillTaskContentService;

    @Resource
    private TeacherFillTaskInfoService teacherFillTaskInfoService;

    @Resource
    private UsersService usersService;

    @Resource
    private Wordbook wordbook;

    @Resource
    private FileService fileService;

    /**
     * 教务欢迎页
     *
     * @return
     */
    @RequestMapping("/student/eadmin/EAdminManager")
    public String EAdminManager() {
        return "/student/eadmin/eadminindex";
    }

    /**
     * 教学任务书列表
     *
     * @param assignmentBookListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/assignmentBookList")
    public String assignmentBookList(AssignmentBookListVo assignmentBookListVo, ModelMap modelMap) {
        if (!StringUtils.hasLength(assignmentBookListVo.getTeachTaskTerm())) {
            assignmentBookListVo.setTeachTaskTerm("");
        }
        modelMap.addAttribute("assignmentBookListVo", assignmentBookListVo);
        return "/administrator/eadmin/assignmentbooklist";
    }

    /**
     * 教学任务书列表数据
     *
     * @param assignmentBookListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/assignmentBookData")
    @ResponseBody
    public AjaxData<AssignmentBookListVo> assignmentBookData(AssignmentBookListVo assignmentBookListVo) {
        AjaxData<AssignmentBookListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }

        if (tieId > 0) {
            int teachTypeId = wordbook.getTeachTypeMap().get(Wordbook.TEACH_TYPE_THEORY);
            Result<Record7<Integer, String, String, String, Date, Date, Byte>> record7s =
                    teachTaskInfoService.findAllByTieIdAndPageAndTeachTypeId(assignmentBookListVo, tieId, teachTypeId);
            if (record7s.isNotEmpty()) {
                List<AssignmentBookListVo> assignmentBookListVos = record7s.into(AssignmentBookListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(assignmentBookListVo.getPageNum());
                paginationData.setPageSize(assignmentBookListVo.getPageSize());
                paginationData.setTotalDatas(teachTaskInfoService.findAllByTieIdAndPageAndTeachTypeIdCount(assignmentBookListVo, tieId, teachTypeId));
                ajaxData.success().listData(assignmentBookListVos).paginationData(paginationData);
            } else {
                ajaxData.fail();
            }
        } else {
            ajaxData.fail();
        }
        return ajaxData;
    }

    @RequestMapping("/administrator/eadmin/assignmentBookUse")
    @ResponseBody
    public AjaxData assignmentBookUse(@RequestParam("id") int id, @RequestParam("isUse") Byte use) {
        TeachTaskInfo teachTaskInfo = teachTaskInfoService.findById(id);
        if (!ObjectUtils.isEmpty(teachTaskInfo)) {
            teachTaskInfo.setIsUse(use);
            teachTaskInfoService.update(teachTaskInfo);
            return new AjaxData().success().msg("更新状态成功!");
        } else {
            return new AjaxData().fail().msg("未找到该教学任务书!");
        }
    }

    /**
     * 教学任务书更新
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/assignmentBookUpdate")
    public String assignmentBookUpdate(@RequestParam("id") int id, ModelMap modelMap) {
        TeachTaskInfo teachTaskInfo = teachTaskInfoService.findById(id);
        if (!ObjectUtils.isEmpty(teachTaskInfo)) {
            modelMap.addAttribute("teachTaskInfo", teachTaskInfo);
            return "/administrator/eadmin/assignmentbookupdate";
        } else {
            return "redirect:/administrator/eadmin/assignmentBookList";
        }
    }

    /**
     * 预览数据
     *
     * @param id       教学任务书id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/assignmentBookLook")
    public String assignmentBookLook(@RequestParam("id") int id, ModelMap modelMap) {
        //该教学任务书下所有标题
        List<TeachTaskTitle> teachTaskTitles = teachTaskTitleService.findByTeachTaskInfoId(id);
        List<TeachTaskContent> teachTaskContents = new ArrayList<>();
        List<TeachTaskGradeCheck> teachTaskGradeChecks = new ArrayList<>();
        TeachTaskInfo teachTaskInfo = teachTaskInfoService.findById(id);
        if (!teachTaskTitles.isEmpty()) {
            List<Integer> teachTaskTitleId = new ArrayList<>();
            for (TeachTaskTitle taskTitle : teachTaskTitles) {
                teachTaskTitleId.add(taskTitle.getId());
            }
            Result<TeachTaskContentRecord> teachTaskContentRecords = teachTaskContentService.findInTeachTaskTitleId(teachTaskTitleId);
            if (teachTaskContentRecords.isNotEmpty()) {
                teachTaskContents = teachTaskContentRecords.into(TeachTaskContent.class);
            }

            Result<TeachTaskGradeCheckRecord> teachTaskGradeCheckRecords = teachTaskGradeCheckService.findByTeachTaskInfoAndCheckIsRight(id, Byte.parseByte("0"));
            if (teachTaskContentRecords.isNotEmpty()) {
                teachTaskGradeChecks = teachTaskGradeCheckRecords.into(TeachTaskGradeCheck.class);
            }
        }
        modelMap.addAttribute("teachTaskContents", teachTaskContents);
        modelMap.addAttribute("teachTaskTitles", teachTaskTitles);
        modelMap.addAttribute("teachTaskGradeChecks", teachTaskGradeChecks);
        modelMap.addAttribute("teachTaskInfo", teachTaskInfo);
        return "/administrator/eadmin/assignmentbooklook";
    }

    /**
     * 教学任务书添加页面
     *
     * @return
     */
    @RequestMapping("/administrator/eadmin/assignmentBookAdd")
    public String assignmentBookAdd() {
        return "/administrator/eadmin/assignmentbookadd";
    }

    /**
     * 检验教学任务书标题
     *
     * @param teachTaskTitle
     * @return
     */
    @RequestMapping("/administrator/eadmin//validAddAssignmentBookTitle")
    @ResponseBody
    public Map<String, Object> validAddAssignmentBookTitle(@RequestParam("teachTaskTitle") String teachTaskTitle) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(teachTaskTitle)) {
            List<TeachTaskInfo> teachTaskInfos = teachTaskInfoService.findByTeachTaskInfoTitle(teachTaskTitle);
            if (teachTaskInfos.isEmpty()) {
                map.put("ok", "");
            } else {
                map.put("error", "该标题已存在!");
            }
        } else {
            map.put("error", "请输入标题!");
        }
        return map;
    }

    /**
     * 检验更新时标题
     *
     * @param teachTaskTitle
     * @param id
     * @return
     */
    @RequestMapping("/administrator/eadmin/validUpdateAssignmentBookTitle")
    @ResponseBody
    public Map<String, Object> validUpdateAssignmentBookTitle(@RequestParam("teachTaskTitle") String teachTaskTitle, @RequestParam("id") int id) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(teachTaskTitle)) {
            Result<TeachTaskInfoRecord> records = teachTaskInfoService.findByIdAndTeachTaskInfoTitle(id, teachTaskTitle);
            if (records.isEmpty()) {
                map.put("ok", "");
            } else {
                map.put("error", "该标题已存在!");
            }
        } else {
            map.put("error", "请输入标题!");
        }
        return map;
    }

    /**
     * 更新教学任务书相关信息
     *
     * @param teachTaskInfo
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateAssignmentBook")
    @ResponseBody
    public AjaxData updateAssignmentBook(TeachTaskInfo teachTaskInfo) {
        teachTaskInfoService.update(teachTaskInfo);
        return new AjaxData().success().msg("更新成功!");
    }

    /**
     * 添加教学任务书
     *
     * @param assignmentBookAddVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/addAssignmentBook")
    @ResponseBody
    public AjaxData addAssignmentBook(AssignmentBookAddVo assignmentBookAddVo, HttpServletRequest request) {
        try {
            if (StringUtils.hasLength(assignmentBookAddVo.getFilePath())) {
                String realPath = request.getSession().getServletContext().getRealPath("/") + assignmentBookAddVo.getFilePath();
                File file = new File(realPath);
                if (file.exists()) {
                    Record record = usersService.findAll(usersService.getUserName());
                    TeachTaskInfo teachTaskInfo = new TeachTaskInfo();
                    teachTaskInfo.setTieId(record.getValue(Tables.TIE.ID));
                    teachTaskInfo.setTeachTaskFileUrl(assignmentBookAddVo.getFilePath());
                    teachTaskInfo.setTeachTaskFileSize(file.length() + "");
                    teachTaskInfo.setTeachTaskFileDate(new Timestamp(System.currentTimeMillis()));
                    teachTaskInfo.setTeachTaskTerm(assignmentBookAddVo.getTeachTaskTerm());
                    teachTaskInfo.setTeachTypeId(wordbook.getTeachTypeMap().get(StringUtils.trimWhitespace(assignmentBookAddVo.getTeachType())));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    teachTaskInfo.setTermStartTime(new java.sql.Date(sdf.parse(assignmentBookAddVo.getTermStartTime()).getTime()));
                    teachTaskInfo.setTermEndTime(new java.sql.Date(sdf.parse(assignmentBookAddVo.getTermEndTime()).getTime()));
                    teachTaskInfo.setFileUser(usersService.getUserName());
                    String ext = assignmentBookAddVo.getFilePath().substring(assignmentBookAddVo.getFilePath().lastIndexOf(".") + 1);
                    teachTaskInfo.setFileType(ext);
                    teachTaskInfo.setTeachTaskTitle(assignmentBookAddVo.getTeachTaskTitle());
                    teachTaskInfo.setYearX(assignmentBookAddVo.getYearX());
                    teachTaskInfo.setYearY(assignmentBookAddVo.getYearY());
                    teachTaskInfo.setGradeX(assignmentBookAddVo.getGradeX());
                    teachTaskInfo.setGradeY(assignmentBookAddVo.getGradeY());
                    teachTaskInfo.setGradeNumX(assignmentBookAddVo.getGradeNumX());
                    teachTaskInfo.setGradeNumY(assignmentBookAddVo.getGradeNumY());
                    assignmentBookAddVo.setYearY(assignmentBookAddVo.getYearY() - 1);
                    assignmentBookAddVo.setGradeY(assignmentBookAddVo.getGradeY() - 1);
                    assignmentBookAddVo.setGradeNumY(assignmentBookAddVo.getGradeNumY() - 1);
                    String path = request.getContextPath();
                    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
                    fileService.readFileForTeachTaskInfo(realPath, ext, assignmentBookAddVo, teachTaskInfo, usersService.getUserInfoBySession(), basePath);
                } else {
                    return new AjaxData().fail().msg("文件不存在!");
                }
            } else {
                return new AjaxData().fail().msg("无文件路径!");
            }
        } catch (ParseException e) {
            log.error(" 时间转换异常: {}", e.getMessage());
            return new AjaxData().fail().msg("转换时间异常!");
        }
        return new AjaxData().success().msg("稍后您的邮箱将会收到任务书检验报告!");
    }

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
            Result<Record5<Integer, String, Timestamp, String, String>> record5s = teacherFillTaskTemplateService.findByTieIdAndPage(teacherFillTemplateListVo, tieId);
            if (record5s.isNotEmpty()) {
                List<TeacherFillTemplateListVo> list = record5s.into(TeacherFillTemplateListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teacherFillTemplateListVo.getPageNum());
                paginationData.setPageSize(teacherFillTemplateListVo.getPageSize());
                paginationData.setTotalDatas(teacherFillTaskTemplateService.findByTieIdAndPageCount(teacherFillTemplateListVo, tieId));
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
     * @param teacherFillTaskTemplate
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/addTeacherFillTaskTemplate")
    public String addTeacherFillTaskTemplate(TeacherFillTaskTemplate teacherFillTaskTemplate, ModelMap modelMap) {
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
            //防止刷新时的误添加
            Result<TeacherFillTaskTemplateRecord> records = teacherFillTaskTemplateService.findByTieIdAndTitle(tieId, StringUtils.trimWhitespace(teacherFillTaskTemplate.getTitle()));
            if (records.isEmpty()) {
                templateId = teacherFillTaskTemplateService.saveAndReturnId(teacherFillTaskTemplate);
            } else {
                return "redirect:/administrator/eadmin/teacherFillTemplateSelect";
            }

        } else {
            return "redirect:/administrator/eadmin/teacherFillTemplateSelect";
        }
        List<TeachTaskTitle> titles = teachTaskTitleService.findByTeachTaskInfoId(teacherFillTaskTemplate.getTeachTaskInfoId());
        modelMap.addAttribute("titles", titles);
        modelMap.addAttribute("templateId", templateId);
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
                teacherFillTaskContent.setContentY(tc.getContentY());
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
                    teacherFillTaskContent.setContentY(tc.getContentY());
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
    public String updateTeacherFillTaskTemplate(@RequestParam("templateId") int templateId, @RequestParam("title") String title, ModelMap modelMap) {
        TeacherFillTaskTemplate teacherFillTaskTemplate = teacherFillTaskTemplateService.findById(templateId);
        if (!ObjectUtils.isEmpty(teacherFillTaskTemplate)) {
            teacherFillTaskTemplate.setTitle(title);
            teacherFillTaskTemplateService.update(teacherFillTaskTemplate);
            modelMap.addAttribute("templateId", templateId);
            List<TeachTaskTitle> titles = teachTaskTitleService.findByTeachTaskInfoId(teacherFillTaskTemplate.getTeachTaskInfoId());
            modelMap.addAttribute("titles", titles);
            return "/administrator/eadmin/teacherfilltemplatetitleupdate";
        } else {
            return "redirect:/administrator/eadmin/teacherFillTemplateList";
        }
    }

    /**
     * 更新时 教师模板标题数据
     *
     * @param templateId
     * @return
     */
    @RequestMapping("/administrator/eadmin/TeacherFillTemplateTitleUpdateData")
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
            Result<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> record7s = teacherFillTaskInfoService.findByTieIdAndPage(teacherFillTaskInfoListVo, tieId);
            if (record7s.isNotEmpty()) {
                List<TeacherFillTaskInfoListVo> list = record7s.into(TeacherFillTaskInfoListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teacherFillTaskInfoListVo.getPageNum());
                paginationData.setPageSize(teacherFillTaskInfoListVo.getPageSize());
                paginationData.setTotalDatas(teacherFillTaskInfoService.findByTieIdAndPageCount(teacherFillTaskInfoListVo, tieId));
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
    public String teacherFillTaskInfoAdd(ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<TeacherFillTaskTemplate> list = new ArrayList<>();
        if (tieId > 0) {
            Result<TeacherFillTaskTemplateRecord> records = teacherFillTaskTemplateService.findByTieId(tieId);
            if (records.isNotEmpty()) {
                list = records.into(TeacherFillTaskTemplate.class);
            }
        }
        modelMap.addAttribute("template", list);
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/administrator/eadmin/teacherFillTaskInfoList";
    }

    /**
     * 更新教师模板信息界面
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherFillTaskInfoUpdate")
    public String teacherFillTaskInfoUpdate(@RequestParam("id") int id, ModelMap modelMap) {
        TeacherFillTaskInfo teacherFillTaskInfo = teacherFillTaskInfoService.findById(id);
        if (!ObjectUtils.isEmpty(teacherFillTaskInfo)) {
            modelMap.addAttribute("teacherFillTaskInfo", teacherFillTaskInfo);
            return "/administrator/eadmin/teacherfilltaskinfoupdate";
        } else {
            return "redirect:/administrator/eadmin/teacherFillTaskInfoList";
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/administrator/eadmin/teacherFillTaskInfoList";
    }

    /**
     * 教师填报列表
     *
     * @return
     */
    @RequestMapping("/teacher/eadmin/teacherReportList")
    public String teacherReportList() {
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
                teachTypeId = wordbook.getTeachTypeMap().get(Wordbook.TEACH_TYPE_THEORY);
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
                        r.setStartTimeString(r.getStartTime().toString());
                        r.setEndTimeString(r.getEndTime().toString());
                    }
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
    public String teacherFillTaskContentList(@RequestParam("templateId") int templateId, @RequestParam("teacherTaskInfoId") int teacherTaskInfoId, ModelMap modelMap) {
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
                                teacherFillTaskContent.setTeacherId(1);
                                teacherFillTaskContent.setContentX(contentX);
                                teacherFillTaskContent.setContentY(1);
                                teacherFillTaskContent.setTeacherFillTaskHeadId(th.getId());
                                teacherFillTaskContentService.save(teacherFillTaskContent);
                            }

                        }
                    }
                    return "redirect:/teacher/eadmin/teacherFillTaskContentList?templateId=" + templateId + "&teacherTaskInfoId="+teacherTaskInfoId;
                }
            }
        }
        return "redirect:/teacher/eadmin/teacherReportList";

    }

}
