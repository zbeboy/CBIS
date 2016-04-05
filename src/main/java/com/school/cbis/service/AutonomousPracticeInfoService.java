package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.AutonomousPracticeInfo;

/**
 * Created by Administrator on 2016/4/5.
 */
public interface AutonomousPracticeInfoService {

    /**
     * 保存自主实习信息
     * @param autonomousPracticeInfo
     */
    int save(AutonomousPracticeInfo autonomousPracticeInfo);
}
