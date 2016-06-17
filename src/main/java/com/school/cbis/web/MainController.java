package com.school.cbis.web;

import com.octo.captcha.service.CaptchaServiceException;
import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.TieRecord;
import com.school.cbis.service.*;
import com.school.cbis.util.CaptchaServiceSingleton;
import com.school.cbis.util.RandomUtils;
import com.school.cbis.vo.exam.ExamListVo;
import com.school.cbis.vo.major.MajorIndexVo;
import com.school.cbis.vo.recruit.RecruitListVo;
import org.joda.time.DateTime;
import org.jooq.Record;
import org.jooq.Record11;
import org.jooq.Result;
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
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-01-05.
 */
@Controller
public class MainController {

    private final Logger log = LoggerFactory.getLogger(MainController.class);

    @Resource
    private UsersService usersService;

    @Resource
    private TieElegantService tieElegantService;

    @Resource
    private TieService tieService;

    @Resource
    private ArticleInfoService articleInfoService;

    @Resource
    private Wordbook wordbook;

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    @Resource
    private TieNoticeService tieNoticeService;

    @Resource
    private MajorService majorService;

    @Resource
    private MailService mailService;

    @Resource
    private MailboxCountService mailboxCountService;

    @Resource
    private SystemLogService systemLogService;

    @Resource
    private RecruitService recruitService;

    @Resource
    private ExamService examService;
    /**
     *  主页
     * ## 本项目只针对一个系,不再提供多个系扩展
     * ## 本方法利用 thymeleaf 缓存
     * @param modelMap
     * @param tieId 提供系接入
     * @return
     */
    @RequestMapping("/")
    public String root(ModelMap modelMap,@RequestParam(value = "tieId",defaultValue = "0",required = false) int tieId) {
        Tie tie = null;
        if(tieId == 0){
            TieRecord tieRecord = wordbook.getTieInfo();
            tie = tieRecord.into(Tie.class);
        } else {
            tie = tieService.findById(tieId);
        }

        int tieIntroduceArticleInfoId = 0;//系简介
        //系简介
        if (!StringUtils.isEmpty(tie) && !StringUtils.isEmpty(tie.getTieIntroduceArticleInfoId())) {
            tieIntroduceArticleInfoId = tie.getTieIntroduceArticleInfoId();
        }

        //系简介
        ArticleInfo tieIntroduce = articleInfoService.findById(tieIntroduceArticleInfoId);
        if (!StringUtils.isEmpty(tieIntroduce)) {
            Users users = usersService.findByUsername(tieIntroduce.getArticleWriter());
            tieIntroduce.setArticleWriter(users.getRealName());
            if (tieIntroduce.getArticleContent().trim().length() > 100) {
                tieIntroduce.setArticleContent(tieIntroduce.getArticleContent().substring(0, 100) + "....");
            }
        }
        modelMap.addAttribute("tieIntroduce", tieIntroduce);

        Byte b = 1;
        //系风采
        List<TieElegant> tieElegants = tieElegantService.findByShow(b);
        List<ArticleInfo> tieElegantData = new ArrayList<>();
        for (TieElegant t : tieElegants) {
            ArticleInfo articleInfo = articleInfoService.findById(t.getTieElegantArticleInfoId());
            tieElegantData.add(articleInfo);
        }
        modelMap.addAttribute("tieElegantData", tieElegantData);


        //系公告
        List<TieNotice> tieNotices = tieNoticeService.findByShow(b);
        List<ArticleInfo> tieNoticeData = new ArrayList<>();
        for (TieNotice t : tieNotices) {
            ArticleInfo articleInfo = articleInfoService.findById(t.getTieNoticeArticleInfoId());
            tieNoticeData.add(articleInfo);
        }
        modelMap.addAttribute("tieNoticeData", tieNoticeData);

        //专业简介
        List<Major> majors = majorService.findByShow(b);
        List<MajorIndexVo> majorIndexVos = new ArrayList<>();
        for (Major m : majors) {
            MajorIndexVo majorIndexVo = new MajorIndexVo();
            majorIndexVo.setMajorId(m.getId());
            majorIndexVo.setMajorName(m.getMajorName());
            if (!ObjectUtils.isEmpty(m.getMajorIntroduceArticleInfoId())) {
                ArticleInfo articleInfo1 = articleInfoService.findById(m.getMajorIntroduceArticleInfoId());
                if (!StringUtils.isEmpty(m.getMajorIntroduceArticleInfoId())) {
                    majorIndexVo.setArticleInfoId(m.getMajorIntroduceArticleInfoId());
                    majorIndexVo.setArticleContent(articleInfo1.getArticleContent());
                }
            }
            majorIndexVos.add(majorIndexVo);
        }
        modelMap.addAttribute("majorInfo", majorIndexVos);

        //招聘信息
        RecruitListVo recruitListVo1 = new RecruitListVo();
        recruitListVo1.setPageNum(0);
        recruitListVo1.setPageSize(7);
        List<RecruitListVo> recruitListVoList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(tie)){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String>>
                    recruitRecords = recruitService.findByTieIdAndPage(recruitListVo1,tie.getId());
            if(recruitRecords.isNotEmpty()){
                recruitListVoList = recruitRecords.into(RecruitListVo.class);
            }
        }
        modelMap.addAttribute("recruitListVoList",recruitListVoList);

