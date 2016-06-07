package com.school.cbis.web.eadmin;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.*;
import com.school.cbis.service.*;
import com.school.cbis.vo.eadmin.*;
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
public class AssignmentBookController {

    private final Logger log = LoggerFactory.getLogger(AssignmentBookController.class);

    @Resource
    private TeachTaskInfoService teachTaskInfoService;

    @Resource
    private TeachTaskTitleService teachTaskTitleService;

    @Resource
    private TeachTaskContentService teachTaskContentService;

    @Resource
    private TeachTaskGradeCheckService teachTaskGradeCheckService;

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
            int teachTypeId = wordbook.getTeachTypeMap().get(assignmentBookListVo.getTeachType());
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

    /**
     * 变更使用状态
     * @param id
     * @param use
     * @return
     */
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
    public String assignmentBookUpdate(@RequestParam("id") int id,String teachType, ModelMap modelMap) {
        TeachTaskInfo teachTaskInfo = teachTaskInfoService.findById(id);
        if (!ObjectUtils.isEmpty(teachTaskInfo)) {
            modelMap.addAttribute("teachTaskInfo", teachTaskInfo);
            modelMap.addAttribute("teachType",teachType);
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
    public String assignmentBookLook(@RequestParam("id") int id,String teachType, ModelMap modelMap) {
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
        modelMap.addAttribute("teachType",teachType);
        return "/administrator/eadmin/assignmentbooklook";
    }

    /**
     * 教学任务书添加页面
     *
     * @return
     */
    @RequestMapping("/administrator/eadmin/assignmentBookAdd")
    public String assignmentBookAdd(String teachType,ModelMap modelMap) {
        modelMap.addAttribute("teachType",teachType);
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
        TeachTaskInfo update = teachTaskInfoService.findById(teachTaskInfo.getId());
        update.setTeachTaskTitle(teachTaskInfo.getTeachTaskTitle());
        update.setTeachTaskTerm(teachTaskInfo.getTeachTaskTerm());
        update.setTermStartTime(teachTaskInfo.getTermStartTime());
        update.setTermEndTime(teachTaskInfo.getTermEndTime());
        teachTaskInfoService.update(update);
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

}
