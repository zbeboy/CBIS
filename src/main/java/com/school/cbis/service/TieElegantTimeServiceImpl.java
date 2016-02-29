package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TieElegantDao;
import com.school.cbis.domain.tables.daos.TieElegantTimeDao;
import com.school.cbis.domain.tables.pojos.TieElegantTime;
import com.school.cbis.domain.tables.records.TieElegantTimeRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2016-02-21.
 */
@Service("tieElegantTimeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TieElegantTimeServiceImpl implements TieElegantTimeService {

    private final DSLContext create;

    private TieElegantTimeDao tieElegantTimeDao;

    @Autowired
    public TieElegantTimeServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.tieElegantTimeDao = new TieElegantTimeDao(configuration);
    }
    @Override
    public List<TieElegantTime> findByTime(String time) {
        List<TieElegantTime> tieElegantTimes = tieElegantTimeDao.fetchByTime(time);
        return tieElegantTimes;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int save(TieElegantTime tieElegantTime) {
        TieElegantTimeRecord record = create.insertInto(Tables.TIE_ELEGANT_TIME)
                .set(Tables.TIE_ELEGANT_TIME.TIME, tieElegantTime.getTime())
                .returning(Tables.TIE_ELEGANT_TIME.ID)
                .fetchOne();
        return record.getId();
    }
}
