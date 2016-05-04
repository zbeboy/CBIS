package com.school.cbis.web.autonomicpractice;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.*;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.*;
import com.school.cbis.domain.tables.records.AuthoritiesRecord;
import com.school.cbis.domain.tables.records.AutonomousPracticeContentRecord;
import com.school.cbis.domain.tables.records.AutonomousPracticeTemplateRecord;
import com.school.cbis.plugin.jsgrid.JsGrid;
import com.school.cbis.service.*;
import com.school.cbis.util.RandomUtils;
import com.school.cbis.vo.autonomicpractice.*;
import com.school.cbis.vo.grade.GradeVo;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    @Resource
    private AuthoritiesService authoritiesService;

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
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
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
        autonomousPracticeInfoService.deleteById(reportSettingVo.getId());
        return jsGrid.deleteItem(reportSettingVo);
    }

    /**
     * 填报设置添加
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/reportSettingAdd")
    public String reportSettingAdd(ModelMap modelMap) {
        //通过用户类型获取系表ID
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
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
     *
     * @param reportSettingAddVo
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/addReportSetting")
    public String reportSettingSave(ReportSettingAddVo reportSettingAddVo) {
        //通过用户类型获取系表ID
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        AutonomousPracticeInfo autonomousPracticeInfo = new AutonomousPracticeInfo();
        autonomousPracticeInfo.setAutonomousPracticeTitle(reportSettingAddVo.getAutonomousPracticeTitle());
        String temp = "";
        for (String s : reportSettingAddVo.getGradeYear()) {
            temp += s + ",";
        }
        temp = temp.substring(0, temp.lastIndexOf(","));
        autonomousPracticeInfo.setGradeYear(temp);
        autonomousPracticeInfo.setAutonomousPracticeTemplateId(reportSettingAddVo.getAutonomousPracticeTemplateId());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts = Timestamp.valueOf(reportSettingAddVo.getStartTime() + " 00:00:00");
        autonomousPracticeInfo.setStartTime(ts);
        ts = Timestamp.valueOf(reportSettingAddVo.getEndTime() + " 23:59:59");
        autonomousPracticeInfo.setEndTime(ts);
        autonomousPracticeInfo.setUsersId(usersService.getUserName());
        autonomousPracticeInfo.setTieId(tieId);
        autonomousPracticeInfoService.save(autonomousPracticeInfo);
        return "redirect:/administrator/autonomicpractice/reportsettingList";
    }

    /**
     * 填报设置更新界面
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/reportSettingUpdate")
    public String reportSettingUpdate(@RequestParam("id") int id,ModelMap modelMap){
        //通过用户类型获取系表ID
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        AutonomousPracticeInfo autonomousPracticeInfo = autonomousPracticeInfoService.findById(id);
        List<AutonomousPracticeTemplate> autonomousPracticeTemplates = autonomousPracticeTemplateService.findAllByTieId(tieId);
        if (!autonomousPracticeTemplates.isEmpty()) {
            modelMap.addAttribute("autonomousPracticeTemplate", autonomousPracticeTemplates);
        }

        if(!ObjectUtils.isEmpty(autonomousPracticeInfo)){
            List<CheckboxData> list = new ArrayList<>();
            String[] isCheckedYears = autonomousPracticeInfo.getGradeYear().split(",");
            Result<Record1<String>> record1s = gradeService.findAllYearDistinct(tieId);
            if (record1s.isNotEmpty()) {
                for (Record r : record1s) {
                    CheckboxData checkboxData = new CheckboxData();
                    checkboxData.setValue(r.getValue("year").toString());
                    checkboxData.setChecked(false);
                    for(String s:isCheckedYears){
                        if(r.getValue("year").toString().equals(s)){
                            checkboxData.setChecked(true);
                            break;
                        }
                    }
                    list.add(checkboxData);
                }
            }
            modelMap.addAttribute("years", list);
        }
        modelMap.addAttribute("autonomousPracticeInfo",autonomousPracticeInfo);
        return "/student/autonomicpractice/reportsettingupdate";
    }

    /**
     * 更新填报设置信息
     * @param reportSettingAddVo
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/updateReportSetting")
    public String updateReportSetting(ReportSettingAddVo reportSettingAddVo){
        //通过用户类型获取系表ID
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        AutonomousPracticeInfo autonomousPracticeInfo = autonomousPracticeInfoService.findById(reportSettingAddVo.getId());
        autonomousPracticeInfo.setAutonomousPracticeTitle(reportSettingAddVo.getAutonomousPracticeTitle());
        String temp = "";
        for (String s : reportSettingAddVo.getGradeYear()) {
            temp += s + ",";
        }
        temp = temp.substring(0, temp.lastIndexOf(","));
        autonomousPracticeInfo.setGradeYear(temp);
        autonomousPracticeInfo.setAutonomousPracticeTemplateId(reportSettingAddVo.getAutonomousPracticeTemplateId());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts = Timestamp.valueOf(reportSettingAddVo.getStartTime() + " 00:00:00");
        autonomousPracticeInfo.setStartTime(ts);
        ts = Timestamp.valueOf(reportSettingAddVo.getEndTime() + " 23:59:59");
        autonomousPracticeInfo.setEndTime(ts);
        autonomousPracticeInfo.setUsersId(usersService.getUserName());
        autonomousPracticeInfo.setTieId(tieId);
        autonomousPracticeInfoService.update(autonomousPracticeInfo);
        return "redirect:/administrator/autonomicpractice/reportsettingList";
    }

    /**
     * 模板编辑列表
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/templateList")
    public String templateList() {
        return "/student/autonomicpractice/templatelist";
    }

    /**
     * 模板编辑列表数据
     * @param templateVo
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/templateData")
    @ResponseBody
    public Map<String, Object> templateData(TemplateVo templateVo) {
        JsGrid<TemplateVo> jsGrid = new JsGrid<>(new HashMap<>());
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<TemplateVo> list = new ArrayList<>();
        if (tieId > 0) {
            Result<Record4<Integer, String, Timestamp, String>> record4s = autonomousPracticeTemplateService.findAllAndPage(templateVo, tieId);
            if (record4s.isNotEmpty()) {
                list = record4s.into(TemplateVo.class);
                jsGrid.loadData(list, autonomousPracticeTemplateService.findAllAndCount(templateVo, tieId));
            } else {
                jsGrid.loadData(list, 0);
            }
        } else {
            jsGrid.loadData(list, 0);
        }

        return jsGrid.getMap();
    }

    /**
     * 删除模板
     *
     * @param templateVo
     * @return
     */
    @RequestMapping(value = "/administrator/autonomicpractice/deleteTemplate", method = RequestMethod.POST)
    @ResponseBody
    public TemplateVo deleteTemplate(TemplateVo templateVo) {
        JsGrid<TemplateVo> jsGrid = new JsGrid<>();
        List<AutonomousPracticeHead> autonomousPracticeHeads = autonomousPracticeHeadService.findByAutonomousPracticeTemplateId(templateVo.getId());
        autonomousPracticeHeads.forEach(m -> {
            autonomousPracticeContentService.deleteByAutonomousPracticeHeadId(m.getId());
        });
        autonomousPracticeHeadService.deleteByAutonomousPracticeTemplateId(templateVo.getId());
        autonomousPracticeInfoService.deleteByAutonomousPracticeTemplateId(templateVo.getId());
        autonomousPracticeTemplateService.deleteById(templateVo.getId());
        return jsGrid.deleteItem(templateVo);
    }

    /**
     * 模板添加界面
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/templateAdd")
    public String templateAdd() {
        return "/student/autonomicpractice/templateadd";
    }

    /**
     * 模板更新界面
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/templateUpdate")
    public String templateUpdate(@RequestParam("id") int id, ModelMap modelMap){
        AutonomousPracticeTemplate autonomousPracticeTemplate = autonomousPracticeTemplateService.findById(id);
        modelMap.addAttribute("headType", headTypeService.findAll());
        List<SelectData> selectDatas = new ArrayList<>();
        selectDatas.add(new SelectData(1, "student", "学生表"));
        modelMap.addAttribute("databaseTables", selectDatas);

        List<SelectData> tableFields = new ArrayList<>();
        tableFields.add(new SelectData(1, "student_number", "学生号"));
        tableFields.add(new SelectData(1, "student_name", "学生姓名"));
        tableFields.add(new SelectData(1, "grade_name", "学生班级"));
        tableFields.add(new SelectData(1, "student_phone", "学生电话"));
        tableFields.add(new SelectData(1, "student_email", "学生邮箱"));
        tableFields.add(new SelectData(1, "student_birthday", "学生生日"));
        tableFields.add(new SelectData(1, "student_sex", "学生性别"));
        tableFields.add(new SelectData(1, "student_identity_card", "学生身份证号"));
        tableFields.add(new SelectData(1, "student_address", "学生地址"));
        modelMap.addAttribute("tableFileds", tableFields);
        modelMap.addAttribute("autonomousPracticeTemplate",autonomousPracticeTemplate);
        Result<Record12<Integer,String,String,String,String,String,String,String,Byte,String,Byte,Integer>> record12s = autonomousPracticeHeadService.findByAutonomousPracticeTemplateIdWithHeadTypeId(id);
        List<AutonomousPracticeHeadAddVo> autonomousPracticeHeadAddVos = new ArrayList<>();
        if(record12s.isNotEmpty()){
            autonomousPracticeHeadAddVos = record12s.into(AutonomousPracticeHeadAddVo.class);
        }
        modelMap.addAttribute("autonomousPracticeHeads",autonomousPracticeHeadAddVos);
        modelMap.addAttribute("templateId",id);
        return "/student/autonomicpractice/templateupdate";
    }

    /**
     * 初始化模板数据
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/initTemplateData")
    @ResponseBody
    public AjaxData initTemplateData(){
        Map<String,Object> map = new HashMap<>();
        map.put("headType", headTypeService.findAll());
        List<SelectData> selectDatas = new ArrayList<>();
        selectDatas.add(new SelectData(1, "student", "学生表"));
        map.put("databaseTables", selectDatas);
        List<SelectData> tableFields = new ArrayList<>();
        tableFields.add(new SelectData(1, "student_number", "学生号"));
        tableFields.add(new SelectData(1, "student_name", "学生姓名"));
        tableFields.add(new SelectData(1, "grade_name", "学生班级"));
        tableFields.add(new SelectData(1, "student_phone", "学生电话"));
        tableFields.add(new SelectData(1, "student_email", "学生邮箱"));
        tableFields.add(new SelectData(1, "student_birthday", "学生生日"));
        tableFields.add(new SelectData(1, "student_sex", "学生性别"));
        tableFields.add(new SelectData(1, "student_identity_card", "学生身份证号"));
        tableFields.add(new SelectData(1, "student_address", "学生地址"));
        map.put("tableFileds", tableFields);
        //权限
        log.debug("roleList : {}",wordbook.getRoleString());
        map.put("roleList",wordbook.getRoleString());
        return new AjaxData().success().mapData(map);
    }

    /**
     * 检验添加模板名是否重复
     * @param templateName
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/validateAddAutonomicPracticeTemplateTitle")
    @ResponseBody
    public AjaxData validateAddAutonomicPracticeTemplateTitle(@RequestParam("templateName") String templateName){
        if(StringUtils.hasLength(templateName)){
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if(!ObjectUtils.isEmpty(record)){
                tieId = record.getValue(Tables.TIE.ID);
            }
            AutonomousPracticeTemplateRecord autonomousPracticeTemplateRecord = autonomousPracticeTemplateService.findByAutonomousPracticeTemplateTitleAndTieIdEq(templateName,tieId);
            if(ObjectUtils.isEmpty(autonomousPracticeTemplateRecord)){
                return new AjaxData().success().msg("可以使用!");
            } else {
                return new AjaxData().fail().msg("模板已存在!");
            }

        } else {
            return new AjaxData().fail().msg("请填写模板名!");
        }
    }

    /**
     * 检验更新模板名是否重复
     * @param templateId
     * @param templateName
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/validateUpdateAutonomicPracticeTemplateTitle")
    @ResponseBody
    public AjaxData validateUpdateAutonomicPracticeTemplateTitle(@RequestParam("id")int templateId,@RequestParam("templateName") String templateName){
        if(StringUtils.hasLength(templateName)){
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if(!ObjectUtils.isEmpty(record)){
                tieId = record.getValue(Tables.TIE.ID);
            }
            AutonomousPracticeTemplateRecord autonomousPracticeTemplateRecord = autonomousPracticeTemplateService.findByAutonomousPracticeTemplateTitleAndTieIdAndNeId(templateId,templateName,tieId);
            if(ObjectUtils.isEmpty(autonomousPracticeTemplateRecord)){
                return new AjaxData().success().msg("可以使用!");
            } else {
                return new AjaxData().fail().msg("模板已存在!");
            }

        } else {
            return new AjaxData().fail().msg("请填写模板名!");
        }
    }

    /**
     * 添加模板
     * @param templateName
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/addAutonomicPracticeTemplate")
    @ResponseBody
    public AjaxData addAutonomicPracticeTemplate(@RequestParam("templateName") String templateName) {
        if (StringUtils.hasLength(templateName)) {
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if(!ObjectUtils.isEmpty(record)){
                tieId = record.getValue(Tables.TIE.ID);
            }
            AutonomousPracticeTemplate autonomousPracticeTemplate = new AutonomousPracticeTemplate();
            autonomousPracticeTemplate.setAutonomousPracticeTemplateTitle(templateName);
            autonomousPracticeTemplate.setUsersId(usersService.getUserName());
            autonomousPracticeTemplate.setTieId(tieId);
            int id = autonomousPracticeTemplateService.save(autonomousPracticeTemplate);
            return new AjaxData().success().obj(id);
        } else {
            return new AjaxData().fail().msg("请填写模板名!");
        }
    }

    /**
     * 更新模板
     * @param templateId
     * @param templateName
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/updateAutonomicPracticeTemplate")
    @ResponseBody
    public AjaxData<AutonomousPracticeHead> updateAutonomicPracticeTemplate(@RequestParam("id")int templateId,@RequestParam("templateName") String templateName){
        if (StringUtils.hasLength(templateName)) {
            AutonomousPracticeTemplate autonomousPracticeTemplate = autonomousPracticeTemplateService.findById(templateId);
            autonomousPracticeTemplate.setAutonomousPracticeTemplateTitle(templateName);
            autonomousPracticeTemplateService.update(autonomousPracticeTemplate);
            List<AutonomousPracticeHead> autonomousPracticeHeads = autonomousPracticeHeadService.findByAutonomousPracticeTemplateId(templateId);
            return new AjaxData().success().obj(templateId).listData(autonomousPracticeHeads);
        } else {
            return new AjaxData().fail().msg("请填写模板名!");
        }
    }

    /**
     * 添加自主实习标题
     * @param autonomousPracticeHeadListVo
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/addAutonomicPracticeHead")
    @ResponseBody
    public AjaxData addAutonomicPracticeHead(AutonomousPracticeHeadListVo autonomousPracticeHeadListVo) {
        log.debug("autonomousPracticeHeadListVo : {}", autonomousPracticeHeadListVo);
        if (autonomousPracticeHeadListVo.getId() > 0) {
            AutonomousPracticeHead autonomousPracticeHead = new AutonomousPracticeHead();
            autonomousPracticeHead.setTitle(autonomousPracticeHeadListVo.getTitle());
            autonomousPracticeHead.setTitleVariable(RandomUtils.generateTitleVariable());
            autonomousPracticeHead.setHeadTypeId(autonomousPracticeHeadListVo.getHeadTypeSelect());
            if (autonomousPracticeHeadListVo.getIsDatabase() == 1) {
                autonomousPracticeHead.setDatabaseTable("student");
            } else {
                autonomousPracticeHead.setDatabaseTable("");
            }
            autonomousPracticeHead.setDatabaseTableField(autonomousPracticeHeadListVo.getDatabaseFieldSelect());
            autonomousPracticeHead.setAuthority(autonomousPracticeHeadListVo.getAuthority());
            autonomousPracticeHead.setIsShowHighlyActive(autonomousPracticeHeadListVo.getIsShowHighlyActive());
            autonomousPracticeHead.setIsRequired(autonomousPracticeHeadListVo.getIsRequired());
            autonomousPracticeHead.setAutonomousPracticeTemplateId(autonomousPracticeHeadListVo.getId());
            autonomousPracticeHead.setContent(autonomousPracticeHeadListVo.getSelectContentInput());
            autonomousPracticeHead.setIsDatabase(autonomousPracticeHeadListVo.getIsDatabase());
            autonomousPracticeHead.setSort(autonomousPracticeHeadListVo.getSort());
            int id = autonomousPracticeHeadService.save(autonomousPracticeHead);
            autonomousPracticeHead.setId(id);
            return new AjaxData().success().obj(autonomousPracticeHead);
        } else {
            return new AjaxData().fail().msg("参数异常!");
        }
    }

    /**
     * 更新自主实习标题
     * @param autonomousPracticeHeadUpdateVo
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/updateAutonomicPracticeHead")
    @ResponseBody
    public AjaxData updateAutonomicPracticeHead(AutonomousPracticeHeadUpdateVo autonomousPracticeHeadUpdateVo) {
        log.debug("autonomousPracticeHeadListVo : {}", autonomousPracticeHeadUpdateVo);
        if (autonomousPracticeHeadUpdateVo.getId() > 0) {
            AutonomousPracticeHead autonomousPracticeHead = autonomousPracticeHeadService.findById(autonomousPracticeHeadUpdateVo.getId());
            autonomousPracticeHead.setTitle(autonomousPracticeHeadUpdateVo.getTitle());
            autonomousPracticeHead.setHeadTypeId(autonomousPracticeHeadUpdateVo.getHeadTypeSelect());
            if (autonomousPracticeHeadUpdateVo.getIsDatabase() == 1) {
                autonomousPracticeHead.setDatabaseTable("student");
            } else {
                autonomousPracticeHead.setDatabaseTable("");
            }
            autonomousPracticeHead.setDatabaseTableField(autonomousPracticeHeadUpdateVo.getDatabaseFieldSelect());
            autonomousPracticeHead.setAuthority(autonomousPracticeHeadUpdateVo.getAuthority());
            autonomousPracticeHead.setIsShowHighlyActive(autonomousPracticeHeadUpdateVo.getIsShowHighlyActive());
            autonomousPracticeHead.setContent(autonomousPracticeHeadUpdateVo.getSelectContentInput());
            autonomousPracticeHead.setIsDatabase(autonomousPracticeHeadUpdateVo.getIsDatabase());
            autonomousPracticeHead.setIsRequired(autonomousPracticeHeadUpdateVo.getIsRequired());
            autonomousPracticeHeadService.update(autonomousPracticeHead);
            return new AjaxData().success().obj(autonomousPracticeHead);
        } else {
            return new AjaxData().fail().msg("参数异常!");
        }
    }

    /**
     * 删除自主实习标题
     * @param headId
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/deleteAutonomicPracticeHead")
    @ResponseBody
    public AjaxData deleteAutonomicPracticeHead(@RequestParam("id") int headId) {
        if (headId > 0) {
            autonomousPracticeContentService.deleteByAutonomousPracticeHeadId(headId);
            autonomousPracticeHeadService.deleteById(headId);
            return new AjaxData().success().msg("删除标题成功!").obj(headId);
        } else {
            return new AjaxData().fail().msg("参数异常!");
        }
    }

    /**
     * 更新标题是否显示在高效工作区
     * @param id
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/updateAutonomicPracticeHeadHighlyShow")
    @ResponseBody
    public AjaxData updateAutonomicPracticeHeadHighlyShow(@RequestParam("id") int id) {
        AutonomousPracticeHead autonomousPracticeHead = autonomousPracticeHeadService.findById(id);
        Byte bytes = 0;
        if (autonomousPracticeHead.getIsShowHighlyActive() == 0) {
            bytes = 1;
        } else {
            bytes = 0;
        }
        autonomousPracticeHead.setIsShowHighlyActive(bytes);
        autonomousPracticeHeadService.update(autonomousPracticeHead);
        return new AjaxData().success();
    }

    /**
     * 更新高效工作区标题显示顺序
     * @param sort
     * @return
     */
    @RequestMapping("/administrator/autonomicpractice/updateAutonomicPracticeHeadHighlySort")
    @ResponseBody
    public AjaxData updateAutonomicPracticeHeadHighlySort(@RequestParam("sort") String sort) {
        if (StringUtils.hasLength(sort)) {
            String[] temp = sort.split(",");
            for (int i = 0; i < temp.length; i++) {
                AutonomousPracticeHead autonomousPracticeHead = autonomousPracticeHeadService.findById(Integer.parseInt(temp[i]));
                autonomousPracticeHead.setSort(i);
                autonomousPracticeHeadService.update(autonomousPracticeHead);
            }
        }
        return new AjaxData().success();
    }

    @RequestMapping("/student/autonomicpractice/autonomicPractice")
    public String autonomicPractice() {
        return "/student/autonomicpractice/autonomicpracticelist";
    }

    @RequestMapping("/student/autonomicpractice/autonomicPracticeData")
    @ResponseBody
    public AjaxData<AutonomicPracticeListVo> autonomicPractice(int pageNum, int pageSize) {
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        boolean isStudent = false;
        String studentYear = null;
        if(!ObjectUtils.isEmpty(record)){
            if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT) == record.getValue(Tables.USERS.USER_TYPE_ID)) {
                isStudent = true;
                studentYear = record.getValue(Tables.GRADE.YEAR);
            }
            tieId = record.getValue(Tables.TIE.ID);
        }
        Result<Record10<Integer, String, Timestamp, String, String, Timestamp, Timestamp, String, Integer, Integer>> record10s
                = autonomousPracticeInfoService.findByTieIdAndPage(tieId, pageNum, pageSize);
        if (record10s.isNotEmpty()) {
            List<AutonomicPracticeListVo> autonomicPracticeListVos = record10s.into(AutonomicPracticeListVo.class);
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            for (AutonomicPracticeListVo autonomicPracticeListVo : autonomicPracticeListVos) {
                if (isStudent) {//是学生
                    System.out.println();
                    if (ts.after(autonomicPracticeListVo.getStartTime()) && ts.before(autonomicPracticeListVo.getEndTime())) {//是否不在时间范围
                        String[] gradeYears = autonomicPracticeListVo.getGradeYear().split(",");
                        boolean isRight = false;
                        for (String s : gradeYears) {
                            if (s.equals(studentYear)) {//是否在年级中
                                isRight = true;
                                break;
                            }
                        }
                        autonomicPracticeListVo.setOk(isRight);
                    } else {
                        autonomicPracticeListVo.setOk(false);
                    }
                } else {
                    autonomicPracticeListVo.setOk(true);
                }

                if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT) == autonomicPracticeListVo.getUserTypeId()) {
                    List<Student> students = studentService.findByStudentNumber(autonomicPracticeListVo.getUsername());
                    if (!students.isEmpty()) {
                        autonomicPracticeListVo.setRealName(students.get(0).getStudentName());
                    }
                } else if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_TEACHER) == autonomicPracticeListVo.getUserTypeId()) {
                    List<Teacher> teachers = teacherService.findByTeacherJobNumber(autonomicPracticeListVo.getUsername());
                    if (!teachers.isEmpty()) {
                        autonomicPracticeListVo.setRealName(teachers.get(0).getTeacherName());
                    }
                }

                autonomicPracticeListVo.setStartTimeString(autonomicPracticeListVo.getStartTime().toString());
                autonomicPracticeListVo.setEndTimeString(autonomicPracticeListVo.getEndTime().toString());
            }
            PaginationData paginationData = new PaginationData();
            paginationData.setPageSize(pageSize);
            paginationData.setPageNum(pageNum);
            paginationData.setTotalDatas(autonomousPracticeInfoService.findByTieIdAndPageCount(tieId));

            return new AjaxData<AutonomicPracticeListVo>().success().listData(autonomicPracticeListVos).paginationData(paginationData);
        } else {
            return new AjaxData<AutonomicPracticeListVo>().fail().msg("无数据!");
        }
    }

    @RequestMapping("/student/autonomicpractice/autonomicPracticeAdd")
    public String autonomicPracticeAdd(ModelMap modelMap, @RequestParam("id") int id) {
        Record record = usersService.findAll(usersService.getUserName());
        if (!ObjectUtils.isEmpty(record)) {
            if (wordbook.getUserTypeMap().get(Wordbook.USER_TYPE_STUDENT) == record.getValue(Tables.USERS.USER_TYPE_ID)) {
                modelMap.addAttribute("isStudent", true);
                AutonomicPracticeStudentInfoVo autonomicPracticeStudentInfoVo = new AutonomicPracticeStudentInfoVo();
                autonomicPracticeStudentInfoVo.setId(record.getValue(Tables.STUDENT.ID));
                autonomicPracticeStudentInfoVo.setStudentNumber(record.getValue(Tables.STUDENT.STUDENT_NUMBER));
                autonomicPracticeStudentInfoVo.setStudentName(record.getValue(Tables.STUDENT.STUDENT_NAME));
                autonomicPracticeStudentInfoVo.setStudentPhone(record.getValue(Tables.STUDENT.STUDENT_PHONE));
                autonomicPracticeStudentInfoVo.setStudentEmail(record.getValue(Tables.STUDENT.STUDENT_EMAIL));
                autonomicPracticeStudentInfoVo.setStudentBirthday(record.getValue(Tables.STUDENT.STUDENT_BIRTHDAY));
                autonomicPracticeStudentInfoVo.setStudentSex(record.getValue(Tables.STUDENT.STUDENT_SEX));
                autonomicPracticeStudentInfoVo.setStudentIdentityCard(record.getValue(Tables.STUDENT.STUDENT_IDENTITY_CARD));
                autonomicPracticeStudentInfoVo.setStudentAddress(record.getValue(Tables.STUDENT.STUDENT_ADDRESS));
                autonomicPracticeStudentInfoVo.setGradeName(record.getValue(Tables.GRADE.GRADE_NAME));
                modelMap.addAttribute("studentInfo", autonomicPracticeStudentInfoVo);

                Result<Record4<Integer, String, Integer, Integer>> record4s = autonomousPracticeContentService.findByAutonomousPracticeTemplateIdAndStudentId(id, record.getValue(Tables.STUDENT.ID));
                if (record4s.isNotEmpty()) {
                    List<AutonomousPracticeContent> autonomousPracticeContents = record4s.into(AutonomousPracticeContent.class);
                    modelMap.addAttribute("isUpdate", true);
                    modelMap.addAttribute("myContents", autonomousPracticeContents);
                } else {
                    modelMap.addAttribute("isUpdate", false);
                }
            } else {
                modelMap.addAttribute("isStudent", false);
            }
        }
        Result<Record12<Integer, String, String,String, String, String, String, String, Byte, String, Byte, Integer>> record12s = autonomousPracticeHeadService.findByAutonomousPracticeTemplateIdWithHeadTypeId(id);
        List<AutonomousPracticeHeadAddVo> autonomousPracticeHeadAddVos = new ArrayList<>();
        List<AuthoritiesRecord> authoritiesRecords = authoritiesService.findByUsername(usersService.getUserName());
        List<String> strings = new ArrayList<>();
        if (record12s.isNotEmpty()) {
            autonomousPracticeHeadAddVos = record12s.into(AutonomousPracticeHeadAddVo.class);
            int width = 100 / autonomousPracticeHeadAddVos.size();
            JsGridData jsGridData = new JsGridData();
            for (AutonomousPracticeHeadAddVo addVo : autonomousPracticeHeadAddVos) {
                if (addVo.getIsShowHighlyActive() == 1 && useTitle(addVo.getAuthority(), authoritiesRecords)) {
                    if (addVo.getTypeValue().equals("select")) {
                        List<JsGridData.SelectData> selectDatas = new ArrayList<>();
                        String[] temp = addVo.getContent().split(",");
                        for (int i = 0; i < temp.length; i++) {
                            JsGridData.SelectData selectData = jsGridData.getSelectData();
                            selectData.setId(i + 1);
                            selectData.setName(temp[i]);
                            selectDatas.add(selectData);
                        }
                        JsGridData.Select select = jsGridData.getSelect();
                        select.setName(addVo.getTitleVariable());
                        select.setTitle(addVo.getTitle());
                        select.setTextField("Name");
                        select.setValueField("Id");
                        select.setWidth(width);
                        select.setItems(jsGridData.selectDataString(selectDatas));
                        if(addVo.getIsDatabase() == 1 &&(addVo.getDatabaseTableField().equals("student_number")||addVo.getDatabaseTableField().equals("grade_name"))){
                            select.setEditing(false);
                        }
                        strings.add(jsGridData.selectString(select));
                    } else if (addVo.getTypeValue().equals("switch")) {
                        JsGridData.Checkbox checkbox = jsGridData.getCheckbox();
                        checkbox.setName(addVo.getTitleVariable());
                        checkbox.setTitle(addVo.getTitle());
                        checkbox.setVisible(true);
                        checkbox.setWidth(width);
                        if(addVo.getIsDatabase() == 1 && (addVo.getDatabaseTableField().equals("student_number")||addVo.getDatabaseTableField().equals("grade_name"))){
                            checkbox.setEditing(false);
                        }
                        strings.add(jsGridData.checkboxString(checkbox));
                    } else {
                        JsGridData.Text text = jsGridData.getText();
                        text.setName(addVo.getTitleVariable());
                        text.setTitle(addVo.getTitle());
                        text.setVisible(true);
                        text.setWidth(width);
                        if(addVo.getIsDatabase() == 1 && (addVo.getDatabaseTableField().equals("student_number")||addVo.getDatabaseTableField().equals("grade_name"))){
                            text.setEditing(false);
                        }
                        strings.add(jsGridData.textString(text));
                    }

                }
            }
            JsGridData.Text text = jsGridData.getText();
            text.setName("studentId");
            text.setTitle("studentId");
            text.setVisible(false);
            text.setWidth(width);
            strings.add(jsGridData.textString(text));
            JsGridData.Control control = jsGridData.getControl();
            control.setEditing(true);
            control.setClearFilterButton(true);
            control.setModeSwitchButton(false);
            control.setDeleteButton(true);
            control.setWidth(width);
            strings.add(jsGridData.controlString(control));
            modelMap.addAttribute("jsGridField",jsGridData.getFieldsSet(strings));
            modelMap.addAttribute("heads", autonomousPracticeHeadAddVos);
        } else {
            modelMap.addAttribute("jsGridField",strings);
            modelMap.addAttribute("heads", autonomousPracticeHeadAddVos);
        }

        if (!authoritiesRecords.isEmpty()) {
            List<Authorities> authoritiesList = new ArrayList<>();
            for (AuthoritiesRecord r : authoritiesRecords) {
                Authorities authorities = new Authorities(r.getUsername(), r.getAuthority());
                authoritiesList.add(authorities);
            }
            modelMap.addAttribute("currentAuthorities", authoritiesList);
        }
        modelMap.addAttribute("templateId", id);
        return "/student/autonomicpractice/autonomicpracticeadd";
    }

    @RequestMapping("/student/autonomicpractice/addAutonomicPractice")
    @ResponseBody
    public AjaxData addAutonomicPractice(HttpServletRequest request) {
        if (StringUtils.hasLength(request.getParameter("templateId"))) {
            int templateId = Integer.parseInt(request.getParameter("templateId"));
            Result<Record12<Integer, String,String, String, String, String, String, String, Byte, String, Byte, Integer>> record12s =
                    autonomousPracticeHeadService.findByAutonomousPracticeTemplateIdWithHeadTypeId(templateId);
            if (record12s.isNotEmpty()) {
                List<AutonomousPracticeHeadAddVo> autonomousPracticeHeadAddVos = record12s.into(AutonomousPracticeHeadAddVo.class);
                List<AuthoritiesRecord> authoritiesRecords = authoritiesService.findByUsername(usersService.getUserName());
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                for (AutonomousPracticeHeadAddVo ap : autonomousPracticeHeadAddVos) {
                    autonomousPracticeContentService.deleteByAutonomousPracticeHeadIdAndStudentId(ap.getId(), studentId);
                }
                for (AutonomousPracticeHeadAddVo ap : autonomousPracticeHeadAddVos) {
                    if (useTitle(ap.getAuthority(), authoritiesRecords)) {
                        AutonomousPracticeContent autonomousPracticeContent = new AutonomousPracticeContent();
                        autonomousPracticeContent.setAutonomousPracticeHeadId(ap.getId());
                        if (ap.getTypeValue().equals("switch")) {
                            String[] temp = ap.getContent().split(",");
                            if (StringUtils.hasLength(request.getParameter(ap.getTitleVariable())) && temp.length == 2) {
                                if (request.getParameter(ap.getTitleVariable()).equals("on")) {
                                    autonomousPracticeContent.setCotent(temp[0]);
                                } else {
                                    autonomousPracticeContent.setCotent(temp[0]);
                                }
                            } else {
                                autonomousPracticeContent.setCotent(temp[1]);
                            }
                        } else if (ap.getTypeValue().equals("checkbox")) {
                            String[] temp = request.getParameterValues(ap.getTitleVariable());
                            String s = "";
                            for (String st : temp) {
                                s = s + st + ",";
                            }
                            s = s.substring(0, s.lastIndexOf(","));
                            autonomousPracticeContent.setCotent(s);
                        } else {
                            autonomousPracticeContent.setCotent(request.getParameter(ap.getTitleVariable()));
                        }
                        autonomousPracticeContent.setStudentId(studentId);
                        autonomousPracticeContentService.save(autonomousPracticeContent);
                    }
                }
            }
        } else {
            return new AjaxData().fail().msg("参数异常!");
        }
        return new AjaxData().success().msg("保存成功!");
    }

    /**
     * 是否有权限使用title
     *
     * @param headsAuthority
     * @param authoritiesRecords
     * @return
     */
    private boolean useTitle(String headsAuthority, List<AuthoritiesRecord> authoritiesRecords) {
        String[] authorities = headsAuthority.split(",");
        boolean isRight = false;
        if (!authoritiesRecords.isEmpty()) {
            for (int i = 0; i < authoritiesRecords.size(); i++) {
                for (int j = 0; j < authorities.length; j++) {
                    if (authoritiesRecords.get(i).getAuthority().equals(authorities[j])) {
                        isRight = true;
                        break;
                    }
                }
                if (isRight) {
                    break;
                }
            }
        }
        return isRight;
    }

    @RequestMapping("/student/autonomicpractice/autonomicPracticeAdminData")
    @ResponseBody
    public Map<String, Object> autonomicPracticeData(HttpServletRequest request) {
        JsGrid jsGrid = new JsGrid(new HashMap<>());
        Result<Record1<Integer>> studentIds = autonomousPracticeContentService.findByAutonomousPracticeTemplateIdDistinctAndPage(request);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        if (studentIds.isNotEmpty()) {
            int autonomousPracticeTemplateId = Integer.parseInt(request.getParameter("autonomousPracticeTemplateId"));
            List<AuthoritiesRecord> authoritiesRecords = authoritiesService.findByUsername(usersService.getUserName());
            for (Record r : studentIds) {
                Result<Record3<String , String,String>> record6s =
                        autonomousPracticeContentService.findByAutonomousPracticeTemplateIdAndStudentIdWithAuthority(autonomousPracticeTemplateId, r.getValue(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID));
                if (record6s.isNotEmpty()) {
                    stringBuffer.append("{");
                    String temp = "\"studentId\":\""+r.getValue(Tables.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID)+"\"";
                    for (Record rs : record6s) {
                        if (useTitle(rs.getValue(Tables.AUTONOMOUS_PRACTICE_HEAD.AUTHORITY), authoritiesRecords)) {
                            //select checkbox ??? 前面页面应先组装标题以及select data
                           temp = temp + "\""+rs.getValue(Tables.AUTONOMOUS_PRACTICE_HEAD.TITLE_VARIABLE)+"\":\""+rs.getValue(Tables.AUTONOMOUS_PRACTICE_CONTENT.COTENT)+"\",";

                        }
                    }
                    temp = temp.substring(0,temp.lastIndexOf(","));
                    stringBuffer.append(temp);
                    stringBuffer.append("}");
                }
            }
        }
        stringBuffer.append("]");
        jsGrid.loadData(stringBuffer.toString(),autonomousPracticeContentService.findByAutonomousPracticeTemplateIdDistinctCount(request));
        return jsGrid.getMap();
    }
}
