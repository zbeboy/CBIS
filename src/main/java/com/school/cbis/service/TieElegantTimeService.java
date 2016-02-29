package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TieElegantTime;

import java.util.List;

/**
 * Created by lenovo on 2016-02-21.
 */
public interface TieElegantTimeService {
    /**
     * 从系风采时间表中查询是否存在该时间
     *
     * @param time xxxx年xx月
     * @return
     */
    List<TieElegantTime> findByTime(String time);

    /**
     * 若时间组表中不存在该时间则插入该时间并获取id
     *
     * @param tieElegantTime
     * @return id
     */
    int save(TieElegantTime tieElegantTime);
}
