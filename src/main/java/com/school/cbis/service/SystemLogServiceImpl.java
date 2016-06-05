package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.SystemLogDao;
import com.school.cbis.domain.tables.daos.TeacherDao;
import com.school.cbis.domain.tables.pojos.SystemLog;
import com.school.cbis.domain.tables.records.SystemLogRecord;
import com.school.cbis.vo.system.SystemLogListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016-05-26.
 */
@Service("systemLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SystemLogServiceImpl implements SystemLogService {

    private final Logger log = LoggerFactory.getLogger(SystemLogServiceImpl.class);

    private final DSLContext create;

    private SystemLogDao systemLogDao;

    @Autowired
    public SystemLogServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.systemLogDao = new SystemLogDao(configuration);
    }

    @Override
    public Result<SystemLogRecord> findByTieIdAndPage(SystemLogListVo systemLogListVo, int tieId) {
        Condition a = Tables.SYSTEM_LOG.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(systemLogListVo.getUsername())) {
            a = a.and(Tables.SYSTEM_LOG.USERNAME.like("%" + systemLogListVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(systemLogListVo.getOperationBehavior())) {
            a = a.and(Tables.SYSTEM_LOG.OPERATION_BEHAVIOR.like("%" + systemLogListVo.getOperationBehavior() + "%"));
        }

        if (StringUtils.hasLength(systemLogListVo.getStartTime())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = sdf.parse(systemLogListVo.getStartTime() + " 00:00:00");
                Timestamp timestamp = new Timestamp(date.getTime());
                a = a.and(Tables.SYSTEM_LOG.CREATE_TIME.ge(timestamp));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.hasLength(systemLogListVo.getEndTime())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = sdf.parse(systemLogListVo.getEndTime() + " 23:59:00");
                Timestamp timestamp = new Timestamp(date.getTime());
                a = a.and(Tables.SYSTEM_LOG.CREATE_TIME.le(timestamp));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int pageNum = systemLogListVo.getPageNum();
        int pageSize = systemLogListVo.getPageSize();
        if (pageNum <= 0) {
            pageNum = 1;
        }

        Result<SystemLogRecord> systemLogRecords = create.selectFrom(Tables.SYSTEM_LOG)
                .where(a)
                .orderBy(Tables.SYSTEM_LOG.CREATE_TIME.desc())
                .limit((pageNum - 1) * pageSize, pageSize)
                .fetch();
        return systemLogRecords;
    }

    @Override
    public int findByTieIdAndPageCount(SystemLogListVo systemLogListVo, int tieId) {
        Condition a = Tables.SYSTEM_LOG.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(systemLogListVo.getUsername())) {
            a = a.and(Tables.SYSTEM_LOG.USERNAME.like("%" + systemLogListVo.getUsername() + "%"));
        }

        if (StringUtils.hasLength(systemLogListVo.getOperationBehavior())) {
            a = a.and(Tables.SYSTEM_LOG.OPERATION_BEHAVIOR.like("%" + systemLogListVo.getOperationBehavior() + "%"));
        }

        if (StringUtils.hasLength(systemLogListVo.getStartTime())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = sdf.parse(systemLogListVo.getStartTime() + " 00:00:00");
                Timestamp timestamp = new Timestamp(date.getTime());
                a = a.and(Tables.SYSTEM_LOG.CREATE_TIME.ge(timestamp));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.hasLength(systemLogListVo.getEndTime())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = sdf.parse(systemLogListVo.getEndTime() + " 23:59:00");
                Timestamp timestamp = new Timestamp(date.getTime());
                a = a.and(Tables.SYSTEM_LOG.CREATE_TIME.le(timestamp));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Record1<Integer> record1 = create.selectCount()
                .from(Tables.SYSTEM_LOG)
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(SystemLog systemLog) {
        create.insertInto(Tables.SYSTEM_LOG)
                .set(Tables.SYSTEM_LOG.USERNAME, systemLog.getUsername())
                .set(Tables.SYSTEM_LOG.OPERATION_BEHAVIOR, systemLog.getOperationBehavior())
                .set(Tables.SYSTEM_LOG.TIE_ID, systemLog.getTieId())
                .execute();
    }
}
