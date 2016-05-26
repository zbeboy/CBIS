package com.school.cbis.web.grade;

import com.school.cbis.data.AjaxData;
import com.school.cbis.data.AutoCompleteData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Grade;
import com.school.cbis.domain.tables.pojos.Student;
import com.school.cbis.domain.tables.pojos.Teacher;
import com.school.cbis.domain.tables.records.GradeRecord;
import com.school.cbis.plugin.jsgrid.JsGrid;
import com.school.cbis.service.GradeService;
import com.school.cbis.service.StudentService;
import com.school.cbis.service.TeacherService;
import com.school.cbis.service.UsersService;
import com.school.cbis.vo.grade.GradeVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-02-14.
 */
@Controller
public class GradeManagerController {

    private final Logger log = LoggerFactory.getLogger(GradeManagerController.class);

    @Resource
    private UsersService usersService;

    @Resource
    private GradeService gradeService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    /**
     * 班级数据
     *
     * @param gradeVo
     * @return
     */
    @RequestMapping("/maintainer/grade/gradeData")
    @ResponseBody
    public AjaxData<GradeVo> gradeData(GradeVo gradeVo) {
        AjaxData<GradeVo> ajaxData = new AjaxData<>();
        //通过用户类型获取系表ID
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<GradeVo> gradeVos = new ArrayList<>();
        if (tieId > 0) {
            Result<Record7<Integer,String, Integer, String, String, String, String>> record7s = gradeService.findAllByPage(gradeVo, tieId);
            if (record7s.isNotEmpty()) {
                gradeVos = record7s.into(GradeVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(gradeVo.getPageNum());
                paginationData.setPageSize(gradeVo.getPageSize());
                paginationData.setTotalDatas(gradeService.findAllByPageCount(gradeVo, tieId));
                ajaxData.success().listData(gradeVos).paginationData(paginationData);
            } else {
                ajaxData.fail().listData(gradeVos);
            }
        } else {
            ajaxData.fail().listData(gradeVos);
        }
        return ajaxData;
    }

    /**
     * 更新班级所需信息
     * @param id
     * @return
     */
    @RequestMapping("/maintainer/grade/gradeUpdate")
    @ResponseBody
    public AjaxData updateGrade(@RequestParam("id") int id){
        Record7<Integer,String, Integer, String, String, String, String> record7 = gradeService.findByGradeIdWithUpdate(id);
        if(!ObjectUtils.isEmpty(record7)){
            GradeVo gradeVo = record7.into(GradeVo.class);
            return new AjaxData().success().obj(gradeVo);
        } else {
            return new AjaxData().fail().msg("获取信息失败!");
        }
    }

    /**
     * 班级主任自动完成数据
     *
     * @param search
     * @return
     */
    @RequestMapping("/maintainer/grade/gradeHead")
    @ResponseBody
    public List<AutoCompleteData> gradeHead(String search) {
        List<AutoCompleteData> autoCompleteDatas = new ArrayList<>();
        //通过用户类型获取系表ID
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        Result<Record4<Integer,Integer,String,String>> teachers = teacherService.findByTieIdAndTeacherName(search, tieId);
        if (teachers.isNotEmpty()) {
            teachers.forEach(teacherRecord -> {
                AutoCompleteData data = new AutoCompleteData();
                data.setValue(teacherRecord.getValue(Tables.TEACHER.TEACHER_JOB_NUMBER));
                data.setTitle(teacherRecord.getValue(Tables.USERS.REAL_NAME));
                data.setUrl("#");
                data.setText("账号:" + teacherRecord.getValue(Tables.TEACHER.TEACHER_JOB_NUMBER));
                autoCompleteDatas.add(data);
            });
        }
        return autoCompleteDatas;
    }

    /**
     * 检查教师编号是否存在
     *
     * @param teacherJobNum
     * @return
     */
    @RequestMapping(value = "/maintainer/grade/checkTeacherNum", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkTeacherNum(@RequestParam(value = "gradeHeadID") String teacherJobNum) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(teacherJobNum)) {
            List<Teacher> teachers = teacherService.findByTeacherJobNumber(teacherJobNum);
            if (teachers.isEmpty()) {
                map.put("error", "教师不存在!");
            } else {
                map.put("ok", "");
            }
        } else {
            map.put("error", "教师编号为空!");
        }
        return map;
    }

    /**
     * 检查班级名
     *
     * @param gradeName
     * @return
     */
    @RequestMapping(value = "/maintainer/grade/checkGradeName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkGradeName(@RequestParam("gradeId") int id, @RequestParam(value = "gradeName") String gradeName) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(gradeName)) {
            if (id > 0) {
                List<GradeRecord> grades = gradeService.findByGradeNameAndId(id, gradeName);
                if (grades.isEmpty()) {
                    map.put("ok", "");
                } else {
                    map.put("error", "班级名已存在!");
                }
            } else {
                List<Grade> grades = gradeService.findByGradeName(gradeName);
                if (grades.isEmpty()) {
                    map.put("ok", "");
                } else {
                    map.put("error", "班级名已存在!");
                }
            }
        } else {
            map.put("error", "班级名为空!");
        }
        return map;
    }

    /**
     * 添加班级
     *
     * @param gradeId
     * @param majorName
     * @param year
     * @param gradeName
     * @param gradeHeadID
     * @return
     */
    @RequestMapping(value = "/maintainer/grade/addGrade", method = RequestMethod.POST)
    public String addGrade(int gradeId, int majorName, String year, String gradeName, String gradeHeadID) {
        Grade grade = new Grade();
        grade.setMajorId(majorName);
        grade.setYear(year);
        grade.setGradeName(gradeName);
        grade.setGradeHead(gradeHeadID);
        gradeService.save(grade);
        return "redirect:/maintainer/grade/gradeManager";
    }

    /**
     * 更新班级信息
     *
     * @param gradeId
     * @param majorName
     * @param year
     * @param gradeName
     * @param gradeHeadID
     * @return
     */
    @RequestMapping(value = "/maintainer/grade/updateGrade", method = RequestMethod.POST)
    public String updateGrade(int gradeId, int majorName, String year, String gradeName, String gradeHeadID) {
        Grade grade = new Grade();
        grade.setId(gradeId);
        grade.setMajorId(majorName);
        grade.setYear(year);
        grade.setGradeName(gradeName);
        grade.setGradeHead(gradeHeadID);
        gradeService.update(grade);
        return "redirect:/maintainer/grade/gradeManager";
    }

    /**
     * 删除班级
     *
     * @param gradeVo
     * @return
     */
    @RequestMapping(value = "/maintainer/grade/deleteGrade", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData deleteGrade(GradeVo gradeVo) {
        List<Student> students = studentService.findByGradeId(gradeVo.getId());
        if(students.isEmpty()){
            gradeService.deleteById(gradeVo.getId());
            return new AjaxData().success().msg("删除成功!");
        } else {
            return new AjaxData().fail().msg("删除失败,该班级下已有学生!");
        }
    }
}
