package com.school.cbis.web.grade;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Grade;
import com.school.cbis.domain.tables.pojos.Teacher;
import com.school.cbis.domain.tables.records.TeacherRecord;
import com.school.cbis.service.GradeService;
import com.school.cbis.service.TeacherService;
import com.school.cbis.service.UsersService;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.AutoCompleteData;
import com.school.cbis.vo.grade.GradeVo;
import org.jooq.Record;
import org.jooq.Record7;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
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
    @Resource
    private UsersService usersService;

    @Resource
    private GradeService gradeService;

    @Resource
    private TeacherService teacherService;

    @RequestMapping("/maintainer/gradedata")
    @ResponseBody
    public Map<String, Object> gradeDatas(GradeVo gradeVo) {
        Map<String, Object> map = new HashMap<>();
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<GradeVo> gradeVos = new ArrayList<>();
        if (tieId > 0) {
            Result<Record7<Integer, Integer, String, String, String, String, String>> record7s = gradeService.findAllByPage(gradeVo, tieId);
            if (record7s.isNotEmpty()) {
                gradeVos = record7s.into(GradeVo.class);
                map.put("data", gradeVos);
                map.put("itemsCount", gradeService.findAllByPageCount(gradeVo, tieId));
            } else {
                map.put("data", gradeVos);
                map.put("itemsCount", 0);
            }
        } else {
            map.put("data", gradeVos);
            map.put("itemsCount", 0);
        }
        return map;
    }

    @RequestMapping("/maintainer/gradehead")
    @ResponseBody
    public List<AutoCompleteData> gradeHead(String search) {
        List<AutoCompleteData> autoCompleteDatas = new ArrayList<>();
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Result<TeacherRecord> teachers = teacherService.findByTieIdAndTearchName(search, tieId);
        if (teachers.isNotEmpty()) {
            for (TeacherRecord teacherRecord : teachers) {
                AutoCompleteData data = new AutoCompleteData();
                data.setValue(teacherRecord.getTeacherJobNumber());
                data.setTitle(teacherRecord.getTeacherName());
                data.setUrl("#");
                data.setText("账号:" + teacherRecord.getTeacherJobNumber());
                autoCompleteDatas.add(data);
            }
        }
        return autoCompleteDatas;
    }

    @RequestMapping(value = "/maintainer/checktearchernum", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData checkTeacherNum(@RequestParam(value = "tearcherJobNum") String tearcherJobNum) {
        AjaxData data = new AjaxData();
        if (StringUtils.hasLength(tearcherJobNum)) {
            List<Teacher> teachers = teacherService.findByTeacherJobNumber(tearcherJobNum);
            if (teachers.isEmpty()) {
                data.setState(false);
                data.setMsg("教师不存在！");
            } else {
                data.setState(true);
                data.setMsg("教师存在！");
            }
        } else {
            data.setState(false);
            data.setMsg("教师工号为空！");
        }
        return data;
    }

    @RequestMapping(value = "/maintainer/checkgradename", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData checkGradeName(@RequestParam(value = "gradeName") String gradeName) {
        AjaxData data = new AjaxData();
        if (StringUtils.hasLength(gradeName)) {
            List<Grade> grades = gradeService.findByGradeName(gradeName);
            if (grades.isEmpty()) {
                data.setState(true);
                data.setMsg("班级名不存在！");
            } else {
                data.setState(false);
                data.setMsg("班级名已存在！");
            }
        } else {
            data.setState(false);
            data.setMsg("班级名为空！");
        }
        return data;
    }

    @RequestMapping(value = "/maintainer/addgrade", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData addGrade(int gradeId, int majorName, String year, String gradeName, String gradeHeadID) {
        AjaxData data = new AjaxData();
        if (gradeId == -1 && majorName > 0 && StringUtils.hasLength(year) && year.matches("\\d{4}") && StringUtils.hasLength(gradeName) && StringUtils.hasLength(gradeHeadID)) {
            List<Teacher> teachers = teacherService.findByTeacherJobNumber(gradeHeadID);
            if (!teachers.isEmpty()) {
                Grade grade = new Grade();
                grade.setMajorId(majorName);
                grade.setYear(year);
                grade.setGradeName(gradeName);
                grade.setGradeHead(teachers.get(0).getTeacherJobNumber());
                gradeService.save(grade);
                data.setState(true);
                data.setMsg("保存班级信息成功！");
            } else {
                data.setState(false);
                data.setMsg("教师信息获取失败！");
            }
        } else {
            data.setState(false);
            data.setMsg("参数有误！");
        }
        return data;
    }

    @RequestMapping(value = "/maintainer/updategrade", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData updateGrade(int gradeId, int majorName, String year, String gradeName, String gradeHeadID) {
        AjaxData data = new AjaxData();
        if (gradeId > 0 && majorName > 0 && StringUtils.hasLength(year) && year.matches("\\d{4}") && StringUtils.hasLength(gradeName) && StringUtils.hasLength(gradeHeadID)) {
            List<Teacher> teachers = teacherService.findByTeacherJobNumber(gradeHeadID);
            if (!teachers.isEmpty()) {
                Grade grade = new Grade();
                grade.setId(gradeId);
                grade.setMajorId(majorName);
                grade.setYear(year);
                grade.setGradeName(gradeName);
                grade.setGradeHead(teachers.get(0).getTeacherJobNumber());
                gradeService.update(grade);
                data.setState(true);
                data.setMsg("保存班级信息成功！");
            } else {
                data.setState(false);
                data.setMsg("教师信息获取失败！");
            }
        } else {
            data.setState(false);
            data.setMsg("参数有误！");
        }
        return data;
    }

    @RequestMapping(value = "/maintainer/deletegrade",method = RequestMethod.POST)
    @ResponseBody
    public GradeVo deleteGrade(GradeVo gradeVo){
        gradeService.deleteById(gradeVo.getId());
        return gradeVo;
    }
}
