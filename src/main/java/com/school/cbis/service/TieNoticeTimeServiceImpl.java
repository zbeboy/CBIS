package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TieNoticeTimeDao;
import com.school.cbis.domain.tables.pojos.TieElegantTime;
import com.school.cbis.domain.tables.pojos.TieNoticeTime;
import com.school.cbis.domain.tables.records.TieElegantTimeRecord;
import com.school.cbis.domain.tables.records.TieNoticeTimeRecord;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lenovo on 2016-03-09.
 */
@Service("tieNoticeTimeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TieNoticeTimeServiceImpl implements TieNoticeTimeService {
    private final DSLContext create;

    private TieNoticeTimeDao tieNoticeTimeDao;

    @Autowired
    public TieNoticeTimeServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.tieNoticeTimeDao = new TieNoticeTimeDao(configuration);
    }
    @Override
    public List<TieNoticeTime> findByTime(String time) {
        List<TieNoticeTime> tieNoticeTimes = tieNoticeTimeDao.fetchByTime(time);
        return tieNoticeTimes;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int save(TieNoticeTime tieNoticeTime) {
        TieNoticeTimeRecord record = create.insertInto(Tables.TIE_NOTICE_TIME)
                .set(Tables.TIE_NOTICE_TIME.TIME, tieNoticeTime.getTime())
                .returning(Tables.TIE_NOTICE_TIME.ID)
                .fetchOne();
        return record.getId();
    }

    @Override
    public TieNoticeTime findById(int id) {
        TieNoticeTime tieNoticeTime = tieNoticeTimeDao.findById(id);
        return tieNoticeTime;
    }

    @Override
    public Result<Record2<Integer, String>> findByBigTitleAndTieIdAndTimeDistinctId(String bigTitle, int tieId) {
        Condition a = Tables.TIE_NOTICE.TIE_ID.eq(tieId);

        if (StringUtils.hasLength(bigTitle)) {
            a = a.and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + bigTitle + "%"));
        }

        Result<Record2<Integer, String>> record2s = create.selectDistinct(Tables.TIE_NOTICE.TIE_NOTICE_TIME_ID.as("id"),
                Tables.TIE_NOTICE_TIME.TIME)
                .from(Tables.TIE_NOTICE)
                .join(Tables.ARTICLE_INFO)
                .on(Tables.TIE_NOTICE.TIE_NOTICE_ARTICLE_INFO_ID.eq(Tables.ARTICLE_INFO.ID))
                .join(Tables.TIE_NOTICE_TIME)
                .on(Tables.TIE_NOTICE.TIE_NOTICE_TIME_ID.eq(Tables.TIE_NOTICE_TIME.ID))
                .where(a)
                .orderBy(Tables.ARTICLE_INFO.DATE.desc())
                .fetch();
        return record2s;
    }
}
