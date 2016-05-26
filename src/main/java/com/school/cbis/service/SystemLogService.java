package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.SystemLog;
import com.school.cbis.domain.tables.records.SystemLogRecord;
import com.school.cbis.vo.system.SystemLogListVo;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-05-26.
 */
public interface SystemLogService {

    /**
     * 通过系id查询全部日志
     * @param systemLogListVo
     * @param tieId
     * @return
     */
    Result<SystemLogRecord> findByTieIdAndPage(SystemLogListVo systemLogListVo, int tieId);

    /**
     * 通过系id查询全部日志总数
     * @param systemLogListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(SystemLogListVo systemLogListVo,int tieId);

    /**
     * 保存日志
     * @param systemLog
     */
    void save(SystemLog systemLog);
}
