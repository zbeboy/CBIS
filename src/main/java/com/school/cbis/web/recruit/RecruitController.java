package com.school.cbis.web.recruit;

import com.school.cbis.commons.Wordbook;
import com.school.cbis.data.AjaxData;
import com.school.cbis.data.PaginationData;
import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Recruit;
import com.school.cbis.domain.tables.records.RecruitRecord;
import com.school.cbis.domain.tables.records.TieRecord;
import com.school.cbis.service.MajorService;
import com.school.cbis.service.RecruitService;
import com.school.cbis.service.UsersService;
import com.school.cbis.vo.recruit.RecruitListVo;
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
 * Created by lenovo on 2016-05-26.
 */
@Controller
public class RecruitController {

    private final Logger log = LoggerFactory.getLogger(RecruitController.class);

    @Resource
    private UsersService usersService;

    @Resource
    private RecruitService recruitService;

    @Resource
    private MajorService majorService;

    @Resource
    private Wordbook wordbook;

    /**
     * 招聘管理
     * @param recruitListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/recruit/recruitManager")
    public String recruitManager(RecruitListVo recruitListVo, ModelMap modelMap){
        modelMap.addAttribute("recruitListVo",recruitListVo);
        return "/maintainer/recruit/recruitlist";
    }

    /**
     * 招聘管理数据
     * @param recruitListVo
     * @return
     */
    @RequestMapping("/maintainer/recruit/recruitManagerData")
    @ResponseBody
    public AjaxData<RecruitListVo> recruitManagerData(RecruitListVo recruitListVo){
        AjaxData<RecruitListVo> ajaxData = new AjaxData<>();
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }

        List<RecruitListVo> recruitListVoList = new ArrayList<>();
        if(tieId>0){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String>> recruitRecords = recruitService.findByTieIdAndPage(recruitListVo,tieId);
            if(recruitRecords.isNotEmpty()){
                recruitListVoList = recruitRecords.into(RecruitListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(recruitListVo.getPageNum());
                paginationData.setPageSize(recruitListVo.getPageSize());
                paginationData.setTotalDatas(recruitService.findByTieIdAndPageCount(recruitListVo,tieId));
                ajaxData.success().listData(recruitListVoList).paginationData(paginationData);
            } else {
                ajaxData.fail().listData(recruitListVoList);
            }
        } else {
            ajaxData.fail().listData(recruitListVoList);
        }
        return ajaxData;
    }

    /**
     * 删除招聘
     * @param recruitListVo
     * @return
     */
    @RequestMapping("/maintainer/recruit/deleteRecruit")
    @ResponseBody
    public AjaxData deleteRecruit(RecruitListVo recruitListVo){
        //目前是没有简历投递，则完全删除
        recruitService.deleteById(recruitListVo.getId());
        return new AjaxData().success().msg("删除招聘成功!");
    }

    /**
     * 更新招聘
     * @param recruitListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/recruit/recruitUpdate")
    public String  recruitUpdate(RecruitListVo recruitListVo,ModelMap modelMap){
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        Recruit recruit = recruitService.findById(recruitListVo.getId());
        if(!ObjectUtils.isEmpty(recruit)){
            recruitListVo.setId(recruit.getId());
            recruitListVo.setRecruitTitle(recruit.getRecruitTitle());
            log.debug(" time : {} ",recruit.getRecruitTime().toString());
            String date = recruit.getRecruitTime().toString().split("[ ]")[0];
            String time = recruit.getRecruitTime().toString().split("[ ]")[1].split("[.]")[0];
            recruitListVo.setRecruitDate(date);
            recruitListVo.setRecruitTime(time);
            recruitListVo.setRecruitAddress(recruit.getRecruitAddress());
            recruitListVo.setFitMajor(recruit.getFitMajor());
            recruitListVo.setRecruitContent(recruit.getRecruitContent());
            recruitListVo.setTextLink(recruit.getTextLink());
            modelMap.addAttribute("recruit",recruitListVo);
        } else {
            modelMap.addAttribute("recruit",new Recruit());
        }
        modelMap.addAttribute("majors",majorService.findAllByTieId(tieId));
        return "/maintainer/recruit/recruitupdate";
    }

    /**
     * 招聘添加
     * @param modelMap
     * @return
     */
    @RequestMapping("/maintainer/recruit/recruitAdd")
    public String recruitAdd(ModelMap modelMap){
        Record record = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if(!ObjectUtils.isEmpty(record)){
            tieId = record.getValue(Tables.TIE.ID);
        }
        modelMap.addAttribute("majors",majorService.findAllByTieId(tieId));
        return "/maintainer/recruit/recruitadd";
    }

