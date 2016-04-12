package com.school.cbis.web.autonomicpractice;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeHead;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeInfo;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeTemplate;
import com.school.cbis.plugin.jsgrid.JsGrid;
import com.school.cbis.service.*;
import com.school.cbis.vo.autonomicpractice.ReportSettingAddVo;
import com.school.cbis.vo.autonomicpractice.ReportSettingVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private HeadTypeService headTypeService;

    @Resource
    private AutonomousPracticeHeadService autonomousPracticeHeadService;

    @Resource
    private AutonomousPracticeContentService autonomousPracticeContentService;

    @Resource
    private Wordbook wordbook;

    /**
     * 自主实习填报设置
     *
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/reportsettingList")
    public String reportSetting() {
        return "/student/autonomicpractice/reportsettinglist";
    }

    /**
     * 自主实习填报设置列表数据
     *
     * @param reportSettingVo
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/reportSettingData")
    @ResponseBody
    public Map<String, Object> reportSettingData(ReportSettingVo reportSettingVo) {
        JsGrid<ReportSettingVo> jsGrid = new JsGrid<>(new HashMap<>());
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<ReportSettingVo> list = new ArrayList<>();
        if (tieId > 0) {
            Result<Record7<Integer, String, Timestamp, String, Timestamp, Timestamp, String>> record7s = autonomousPracticeInfoService.findByTieIdAndPage(reportSettingVo, tieId);
            if (record7s.isNotEmpty()) {
                list = record7s.into(ReportSettingVo.class);
                jsGrid.loadData(list, autonomousPracticeInfoService.findByTieIdAndCount(reportSettingVo, tieId));
            } else {
                jsGrid.loadData(list, 0);
            }
        } else {
            jsGrid.loadData(list, 0);
        }

        return jsGrid.getMap();
    }

    /**
     * 删除自主实习
     *
     * @param reportSettingVo
     * @return
     */
    @RequestMapping(value = "/administrator/autonomicpractice/deleteReportSetting", method = RequestMethod.POST)
    @ResponseBody
    public ReportSettingVo deleteReportSetting(ReportSettingVo reportSettingVo) {
        JsGrid<ReportSettingVo> jsGrid = new JsGrid<>();
        List<AutonomousPracticeHead> autonomousPracticeHeads = autonomousPracticeHeadService.findByAutonomousPracticeInfoId(reportSettingVo.getId());
        if (!autonomousPracticeHeads.isEmpty()) {
            autonomousPracticeHeads.forEach(m -> {
                autonomousPracticeContentService.deleteByAutonomousPracticeHeadId(m.getId());
            });
        }
        autonomousPracticeHeadService.deleteByAutonomousPracticeInfoId(reportSettingVo.getId());
        autonomousPracticeInfoService.deleteById(reportSettingVo.getId());
        return jsGrid.deleteItem(reportSettingVo);
    }

    /**
     * 填报设置添加
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/reportSettingAdd")
    public String reportSettingAdd(ModelMap modelMap) {
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<AutonomousPracticeTemplate> autonomousPracticeTemplates = autonomousPracticeTemplateService.findAllByTieId(tieId);
        if (!autonomousPracticeTemplates.isEmpty()) {
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
        return "/student/autonomicpractice/reportsettingadd";
    }

    /**
     * 保存填报设置
     * @param reportSettingAddVo
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/reportSettingSave")
    public String reportSettingSave(ReportSettingAddVo reportSettingAddVo){
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        AutonomousPracticeInfo autonomousPracticeInfo = new AutonomousPracticeInfo();
        autonomousPracticeInfo.setAutonomousPracticeTitle(reportSettingAddVo.getAutonomousPracticeTitle());
        String temp = "";
        for (String s : reportSettingAddVo.getGradeYear()) {
            temp += s + ",";
        }
        temp = temp.substring(0, temp.lastIndexOf(","));
        autonomousPracticeInfo.setGradeYear(temp);
        if (!StringUtils.hasLength(reportSettingAddVo.getAutonomousPracticeTemplateId())) {
            autonomousPracticeInfo.setAutonomousPracticeTemplateId(0);
        } else {
            autonomousPracticeInfo.setAutonomousPracticeTemplateId(Integer.parseInt(reportSettingAddVo.getAutonomousPracticeTemplateId()));
        }
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts = Timestamp.valueOf(reportSettingAddVo.getStartTime() + " 00:00:00");
        autonomousPracticeInfo.setStartTime(ts);
        ts = Timestamp.valueOf(reportSettingAddVo.getEndTime() + " 00:00:00");
        autonomousPracticeInfo.setEndTime(ts);
        autonomousPracticeInfo.setUsersId(usersService.getUserName());
        autonomousPracticeInfo.setTieId(tieId);
        autonomousPracticeInfoService.save(autonomousPracticeInfo);
        return "redirect:/administrator/autonomicpractice/reportsettingList";
    }
}
