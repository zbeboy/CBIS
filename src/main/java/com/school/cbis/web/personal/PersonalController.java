package com.school.cbis.web.personal;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.FileData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.service.*;
import com.school.cbis.util.MD5Utils;
import com.school.cbis.vo.personal.RevisePasswordVo;
import com.school.cbis.vo.personal.StudentModifyDataVo;
import com.school.cbis.vo.personal.TeacherModifyDataVo;
import org.apache.poi.ss.formula.functions.T;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-01-09.
 */
@Controller
public class PersonalController {

    private final Logger log = LoggerFactory.getLogger(PersonalController.class);

    @Resource
    private UsersService usersService;// 用户表

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private ArticleInfoService articleInfoService;

    @Resource
    private ArticleSubService articleSubService;

    @Resource
    private Wordbook wordbook;

    /**
     * 修改密码页面
     *
     * @return 页面地址
     */
    @RequestMapping("/student/personal/revisePassword")
    public String revisePassword(ModelMap map) {
        buildMap(map, false, new RevisePasswordVo(), false, false, "");
        return "/student/personal/revisepassword";
    }

    /**
     * 更新密码
     *
     * @return 消息
     */
    @RequestMapping(value = "/student/personal/updatePassword", method = RequestMethod.POST)
    public String updatePassword(@Valid RevisePasswordVo passwordVo, BindingResult bindingResult, ModelMap map) {
        if (!bindingResult.hasErrors()) {
            String oldPassword = StringUtils.trimWhitespace(passwordVo.getOldPassword());
            if (!StringUtils.isEmpty(usersService.getPassword())) {//用户登录密码
                if (MD5Utils.md5(oldPassword).equals(usersService.getPassword())) {//校验旧密码
                    if (StringUtils.trimWhitespace(passwordVo.getNewPassword()).equals(StringUtils.trimWhitespace(passwordVo.getOkPassword()))) {//确认密码一致
                        Users users = usersService.findByUsername(usersService.getUserName());
                        users.setPassword(MD5Utils.md5(passwordVo.getOkPassword()));
                        users.setUsername(usersService.getUserName());
                        usersService.update(users);//存入数据库
                        buildMap(map, false, new RevisePasswordVo(), false, true, "修改成功，请点击退出按钮，重新登录！");

                    } else {
                        buildMap(map, false, new RevisePasswordVo(), true, false, "密码不一致！");
                    }
                } else {
                    buildMap(map, true, new RevisePasswordVo(), false, false, "");
                }
            } else {
                return "/login";
            }
        }
        return "/student/personal/revisepassword";
    }

    /**
     * 验证原来的密码是否正确
     *
     * @param oldPassword
     * @return ajax
     */
    @RequestMapping(value = "/student/personal/validPassword", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validPassword(@RequestParam("oldPassword") String oldPassword) {
        Map<String, Object> map = new HashMap<>();
        String regex = "^[\\w]{6,20}$";
        String op = StringUtils.trimWhitespace(oldPassword);
        AjaxData ajaxData = new AjaxData();
        if (op.matches(regex)) {
            if (StringUtils.trimWhitespace(usersService.getPassword()).equals(MD5Utils.md5(op))) {
                map.put("ok", "");
            } else {
                map.put("error", "密码错误!");
            }
        } else {
            map.put("error", "密码由6到20位任意字符组成!");
        }
        return map;
    }

    /**
     * 组装消息 modelMap
     *
     * @param map               ModelMap
     * @param passwordError     密码错误
     * @param revisePasswordVo  页面数据对象
     * @param validationError   校验数据错误
     * @param validationSuccess 校验数据成功
     * @param msg               回调消息
     */
    private void buildMap(ModelMap map, boolean passwordError, RevisePasswordVo revisePasswordVo,
                          boolean validationError, boolean validationSuccess, String msg) {
        map.addAttribute("passwordError", passwordError);//校验旧密码
        map.addAttribute("revisePasswordVo", revisePasswordVo);//页面参数
        map.addAttribute("validationError", validationError);//校验页面参数有错
        map.addAttribute("validationSuccess", validationSuccess);//校验成功
        map.addAttribute("msg", msg);//错误消息
    }

    /**
     * 修改资料页面
     *
     * @return
     */
    @RequestMapping("/student/personal/modifyData")
    public String modifyData(ModelMap modelMap) {
        Users users = usersService.findByUsername(usersService.getUserName());
        modelMap.addAttribute("users", users);
        if (users.getUserTypeId() == wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER)) {//类型为老师
            return "/teacher/personal/teachermodifydata";
        } else {
            List<Student> students = studentService.findByStudentNumber(users.getUsername());
            if (!students.isEmpty()) {
                modelMap.addAttribute("studentInfo", students.get(0));
            } else {
                modelMap.addAttribute("studentInfo", new Student());
            }
            return "/student/personal/studentmodifydata";
        }
    }

