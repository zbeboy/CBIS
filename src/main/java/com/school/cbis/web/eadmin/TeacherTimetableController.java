package com.school.cbis.web.eadmin;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.service.*;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.eadmin.AddTeacherTimetableVo;
import com.school.cbis.vo.eadmin.TeacherInfoVo;
import com.school.cbis.vo.eadmin.TeacherTimetableListVo;
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
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016-06-06.
 */
@Controller
public class TeacherTimetableController {

    private final Logger log = LoggerFactory.getLogger(TeacherTimetableController.class);

    @Resource
    private TeacherCourseTimetableInfoService teacherCourseTimetableInfoService;

    @Resource
    private UsersService usersService;

    @Resource
    private MajorService majorService;

    @Resource
    private FileService fileService;

    @Resource
    private Wordbook wordbook;

    @Resource
    private TeacherService teacherService;

    /**
     * 教师课表列表页
     *
     * @param teacherTimetableListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherTimetableList")
    public String teacherTimetableList(TeacherTimetableListVo teacherTimetableListVo, ModelMap modelMap) {
        if (!StringUtils.hasLength(teacherTimetableListVo.getTimetableInfoTerm())) {
            teacherTimetableListVo.setTimetableInfoTerm("");
        }
        modelMap.addAttribute("teacherTimetableListVo", teacherTimetableListVo);
        return "/administrator/eadmin/teachertimetablelist";
    }

    /**
     * 教师课表列表页数据
     *
     * @param teacherTimetableListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherTimetableData")
    @ResponseBody
    public AjaxData<TeacherTimetableListVo> teacherTimetableData(TeacherTimetableListVo teacherTimetableListVo) {
        AjaxData<TeacherTimetableListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            Result<Record14<Integer, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date, String, String, String>> record14s =
                    teacherCourseTimetableInfoService.findByTieIdAndPage(teacherTimetableListVo, tieId);
            if (record14s.isNotEmpty()) {
                List<TeacherTimetableListVo> list = record14s.into(TeacherTimetableListVo.class);
                list.forEach(s -> {
                    s.setTimetableInfoFileSize(FilesUtils.sizeToString(Long.parseLong(s.getTimetableInfoFileSize())));
                });
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(teacherTimetableListVo.getPageNum());
                paginationData.setPageSize(teacherTimetableListVo.getPageSize());
                paginationData.setTotalDatas(teacherCourseTimetableInfoService.findByTieIdAndPageCount(teacherTimetableListVo, tieId));
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
     * 教师课表添加界面
     *
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherTimetableAdd")
    public String teacherTimetableAdd() {
        return "/administrator/eadmin/teachertimetableadd";
    }

    /**
     * 保存
     *
     * @param addTeacherTimetableVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/addTeacherTimetable")
    @ResponseBody
    public AjaxData addTeacherTimetable(AddTeacherTimetableVo addTeacherTimetableVo, HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        try {
            String inputFile = request.getSession().getServletContext().getRealPath("/") + addTeacherTimetableVo.getFilePath();
            String outputFile =
                    request.getSession().getServletContext().getRealPath("/") + addTeacherTimetableVo.getFilePath().substring(0, addTeacherTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf";
            File file = new File(inputFile);
            if (fileService.convert2PDF(inputFile, outputFile)) {
                Record record = usersService.findAll(usersService.getUserName());
                int tieId = 0;
                if (!ObjectUtils.isEmpty(record)) {
                    tieId = record.getValue(Tables.TIE.ID);
                }
                if (tieId > 0) {
                    TeacherCourseTimetableInfo teacherCourseTimetableInfo = new TeacherCourseTimetableInfo();
                    teacherCourseTimetableInfo.setTimetableInfoFileName(addTeacherTimetableVo.getTimetableInfoFileName());
                    teacherCourseTimetableInfo.setTimetableInfoTerm(addTeacherTimetableVo.getTimetableInfoTerm());
                    teacherCourseTimetableInfo.setFileUser(usersService.getUserName());
                    teacherCourseTimetableInfo.setTimetableInfoFileDate(new Timestamp(System.currentTimeMillis()));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    teacherCourseTimetableInfo.setTermStartTime(new Date(sdf.parse(addTeacherTimetableVo.getTermStartTime()).getTime()));
                    teacherCourseTimetableInfo.setTermEndTime(new Date(sdf.parse(addTeacherTimetableVo.getTermEndTime()).getTime()));
                    teacherCourseTimetableInfo.setTieId(tieId);
                    teacherCourseTimetableInfo.setTimetableInfoFileUrl(addTeacherTimetableVo.getFilePath());
                    teacherCourseTimetableInfo.setFileType(addTeacherTimetableVo.getFilePath().substring(addTeacherTimetableVo.getFilePath().lastIndexOf(".") + 1));
                    teacherCourseTimetableInfo.setTeachTypeId(wordbook.getTeachTypeMap().get(addTeacherTimetableVo.getTeachType()));
                    teacherCourseTimetableInfo.setTimetableInfoFileDownTimes(0);
                    teacherCourseTimetableInfo.setTimetableInfoFileSize(file.length() + "");
                    teacherCourseTimetableInfo.setTeacherId(addTeacherTimetableVo.getTeacherId());
                    teacherCourseTimetableInfo.setTimetableInfoFilePdf(addTeacherTimetableVo.getFilePath().substring(0, addTeacherTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf");
                    teacherCourseTimetableInfoService.save(teacherCourseTimetableInfo);
                    ajaxData.success().msg("保存信息成功!");
                } else {
                    ajaxData.fail().msg("获取用户信息失败!");
                }

            } else {
                ajaxData.fail().msg("转换为pdf时异常!");
            }
        } catch (ParseException e) {
            ajaxData.fail().msg("转换时间异常!");
        }
        return ajaxData;
    }

    /**
     * 预览
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherTimetableLook")
    public String teacherTimetableLook(@RequestParam("id") int id, ModelMap modelMap) {
        TeacherCourseTimetableInfo teacherCourseTimetableInfo = teacherCourseTimetableInfoService.findById(id);
        if (!ObjectUtils.isEmpty(teacherCourseTimetableInfo)) {
            modelMap.addAttribute("file_path", teacherCourseTimetableInfo.getTimetableInfoFilePdf());
            return "/administrator/eadmin/teachertimetablelook";
        }
        return "redirect:/administrator/eadmin/teacherTimetableList";
    }

    /**
     * 更新界面
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/teacherTimetableUpdate")
    public String teacherTimetableUpdate(@RequestParam("id") int id, ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            TeacherCourseTimetableInfo teacherCourseTimetableInfo = teacherCourseTimetableInfoService.findById(id);
            if (!ObjectUtils.isEmpty(teacherCourseTimetableInfo)) {
                Teacher teacher = teacherService.findById(teacherCourseTimetableInfo.getTeacherId());
                modelMap.addAttribute("teacher", teacher);
                modelMap.addAttribute("teacherCourseTimetableInfo", teacherCourseTimetableInfo);
                return "/administrator/eadmin/teachertimetableupdate";
            }
        }
        return "redirect:/administrator/eadmin/teacherTimetableList";
    }

    /**
     * 更新
     * @param addTeacherTimetableVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateTeacherTimetable")
    @ResponseBody
    public AjaxData updateTeacherTimetable(AddTeacherTimetableVo addTeacherTimetableVo, HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        try {
            TeacherCourseTimetableInfo teacherCourseTimetableInfo = teacherCourseTimetableInfoService.findById(addTeacherTimetableVo.getId());
            teacherCourseTimetableInfo.setTimetableInfoFileName(addTeacherTimetableVo.getTimetableInfoFileName());
            teacherCourseTimetableInfo.setTimetableInfoTerm(addTeacherTimetableVo.getTimetableInfoTerm());
            teacherCourseTimetableInfo.setTimetableInfoFileDate(new Timestamp(System.currentTimeMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            teacherCourseTimetableInfo.setTermStartTime(new Date(sdf.parse(addTeacherTimetableVo.getTermStartTime()).getTime()));
            teacherCourseTimetableInfo.setTermEndTime(new Date(sdf.parse(addTeacherTimetableVo.getTermEndTime()).getTime()));
            teacherCourseTimetableInfo.setTeacherId(addTeacherTimetableVo.getTeacherId());

            if (!teacherCourseTimetableInfo.getTimetableInfoFileUrl().equals(StringUtils.trimWhitespace(addTeacherTimetableVo.getFilePath()))) {
                String inputFile = request.getSession().getServletContext().getRealPath("/") + addTeacherTimetableVo.getFilePath();
                String outputFile =
                        request.getSession().getServletContext().getRealPath("/") + addTeacherTimetableVo.getFilePath().substring(0, addTeacherTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf";
                File file = new File(inputFile);
                if (fileService.convert2PDF(inputFile, outputFile)) {
                    teacherCourseTimetableInfo.setTimetableInfoFileUrl(addTeacherTimetableVo.getFilePath());
                    teacherCourseTimetableInfo.setFileType(addTeacherTimetableVo.getFilePath().substring(addTeacherTimetableVo.getFilePath().lastIndexOf(".") + 1));
                    teacherCourseTimetableInfo.setTimetableInfoFileSize(file.length() + "");
                    teacherCourseTimetableInfo.setTimetableInfoFilePdf(addTeacherTimetableVo.getFilePath().substring(0, addTeacherTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf");
                } else {
                    ajaxData.fail().msg("转换为pdf时异常!");
                    return ajaxData;
                }
            }
            teacherCourseTimetableInfoService.update(teacherCourseTimetableInfo);
            ajaxData.success().msg("保存信息成功!");
        } catch (ParseException e) {
            ajaxData.fail().msg("转换时间异常!");
        }
        return ajaxData;
    }

    /**
     * 删除
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/deleteTeacherTimetable")
    @ResponseBody
    public AjaxData deleteTeacherTimetable(@RequestParam("id") int id, HttpServletRequest request){
        try {
            TeacherCourseTimetableInfo teacherCourseTimetableInfo = teacherCourseTimetableInfoService.findById(id);
            if (!ObjectUtils.isEmpty(teacherCourseTimetableInfo)) {
                String webPath = request.getSession().getServletContext().getRealPath("/");
                FilesUtils.deleteFile(webPath + teacherCourseTimetableInfo.getTimetableInfoFileUrl());
                FilesUtils.deleteFile(webPath + teacherCourseTimetableInfo.getTimetableInfoFilePdf());
                teacherCourseTimetableInfoService.deleteById(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AjaxData().success().msg("删除成功!");
    }
}

