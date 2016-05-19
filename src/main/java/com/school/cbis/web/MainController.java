package com.school.cbis.web;

import com.octo.captcha.service.CaptchaServiceException;
import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.service.*;
import com.school.cbis.util.CaptchaServiceSingleton;
import com.school.cbis.util.RandomUtils;
import com.school.cbis.vo.major.MajorIndexVo;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.joda.time.DateTime;
import org.jooq.Record;
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
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 主页
     *
     * @return
     */
    @RequestMapping("/")
    public String root(ModelMap modelMap) {
        int tieId = 0;
        int tieIntroduceArticleInfoId = 0;//系简介
        if (!StringUtils.isEmpty(usersService.getUserName())) {
            Record record = usersService.findAll(usersService.getUserName());
            if (!ObjectUtils.isEmpty(record)) {
                tieId = record.getValue(Tables.TIE.ID);
                if (!StringUtils.isEmpty(record.getValue(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID))) {
                    tieIntroduceArticleInfoId = record.getValue(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID);
                }
            }
        } else {
            tieId = 1;

            //系简介
            Tie tie = tieService.findById(tieId);
            if (!StringUtils.isEmpty(tie) && !StringUtils.isEmpty(tie.getTieIntroduceArticleInfoId())) {
                tieIntroduceArticleInfoId = tie.getTieIntroduceArticleInfoId();
            }
        }

        //系风采
        List<TieElegant> tieElegants = tieElegantService.findByShow(Byte.parseByte("1"));
        List<ArticleInfo> tieElegantData = new ArrayList<>();
        if (!tieElegants.isEmpty()) {
            for (TieElegant t : tieElegants) {
                ArticleInfo articleInfo = articleInfoService.findById(t.getTieElegantArticleInfoId());
                if (StringUtils.hasLength(articleInfo.getArticlePhotoUrl())) {
                    String[] paths = articleInfo.getArticlePhotoUrl().split("/");
                    String photo = "/" + paths[paths.length - 3] + "/" + paths[paths.length - 2] + "/" + paths[paths.length - 1];
                    articleInfo.setArticlePhotoUrl(photo);
                }
                tieElegantData.add(articleInfo);
            }
            modelMap.addAttribute("tieElegantData", tieElegantData);
        }

        //系公告
        List<TieNotice> tieNotices = tieNoticeService.findByShow(Byte.parseByte("1"));
        List<ArticleInfo> tieNoticeData = new ArrayList<>();
        if (!tieNotices.isEmpty()) {
            for (TieNotice t : tieNotices) {
                ArticleInfo articleInfo = articleInfoService.findById(t.getTieNoticeArticleInfoId());
                tieNoticeData.add(articleInfo);
            }
            modelMap.addAttribute("tieNoticeData", tieNoticeData);
        }

        //系简介
        ArticleInfo articleInfo = articleInfoService.findById(tieIntroduceArticleInfoId);
        if (!StringUtils.isEmpty(articleInfo)) {
            Users users = usersService.findByUsername(articleInfo.getArticleWriter());
            articleInfo.setArticleWriter(users.getRealName());
            if (articleInfo.getArticleContent().trim().length() > 100) {
                articleInfo.setArticleContent(articleInfo.getArticleContent().substring(0, 100) + "....");
            }
        } else {
            articleInfo = new ArticleInfo();
        }

        modelMap.addAttribute("tieIntroduce", articleInfo);

        //专业简介
        List<Major> majors = majorService.findByShow(Byte.parseByte("1"));
        List<MajorIndexVo> majorIndexVos = new ArrayList<>();
        if (!majors.isEmpty()) {
            for (Major m : majors) {
                MajorIndexVo majorIndexVo = new MajorIndexVo();
                ArticleInfo articleInfo1 = articleInfoService.findById(m.getMajorIntroduceArticleInfoId());
                majorIndexVo.setMajorId(m.getId());
                majorIndexVo.setMajorName(m.getMajorName());
                if (!StringUtils.isEmpty(m.getMajorIntroduceArticleInfoId())) {
                    majorIndexVo.setArticleInfoId(m.getMajorIntroduceArticleInfoId());
                    majorIndexVo.setArticleContent(articleInfo1.getArticleContent());
                }
                majorIndexVos.add(majorIndexVo);
            }
            modelMap.addAttribute("majorInfo", majorIndexVos);
        }

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

    @RequestMapping("/user/jcaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] captchaChallengeAsJpeg = null;
        // the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try{
            // get the session id that will identify the generated captcha.
            // the same id must be used to validate the response, the session id is a good candidate!
            String captchaId = request.getSession().getId();
            log.debug("getCaptcha captchaId : {}",captchaId);
            // call the ImageCaptchaService getChallenge method
            BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId,request.getLocale());
            // a jpeg encoder
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
            jpegEncoder.encode(challenge);
        } catch (IllegalArgumentException e){
            response.sendError(response.SC_NOT_FOUND);
            log.error(" jcaptcha exception : {} ",e.getMessage());
            return;
        } catch (CaptchaServiceException e) {
            response.sendError(response.SC_INTERNAL_SERVER_ERROR);
            log.error(" jcaptcha exception : {} ",e.getMessage());
            return;
        }

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        //flush it in the response
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();

        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @RequestMapping("/user/validateCaptchaForId")
    @ResponseBody
    public Map<String,Object> validateCaptchaForId(@RequestParam("j_captcha_response") String captcha,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Boolean isResponseCorrect = Boolean.FALSE;
        // remenber that we need an id to validate!
        String captchaId = request.getSession().getId();
        log.debug("validateCaptchaForId captchaId : {}",captchaId);
        log.debug(" j_captcha_response : {} ",captcha);
        // call the service method
        try{
            isResponseCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId,captcha);
            if(isResponseCorrect){
                map.put("ok","");
            } else {
                map.put("error","验证码错误!");
            }
        } catch (CaptchaServiceException e){
            // should not happen,may be thrown if the id is not valid
            map.put("error","参数无效,请刷新页面!");
            log.error(" validateCaptchaForId exception : {} ",e.getMessage());
        }
        return  map;
    }
}
