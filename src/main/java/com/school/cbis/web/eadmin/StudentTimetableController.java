package com.school.cbis.web.eadmin;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Grade;
import com.school.cbis.domain.tables.pojos.StudentCourseTimetableInfo;
import com.school.cbis.domain.tables.pojos.TeachCourseInfo;
import com.school.cbis.service.FileService;
import com.school.cbis.service.GradeService;
import com.school.cbis.service.StudentCourseTimetableInfoService;
import com.school.cbis.service.UsersService;
import com.school.cbis.util.FilesUtils;
import com.school.cbis.vo.eadmin.AddStudentTimetableVo;
import com.school.cbis.vo.eadmin.StudentTimetableListVo;
import org.apache.commons.lang3.CharEncoding;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record14;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
public class StudentTimetableController {

    private final Logger log = LoggerFactory.getLogger(StudentTimetableController.class);

    @Resource
    private StudentCourseTimetableInfoService studentCourseTimetableInfoService;

    @Resource
    private UsersService usersService;

    @Resource
    private GradeService gradeService;

    @Resource
    private FileService fileService;

    @Resource
    private Wordbook wordbook;

    /**
     * 学生课表列表页
     * @param studentTimetableListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/studentTimetableList")
    public String studentTimetableList(StudentTimetableListVo studentTimetableListVo, ModelMap modelMap){
        if(!StringUtils.hasLength(studentTimetableListVo.getTimetableInfoTerm())){
            studentTimetableListVo.setTimetableInfoTerm("");
        }
        modelMap.addAttribute("studentTimetableListVo",studentTimetableListVo);
        return "/administrator/eadmin/studenttimetablelist";
    }

    /**
     * 学生课表列表数据
     * @param studentTimetableListVo
     * @return
     */
    @RequestMapping("/administrator/eadmin/studentTimetableData")
    @ResponseBody
    public AjaxData<StudentTimetableListVo> studentTimetableData(StudentTimetableListVo studentTimetableListVo){
        AjaxData<StudentTimetableListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }

