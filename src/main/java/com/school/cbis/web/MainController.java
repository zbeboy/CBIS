package com.school.cbis.web;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.service.*;
import com.school.cbis.vo.major.MajorIndexVo;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        Users users =usersService.getUserInfoBySession();
        mailService.sendActivationEmail(users,"/test");
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
}
