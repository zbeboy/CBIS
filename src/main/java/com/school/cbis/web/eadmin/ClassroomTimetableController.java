package com.school.cbis.web.eadmin;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo;
import com.school.cbis.domain.tables.pojos.Teacher;
import com.school.cbis.domain.tables.pojos.TeacherCourseTimetableInfo;
import com.school.cbis.domain.tables.records.ClassroomCourseTimetableInfoRecord;
import com.school.cbis.service.ClassroomCourseTimetableInfoService;
import com.school.cbis.service.FileService;
import com.school.cbis.service.UsersService;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.eadmin.AddClassroomTimetableVo;
import com.school.cbis.vo.eadmin.AddTeacherTimetableVo;
import com.school.cbis.vo.eadmin.ClassroomTimetableListVo;
import org.jooq.Record;
import org.jooq.Result;
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
import java.util.List;

/**
 * Created by lenovo on 2016-06-06.
 */
@Controller
public class ClassroomTimetableController {

    private final Logger log = LoggerFactory.getLogger(ClassroomTimetableController.class);

    @Resource
    private ClassroomCourseTimetableInfoService classroomCourseTimetableInfoService;

    @Resource
    private UsersService usersService;

    @Resource
    private FileService fileService;

    @Resource
    private Wordbook wordbook;

    /**
     * 教室课表列表
     * @param classroomTimetableListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/classroomTimetableList")
    public String classroomTimetableList(ClassroomTimetableListVo classroomTimetableListVo, ModelMap modelMap){
        if (!StringUtils.hasLength(classroomTimetableListVo.getTimetableInfoTerm())) {
            classroomTimetableListVo.setTimetableInfoTerm("");
        }
        modelMap.addAttribute("classroomTimetableListVo", classroomTimetableListVo);
        return "/administrator/eadmin/classroomtimetablelist";
    }

    /**
     * 教室课表列表数据
     * @param classroomTimetableListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/classroomTimetableData")
    @ResponseBody
    public AjaxData<ClassroomTimetableListVo> classroomTimetableData(ClassroomTimetableListVo classroomTimetableListVo){
        AjaxData<ClassroomTimetableListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        if(tieId>0){
            Result<ClassroomCourseTimetableInfoRecord> records = classroomCourseTimetableInfoService.findByTieIdAndPage(classroomTimetableListVo,tieId);
            if(records.isNotEmpty()){
                List<ClassroomTimetableListVo> list = records.into(ClassroomTimetableListVo.class);
                list.forEach(s->{
                    s.setTimetableInfoFileSize(FilesUtils.sizeToString(Long.parseLong(s.getTimetableInfoFileSize())));
                });
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(classroomTimetableListVo.getPageNum());
                paginationData.setPageSize(classroomTimetableListVo.getPageSize());
                paginationData.setTotalDatas(classroomCourseTimetableInfoService.findByTieIdAndPageCount(classroomTimetableListVo,tieId));
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
     * 教室课表添加界面
     *
     * @return
     */
    @RequestMapping("/administrator/eadmin/classroomTimetableAdd")
    public String classroomTimetableAdd() {
        return "/administrator/eadmin/classroomtimetableadd";
    }

