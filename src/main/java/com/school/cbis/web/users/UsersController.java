package com.school.cbis.web.users;

import com.alibaba.fastjson.JSON;
import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.AuthoritiesRecord;
import com.school.cbis.service.*;
import com.school.cbis.util.MD5Utils;
import com.school.cbis.vo.article.UsersArticleVo;
import com.school.cbis.vo.users.StudentVo;
import com.school.cbis.vo.users.TeacherVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource
    private ArticleInfoService articleInfoService;

    @Resource
    private ArticleSubService articleSubService;

    /**
     * 用户管理界面
     *
     * @return
     */
    @RequestMapping("/maintainer/users/usersManager")
    public String usersManager() {
        return "redirect:/maintainer/users/studentManager";
    }

    /**
     * 学生管理界面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/users/studentManager")
    public String studentManager(ModelMap modelMap, String realName, String studentNumber) {
        modelMap.addAttribute("realName", realName);
        modelMap.addAttribute("studentNumber", studentNumber);

        //通过用户类型获取系表ID
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
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

    /**
     * 班级数据
     * @param year
     * @return
     */
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
     * 获取该系下教师
     * @return
     */
    @RequestMapping("/maintainer/users/teacherAllData")
    @ResponseBody
    public AjaxData<TeacherVo> teacherAllData(String teacherName){
        AjaxData<TeacherVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        if(tieId>0){
            Result<Record2<Integer,String>> record2s = teacherService.findByTieIdWithTeacherName(teacherName,tieId);
            if(record2s.isNotEmpty()){
                List<TeacherVo> list = record2s.into(TeacherVo.class);
                ajaxData.success().listData(list);
            } else {
                ajaxData.fail().msg("无数据!");
            }
        } else {
            ajaxData.fail().msg("获取用户信息异常!");
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
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        Result<Record5<Integer, String, String, Byte, String>> record5s = studentService.findByTieIdAndPage(map.get("realName").toString(),
                map.get("studentNumber").toString(), Integer.parseInt(map.get("pageNum").toString()), Integer.parseInt(map.get("pageSize").toString()),
                tieId);
        if (record5s.isNotEmpty()) {
            studentVos = record5s.into(StudentVo.class);
            studentVos.forEach(s -> {
                List<AuthoritiesRecord> authoritiesRecords = authoritiesService.findByUsername(s.getStudentNumber());
                List<String> authorities = new ArrayList<>();
                authoritiesRecords.forEach(a -> {
                    authorities.add(wordbook.getRoleMap().get(a.getAuthority()));
                });
                s.setAuthorities(authorities);
            });
        }

        map.put("totalData", studentService.findByTieIdAndPageCount(map.get("realName").toString(),
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
    public String teacherManager(String realName, String teacherJobNumber, ModelMap modelMap) {
        modelMap.addAttribute("realName", realName);
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
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        Result<Record4<Integer, String, String, Byte>> record4s = teacherService.findByTieIdAndPage(map.get("realName").toString(),
                map.get("teacherJobNumber").toString(), Integer.parseInt(map.get("pageNum").toString()), Integer.parseInt(map.get("pageSize").toString()),
                tieId);
        if (record4s.isNotEmpty()) {
            teacherVos = record4s.into(TeacherVo.class);
            teacherVos.forEach(t -> {
                List<AuthoritiesRecord> authoritiesRecords = authoritiesService.findByUsername(t.getTeacherJobNumber());
                List<String> authorities = new ArrayList<>();
                authoritiesRecords.forEach(a -> {
                    authorities.add(wordbook.getRoleMap().get(a.getAuthority()));
                });
                t.setAuthorities(authorities);
            });
        }
        map.put("totalData", teacherService.findByTieIdAndPageCount(map.get("realName").toString(),
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
    public AjaxData resetEnable(@RequestParam("username") String username, String authority) {
        AjaxData ajaxData = new AjaxData();
        if (!StringUtils.isEmpty(username)) {

            List<AuthoritiesRecord> authoritiesRecords = new ArrayList<>();
            authoritiesService.delete(username);

            if (StringUtils.hasLength(authority)) {
                String[] authorities = authority.split(",");

                if (authorities.length > 0) {
                    for (String s : authorities) {
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
    public String addTeacher(@RequestParam("username") String username, @RequestParam("realname") String realname, HttpServletRequest request) {
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (!ObjectUtils.isEmpty(record)) {
            tieId = record.getValue(Tables.TIE.ID);
        }
        Teacher teacher = new Teacher();
        teacher.setTeacherJobNumber(username);
        teacher.setTieId(tieId);
        teacherService.save(teacher);
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(MD5Utils.md5(username));
        users.setRealName(realname);
        Byte bs = 0;
        users.setIsCheckEmail(bs);
        users.setIsCheckMobile(bs);
        Byte b = 1;
        users.setEnabled(b);
        users.setUserTypeId(wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER));
        users.setLangKey(request.getLocale().toString());
        users.setHeadImg(Wordbook.USER_DEFAULT_HEAD_IMG);
        usersService.save(users);
        AuthoritiesRecord authoritiesRecord = new AuthoritiesRecord();
        authoritiesRecord.setUsername(username);
        authoritiesRecord.setAuthority(Wordbook.CBIS_TEA);
        List<AuthoritiesRecord> authoritiesRecords = new ArrayList<>();
        authoritiesRecords.add(authoritiesRecord);
        authoritiesService.save(authoritiesRecords);
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
    public String addStudent(@RequestParam("username") String username, @RequestParam("realname") String realname, @RequestParam("grade") int grade,HttpServletRequest request) {
        Student student = new Student();
        student.setStudentNumber(username);
        student.setGradeId(grade);
        studentService.save(student);
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(MD5Utils.md5(username));
        Byte bs = 0;
        users.setIsCheckEmail(bs);
        users.setIsCheckMobile(bs);
        users.setRealName(realname);
        Byte b = 1;
        users.setEnabled(b);
        users.setUserTypeId(wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT));
        users.setLangKey(request.getLocale().toString());
        users.setHeadImg(Wordbook.USER_DEFAULT_HEAD_IMG);
        usersService.save(users);
        AuthoritiesRecord authoritiesRecord = new AuthoritiesRecord();
        authoritiesRecord.setUsername(username);
        authoritiesRecord.setAuthority(Wordbook.CBIS_STU);
        List<AuthoritiesRecord> authoritiesRecords = new ArrayList<>();
        authoritiesRecords.add(authoritiesRecord);
        authoritiesService.save(authoritiesRecords);
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

    /**
     * 获取权限数据
     * @return
     */
    @RequestMapping("/maintainer/users/getAuthorities")
    @ResponseBody
    public AjaxData getAuthorities(){
        AjaxData ajaxData = new AjaxData();
        Map<String,Object> map = new HashMap<>();
        //权限
        log.debug("roleList : {}",wordbook.getRoleString());
        map.put("roleList",wordbook.getRoleString());
        return ajaxData.success().mapData(map);
    }

    /**
     * 编辑
     * @param username
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/users/editUserData")
    public String editUserData(@RequestParam("username") String username,ModelMap modelMap){
        Users users = usersService.findByUsername(username);
        modelMap.addAttribute("users", users);
        if (users.getUserTypeId() == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {//类型为老师
            return "/maintainer/users/teacherdata";
        } else {
            List<Student> students = studentService.findByStudentNumber(users.getUsername());
            if (!students.isEmpty()) {
                modelMap.addAttribute("studentInfo", students.get(0));
            } else {
                modelMap.addAttribute("studentInfo", new Student());
            }
            return "/maintainer/users/studentdata";
        }
    }

    /**
     * 管理用户简介
     * @param usersArticleVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/users/userArticle")
    public String userArticle(UsersArticleVo usersArticleVo,ModelMap modelMap){
        modelMap.addAttribute("usersArticleVo",usersArticleVo);
        if(usersArticleVo.getUserType().equals(Wordbook.USER_TYPE_STUDENT)){
            return "/maintainer/users/studentarticle";
        } else {
            return "/maintainer/users/teacherarticle";
        }

    }

    /**
     * 简介数据
     * @param usersArticleVo
     * @return
     */
    @RequestMapping("/maintainer/users/userArticleData")
    @ResponseBody
    public AjaxData<UsersArticleVo> userArticleData(UsersArticleVo usersArticleVo){
        AjaxData<UsersArticleVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<UsersArticleVo> usersArticleVos = new ArrayList<>();
        if(tieId > 0){
            int userTypeId = wordbook.getUserTypeMap().get(StringUtils.trimWhitespace(usersArticleVo.getUserType()));
            Result<Record4<String ,String ,String ,Integer>> record4s = usersService.findByUserTypeIdAndTieIdWithArticle(usersArticleVo,userTypeId,tieId);
            if(record4s.isNotEmpty()){
                usersArticleVos = record4s.into(UsersArticleVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(usersArticleVo.getPageNum());
                paginationData.setPageSize(usersArticleVo.getPageSize());
                paginationData.setTotalDatas(usersService.findByUserTypeIdAndTieIdWithArticleCount(usersArticleVo,userTypeId,tieId));
                ajaxData.success().listData(usersArticleVos).paginationData(paginationData);
            } else {
                ajaxData.fail().listData(usersArticleVos);
            }
        } else {
            ajaxData.fail().listData(usersArticleVos);
        }
        return ajaxData;
    }

    /**
     * 编辑个人简介
     * @param modelMap
     * @param username 账号
     * @return
     */
    @RequestMapping("/maintainer/users/editUserArticle")
    public String individualResume(ModelMap modelMap,@RequestParam("username") String username,@RequestParam("userType") String userType) {
        ArticleInfo articleInfo = new ArticleInfo();
        List<ArticleSub> articleSubs = null;
        if(StringUtils.hasLength(username)){
            Record record = articleInfoService.findByUsername(username);
            if (!ObjectUtils.isEmpty(record)) {
                articleInfo = record.into(ArticleInfo.class);
                articleSubs = articleSubService.findByArticleInfoId(articleInfo.getId());
            } else {
                articleInfo = new ArticleInfo();
            }
        }
        modelMap.addAttribute("articleinfo", articleInfo);
        modelMap.addAttribute("articlesubinfo", articleSubs);
        modelMap.addAttribute("username", username);
        if(userType.equals(Wordbook.USER_TYPE_STUDENT)){
            return "/maintainer/users/studentarticleupdate";
        } else {
            return "/maintainer/users/teacherarticleupdate";
        }
    }
}
