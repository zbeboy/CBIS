package com.school.cbis.web.personal;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.FileData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.UsersRecord;
import com.school.cbis.service.*;
import com.school.cbis.util.MD5Utils;
import com.school.cbis.util.RandomUtils;
import com.school.cbis.vo.personal.ResetPasswordVo;
import com.school.cbis.vo.personal.RevisePasswordVo;
import com.school.cbis.vo.personal.StudentModifyDataVo;
import com.school.cbis.vo.personal.TeacherModifyDataVo;
import org.apache.poi.ss.formula.functions.T;
import org.joda.time.DateTime;
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
import java.util.*;

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
    private MailService mailService;

    @Resource
    private MobileService mobileService;

    @Resource
    private MailboxCountService mailboxCountService;

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
     * 重置密码
     * @param resetPasswordVo
     * @param bindingResult
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/personal/resetPassword")
    public String resetPassword(@Valid ResetPasswordVo resetPasswordVo,BindingResult bindingResult,ModelMap modelMap){
        if(!bindingResult.hasErrors()){
            if (StringUtils.trimWhitespace(resetPasswordVo.getNewPassword()).equals(StringUtils.trimWhitespace(resetPasswordVo.getOkPassword()))) {//确认密码一致
                Users users = usersService.findByUsername(resetPasswordVo.getUsername());
                if(!ObjectUtils.isEmpty(users)){
                    users.setPassword(MD5Utils.md5(resetPasswordVo.getOkPassword()));
                    usersService.update(users);//存入数据库
                    modelMap.addAttribute("msg","您的账号:"+users.getUsername()+"重置密码成功!");
                } else {
                    modelMap.addAttribute("msg","账号不存在!");
                }
            } else {
                modelMap.addAttribute("msg","密码不一致,重置失败!");
            }
        } else {
            modelMap.addAttribute("msg","参数异常!");
        }
        return "/user/mail/forgetpasswordmsg";
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
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/student/personal/individualResume")
    public String individualResume(ModelMap modelMap) {
        Users users = usersService.getUserInfoBySession();
        log.debug("username : {}", users.getUsername());
        Record record = articleInfoService.findByUsername(users.getUsername());
        ArticleInfo articleInfo;
        List<ArticleSub> articleSubs = null;
        if (!ObjectUtils.isEmpty(record)) {
            articleInfo = record.into(ArticleInfo.class);
            log.debug("articleInfo : {}", articleInfo);
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
     * @param username
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/personal/individualResumeShow")
    public String individualResume( @RequestParam("username") String username, ModelMap modelMap) {
        Record record = articleInfoService.findByUsername(username);
        List<ArticleSub> articleSubs = new ArrayList<>();
        if(!ObjectUtils.isEmpty(record)){
            Users users = record.into(Users.class);
            //为保证用户信息安全，以下信息清空
            users.setPasswordResetKey(null);
            users.setPassword(null);
            users.setEmailCheckKey(null);
            users.setMobileCheckKey(null);
            users.setBirthday(null);
            users.setIdentityCard(null);
            users.setNation(null);
            users.setPoliticalLandscape(null);
            users.setPost(null);
            users.setEmail(null);
            users.setMobile(null);
            users.setSex(null);
            users.setReligiousBelief(null);
            modelMap.addAttribute("userInfo", users);
            ArticleInfo articleInfo = record.into(ArticleInfo.class);
            modelMap.addAttribute("articleInfo", articleInfo);
            articleSubs = articleSubService.findByArticleInfoId(articleInfo.getId());
            modelMap.addAttribute("articleSub", articleSubs);
        } else {
            modelMap.addAttribute("userInfo", new Users());
            modelMap.addAttribute("articleInfo", new ArticleInfo());
            modelMap.addAttribute("articleSub", articleSubs);
        }


        return "/user/personal/individualresumeshow";
    }

    /**
     * 邮箱验证页
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/student/personal/mailboxVerification")
    public String mailboxVerification(ModelMap modelMap) {
        Users users = usersService.findByUsername(usersService.getUserName());
        if (users.getIsCheckEmail() == 1) {
            modelMap.addAttribute("isCheckEmail", true);
            modelMap.addAttribute("email", users.getEmail());
        } else {
            modelMap.addAttribute("isCheckEmail", false);
        }
        return "/student/personal/mailboxverification";
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return
     */
    @RequestMapping("/student/personal/validEmail")
    @ResponseBody
    public Map<String, Object> validEmail(@RequestParam("email") String email) {
        Map<String, Object> map = new HashMap<>();
        Users users = usersService.getUserInfoBySession();
        if (!ObjectUtils.isEmpty(users.getEmail()) && users.getEmail().equals(email) && users.getIsCheckEmail() == 1) {
            map.put("error", "该邮箱已验证!");
        } else {
            UsersRecord record = usersService.findByEmailAndUsername(email, usersService.getUserName());
            if (ObjectUtils.isEmpty(record)) {
                map.put("ok", "");
            } else {
                map.put("error", "该邮箱已被使用!");
            }
        }
        return map;
    }

    /**
     * 发送邮箱验证
     *
     * @param email
     * @param request
     * @return
     */
    @RequestMapping("/student/personal/updateEmail")
    @ResponseBody
    public AjaxData updateEmail(@RequestParam("email") String email, HttpServletRequest request) {
        if (wordbook.mailSwitch) {
            if (mailboxCountService.isExceedDailyLimit()) {
                return new AjaxData().fail().msg("发送失败,已超过每日邮件发送上限!");
            } else {
                Users users = usersService.findByUsername(usersService.getUserName());
                users.setEmail(email);
                DateTime dateTime = new DateTime().plusDays(2);
                Timestamp timestamp = new Timestamp(dateTime.getMillis());
                users.setEmailCheckKeyValidityPeriod(timestamp);
                users.setEmailCheckKey(RandomUtils.generateEmailCheckKey());
                Byte b = 0;
                users.setIsCheckEmail(b);
                usersService.update(users);
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
                mailService.sendValidEmailMail(users, basePath);
                return new AjaxData().success().msg("邮件已发送至您的邮箱!");
            }
        } else {
            return new AjaxData().fail().msg("发送失败,管理员已关闭邮箱功能!");
        }

    }

    /**
     * 验证邮箱
     *
     * @param key
     * @param username
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/checkEmail")
    public String checkEmail(@RequestParam("key") String key, @RequestParam("username") String username, ModelMap modelMap) {
        Users users = usersService.findByUsername(username);
        if (!ObjectUtils.isEmpty(users)) {
            Timestamp cur = new Timestamp(System.currentTimeMillis());
            if (!ObjectUtils.isEmpty(users.getEmailCheckKeyValidityPeriod()) && cur.before(users.getEmailCheckKeyValidityPeriod())) {
                if (StringUtils.trimWhitespace(key).equals(users.getEmailCheckKey())) {
                    Byte b = 1;
                    users.setIsCheckEmail(b);
                    usersService.update(users);
                    modelMap.addAttribute("msg", "恭喜您,您的邮箱:" + users.getEmail() + "已经验证成功!");
                } else {
                    modelMap.addAttribute("msg", "验证码不正确,请登录重新获取验证邮件!");
                }
            } else {
                modelMap.addAttribute("msg", "您的邮箱验证已过有效期(2日内),请登录重新获取验证邮件!");
            }
        } else {
            modelMap.addAttribute("msg", "未获取到用户信息,验证失效!");
        }
        return "/user/personal/checkemailmsg";
    }

    /**
     * 手机验证页
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/student/personal/mobileVerification")
    public String mobileVerification(ModelMap modelMap) {
        Users users = usersService.findByUsername(usersService.getUserName());
        if (users.getIsCheckMobile() == 1) {
            modelMap.addAttribute("isCheckMobile", true);
            modelMap.addAttribute("mobile", users.getMobile());
        } else {
            modelMap.addAttribute("isCheckMobile", false);
        }
        return "/student/personal/mobileverification";
    }

    /**
     * 发送手机验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping("/student/personal/sendMobileKey")
    @ResponseBody
    public AjaxData sendMobileKey(@RequestParam("mobile") String mobile) {
        AjaxData ajaxData = new AjaxData();
        Users users = usersService.findByUsername(usersService.getUserName());
        if (!ObjectUtils.isEmpty(users.getMobile()) && users.getMobile().equals(mobile) && users.getIsCheckMobile() == 1) {
            ajaxData.fail().msg("该手机号已验证!");
        } else {
            UsersRecord record = usersService.findByMobileAndUsername(mobile, usersService.getUserName());
            if (ObjectUtils.isEmpty(record)) {
                if (wordbook.mobileSwitch) {
                    users.setMobile(mobile);
                    DateTime dateTime = new DateTime().plusMinutes(5);
                    Timestamp timestamp = new Timestamp(dateTime.getMillis());
                    users.setMobileCheckKeyValidityPeriod(timestamp);
                    String mobileKey = RandomUtils.generateMobileKey();
                    users.setMobileCheckKey(mobileKey);
                    Byte b = 0;
                    users.setIsCheckMobile(b);
                    usersService.update(users);
                    mobileService.sendValidMobileShortMessage(users, mobileKey);
                    log.debug(" mobilekey : {} ", mobileKey);
                    ajaxData.success().msg("短信已发至您的手机,可能会有延迟,请稍等!");
                } else {
                    ajaxData.fail().msg("发送失败,管理员已关闭手机功能!");
                }
            } else {
                ajaxData.fail().msg("该手机号已被使用!");
            }
        }
        return ajaxData;
    }

    /**
     * 校验手机
     *
     * @param mobile
     * @return
     */
    @RequestMapping("/student/personal/validMobile")
    @ResponseBody
    public Map<String, Object> validMobile(@RequestParam("mobile") String mobile) {
        Map<String, Object> map = new HashMap<>();
        Users users = usersService.getUserInfoBySession();
        if (!ObjectUtils.isEmpty(users.getMobile()) && users.getMobile().equals(mobile) && users.getIsCheckMobile() == 1) {
            map.put("error", "该手机号已验证!");
        } else {
            UsersRecord record = usersService.findByMobileAndUsername(mobile, usersService.getUserName());
            if (ObjectUtils.isEmpty(record)) {
                map.put("ok", "");
            } else {
                map.put("error", "该手机号已被使用!");
            }
        }
        return map;
    }

    /**
     * 更新手机
     *
     * @param mobile
     * @return
     */
    @RequestMapping("/student/checkMobile")
    @ResponseBody
    public AjaxData checkMobile(@RequestParam("mobile") String mobile, @RequestParam("code") String key) {
        Users users = usersService.findByUsername(usersService.getUserName());
        AjaxData ajaxData = new AjaxData();
        if (!ObjectUtils.isEmpty(users)) {
            Timestamp cur = new Timestamp(System.currentTimeMillis());
            if (!ObjectUtils.isEmpty(users.getMobileCheckKeyValidityPeriod()) && cur.before(users.getMobileCheckKeyValidityPeriod())) {
                if (StringUtils.trimWhitespace(key).equals(users.getMobileCheckKey())) {
                    Byte b = 1;
                    users.setMobile(mobile);
                    users.setIsCheckMobile(b);
                    usersService.update(users);
                    ajaxData.success().msg("恭喜您,您的手机:" + users.getMobile() + "已经验证成功!");
                } else {
                    ajaxData.fail().msg("验证码不正确,请重新获取验证码!");
                }
            } else {
                ajaxData.fail().msg("您的手机验证已过有效期(5分钟内),请重新获取验证码!");
            }
        } else {
            ajaxData.fail().msg("未获取到用户信息,验证失效!");
        }
        return ajaxData;
    }
}
