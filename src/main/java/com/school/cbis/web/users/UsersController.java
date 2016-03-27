package com.school.cbis.web.users;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Grade;
import com.school.cbis.domain.tables.pojos.Student;
import com.school.cbis.domain.tables.pojos.Teacher;
import com.school.cbis.domain.tables.pojos.Users;
import com.school.cbis.domain.tables.records.AuthoritiesRecord;
import com.school.cbis.service.*;
import com.school.cbis.util.MD5Util;
import com.school.cbis.vo.grade.GradeVo;
import com.school.cbis.vo.users.StudentVo;
import com.school.cbis.vo.users.TeacherVo;
import org.jooq.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
 * Created by lenovo on 2016-03-20.
 */
@Controller
public class UsersController {

    @Resource
    private UsersService usersService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    @Resource
    private GradeService gradeService;

    @Resource
    private Wordbook wordbook;

    @Resource
    private AuthoritiesService authoritiesService;

    /**
     * 学生管理界面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/users/studentManager")
    public String studentManager(ModelMap map, StudentVo studentVo) {
        List<StudentVo> studentVos = new ArrayList<>();
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Result<Record6<Integer, String, String, Byte, String, String>> record6s = studentService.findByTieIdAndPage(studentVo, tieId);
        if (record6s.isNotEmpty()) {
            studentVos = record6s.into(StudentVo.class);
            for (StudentVo t : studentVos) {
                if (!StringUtils.isEmpty(t.getAuthority())) {
                    if (t.getAuthority().equals("ROLE_ADMIN")) {
                        t.setAuthority("超级管理员");
                    } else if (t.getAuthority().equals("ROLE_MAI")) {
                        t.setAuthority("管理员");
                    } else if (t.getAuthority().equals("ROLE_TEA")) {
                        t.setAuthority("教师");
                    } else if (t.getAuthority().equals("ROLE_STU")) {
                        t.setAuthority("学生");
                    }
                }
            }
        }
        if (!StringUtils.isEmpty(studentVo)) {
            studentVo.setTotalData(studentService.findByTieIdAndPageCount(studentVo, tieId));
        }

        Result<Record2<Integer,String>> record2s = gradeService.findByTieId(tieId);
        List<GradeVo> gradeVos = new ArrayList<>();
        if(record2s.isNotEmpty()){
            gradeVos = record2s.into(GradeVo.class);
        }

        map.addAttribute("grades",gradeVos);
        map.addAttribute("students", studentVos);
        map.addAttribute("studentVo", studentVo);
        return "/maintainer/users/studentlist";
    }

    /**
     * 教师管理界面
     *
     * @param map
     * @return
     */
    @RequestMapping("/maintainer/users/teacherManager")
    public String teacherManager(ModelMap map, TeacherVo teacherVo) {
        List<TeacherVo> teacherVos = new ArrayList<>();
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Result<Record5<Integer, String, String, Byte, String>> record5s = teacherService.findByTieIdAndPage(teacherVo, tieId);
        if (record5s.isNotEmpty()) {
            teacherVos = record5s.into(TeacherVo.class);
            for (TeacherVo t : teacherVos) {
                if (!StringUtils.isEmpty(t.getAuthority())) {
                    if (t.getAuthority().equals("ROLE_ADMIN")) {
                        t.setAuthority("超级管理员");
                    } else if (t.getAuthority().equals("ROLE_MAI")) {
                        t.setAuthority("管理员");
                    } else if (t.getAuthority().equals("ROLE_TEA")) {
                        t.setAuthority("教师");
                    } else if (t.getAuthority().equals("ROLE_STU")) {
                        t.setAuthority("学生");
                    }
                }
            }
        }
        if (!StringUtils.isEmpty(teacherVo)) {
            teacherVo.setTotalData(teacherService.findByTieIdAndPageCount(teacherVo, tieId));
        }
        map.addAttribute("teachers", teacherVos);
        map.addAttribute("teacherVo", teacherVo);
        return "/maintainer/users/teacherlist";
    }

