package com.school.cbis.web.users;

import com.alibaba.fastjson.JSON;
import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Grade;
import com.school.cbis.domain.tables.pojos.Student;
import com.school.cbis.domain.tables.pojos.Teacher;
import com.school.cbis.domain.tables.pojos.Users;
import com.school.cbis.domain.tables.records.AuthoritiesRecord;
import com.school.cbis.service.*;
import com.school.cbis.util.MD5Utils;
import com.school.cbis.vo.users.StudentVo;
import com.school.cbis.vo.users.TeacherVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(UsersController.class);

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
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/users/studentManager")
    public String studentManager(ModelMap modelMap, String studentName, String studentNumber) {
        modelMap.addAttribute("studentName", studentName);
        modelMap.addAttribute("studentNumber", studentNumber);

        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<String> list = new ArrayList<>();
        Result<Record1<String>> record1s = gradeService.findAllYearDistinct(tieId);
        if (record1s.isNotEmpty()) {
            for (Record r : record1s) {
                list.add(r.getValue("year").toString());
            }
        }
        modelMap.addAttribute("years", list);
        return "/maintainer/users/studentlist";
    }

    @RequestMapping("/maintainer/users/gradeData")
    @ResponseBody
    public AjaxData<Grade> gradeData(@RequestParam("year") String year) {
        AjaxData<Grade> ajaxData = new AjaxData<>();
        if (StringUtils.hasLength(year)) {
            List<Grade> grades = gradeService.findByYear(year);
            ajaxData.success().listData(grades);
        } else {
            ajaxData.fail().msg("参数异常!");
        }
        return ajaxData;
    }

    /**
     * 学生数据
     *
     * @param param
     * @return
     */
    @RequestMapping("/maintainer/users/studentManagerData")
    @ResponseBody
    public AjaxData<StudentVo> studentManagerData(@RequestParam("param") String param) {
        AjaxData<StudentVo> ajaxData = new AjaxData<>();
        List<StudentVo> studentVos = new ArrayList<>();
        Map<String, Object> map = (Map<String, Object>) JSON.parse(param);
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Result<Record5<Integer, String, String, Byte, String>> record5s = studentService.findByTieIdAndPage(map.get("studentName").toString(),
                map.get("studentNumber").toString(), Integer.parseInt(map.get("pageNum").toString()), Integer.parseInt(map.get("pageSize").toString()),
                tieId);
        if (record5s.isNotEmpty()) {
            studentVos = record5s.into(StudentVo.class);
            studentVos.forEach(s->{
                List<AuthoritiesRecord> authoritiesRecords = authoritiesService.findByUsername(s.getStudentNumber());
                List<String> authorities = new ArrayList<>();
                authoritiesRecords.forEach(a->{
                    authorities.add(wordbook.getRoleMap().get(a.getAuthority()));
                });
                s.setAuthorities(authorities);
            });
        }

        map.put("totalData", studentService.findByTieIdAndPageCount(map.get("studentName").toString(),
                map.get("studentNumber").toString(), tieId));
        ajaxData.success().listData(studentVos).mapData(map);
        return ajaxData;
    }

    /**
     * 教师管理界面
     *
     * @return
     */
    @RequestMapping("/maintainer/users/teacherManager")
    public String teacherManager(String teacherName, String teacherJobNumber, ModelMap modelMap) {
        modelMap.addAttribute("teacherName", teacherName);
        modelMap.addAttribute("teacherJobNumber", teacherJobNumber);
        return "/maintainer/users/teacherlist";
    }

    /**
     * 教师数据
     *
     * @param param
     * @return
     */
    @RequestMapping("/maintainer/users/teacherManagerData")
    @ResponseBody
    public AjaxData<TeacherVo> teacherManagerData(@RequestParam("param") String param) {
        AjaxData<TeacherVo> ajaxData = new AjaxData<>();
        Map<String, Object> map = (Map<String, Object>) JSON.parse(param);
        List<TeacherVo> teacherVos = new ArrayList<>();
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Result<Record4<Integer, String, String, Byte>> record4s = teacherService.findByTieIdAndPage(map.get("teacherName").toString(),
                map.get("teacherJobNumber").toString(), Integer.parseInt(map.get("pageNum").toString()), Integer.parseInt(map.get("pageSize").toString()),
                tieId);
        if (record4s.isNotEmpty()) {
            teacherVos = record4s.into(TeacherVo.class);
            teacherVos.forEach(t->{
                List<AuthoritiesRecord> authoritiesRecords = authoritiesService.findByUsername(t.getTeacherJobNumber());
                List<String> authorities = new ArrayList<>();
                authoritiesRecords.forEach(a->{
                    authorities.add(wordbook.getRoleMap().get(a.getAuthority()));
                });
                t.setAuthorities(authorities);
            });
        }
        map.put("totalData", teacherService.findByTieIdAndPageCount(map.get("teacherName").toString(),
                map.get("teacherJobNumber").toString(), tieId));
        ajaxData.success().listData(teacherVos).mapData(map);
        return ajaxData;
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
            users.setPassword(MD5Utils.md5(username));
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
    public AjaxData resetEnable(@RequestParam("username") String username, String  authority) {
        AjaxData ajaxData = new AjaxData();
        if (!StringUtils.isEmpty(username)) {

            List<AuthoritiesRecord> authoritiesRecords = new ArrayList<>();
            authoritiesService.delete(username);

            if(StringUtils.hasLength(authority)){
                String[] authorities = authority.split(",");

                if(authorities.length>0){
                    for(String s:authorities){
                        AuthoritiesRecord authoritiesRecord = new AuthoritiesRecord();
                        authoritiesRecord.setUsername(username);
                        authoritiesRecord.setAuthority(s);
                        authoritiesRecords.add(authoritiesRecord);
                    }
                    authoritiesService.save(authoritiesRecords);
                }
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
        users.setPassword(MD5Utils.md5(username));
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
    public String addStudent(@RequestParam("username") String username, @RequestParam("realname") String realname, @RequestParam("grade") int grade) {
        Student student = new Student();
        student.setStudentNumber(username);
        student.setStudentName(realname);
        student.setGradeId(grade);
        studentService.save(student);
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(MD5Utils.md5(username));
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