        //考试信息
        ExamListVo examListVo1 = new ExamListVo();
        examListVo1.setPageNum(0);
        examListVo1.setPageSize(7);
        List<ExamListVo> examListVoList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(tie)){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String>>
                    examRecords = examService.findByTieIdAndPage(examListVo1, tie.getId());
            if(examRecords.isNotEmpty()){
                examListVoList = examRecords.into(ExamListVo.class);
            }
        }
        modelMap.addAttribute("examListVoList",examListVoList);

        return "/user/index";
    }

    /**
     * 后台管理
     *
     * @return
     */
    @RequestMapping("/backstage")
    public String backstage() {
        if (StringUtils.isEmpty(usersService.getUserName())) {
            return "/login";
        }
        Record record = usersService.findAll(usersService.getUserName());
        SystemLog systemLog = new SystemLog();
        systemLog.setUsername(usersService.getUserName());
        systemLog.setTieId(record.getValue(Tables.TIE.ID));
        systemLog.setOperationBehavior("登录后台管理!");
        systemLogService.save(systemLog);
        return "/student/backstage";
    }

    /**
     * 登录页
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 忘记密码
     *
     * @return
     */
    @RequestMapping("/user/mail/forgetPassword")
    public String forgetPassword() {
        return "/user/mail/forgetpassword";
    }

    /**
     * 重置密码时检验用户账号
     *
     * @param username
     * @return
     */
    @RequestMapping("/user/mail/validUsername")
    @ResponseBody
    public Map<String, Object> validUsername(@RequestParam("username") String username) {
        Map<String, Object> map = new HashMap<>();
        Users users = usersService.findByUsername(StringUtils.trimWhitespace(username));
        if (!ObjectUtils.isEmpty(users)) {
            if (users.getIsCheckEmail() == 0) {
                map.put("error", "您的账号未验证邮箱,无法完成重置!");
            } else {
                map.put("ok", "ok");
            }
        } else {
            map.put("error", "账号不存在!");
        }
        return map;
    }

    /**
     * 发送重置密码邮件
     *
     * @param username
     * @param request
     * @return
     */
    @RequestMapping("/user/mail/sendResetPasswordEmail")
    @ResponseBody
    public AjaxData sendResetPasswordEmail(@RequestParam("username") String username, HttpServletRequest request) {
        Users users = usersService.findByUsername(StringUtils.trimWhitespace(username));
        if (!ObjectUtils.isEmpty(users)) {
            if (users.getIsCheckEmail() == 0) {
                return new AjaxData().fail().msg("您的账号未验证邮箱,无法完成重置!");
            } else {
                if (wordbook.mailSwitch) {
                    if (mailboxCountService.isExceedDailyLimit()) {
                        return new AjaxData().fail().msg("发送失败,已超过每日邮件发送上限!");
                    } else {
                        users.setPasswordResetKey(RandomUtils.generateResetKey());
                        DateTime dateTime = new DateTime().plusDays(2);
                        Timestamp timestamp = new Timestamp(dateTime.getMillis());
                        users.setPasswordResetKeyValidityPeriod(timestamp);
                        usersService.update(users);
                        String path = request.getContextPath();
                        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
                        mailService.sendPasswordResetMail(users, basePath);
                        return new AjaxData().success().msg("重置邮件已发送至您的邮箱,请注意查收!");
                    }
                } else {
                    return new AjaxData().fail().msg("发送失败,管理员已关闭邮箱功能!");
                }
            }
        } else {
            return new AjaxData().fail().msg("账号不存在!");
        }
    }

    /**
     * 验证重置key
     *
     * @param key
     * @param username
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/checkResetPassword")
    public String checkResetPassword(@RequestParam("key") String key, @RequestParam("username") String username, ModelMap modelMap) {
        Users users = usersService.findByUsername(StringUtils.trimWhitespace(username));
        if (!ObjectUtils.isEmpty(users)) {
            Timestamp cur = new Timestamp(System.currentTimeMillis());
            if (cur.before(users.getPasswordResetKeyValidityPeriod())) {
                if (users.getPasswordResetKey().equals(key)) {
                    modelMap.addAttribute("msg", "");
                    modelMap.addAttribute("username", users.getUsername());
                    return "/user/mail/resetpassword";
                } else {
                    modelMap.addAttribute("msg", "验证码无效!");
                }
            } else {
                modelMap.addAttribute("msg", "重置链接已过有效期!");
            }
        } else {
            modelMap.addAttribute("msg", "账号不存在!");
        }
        return "/user/mail/forgetpasswordmsg";
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/user/jcaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] captchaChallengeAsJpeg = null;
        // the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // get the session id that will identify the generated captcha.
            // the same id must be used to validate the response, the session id is a good candidate!
            String captchaId = request.getSession().getId();
            // call the ImageCaptchaService getChallenge method
            BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId, request.getLocale());
            // a jpeg encoder
            ImageIO.write(challenge, "jpeg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(response.SC_NOT_FOUND);
            log.error(" jcaptcha exception : {} ", e.getMessage());
            return;
        } catch (CaptchaServiceException e) {
            response.sendError(response.SC_INTERNAL_SERVER_ERROR);
            log.error(" jcaptcha exception : {} ", e.getMessage());
            return;
        }

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        //flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();

        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * js 检验验证码
     * @param captcha
     * @param request
     * @return
     */
    @RequestMapping("/user/validateCaptchaForId")
    @ResponseBody
    public Map<String, Object> validateCaptchaForId(@RequestParam("j_captcha_response") String captcha, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Boolean isResponseCorrect = Boolean.FALSE;
        // remenber that we need an id to validate!
        String captchaId = request.getSession().getId();
        log.debug("validateCaptchaForId captchaId : {}", captchaId);
        log.debug(" j_captcha_response : {} ", captcha);
        // call the service method
        try {
            isResponseCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, captcha);
            if (isResponseCorrect) {
                map.put("ok", "");
            } else {
                map.put("error", "验证码错误!");
            }
        } catch (CaptchaServiceException e) {
            // should not happen,may be thrown if the id is not valid
            map.put("error", "参数无效,请刷新页面!");
            log.error(" validateCaptchaForId exception : {} ", e.getMessage());
        }
        return map;
    }
}
