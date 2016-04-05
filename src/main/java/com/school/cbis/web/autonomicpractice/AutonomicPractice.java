package com.school.cbis.web.autonomicpractice;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeInfo;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeTemplate;
import com.school.cbis.service.AutonomousPracticeInfoService;
import com.school.cbis.service.AutonomousPracticeTemplateService;
import com.school.cbis.service.GradeService;
import com.school.cbis.service.UsersService;
import com.school.cbis.vo.autonomicpractice.AutonomicPracticeVo;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016-04-04.
 */
@Controller
public class AutonomicPractice {

    private final Logger log = LoggerFactory.getLogger(AutonomicPractice.class);

    @Resource
    private UsersService usersService;

    @Resource
    private GradeService gradeService;

    @Resource
    private AutonomousPracticeInfoService autonomousPracticeInfoService;

    @Resource
    private AutonomousPracticeTemplateService autonomousPracticeTemplateService;

    /**
     * 自主实习填报设置
     *
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/reportSetting")
    public String autonomicLearning(ModelMap modelMap) {
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        Result<Record4<Integer, String, Integer, Timestamp>> record4s = autonomousPracticeTemplateService.findAllByTieId(tieId);
        if (record4s.isNotEmpty()) {
            List<AutonomousPracticeTemplate> autonomousPracticeTemplates = record4s.into(AutonomousPracticeTemplate.class);
            modelMap.addAttribute("autonomousPracticeTemplate", autonomousPracticeTemplates);
        }

        List<String> list = new ArrayList<>();
        Result<Record1<String>> record1s = gradeService.findAllYearDistinct(tieId);
        if (record1s.isNotEmpty()) {
            for (Record r : record1s) {
                list.add(r.getValue("year").toString());
            }
        }
        modelMap.addAttribute("years", list);
        return "/student/autonomicpractice/reportsetting";
    }

    @RequestMapping("/administrator/autonomicpractice/autonomicPracticeTitle")
    public String autonomicPracticeTitle(AutonomicPracticeVo autonomicPracticeVo, ModelMap modelMap) {
        //通过用户类型获取系表ID
//        Result<Record> records = usersService.findAll(usersService.getUserName());
//        int tieId = 0;
//        if (records.isNotEmpty()) {
//            for (Record r : records) {
//                tieId = r.getValue(Tables.TIE.ID);
//            }
//        }
//        AutonomousPracticeInfo autonomousPracticeInfo = new AutonomousPracticeInfo();
//        autonomousPracticeInfo.setAutonomousPracticeTitle(autonomicPracticeVo.getAutonomousPracticeTitle());
//        String temp = "";
//        for (String s : autonomicPracticeVo.getGradeYear()) {
//            temp += s + ",";
//        }
//        temp = temp.substring(0, temp.lastIndexOf(","));
//        autonomousPracticeInfo.setGradeYear(temp);
//        if (!StringUtils.hasLength(autonomicPracticeVo.getAutonomousPracticeTemplateId())) {
//            autonomousPracticeInfo.setAutonomousPracticeTemplateId(0);
//        } else {
//            autonomousPracticeInfo.setAutonomousPracticeTemplateId(Integer.parseInt(autonomicPracticeVo.getAutonomousPracticeTemplateId()));
//        }
//        Timestamp ts = new Timestamp(System.currentTimeMillis());
//        ts = Timestamp.valueOf(autonomicPracticeVo.getStartTime() + " 00:00:00");
//        autonomousPracticeInfo.setStartTime(ts);
//        ts = Timestamp.valueOf(autonomicPracticeVo.getEndTime() + " 00:00:00");
//        autonomousPracticeInfo.setEndTime(ts);
//        autonomousPracticeInfo.setUsersId(usersService.getUserName());
//        autonomousPracticeInfo.setTieId(tieId);
//        int id = autonomousPracticeInfoService.save(autonomousPracticeInfo);
//        modelMap.addAttribute("autonomousPracticeInfoId",id);
        return "/student/autonomicpractice/autonomicpracticetitle";
    }

    @RequestMapping("/administrator/autonomicpractice/autonomicPracticeWork")
    public String autonomicPracticeWork() {
        return "/student/autonomicpractice/autonomicpracticework";
    }
}
