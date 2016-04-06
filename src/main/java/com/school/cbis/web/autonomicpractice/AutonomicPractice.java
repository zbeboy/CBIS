package com.school.cbis.web.autonomicpractice;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.SelectData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeHead;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeInfo;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeTemplate;
import com.school.cbis.service.*;
import com.school.cbis.vo.autonomicpractice.AutonomicPracticeTitleVo;
import com.school.cbis.vo.autonomicpractice.AutonomicPracticeVo;
import org.apache.commons.lang3.RandomStringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private HeadTypePluginService headTypePluginService;

    @Resource
    private AutonomousPracticeHeadService autonomousPracticeHeadService;

    @Resource
    private Wordbook wordbook;

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
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        AutonomousPracticeInfo autonomousPracticeInfo = new AutonomousPracticeInfo();
        autonomousPracticeInfo.setAutonomousPracticeTitle(autonomicPracticeVo.getAutonomousPracticeTitle());
        String temp = "";
        for (String s : autonomicPracticeVo.getGradeYear()) {
            temp += s + ",";
        }
        temp = temp.substring(0, temp.lastIndexOf(","));
        autonomousPracticeInfo.setGradeYear(temp);
        if (!StringUtils.hasLength(autonomicPracticeVo.getAutonomousPracticeTemplateId())) {
            autonomousPracticeInfo.setAutonomousPracticeTemplateId(0);
        } else {
            autonomousPracticeInfo.setAutonomousPracticeTemplateId(Integer.parseInt(autonomicPracticeVo.getAutonomousPracticeTemplateId()));
        }
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts = Timestamp.valueOf(autonomicPracticeVo.getStartTime() + " 00:00:00");
        autonomousPracticeInfo.setStartTime(ts);
        ts = Timestamp.valueOf(autonomicPracticeVo.getEndTime() + " 00:00:00");
        autonomousPracticeInfo.setEndTime(ts);
        autonomousPracticeInfo.setUsersId(usersService.getUserName());
        autonomousPracticeInfo.setTieId(tieId);
        int id = autonomousPracticeInfoService.save(autonomousPracticeInfo);
        modelMap.addAttribute("autonomousPracticeInfoId", id);

        modelMap.addAttribute("headTypes", headTypeService.findAll());
        modelMap.addAttribute("headTypePlugins", headTypePluginService.findAll());
        List<SelectData> selectDatas = new ArrayList<>();
        selectDatas.add(new SelectData(1, "student", "学生表"));
        modelMap.addAttribute("databaseTables", selectDatas);

        List<SelectData> tableFields = new ArrayList<>();
        tableFields.add(new SelectData(1, "student_number", "学生号"));
        tableFields.add(new SelectData(1, "student_name", "学生姓名"));
        tableFields.add(new SelectData(1, "grade_id", "班级"));
        tableFields.add(new SelectData(1, "student_phone", "学生电话"));
        tableFields.add(new SelectData(1, "student_email", "学生邮箱"));
        tableFields.add(new SelectData(1, "student_birthday", "学生生日"));
        tableFields.add(new SelectData(1, "student_sex", "学生性别"));
        tableFields.add(new SelectData(1, "student_identity_card", "学生身份证号"));
        tableFields.add(new SelectData(1, "student_address", "学生地址"));
        modelMap.addAttribute("tableFileds", tableFields);

        List<SelectData> roleList = new ArrayList<>();
        Set<Map.Entry<String, String>> set = wordbook.getRoleMap().entrySet();
        set.forEach(m -> {
            SelectData selectData = new SelectData();
            selectData.setValue(m.getKey());
            selectData.setText(m.getValue());
            roleList.add(selectData);
        });
        modelMap.addAttribute("roles", roleList);

        return "/student/autonomicpractice/autonomicpracticetitle";
    }

    @RequestMapping("/administrator/autonomicpractice/saveAutonomicPracticeTitle")
    @ResponseBody
    public AjaxData saveAutonomicPracticeTitle(AutonomicPracticeTitleVo autonomicPracticeTitleVo) {
        if (StringUtils.hasLength(autonomicPracticeTitleVo.getTitle())) {

            if (StringUtils.hasLength(autonomicPracticeTitleVo.getDatabaseTable())) {

                if (StringUtils.hasLength(autonomicPracticeTitleVo.getDatabaseField())) {
                    AutonomousPracticeHead autonomousPracticeHead = new AutonomousPracticeHead();
                    autonomousPracticeHead.setTitle(autonomicPracticeTitleVo.getTitle());
                    autonomousPracticeHead.setTitleVariable("cbis" + RandomStringUtils.randomAlphanumeric(12));
                    autonomousPracticeHead.setDatabaseTable("student");
                    autonomousPracticeHead.setDatabaseTableField(autonomicPracticeTitleVo.getDatabaseField());

                    String temp = "";
                    for (String s : autonomicPracticeTitleVo.getAuthority()) {
                        temp += s + ",";
                    }

                    temp = temp.substring(0, temp.lastIndexOf(","));
                    autonomousPracticeHead.setAuthority(temp);

                    Byte bytes = 1;

                    if (StringUtils.hasLength(autonomicPracticeTitleVo.getEdit())) {
                        autonomousPracticeHead.setIsEditing(bytes);
                    }

                    if (StringUtils.hasLength(autonomicPracticeTitleVo.getFilter())) {
                        autonomousPracticeHead.setIsFiltering(bytes);
                    }

                    if (StringUtils.hasLength(autonomicPracticeTitleVo.getSort())) {
                        autonomousPracticeHead.setIsSorting(bytes);
                    }

                    if (StringUtils.hasLength(autonomicPracticeTitleVo.getVisible())) {
                        autonomousPracticeHead.setIsVisible(bytes);
                    }

                    if (StringUtils.hasLength(autonomicPracticeTitleVo.getRequired())) {
                        autonomousPracticeHead.setIsRequired(bytes);
                    }

                    autonomousPracticeHead.setAutonomousPracticeInfoId(autonomicPracticeTitleVo.getAutonomousPracticeInfoId());

                    autonomousPracticeHeadService.save(autonomousPracticeHead);
                    return new AjaxData().success();
                } else {
                    return new AjaxData().fail().msg("请选择对应数据库字段!");
                }

            } else {

                if (StringUtils.hasLength(autonomicPracticeTitleVo.getHeadType())) {

                    if (StringUtils.hasLength(autonomicPracticeTitleVo.getHeadTypePlugin())) {
                        AutonomousPracticeHead autonomousPracticeHead = new AutonomousPracticeHead();
                        autonomousPracticeHead.setTitle(autonomicPracticeTitleVo.getTitle());
                        autonomousPracticeHead.setTitleVariable("cbis" + RandomStringUtils.randomAlphanumeric(12));
                        autonomousPracticeHead.setHeadTypeId(Integer.parseInt(autonomicPracticeTitleVo.getHeadType()));
                        autonomousPracticeHead.setHeadTypePluginId(Integer.parseInt(autonomicPracticeTitleVo.getHeadTypePlugin()));
                        autonomousPracticeHead.setHeadTypePluginContent(autonomicPracticeTitleVo.getHeadContent());

                        String temp = "";
                        for (String s : autonomicPracticeTitleVo.getAuthority()) {
                            temp += s + ",";
                        }

                        temp = temp.substring(0, temp.lastIndexOf(","));
                        autonomousPracticeHead.setAuthority(temp);

                        Byte bytes = 1;

                        if (StringUtils.hasLength(autonomicPracticeTitleVo.getEdit())) {
                            autonomousPracticeHead.setIsEditing(bytes);
                        }

                        if (StringUtils.hasLength(autonomicPracticeTitleVo.getFilter())) {
                            autonomousPracticeHead.setIsFiltering(bytes);
                        }

                        if (StringUtils.hasLength(autonomicPracticeTitleVo.getSort())) {
                            autonomousPracticeHead.setIsSorting(bytes);
                        }

                        if (StringUtils.hasLength(autonomicPracticeTitleVo.getVisible())) {
                            autonomousPracticeHead.setIsVisible(bytes);
                        }

                        if (StringUtils.hasLength(autonomicPracticeTitleVo.getRequired())) {
                            autonomousPracticeHead.setIsRequired(bytes);
                        }

                        autonomousPracticeHead.setAutonomousPracticeInfoId(autonomicPracticeTitleVo.getAutonomousPracticeInfoId());
                        autonomousPracticeHeadService.save(autonomousPracticeHead);
                        return new AjaxData().success().obj(autonomousPracticeHead.getId());
                    } else {
                        return new AjaxData().fail().msg("请选择标题扩展!");
                    }
                } else {
                    return new AjaxData().fail().msg("请选择标题类型!");
                }
            }

        } else {
            return new AjaxData().fail().msg("请输入标题!");
        }
    }

    @RequestMapping("/administrator/autonomicpractice/autonomicPracticeWork")
    public String autonomicPracticeWork() {
        return "/student/autonomicpractice/autonomicpracticework";
    }
}
