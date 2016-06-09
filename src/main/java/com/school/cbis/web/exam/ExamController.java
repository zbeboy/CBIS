package com.school.cbis.web.exam;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Exam;
import com.school.cbis.domain.tables.records.TieRecord;
import com.school.cbis.service.ExamService;
import com.school.cbis.service.MajorService;
import com.school.cbis.service.UsersService;
import com.school.cbis.vo.exam.ExamListVo;
import org.jooq.Record;
import org.jooq.Record11;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guipeng on 2016/6/7.
 */
@Controller
public class ExamController {

    private final Logger log = LoggerFactory.getLogger(ExamController.class);

    @Resource
    private UsersService usersService;

    @Resource
    private ExamService examService;

    @Resource
    private MajorService majorService;

    @Resource
    private Wordbook wordbook;

    /**
     * 考试管理
     * @param examListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/exam/examManager")
    public String examManager(ExamListVo examListVo, ModelMap modelMap){
        modelMap.addAttribute("examListVo", examListVo);
        return "/maintainer/exam/examlist";
    };

    /**
     * 考试管理数据
     * @param examListVo
     * @return
     */
    @RequestMapping("/maintainer/exam/examManagerData")
    @ResponseBody
    public AjaxData<ExamListVo> examManagerData(ExamListVo examListVo){
        AjaxData<ExamListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        List<ExamListVo> examListVoList = new ArrayList<>();
        if(tieId>0){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String>> examRecords = examService.findByTieIdAndPage(examListVo, tieId);
            if (examRecords.isNotEmpty()){
                examListVoList = examRecords.into(ExamListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(examListVo.getPageNum());
                paginationData.setPageSize(examListVo.getPageSize());
                paginationData.setTotalDatas(examService.findByTieIdAndPageCount(examListVo, tieId));
                ajaxData.success().listData(examListVoList).setPaginationData(paginationData);
            } else {
                ajaxData.fail().listData(examListVoList);
            }
        } else {
            ajaxData.fail().listData(examListVoList);
        }
        return ajaxData;
    }

    /**
     * 删除考试
     * @param examListVo
     * @return
     */
    @RequestMapping("/maintainer/exam/deleteExam")
    @ResponseBody
    public AjaxData deleteExam(ExamListVo examListVo){
        //完全删除
        examService.deleteById(examListVo.getId());
        return new AjaxData().success().msg("删除考试成功!");
    }

    /**
     * 更新考试
     * @param examListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/exam/examUpdate")
    public String  examUpdate(ExamListVo examListVo,ModelMap modelMap){
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        Exam exam = examService.findById(examListVo.getId());
        if (!ObjectUtils.isEmpty(exam)) {
            examListVo.setId(exam.getId());
            examListVo.setExamTitle(exam.getExamTitle());
            log.debug(" time : {} ",exam.getExamTime().toString());
            String date = exam.getExamTime().toString().split("[ ]")[0];
            String time = exam.getExamTime().toString().split("[ ]")[1].split("[.]")[0];
            examListVo.setExamDate(date);
            examListVo.setExamTime(time);
            examListVo.setExamAddress(exam.getExamAddress());
            examListVo.setMajorId(exam.getMajorId());
            examListVo.setExamContent(exam.getExamContent());
            modelMap.addAttribute("exam", examListVo);
        }else {
            modelMap.addAttribute("exam",new Exam());
        }
        modelMap.addAttribute("majors",majorService.findAllByTieId(tieId));
        return "/maintainer/exam/examupdate";
    }

    /**
     * 考试添加
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/exam/examAdd")
    public String examAdd(ModelMap modelMap){
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        modelMap.addAttribute("majors",majorService.findAllByTieId(tieId));
        return "/maintainer/exam/examadd";
    }

    /**
     * 添加考试数据
     * @param examListVo
     * @return
     */
    @RequestMapping("/maintainer/exam/addExam")
    public String addExam(ExamListVo examListVo){
        log.debug(" examListVo : {} ",examListVo);
        try{
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if(!ObjectUtils.isEmpty(record)){
                tieId = record.getValue(Tables.TIE.ID);
            }
            if(tieId>0){
                Exam exam = new Exam();
                exam.setTieId(tieId);
                exam.setMajorId(examListVo.getMajorId());
                exam.setExamAddress(examListVo.getExamAddress());
                exam.setExamTitle(examListVo.getExamTitle());
                exam.setExamContent(examListVo.getExamContent());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date date = sdf.parse(examListVo.getExamDate() +" "+ examListVo.getExamTime());
                Timestamp timestamp = new Timestamp(date.getTime());
                exam.setExamTime(timestamp);
                exam.setUsername(usersService.getUserName());
                exam.setCreateTime(new Timestamp(System.currentTimeMillis()));
                examService.save(exam);
            }
        } catch (ParseException e) {
            log.error("exam format time exception is {} ",e.getMessage());
        }
        return "redirect:/maintainer/exam/examManager";
    }

    /**
     * 更新考试
     * @param examListVo
     * @return
     */
    @RequestMapping("/maintainer/exam/updateExam")
    public String updateExam(ExamListVo examListVo){
        try {
            Exam exam = examService.findById(examListVo.getId());
            exam.setMajorId(examListVo.getMajorId());
            exam.setExamAddress(examListVo.getExamAddress());
            exam.setExamContent(examListVo.getExamContent());
            exam.setExamTitle(examListVo.getExamTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date = sdf.parse(examListVo.getExamDate() + " " + examListVo.getExamTime());
            Timestamp timestamp = new Timestamp(date.getTime());
            exam.setExamTime(timestamp);
            examService.update(exam);
        } catch (ParseException e) {
            log.error("exam format time exception is {} ",e.getMessage());
        }
        return "redirect:/maintainer/exam/examManager";
    }

    /**
     * 考试展示
     * @param examListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/exam/examShow")
    public String examShow(ExamListVo examListVo,ModelMap modelMap){
        modelMap.addAttribute("examListVo",examListVo);
        TieRecord tieRecord = wordbook.getTieInfo();
        //显示最新4条数据
        ExamListVo examListVo1 = new ExamListVo();
        examListVo1.setPageNum(0);
        examListVo1.setPageSize(4);
        List<ExamListVo> examListVoList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(tieRecord)){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String>>
                    examRecords = examService.findByTieIdAndPage(examListVo1, tieRecord.getId());
            if(examRecords.isNotEmpty()){
                examListVoList = examRecords.into(ExamListVo.class);
            }
        }
        modelMap.addAttribute("examListVoList",examListVoList);
        return "/user/exam/examshow";
    }

    /**
     * 考试展示数据
     * @param examListVo
     * @return
     */
    @RequestMapping("/user/exam/examShowData")
    @ResponseBody
    public AjaxData<ExamListVo> examShowData(ExamListVo examListVo){
        AjaxData<ExamListVo> ajaxData = new AjaxData<>();
        TieRecord tieRecord = wordbook.getTieInfo();
        List<ExamListVo> examListVoList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(tieRecord)){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String>>
                    examRecords = examService.findByTieIdAndPage(examListVo, tieRecord.getId());
            if(examRecords.isNotEmpty()){
                examListVoList = examRecords.into(ExamListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(examListVo.getPageNum());
                paginationData.setPageSize(examListVo.getPageSize());
                paginationData.setTotalDatas(examService.findByTieIdAndPageCount(examListVo,tieRecord.getId()));
                ajaxData.success().listData(examListVoList).paginationData(paginationData);
            } else {
                ajaxData.fail().listData(examListVoList);
            }
        } else {
            ajaxData.fail().listData(examListVoList);
        }
        return ajaxData;
    }

    /**
     * 招聘展示详细
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/exam/examShowDetailed")
    public String examShowDetailed(@RequestParam("id") int id, ModelMap modelMap){
        TieRecord tieRecord = wordbook.getTieInfo();
        //显示最新4条数据
        ExamListVo examListVo1 = new ExamListVo();
        examListVo1.setPageNum(0);
        examListVo1.setPageSize(4);
        List<ExamListVo> examListVoList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(tieRecord)){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String>>
                    examRecords = examService.findByTieIdAndPage(examListVo1, tieRecord.getId());
            if(examRecords.isNotEmpty()){
                examListVoList = examRecords.into(ExamListVo.class);
            }
        }
        modelMap.addAttribute("examListVoList",examListVoList);

        ExamListVo examListVo = new ExamListVo();
        Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String> record11 =
                examService.findByIdWithUserAndMajor(id);
        if(!ObjectUtils.isEmpty(record11)){
            examListVo = record11.into(ExamListVo.class);
        }
        modelMap.addAttribute("examListVo",examListVo);
        return "/user/exam/examshowdetailed";
    }
}