    /**
     * 重置密码
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/maintainer/users/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData resetPassword(@RequestParam("username") String username) {
        AjaxData ajaxData = new AjaxData();
        if (!StringUtils.isEmpty(username)) {
            Users users = usersService.findByUsername(username);
            users.setPassword(MD5Util.md5(username));
            usersService.update(users);
            ajaxData.success().msg("更新密码成功，默认密码为账号!");
        } else {
            ajaxData.fail().msg("参数异常!");
        }
        return ajaxData;
    }

    /**
     * 更新用户状态
     *
     * @return
     */
    @RequestMapping(value = "/maintainer/users/resetEnable", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData resetEnable(@RequestParam("username") String username, @RequestParam("enable") Byte enable) {
        AjaxData ajaxData = new AjaxData();
        if (!StringUtils.isEmpty(username)) {
            Users users = usersService.findByUsername(username);
            users.setEnabled(enable);
            usersService.update(users);
            ajaxData.success().msg("更新状态成功!");
        } else {
            ajaxData.fail().msg("参数异常!");
        }
        return ajaxData;
    }

    /**
     * 更新用户权限
     *
     * @return
     */
    @RequestMapping(value = "/maintainer/users/resetAuthority", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData resetEnable(@RequestParam("username") String username, @RequestParam("authority") String authority) {
        AjaxData ajaxData = new AjaxData();
        if (!StringUtils.isEmpty(username)) {
            AuthoritiesRecord authoritiesRecord = authoritiesService.findByUsername(username);
            if (StringUtils.isEmpty(authoritiesRecord)) {
                authoritiesRecord = new AuthoritiesRecord();
                authoritiesRecord.setUsername(username);
                authoritiesRecord.setAuthority(authority);
                authoritiesService.save(authoritiesRecord);
            } else {
                authoritiesRecord.setAuthority(authority);
                authoritiesService.update(authoritiesRecord);
            }
            ajaxData.success().msg("更新权限成功!");
        } else {
            ajaxData.fail().msg("参数异常!");
        }
        return ajaxData;
    }

    /**
     * 添加教师用户
     *
     * @param username
     * @param realname
     * @return
     */
    @RequestMapping(value = "/maintainer/users/addTeacher", method = RequestMethod.POST)
    public String addTeacher(@RequestParam("username") String username, @RequestParam("realname") String realname) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Teacher teacher = new Teacher();
        teacher.setTeacherJobNumber(username);
        teacher.setTeacherName(realname);
        teacher.setTieId(tieId);
        teacherService.save(teacher);
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(MD5Util.md5(username));
        Byte b = 1;
        users.setEnabled(b);
        users.setUserTypeId(wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER));
        usersService.save(users);
        return "redirect:/maintainer/users/teacherManager";
    }

    /**
     * 添加学生用户
     *
     * @param username
     * @param realname
     * @return
     */
    @RequestMapping(value = "/maintainer/users/addStudent", method = RequestMethod.POST)
    public String addStudent(@RequestParam("username") String username, @RequestParam("realname") String realname,@RequestParam("grade") int grade ) {
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }

        Student student = new Student();
        student.setStudentNumber(username);
        student.setStudentName(realname);
        student.setGradeId(grade);
        studentService.save(student);
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(MD5Util.md5(username));
        Byte b = 1;
        users.setEnabled(b);
        users.setUserTypeId(wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT));
        usersService.save(users);
        return "redirect:/maintainer/users/studentManager";
    }

    /**
     * 检验账号
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/maintainer/users/validUsername", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validUsername(@RequestParam("username") String username) {
        Map<String, Object> map = new HashMap<>();
        Users users = usersService.findByUsername(username);
        if (StringUtils.isEmpty(users)) {
            map.put("ok", "");
        } else {
            map.put("error", "该账号已存在!");
        }
        return map;
    }
}