    /**
     * 保存
     *
     * @param addClassroomTimetableVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/addClassroomTimetable")
    @ResponseBody
    public AjaxData addClassroomTimetable(AddClassroomTimetableVo addClassroomTimetableVo, HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        try {
            String inputFile = request.getSession().getServletContext().getRealPath("/") + addClassroomTimetableVo.getFilePath();
            String outputFile =
                    request.getSession().getServletContext().getRealPath("/") + addClassroomTimetableVo.getFilePath().substring(0, addClassroomTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf";
            File file = new File(inputFile);
            if (fileService.convert2PDF(inputFile, outputFile)) {
                Record record = usersService.findAll(usersService.getUserName());
                int tieId = 0;
                if (!ObjectUtils.isEmpty(record)) {
                    tieId = record.getValue(Tables.TIE.ID);
                }
                if (tieId > 0) {
                    ClassroomCourseTimetableInfo classroomCourseTimetableInfo = new ClassroomCourseTimetableInfo();
                    classroomCourseTimetableInfo.setTimetableInfoFileName(addClassroomTimetableVo.getTimetableInfoFileName());
                    classroomCourseTimetableInfo.setTimetableInfoTerm(addClassroomTimetableVo.getTimetableInfoTerm());
                    classroomCourseTimetableInfo.setFileUser(usersService.getUserName());
                    classroomCourseTimetableInfo.setTimetableInfoFileDate(new Timestamp(System.currentTimeMillis()));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    classroomCourseTimetableInfo.setTermStartTime(new Date(sdf.parse(addClassroomTimetableVo.getTermStartTime()).getTime()));
                    classroomCourseTimetableInfo.setTermEndTime(new Date(sdf.parse(addClassroomTimetableVo.getTermEndTime()).getTime()));
                    classroomCourseTimetableInfo.setTieId(tieId);
                    classroomCourseTimetableInfo.setTimetableInfoFileUrl(addClassroomTimetableVo.getFilePath());
                    classroomCourseTimetableInfo.setFileType(addClassroomTimetableVo.getFilePath().substring(addClassroomTimetableVo.getFilePath().lastIndexOf(".") + 1));
                    classroomCourseTimetableInfo.setTeachTypeId(wordbook.getTeachTypeMap().get(addClassroomTimetableVo.getTeachType()));
                    classroomCourseTimetableInfo.setTimetableInfoFileDownTimes(0);
                    classroomCourseTimetableInfo.setTimetableInfoFileSize(file.length() + "");
                    classroomCourseTimetableInfo.setClassroom(addClassroomTimetableVo.getClassroom());
                    classroomCourseTimetableInfo.setTimetableInfoFilePdf(addClassroomTimetableVo.getFilePath().substring(0, addClassroomTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf");
                    classroomCourseTimetableInfoService.save(classroomCourseTimetableInfo);
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
    @RequestMapping("/administrator/eadmin/classroomTimetableLook")
    public String classroomTimetableLook(@RequestParam("id") int id, ModelMap modelMap) {
        ClassroomCourseTimetableInfo classroomCourseTimetableInfo = classroomCourseTimetableInfoService.findById(id);
        if (!ObjectUtils.isEmpty(classroomCourseTimetableInfo)) {
            modelMap.addAttribute("file_path", classroomCourseTimetableInfo.getTimetableInfoFilePdf());
            return "/administrator/eadmin/classroomtimetablelook";
        }
        return "redirect:/administrator/eadmin/classroomTimetableList";
    }

    /**
     * 更新界面
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/classroomTimetableUpdate")
    public String classroomTimetableUpdate(@RequestParam("id") int id, ModelMap modelMap) {
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        if (tieId > 0) {
            ClassroomCourseTimetableInfo classroomCourseTimetableInfo = classroomCourseTimetableInfoService.findById(id);
            if (!ObjectUtils.isEmpty(classroomCourseTimetableInfo)) {
                modelMap.addAttribute("classroomCourseTimetableInfo", classroomCourseTimetableInfo);
                return "/administrator/eadmin/classroomtimetableupdate";
            }
        }
        return "redirect:/administrator/eadmin/classroomTimetableList";
    }

    /**
     * 更新
     * @param addClassroomTimetableVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateClassroomTimetable")
    @ResponseBody
    public AjaxData updateClassroomTimetable(AddClassroomTimetableVo addClassroomTimetableVo, HttpServletRequest request) {
        AjaxData ajaxData = new AjaxData();
        try {
            ClassroomCourseTimetableInfo classroomCourseTimetableInfo = classroomCourseTimetableInfoService.findById(addClassroomTimetableVo.getId());
            classroomCourseTimetableInfo.setTimetableInfoFileName(addClassroomTimetableVo.getTimetableInfoFileName());
            classroomCourseTimetableInfo.setTimetableInfoTerm(addClassroomTimetableVo.getTimetableInfoTerm());
            classroomCourseTimetableInfo.setTimetableInfoFileDate(new Timestamp(System.currentTimeMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            classroomCourseTimetableInfo.setTermStartTime(new Date(sdf.parse(addClassroomTimetableVo.getTermStartTime()).getTime()));
            classroomCourseTimetableInfo.setTermEndTime(new Date(sdf.parse(addClassroomTimetableVo.getTermEndTime()).getTime()));
            classroomCourseTimetableInfo.setClassroom(addClassroomTimetableVo.getClassroom());

            if (!classroomCourseTimetableInfo.getTimetableInfoFileUrl().equals(StringUtils.trimWhitespace(addClassroomTimetableVo.getFilePath()))) {
                String inputFile = request.getSession().getServletContext().getRealPath("/") + addClassroomTimetableVo.getFilePath();
                String outputFile =
                        request.getSession().getServletContext().getRealPath("/") + addClassroomTimetableVo.getFilePath().substring(0, addClassroomTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf";
                File file = new File(inputFile);
                if (fileService.convert2PDF(inputFile, outputFile)) {
                    classroomCourseTimetableInfo.setTimetableInfoFileUrl(addClassroomTimetableVo.getFilePath());
                    classroomCourseTimetableInfo.setFileType(addClassroomTimetableVo.getFilePath().substring(addClassroomTimetableVo.getFilePath().lastIndexOf(".") + 1));
                    classroomCourseTimetableInfo.setTimetableInfoFileSize(file.length() + "");
                    classroomCourseTimetableInfo.setTimetableInfoFilePdf(addClassroomTimetableVo.getFilePath().substring(0, addClassroomTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf");
                } else {
                    ajaxData.fail().msg("转换为pdf时异常!");
                    return ajaxData;
                }
            }
            classroomCourseTimetableInfoService.update(classroomCourseTimetableInfo);
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
    @RequestMapping("/administrator/eadmin/deleteClassroomTimetable")
    @ResponseBody
    public AjaxData deleteClassroomTimetable(@RequestParam("id") int id, HttpServletRequest request){
        try {
            ClassroomCourseTimetableInfo classroomCourseTimetableInfo = classroomCourseTimetableInfoService.findById(id);
            if (!ObjectUtils.isEmpty(classroomCourseTimetableInfo)) {
                String webPath = request.getSession().getServletContext().getRealPath("/");
                FilesUtils.deleteFile(webPath + classroomCourseTimetableInfo.getTimetableInfoFileUrl());
                FilesUtils.deleteFile(webPath + classroomCourseTimetableInfo.getTimetableInfoFilePdf());
                classroomCourseTimetableInfoService.deleteById(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AjaxData().success().msg("删除成功!");
    }
}
