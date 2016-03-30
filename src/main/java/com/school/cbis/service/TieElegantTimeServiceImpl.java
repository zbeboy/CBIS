package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TieElegantTimeDao;
import com.school.cbis.domain.tables.pojos.TieElegantTime;
import com.school.cbis.domain.tables.records.TieElegantTimeRecord;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lenovo on 2016-02-21.
 */
@Service("tieElegantTimeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TieElegantTimeServiceImpl implements TieElegantTimeService {

    private final Logger log = LoggerFactory.getLogger(TieElegantTimeServiceImpl.class);

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

    @Override
    public TieElegantTime findById(int id) {
        TieElegantTime tieElegantTime = tieElegantTimeDao.findById(id);
        return tieElegantTime;
    }

    @Override
    public Result<Record2<Integer, String>> findByBigTitleAndTieIdAndTimeDistinctId( String bigTitle, int tieId) {

        Condition a = Tables.TIE_ELEGANT.TIE_ID.eq(tieId);

        if (StringUtils.hasLength(bigTitle)) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + bigTitle + "%"));
        }

        Result<Record2<Integer, String>> record2s = create.selectDistinct(Tables.TIE_ELEGANT.TIE_ELEGANT_TIME_ID.as("id"),
                Tables.TIE_ELEGANT_TIME.TIME)
                .from(Tables.TIE_ELEGANT)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .join(Tables.TIE_ELEGANT_TIME)
                .on(Tables.TIE_ELEGANT.TIE_ELEGANT_TIME_ID.eq(Tables.TIE_ELEGANT_TIME.ID))
                .where(a)
                .orderBy(Tables.ARTICLE_INFO.DATE.desc())
                .fetch();
        return record2s;
    }

}