    /**
     * 更新学生资料
     *
     * @param studentModifyDataVo
     * @return
     */
    @RequestMapping("/student/personal/updateStudentModifyData")
    @ResponseBody
    public AjaxData updateStudentModifyData(StudentModifyDataVo studentModifyDataVo) {

        try {
            if (StringUtils.hasLength(studentModifyDataVo.getUsername())) {
                Users users = usersService.findByUsername(studentModifyDataVo.getUsername());
                if (!ObjectUtils.isEmpty(users)) {
                    if (StringUtils.hasLength(studentModifyDataVo.getBirthday())) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.sql.Date date = new java.sql.Date(sdf.parse(studentModifyDataVo.getBirthday()).getTime());
                        users.setBirthday(date);
                    }
                    users.setRealName(studentModifyDataVo.getRealName());
                    users.setSex(studentModifyDataVo.getSex());
                    users.setNation(studentModifyDataVo.getNation());
                    users.setPost(studentModifyDataVo.getPost());
                    users.setPoliticalLandscape(studentModifyDataVo.getPoliticalLandscape());
                    users.setReligiousBelief(studentModifyDataVo.getReligiousBelief());
                    users.setHeadImg(studentModifyDataVo.getHeadImg());
                    users.setIdentityCard(studentModifyDataVo.getIdentityCard());
                    users.setFamilyResidence(studentModifyDataVo.getFamilyResidence());
                    users.setPersonaIntroduction(studentModifyDataVo.getPersonaIntroduction());
                    usersService.update(users);
                    List<Student> students = studentService.findByStudentNumber(studentModifyDataVo.getUsername());
                    if (!students.isEmpty()) {
                        Student student = students.get(0);
                        student.setParentName(studentModifyDataVo.getParentName());
                        student.setParentContactPhone(studentModifyDataVo.getParentContactPhone());
                        student.setDormitoryNumber(studentModifyDataVo.getDormitoryNumber());
                        student.setPlaceOrigin(studentModifyDataVo.getPlaceOrigin());
                        student.setProblemSituation(studentModifyDataVo.getProblemSituation());
                        studentService.update(student);
                    } else {
                        return new AjaxData().fail().msg("不存在该学生!");
                    }
                } else {
                    return new AjaxData().fail().msg("不存在该用户!");
                }
            } else {
                return new AjaxData().fail().msg("参数异常!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return new AjaxData().fail().msg("转换异常!");
        }
        return new AjaxData().success().msg("更新成功!");
    }

    /**
     * 更新教师资料
     *
     * @param teacherModifyDataVo
     * @return
     */
    @RequestMapping("/teacher/personal/updateTeacherModifyData")
    @ResponseBody
    public AjaxData updateTeacherModifyData(TeacherModifyDataVo teacherModifyDataVo) {

        try {
            if (StringUtils.hasLength(teacherModifyDataVo.getUsername())) {
                Users users = usersService.findByUsername(teacherModifyDataVo.getUsername());
                if (!ObjectUtils.isEmpty(users)) {
                    if (StringUtils.hasLength(teacherModifyDataVo.getBirthday())) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.sql.Date date = new java.sql.Date(sdf.parse(teacherModifyDataVo.getBirthday()).getTime());
                        users.setBirthday(date);
                    }
                    users.setRealName(teacherModifyDataVo.getRealName());
                    users.setSex(teacherModifyDataVo.getSex());
                    users.setNation(teacherModifyDataVo.getNation());
                    users.setPost(teacherModifyDataVo.getPost());
                    users.setPoliticalLandscape(teacherModifyDataVo.getPoliticalLandscape());
                    users.setReligiousBelief(teacherModifyDataVo.getReligiousBelief());
                    users.setHeadImg(teacherModifyDataVo.getHeadImg());
                    users.setIdentityCard(teacherModifyDataVo.getIdentityCard());
                    users.setFamilyResidence(teacherModifyDataVo.getFamilyResidence());
                    users.setPersonaIntroduction(teacherModifyDataVo.getPersonaIntroduction());
                    usersService.update(users);
                } else {
                    return new AjaxData().fail().msg("不存在该用户!");
                }
            } else {
                return new AjaxData().fail().msg("参数异常!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return new AjaxData().fail().msg("转换异常!");
        }
        return new AjaxData().success().msg("更新成功!");
    }

    /**
     * 个人简介
     * @param modelMap
     * @return
     */
    @RequestMapping("/student/personal/individualResume")
    public String individualResume(ModelMap modelMap) {
        Users users = usersService.getUserInfoBySession();
        log.debug("username : {}",users.getUsername());
        Record record = articleInfoService.findByUsername(users.getUsername());
        ArticleInfo articleInfo;
        List<ArticleSub> articleSubs = null;
        if(!ObjectUtils.isEmpty(record)){
            articleInfo = record.into(ArticleInfo.class);
            log.debug("articleInfo : {}",articleInfo);
            articleSubs = articleSubService.findByArticleInfoId(articleInfo.getId());
        } else {
            articleInfo = new ArticleInfo();
        }
        modelMap.addAttribute("articleinfo", articleInfo);
        modelMap.addAttribute("articlesubinfo", articleSubs);
        modelMap.addAttribute("username", users.getUsername());
        return "/student/personal/individualresume";
    }

    /**
     * 个人简介展示
     * @param id
     * @param username
     * @param modelMap
     * @return
     */
    @RequestMapping("/student/personal/individualResumeShow")
    public String individualResume(@RequestParam("id") int id, @RequestParam("username") String username,  ModelMap modelMap) {
        Record record = articleInfoService.findByUsername(username);
        Users users = record.into(Users.class);
        modelMap.addAttribute("userInfo", users);
        ArticleInfo articleInfo = record.into(ArticleInfo.class);
        modelMap.addAttribute("articleInfo", articleInfo);
        List<ArticleSub> articleSubs = articleSubService.findByArticleInfoId(articleInfo.getId());
        modelMap.addAttribute("articleSub", articleSubs);
        return "/user/personal/individualresumeshow";
    }
}
