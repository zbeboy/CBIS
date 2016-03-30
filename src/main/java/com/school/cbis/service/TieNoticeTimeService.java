package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TieNoticeTime;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-03-09.
 */
public interface TieNoticeTimeService {
    /**
     * 从系公告时间表中查询是否存在该时间
     *
     * @param time xxxx年xx月
     * @return
     */
    List<TieNoticeTime> findByTime(String time);

    /**
     * 若时间组表中不存在该时间则插入该时间并获取id
     *
     * @param tieNoticeTime
     * @return id
     */
    int save(TieNoticeTime tieNoticeTime);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    TieNoticeTime findById(int id);

    /**
     * 系公告分组查询
     *
     * @param bigTitle
     * @param tieId
     * @return
     */
    Result<Record2<Integer, String>> findByBigTitleAndTieIdAndTimeDistinctId(String bigTitle, int tieId);
}
