package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.records.TieElegantRecord;
import com.school.cbis.domain.tables.records.TieElegantTimeRecord;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-01-24.
 */
@Service("tieElegantService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TieElegantServiceImpl implements TieElegantService {

    private final DSLContext create;

    @Autowired
    public TieElegantServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public Result<TieElegantTimeRecord> findByTime(String time) {
        Result<TieElegantTimeRecord> records = create.selectFrom(Tables.TIE_ELEGANT_TIME).where(Tables.TIE_ELEGANT_TIME.TIME.eq(time)).fetch();
        return records;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int saveTime(TieElegantTimeRecord tieElegantTimeRecord) {
        TieElegantTimeRecord record = create.insertInto(Tables.TIE_ELEGANT_TIME)
                .set(Tables.TIE_ELEGANT_TIME.TIME, tieElegantTimeRecord.getTime())
                .returning(Tables.TIE_ELEGANT_TIME.ID)
                .fetchOne();
        return record.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public boolean saveTieElegant(TieElegantRecord record) {
        int count = create.insertInto(Tables.TIE_ELEGANT)
                .set(Tables.TIE_ELEGANT.TIE_ID, record.getTieId())
                .set(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID, record.getTieElegantArticleInfoId())
                .set(Tables.TIE_ELEGANT.TIE_ELEGANT_TIME_ID, record.getTieElegantTimeId())
                .execute();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Result<Record3<Integer, String, String>> searchItems(String big_title, int tie_id) {
        Result<Record3<Integer, String, String>> result = null;
        if (!StringUtils.isEmpty(big_title) && StringUtils.hasLength(StringUtils.trimWhitespace(big_title))) {
            result = create.select(Tables.TIE_ELEGANT.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.ARTICLE_INFO.ARTICLE_CONTENT)
                    .from(Tables.TIE_ELEGANT)
                    .join(Tables.ARTICLE_INFO)
                    .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                    .where(Tables.TIE_ELEGANT.TIE_ID.equal(tie_id).and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + big_title + "%")))
                    .fetch();
        } else {
            result = create.select(Tables.TIE_ELEGANT.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.ARTICLE_INFO.ARTICLE_CONTENT)
                    .from(Tables.TIE_ELEGANT)
                    .join(Tables.ARTICLE_INFO)
                    .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                    .where(Tables.TIE_ELEGANT.TIE_ID.equal(tie_id))
                    .fetch();
        }
        return result;
    }

    @Override
    public Result<Record4<Integer, String, String, Timestamp>> getTieElegantInfoByPage(String bigTitle, int pageNum, int pageSize, int tie_id) {
        Result<Record4<Integer, String, String, Timestamp>> records = null;
        if ((StringUtils.isEmpty(bigTitle) || !StringUtils.hasLength(StringUtils.trimWhitespace(bigTitle)))) {
            records = create.select(Tables.ARTICLE_INFO.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.ARTICLE_INFO.DATE)
                    .from(Tables.TIE_ELEGANT)
                    .join(Tables.ARTICLE_INFO)
                    .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                    .join(Tables.USERS)
                    .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.equal(Tables.USERS.USERNAME))
                    .where(Tables.TIE_ELEGANT.TIE_ID.equal(tie_id))
                    .orderBy(Tables.TIE_ELEGANT.ID.desc())
                    .limit(pageNum, pageSize)
                    .fetch();
        } else if ((!StringUtils.isEmpty(bigTitle) || StringUtils.hasLength(StringUtils.trimWhitespace(bigTitle)))) {
            records = create.select(Tables.ARTICLE_INFO.ID, Tables.ARTICLE_INFO.BIG_TITLE, Tables.USERS.USERNAME, Tables.ARTICLE_INFO.DATE)
                    .from(Tables.TIE_ELEGANT)
                    .join(Tables.ARTICLE_INFO)
                    .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                    .join(Tables.USERS)
                    .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.equal(Tables.USERS.USERNAME))
                    .where(Tables.TIE_ELEGANT.TIE_ID.equal(tie_id).and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + bigTitle + "%")))
                    .orderBy(Tables.TIE_ELEGANT.ID.desc())
                    .fetch();
        }

        return records;
    }

    @Override
    public int tieElegantInfoCount(String bigTitle, int tie_id) {
        int count = 0;
        if (StringUtils.isEmpty(bigTitle) || !StringUtils.hasLength(StringUtils.trimWhitespace(bigTitle))) {
            count = create.select()
                    .from(Tables.TIE_ELEGANT)
                    .join(Tables.ARTICLE_INFO)
                    .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                    .join(Tables.USERS)
                    .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.equal(Tables.USERS.USERNAME))
                    .where(Tables.TIE_ELEGANT.TIE_ID.equal(tie_id))
                    .execute();
        } else {
            count = create.select()
                    .from(Tables.TIE_ELEGANT)
                    .join(Tables.ARTICLE_INFO)
                    .on(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.equal(Tables.ARTICLE_INFO.ID))
                    .join(Tables.USERS)
                    .on(Tables.ARTICLE_INFO.ARTICLE_WRITER.equal(Tables.USERS.USERNAME))
                    .where(Tables.TIE_ELEGANT.TIE_ID.equal(tie_id).and(Tables.ARTICLE_INFO.BIG_TITLE.like("%" + bigTitle + "%")))
                    .execute();
        }
        return count;
    }

    @Override
    public boolean deleteTieElegant(int id) {
        int count = create.deleteFrom(Tables.TIE_ELEGANT).where(Tables.TIE_ELEGANT.TIE_ELEGANT_ARTICLE_INFO_ID.eq(id)).execute();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTieElegantImg(int id) {
        int count = create.update(Tables.ARTICLE_INFO)
                .set(Tables.ARTICLE_INFO.ARTICLE_PHOTO_URL, "")
                .where(Tables.ARTICLE_INFO.ID.eq(id)).execute();
        if (count > 0) {
            return true;
        }
        return false;
    }
}
