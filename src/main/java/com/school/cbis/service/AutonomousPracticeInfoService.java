package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.AutonomousPracticeInfo;
import com.school.cbis.vo.autonomicpractice.AutonomicPracticeListVo;
import com.school.cbis.vo.autonomicpractice.ReportSettingVo;
import org.jooq.Record10;
import org.jooq.Record7;
import org.jooq.Record9;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/4/5.
 */
public interface AutonomousPracticeInfoService {

    /**
     * 通过系id查询全部
     * @param reportSettingVo
     * @param tieId
     * @return
     */
    Result<Record7<Integer,String,Timestamp,String,Timestamp,Timestamp,String>> findByTieIdAndPage(ReportSettingVo reportSettingVo,int tieId);

    /**
     * 通过系id查询全部总数
     * @param reportSettingVo
     * @param tieId
     * @return
     */
    int findByTieIdAndCount(ReportSettingVo reportSettingVo,int tieId);

    /**
     * 保存自主实习信息
     * @param autonomousPracticeInfo
     */
    int save(AutonomousPracticeInfo autonomousPracticeInfo);

    /**
     * 通过主键删除
     * @param id
     */
    void deleteById(int id);

    /**
     * 自主实习列表所需数据
     * @param tieId
     * @return
     */
    Result<Record10<Integer, String, Timestamp, String, String, Timestamp, Timestamp, String, Integer,Integer>> findByTieIdAndPage(int tieId, int pageNum, int pageSize);

    /**
     * 自主实习列表所需数据总数
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(int tieId);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    AutonomousPracticeInfo findById(int id);

    /**
     * 更新
     * @param autonomousPracticeInfo
     */
    void update(AutonomousPracticeInfo autonomousPracticeInfo);

    /**
     * 根据模板id删除
     * @param autonomousPracticeTemplateId
     */
    void deleteByAutonomousPracticeTemplateId(int autonomousPracticeTemplateId);
}
