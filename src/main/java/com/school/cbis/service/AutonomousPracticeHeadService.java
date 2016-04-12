package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.AutonomousPracticeHead;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public interface AutonomousPracticeHeadService {

    /**
     * 保存标题
     * @param autonomousPracticeHead
     */
    void save(AutonomousPracticeHead autonomousPracticeHead);

    /**
     * 通过自主实习信息表主键查询
     * @param autonomousPracticeInfoId
     * @return
     */
    List<AutonomousPracticeHead> findByAutonomousPracticeInfoId(int autonomousPracticeInfoId);

    /**
     * 通过自主实习信息表主键删除
     * @param autonomousPracticeInfoId
     */
    void deleteByAutonomousPracticeInfoId(int autonomousPracticeInfoId);

}
