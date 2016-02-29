package com.school.cbis.web.major;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.pojos.Major;
import com.school.cbis.service.MajorService;
import com.school.cbis.service.UsersService;
import com.school.cbis.vo.major.*;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
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
 * Created by lenovo on 2016-02-07.
 */
@Controller
public class MajorManagerController {

    @Resource
    private UsersService usersService;

    @Resource
    private MajorService majorService;

    @RequestMapping("/maintainer/majordata")
    @ResponseBody
    public Map<String, Object> majorDatas(MajorVo majorVo) {
        Map<String, Object> map = new HashMap<>();
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorVo> majorVos = new ArrayList<>();
        if (tieId > 0) {
            Result<Record2<Integer, String>> record2s = majorService.findByTieIdByPage(majorVo, tieId);
            if (record2s.isNotEmpty()) {
                majorVos = record2s.into(MajorVo.class);
                map.put("data", majorVos);
                map.put("itemsCount", majorService.findByTieIdCount(majorVo, tieId));
            } else {
                map.put("data", majorVos);
                map.put("itemsCount", 0);
            }
        } else {
            map.put("data", majorVos);
            map.put("itemsCount", 0);
        }
        return map;
    }

    @RequestMapping(value = "/maintainer/savemajor", method = RequestMethod.POST)
    @ResponseBody
    public MajorVo saveMajor(MajorVo majorVo) {
        //通过用户类型获取系表ID
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        if (tieId > 0) {
            if (StringUtils.hasLength(majorVo.getMajorName())) {
                List<Major> majors = majorService.findByMajorName(majorVo.getMajorName());
                if (majors.isEmpty()) {
                    Major major = new Major();
                    major.setMajorName(majorVo.getMajorName());
                    major.setTieId(tieId);
                    majorService.saveMajor(major);
                    return majorVo;
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/maintainer/updatemajor", method = RequestMethod.POST)
    @ResponseBody
    public MajorVo updateMajor(MajorVo majorVo) {
        Map<String, Object> map = new HashMap<>();
        Major major = majorService.findById(majorVo.getId());
        if (StringUtils.hasLength(majorVo.getMajorName())) {
            List<Major> majors = majorService.findByMajorName(majorVo.getMajorName());
            if (majors.isEmpty()) {
                major.setMajorName(majorVo.getMajorName());
                majorService.update(major);
                return majorVo;
            }
        }
        return null;
    }

    @RequestMapping(value = "/maintainer/deletemajor", method = RequestMethod.POST)
    @ResponseBody
    public MajorVo deleteMajor(MajorVo majorVo) {
        majorService.deleteById(majorVo.getId());
        return majorVo;
    }

    @RequestMapping("/maintainer/majorheaddata")
    @ResponseBody
    public Map<String, Object> majorHeadDatas(MajorHeadVo majorHeadVo) {
        Map<String, Object> map = new HashMap<>();
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorHeadVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record5<Integer, String, String, String, Timestamp>> record5s = majorService.findAllWithHeadByPage(majorHeadVo,tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(MajorHeadVo.class);
                map.put("data", list);
                map.put("itemsCount", majorService.findAllWithHeadByPageCount(majorHeadVo,tieId));
            } else {
                map.put("data", list);
                map.put("itemsCount", 0);
            }
        } else {
            map.put("data", list);
            map.put("itemsCount", 0);
        }
        return map;
    }

    @RequestMapping("/maintainer/majorintroducedata")
    @ResponseBody
    public Map<String, Object> majorIntroduceDatas(MajorIntroduceVo majorIntroduceVo) {
        Map<String, Object> map = new HashMap<>();
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorIntroduceVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record5<Integer, String, String, String, Timestamp>> record5s = majorService.findAllWithIntroduceByPage(majorIntroduceVo, tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(MajorIntroduceVo.class);
                map.put("data", list);
                map.put("itemsCount", majorService.findAllWithIntroduceByPageCount(majorIntroduceVo, tieId));
            } else {
                map.put("data", list);
                map.put("itemsCount", 0);
            }
        } else {
            map.put("data", list);
            map.put("itemsCount", 0);
        }
        return map;
    }

    @RequestMapping("/maintainer/majortraininggoaldata")
    @ResponseBody
    public Map<String, Object> majorTrainingGoalDatas(MajorTrainingGoalVo majorTrainingGoalVo) {
        Map<String, Object> map = new HashMap<>();
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorTrainingGoalVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record5<Integer, String, String, String, Timestamp>> record5s = majorService.findAllWithTrainingGoalByPage(majorTrainingGoalVo,tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(MajorTrainingGoalVo.class);
                map.put("data", list);
                map.put("itemsCount", majorService.findAllWithTrainingGoalByPageCount(majorTrainingGoalVo,tieId));
            } else {
                map.put("data", list);
                map.put("itemsCount", 0);
            }
        } else {
            map.put("data", list);
            map.put("itemsCount", 0);
        }
        return map;
    }

    @RequestMapping("/maintainer/majortraitdata")
    @ResponseBody
    public Map<String, Object> majorTraitDatas(MajorTraitVo majorTraitVo) {
        Map<String, Object> map = new HashMap<>();
        Result<Record> records = usersService.findAll(usersService.getUserName());
        int tieId = 0;
        if (records.isNotEmpty()) {
            for (Record r : records) {
                tieId = r.getValue(Tables.TIE.ID);
            }
        }
        List<MajorTraitVo> list = new ArrayList<>();

        if (tieId > 0) {
            Result<Record5<Integer, String, String, String, Timestamp>> record5s = majorService.findAllWithTraitByPage(majorTraitVo,tieId);
            if (record5s.isNotEmpty()) {
                list = record5s.into(MajorTraitVo.class);
                map.put("data", list);
                map.put("itemsCount", majorService.findAllWithTraitByPageCount(majorTraitVo,tieId));
            } else {
                map.put("data", list);
                map.put("itemsCount", 0);
            }
        } else {
            map.put("data", list);
            map.put("itemsCount", 0);
        }
        return map;
    }

}