        if(tieId>0){
            studentTimetableListVo.setTeachTypeId(wordbook.getTeachTypeMap().get(StringUtils.trimWhitespace(studentTimetableListVo.getTeachType())));
            Result<Record14<Integer,String,String,String,String,String,String,Timestamp,Integer,Integer,Date,Date,String,String>> record14s =
                    studentCourseTimetableInfoService.findByTieIdAndTeachTypeIdAndPage(studentTimetableListVo,tieId);
            if(record14s.isNotEmpty()){
                List<StudentTimetableListVo> list = record14s.into(StudentTimetableListVo.class);
                list.forEach(s->{
                    s.setTimetableInfoFileSize(FilesUtils.sizeToString(Long.parseLong(s.getTimetableInfoFileSize())));
                });
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(studentTimetableListVo.getPageNum());
                paginationData.setPageSize(studentTimetableListVo.getPageSize());
                paginationData.setTotalDatas(studentCourseTimetableInfoService.findByTieIdAndTeachTypeIdAndPageCount(studentTimetableListVo,tieId));
                ajaxData.success().listData(list).paginationData(paginationData);
            } else {
                ajaxData.fail();
            }
        }else {
            ajaxData.fail();
        }
        return ajaxData;
    }

    /**
     * 添加界面
     * @return
     */
    @RequestMapping("/administrator/eadmin/studentTimetableAdd")
    public String studentTimetableAdd(String teachType ,ModelMap modelMap){
        try{
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if(!ObjectUtils.isEmpty(record)){
                tieId = record.getValue(Tables.TIE.ID);
            }

            if(tieId>0){
                List<String> list = new ArrayList<>();
                Result<Record1<String>> record1s = gradeService.findAllYearDistinct(tieId);
                if (record1s.isNotEmpty()) {
                    for (Record r : record1s) {
                        list.add(r.getValue("year").toString());
                    }
                }
                modelMap.addAttribute("years", list);
                modelMap.addAttribute("teachType",teachType);
                return "/administrator/eadmin/studenttimetableadd";
            }
            teachType = URLEncoder.encode(teachType, CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "redirect:/administrator/eadmin/studentTimetableList?teachType="+teachType;
    }

    /**
     * 保存
     * @param addStudentTimetableVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/addStudentTimetable")
    @ResponseBody
    public AjaxData addStudentTimetable(AddStudentTimetableVo addStudentTimetableVo, HttpServletRequest request){
        AjaxData ajaxData = new AjaxData();
        try {
                String inputFile = request.getSession().getServletContext().getRealPath("/") + addStudentTimetableVo.getFilePath();
                String outputFile =
                        request.getSession().getServletContext().getRealPath("/") + addStudentTimetableVo.getFilePath().substring(0, addStudentTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf";
                File file = new File(inputFile);
                if (fileService.convert2PDF(inputFile, outputFile)) {
                    StudentCourseTimetableInfo studentCourseTimetableInfo = new StudentCourseTimetableInfo();
                    studentCourseTimetableInfo.setTimetableInfoFileName(addStudentTimetableVo.getTimetableInfoFileName());
                    studentCourseTimetableInfo.setTimetableInfoTerm(addStudentTimetableVo.getTimetableInfoTerm());
                    studentCourseTimetableInfo.setFileUser(usersService.getUserName());
                    studentCourseTimetableInfo.setTimetableInfoFileDate(new Timestamp(System.currentTimeMillis()));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    studentCourseTimetableInfo.setTermStartTime(new Date(sdf.parse(addStudentTimetableVo.getTermStartTime()).getTime()));
                    studentCourseTimetableInfo.setTermEndTime(new Date(sdf.parse(addStudentTimetableVo.getTermEndTime()).getTime()));
                    studentCourseTimetableInfo.setTimetableInfoFileUrl(addStudentTimetableVo.getFilePath());
                    studentCourseTimetableInfo.setFileType(addStudentTimetableVo.getFilePath().substring(addStudentTimetableVo.getFilePath().lastIndexOf(".") + 1));
                    studentCourseTimetableInfo.setTeachTypeId(wordbook.getTeachTypeMap().get(addStudentTimetableVo.getTeachType()));
                    studentCourseTimetableInfo.setTimetableInfoFileDownTimes(0);
                    studentCourseTimetableInfo.setTimetableInfoFileSize(file.length() + "");
                    studentCourseTimetableInfo.setGradeId(addStudentTimetableVo.getGradeId());
                    studentCourseTimetableInfo.setTimetableInfoFilePdf(addStudentTimetableVo.getFilePath().substring(0, addStudentTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf");
                    studentCourseTimetableInfoService.save(studentCourseTimetableInfo);
                    ajaxData.success().msg("保存信息成功!");
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
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/studentTimetableLook")
    public String studentTimetableLook(@RequestParam("id") int id,String teachType,ModelMap modelMap){
        try{
            StudentCourseTimetableInfo studentCourseTimetableInfo = studentCourseTimetableInfoService.findById(id);
            if (!ObjectUtils.isEmpty(studentCourseTimetableInfo)) {
                modelMap.addAttribute("file_path", studentCourseTimetableInfo.getTimetableInfoFilePdf());
                return "/administrator/eadmin/studenttimetablelook";
            }
            teachType = URLEncoder.encode(teachType,CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/administrator/eadmin/studentTimetableList?teachType="+teachType;
    }

    /**
     * 学生更新界面
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/eadmin/studentTimetableUpdate")
    public String studentTimetableUpdate(@RequestParam("id") int id,String teachType,ModelMap modelMap){
        try{
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if(!ObjectUtils.isEmpty(record)){
                tieId = record.getValue(Tables.TIE.ID);
            }
            if(tieId>0){
                StudentCourseTimetableInfo studentCourseTimetableInfo = studentCourseTimetableInfoService.findById(id);
                if (!ObjectUtils.isEmpty(studentCourseTimetableInfo)) {
                    List<String> list = new ArrayList<>();
                    Result<Record1<String>> record1s = gradeService.findAllYearDistinct(tieId);
                    if (record1s.isNotEmpty()) {
                        for (Record r : record1s) {
                            list.add(r.getValue("year").toString());
                        }
                    }

                    Grade grade = gradeService.findById(studentCourseTimetableInfo.getGradeId());
                    modelMap.addAttribute("years", list);
                    modelMap.addAttribute("grade",grade);
                    modelMap.addAttribute("studentCourseTimetableInfo", studentCourseTimetableInfo);
                    modelMap.addAttribute("teachType",teachType);
                    return "/administrator/eadmin/studenttimetableupdate";
                }
            }
            teachType = URLEncoder.encode(teachType,CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/administrator/eadmin/studentTimetableList?teachType="+teachType;
    }

    /**
     * 更新
     * @param addStudentTimetableVo
     * @param request
     * @return
     */
    @RequestMapping("/administrator/eadmin/updateStudentTimetable")
    @ResponseBody
    public AjaxData updateStudentTimetable(AddStudentTimetableVo addStudentTimetableVo, HttpServletRequest request){
        AjaxData ajaxData = new AjaxData();
        try {
            StudentCourseTimetableInfo studentCourseTimetableInfo = studentCourseTimetableInfoService.findById(addStudentTimetableVo.getId());
            studentCourseTimetableInfo.setTimetableInfoFileName(addStudentTimetableVo.getTimetableInfoFileName());
            studentCourseTimetableInfo.setTimetableInfoTerm(addStudentTimetableVo.getTimetableInfoTerm());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            studentCourseTimetableInfo.setTermStartTime(new Date(sdf.parse(addStudentTimetableVo.getTermStartTime()).getTime()));
            studentCourseTimetableInfo.setTermEndTime(new Date(sdf.parse(addStudentTimetableVo.getTermEndTime()).getTime()));
            studentCourseTimetableInfo.setGradeId(addStudentTimetableVo.getGradeId());

            if (!studentCourseTimetableInfo.getTimetableInfoFileUrl().equals(StringUtils.trimWhitespace(addStudentTimetableVo.getFilePath()))) {
                String inputFile = request.getSession().getServletContext().getRealPath("/") + addStudentTimetableVo.getFilePath();
                String outputFile =
                        request.getSession().getServletContext().getRealPath("/") + addStudentTimetableVo.getFilePath().substring(0, addStudentTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf";
                File file = new File(inputFile);
                if (fileService.convert2PDF(inputFile, outputFile)) {
                    studentCourseTimetableInfo.setTimetableInfoFileUrl(addStudentTimetableVo.getFilePath());
                    studentCourseTimetableInfo.setFileType(addStudentTimetableVo.getFilePath().substring(addStudentTimetableVo.getFilePath().lastIndexOf(".") + 1));
                    studentCourseTimetableInfo.setTimetableInfoFileSize(file.length() + "");
                    studentCourseTimetableInfo.setTimetableInfoFilePdf(addStudentTimetableVo.getFilePath().substring(0, addStudentTimetableVo.getFilePath().lastIndexOf(".")) + ".pdf");
                } else {
                    ajaxData.fail().msg("转换为pdf时异常!");
                    return ajaxData;
                }
            }
            studentCourseTimetableInfoService.update(studentCourseTimetableInfo);
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
    @RequestMapping("/administrator/eadmin/deleteStudentTimetable")
    @ResponseBody
    public AjaxData deleteStudentTimetable(@RequestParam("id") int id, HttpServletRequest request){
        try {
            StudentCourseTimetableInfo studentCourseTimetableInfo = studentCourseTimetableInfoService.findById(id);
            if (!ObjectUtils.isEmpty(studentCourseTimetableInfo)) {
                String webPath = request.getSession().getServletContext().getRealPath("/");
                FilesUtils.deleteFile(webPath + studentCourseTimetableInfo.getTimetableInfoFileUrl());
                FilesUtils.deleteFile(webPath + studentCourseTimetableInfo.getTimetableInfoFilePdf());
                studentCourseTimetableInfoService.deleteById(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AjaxData().success().msg("删除成功!");
    }
}