    /**
     * 添加招聘数据
     * @param recruitListVo
     * @return
     */
    @RequestMapping("/maintainer/recruit/addRecruit")
    public String addRecruit(RecruitListVo recruitListVo){
        log.debug(" recruitListVo : {} ",recruitListVo);
        try {
            Record record = usersService.findAll(usersService.getUserName());
            int tieId = 0;
            if(!ObjectUtils.isEmpty(record)){
                tieId = record.getValue(Tables.TIE.ID);
            }
            if(tieId>0){
                Recruit recruit = new Recruit();
                recruit.setTieId(tieId);
                recruit.setFitMajor(recruitListVo.getFitMajor());
                recruit.setRecruitAddress(recruitListVo.getRecruitAddress());
                recruit.setRecruitContent(recruitListVo.getRecruitContent());
                recruit.setRecruitTitle(recruitListVo.getRecruitTitle());
                recruit.setTextLink(recruitListVo.getTextLink());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date date = sdf.parse(recruitListVo.getRecruitDate() + " " + recruitListVo.getRecruitTime());
                Timestamp timestamp = new Timestamp(date.getTime());
                recruit.setRecruitTime(timestamp);
                recruit.setUsername(usersService.getUserName());
                recruit.setCreateTime(new Timestamp(System.currentTimeMillis()));
                recruitService.save(recruit);
            }
        } catch (ParseException e) {
            log.error("recruit format time exception is {} ",e.getMessage());
        }

        return "redirect:/maintainer/recruit/recruitManager";
    }

    /**
     * 更新招聘
     * @param recruitListVo
     * @return
     */
    @RequestMapping("/maintainer/recruit/updateRecruit")
    public String updateRecruit(RecruitListVo recruitListVo){
        try {
                Recruit recruit = recruitService.findById(recruitListVo.getId());
                recruit.setFitMajor(recruitListVo.getFitMajor());
                recruit.setRecruitAddress(recruitListVo.getRecruitAddress());
                recruit.setRecruitContent(recruitListVo.getRecruitContent());
                recruit.setRecruitTitle(recruitListVo.getRecruitTitle());
                recruit.setTextLink(recruitListVo.getTextLink());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date date = sdf.parse(recruitListVo.getRecruitDate() + " " + recruitListVo.getRecruitTime());
                Timestamp timestamp = new Timestamp(date.getTime());
                recruit.setRecruitTime(timestamp);
                recruitService.update(recruit);
        } catch (ParseException e) {
            log.error("recruit format time exception is {} ",e.getMessage());
        }
        return "redirect:/maintainer/recruit/recruitManager";
    }

    /**
     * 招聘展示
     * @param recruitListVo
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/recruit/recruitShow")
    public String recruitShow(RecruitListVo recruitListVo,ModelMap modelMap){
        modelMap.addAttribute("recruitListVo",recruitListVo);
        TieRecord tieRecord = wordbook.getTieInfo();
        //显示最新4条数据
        RecruitListVo recruitListVo1 = new RecruitListVo();
        recruitListVo1.setPageNum(0);
        recruitListVo1.setPageSize(4);
        List<RecruitListVo> recruitListVoList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(tieRecord)){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String>>
                    recruitRecords = recruitService.findByTieIdAndPage(recruitListVo1,tieRecord.getId());
            if(recruitRecords.isNotEmpty()){
                recruitListVoList = recruitRecords.into(RecruitListVo.class);
            }
        }
        modelMap.addAttribute("recruitListVoList",recruitListVoList);
        return "/user/recruit/recruitshow";
    }

    /**
     * 招聘展示数据
     * @param recruitListVo
     * @return
     */
    @RequestMapping("/user/recruit/recruitShowData")
    @ResponseBody
    public AjaxData<RecruitListVo> recruitShowData(RecruitListVo recruitListVo){
        AjaxData<RecruitListVo> ajaxData = new AjaxData<>();
        TieRecord tieRecord = wordbook.getTieInfo();
        List<RecruitListVo> recruitListVoList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(tieRecord)){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String>>
                    recruitRecords = recruitService.findByTieIdAndPage(recruitListVo,tieRecord.getId());
            if(recruitRecords.isNotEmpty()){
                recruitListVoList = recruitRecords.into(RecruitListVo.class);
                PaginationData paginationData = new PaginationData();
                paginationData.setPageNum(recruitListVo.getPageNum());
                paginationData.setPageSize(recruitListVo.getPageSize());
                paginationData.setTotalDatas(recruitService.findByTieIdAndPageCount(recruitListVo,tieRecord.getId()));
                ajaxData.success().listData(recruitListVoList).paginationData(paginationData);
            } else {
                ajaxData.fail().listData(recruitListVoList);
            }
        } else {
            ajaxData.fail().listData(recruitListVoList);
        }
        return ajaxData;
    }

    /**
     * 招聘展示详细
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/recruit/recruitShowDetailed")
    public String recruitShowDetailed(@RequestParam("id") int id, ModelMap modelMap){
        TieRecord tieRecord = wordbook.getTieInfo();
        //显示最新4条数据
        RecruitListVo recruitListVo1 = new RecruitListVo();
        recruitListVo1.setPageNum(0);
        recruitListVo1.setPageSize(4);
        List<RecruitListVo> recruitListVoList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(tieRecord)){
            Result<Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String>>
                    recruitRecords = recruitService.findByTieIdAndPage(recruitListVo1,tieRecord.getId());
            if(recruitRecords.isNotEmpty()){
                recruitListVoList = recruitRecords.into(RecruitListVo.class);
            }
        }
        modelMap.addAttribute("recruitListVoList",recruitListVoList);

        RecruitListVo recruitListVo = new RecruitListVo();
        Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String> record11 =
                recruitService.findByIdWithUser(id);
        if(!ObjectUtils.isEmpty(record11)){
            recruitListVo = record11.into(RecruitListVo.class);
        }
        modelMap.addAttribute("recruitListVo",recruitListVo);
        return "/user/recruit/recruitshowdetailed";
    }
}
