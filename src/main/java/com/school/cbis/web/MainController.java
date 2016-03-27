package com.school.cbis.web;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.service.*;
import com.school.cbis.vo.article.ArticleVo;
import com.school.cbis.vo.major.MajorIndexVo;
import org.jooq.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016-01-05.
 */
@Controller
public class MainController {

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
            Result<Record> records = usersService.findAll(usersService.getUserName());
            if (records.isNotEmpty()) {
                for (Record r : records) {
                    tieId = r.getValue(Tables.TIE.ID);
                    if (!StringUtils.isEmpty(r.getValue(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID))) {
                        tieIntroduceArticleInfoId = r.getValue(Tables.TIE.TIE_INTRODUCE_ARTICLE_INFO_ID);
                    }
                }
            }
        } else {
            tieId = 1;

            //系简介
            Tie tie = tieService.findById(tieId);
            if (!StringUtils.isEmpty(tie)&&!StringUtils.isEmpty(tie.getTieIntroduceArticleInfoId())) {
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
            for(TieNotice t:tieNotices){
                ArticleInfo articleInfo = articleInfoService.findById(t.getTieNoticeArticleInfoId());
                tieNoticeData.add(articleInfo);
            }
            modelMap.addAttribute("tieNoticeData", tieNoticeData);
        }

        //系简介
        ArticleInfo articleInfo = articleInfoService.findById(tieIntroduceArticleInfoId);
        if (!StringUtils.isEmpty(articleInfo)) {
            Users users = usersService.findByUsername(articleInfo.getArticleWriter());
            if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER) == users.getUserTypeId()) {//教师类型
                List<Teacher> teachers = teacherService.findByTeacherJobNumber(articleInfo.getArticleWriter());
                if (!teachers.isEmpty()) {
                    articleInfo.setArticleWriter(teachers.get(0).getTeacherName());
                }
            } else if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT) == users.getUserTypeId()) {//学生类型
                List<Student> students = studentService.findByStudentNumber(articleInfo.getArticleWriter());
                if (!students.isEmpty()) {
                    articleInfo.setArticleWriter(students.get(0).getStudentName());
                }
            }
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
        if(!majors.isEmpty()){
            for(Major m:majors){
                MajorIndexVo majorIndexVo = new MajorIndexVo();
                ArticleInfo articleInfo1 = articleInfoService.findById(m.getMajorIntroduceArticleInfoId());
                majorIndexVo.setMajorId(m.getId());
                majorIndexVo.setMajorName(m.getMajorName());
                if(!StringUtils.isEmpty(m.getMajorIntroduceArticleInfoId())){
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
}
