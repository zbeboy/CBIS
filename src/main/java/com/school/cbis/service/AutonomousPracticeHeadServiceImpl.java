package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.AutonomousPracticeHeadDao;
import com.school.cbis.domain.tables.daos.AutonomousPracticeInfoDao;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeHead;
import com.school.cbis.domain.tables.records.AutonomousPracticeHeadRecord;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
@Service("autonomousPracticeHeadService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AutonomousPracticeHeadServiceImpl implements AutonomousPracticeHeadService {

    private final Logger log = LoggerFactory.getLogger(AutonomousPracticeHeadServiceImpl.class);

    private final DSLContext create;

    private AutonomousPracticeHeadDao autonomousPracticeHeadDao;

    @Autowired
    public AutonomousPracticeHeadServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.autonomousPracticeHeadDao = new AutonomousPracticeHeadDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int save(AutonomousPracticeHead autonomousPracticeHead) {
        AutonomousPracticeHeadRecord autonomousPracticeHeadRecord = create.insertInto(Tables.AUTONOMOUS_PRACTICE_HEAD)
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.TITLE,autonomousPracticeHead.getTitle())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.TITLE_VARIABLE,autonomousPracticeHead.getTitleVariable())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.HEAD_TYPE_ID,autonomousPracticeHead.getHeadTypeId())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.DATABASE_TABLE,autonomousPracticeHead.getDatabaseTable())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.DATABASE_TABLE_FIELD,autonomousPracticeHead.getDatabaseTableField())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.AUTHORITY,autonomousPracticeHead.getAuthority())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.IS_SHOW_HIGHLY_ACTIVE,autonomousPracticeHead.getIsShowHighlyActive())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_TEMPLATE_ID,autonomousPracticeHead.getAutonomousPracticeTemplateId())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.CONTENT,autonomousPracticeHead.getContent())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.IS_DATABASE,autonomousPracticeHead.getIsDatabase())
                .set(Tables.AUTONOMOUS_PRACTICE_HEAD.SORT,autonomousPracticeHead.getSort())
                .returning(Tables.AUTONOMOUS_PRACTICE_HEAD.ID)
                .fetchOne();
        return autonomousPracticeHeadRecord.getId();
    }

    @Override
    public List<AutonomousPracticeHead> findByAutonomousPracticeTemplateId(int autonomousPracticeTemplateId) {
        List<AutonomousPracticeHead> autonomousPracticeHeads = autonomousPracticeHeadDao.fetchByAutonomousPracticeTemplateId(autonomousPracticeTemplateId);
        return autonomousPracticeHeads;
    }

    @Override
    public void deleteByAutonomousPracticeTemplateId(int autonomousPracticeTemplateId) {
        create.deleteFrom(Tables.AUTONOMOUS_PRACTICE_HEAD).where(Tables.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(autonomousPracticeTemplateId)).execute();
    }

    @Override
    public AutonomousPracticeHead findById(int id) {
        AutonomousPracticeHead autonomousPracticeHead = autonomousPracticeHeadDao.findById(id);
        return autonomousPracticeHead;
    }

    @Override
    public void update(AutonomousPracticeHead autonomousPracticeHead) {
        autonomousPracticeHeadDao.update(autonomousPracticeHead);
    }

    @Override
    public Result<Record12<Integer, String, String, String,String, String, String, String, Byte, String, Byte, Integer>> findByAutonomousPracticeTemplateIdWithHeadTypeId(int autonomousPracticeTemplateId) {
        Result<Record12<Integer, String, String,String, String, String, String, String, Byte, String, Byte, Integer>> record12s = create.select(Tables.AUTONOMOUS_PRACTICE_HEAD.ID,Tables.AUTONOMOUS_PRACTICE_HEAD.TITLE,
                Tables.AUTONOMOUS_PRACTICE_HEAD.TITLE_VARIABLE,Tables.HEAD_TYPE.TYPE_VALUE,Tables.HEAD_TYPE.TYPE_NAME,
                Tables.AUTONOMOUS_PRACTICE_HEAD.DATABASE_TABLE,Tables.AUTONOMOUS_PRACTICE_HEAD.DATABASE_TABLE_FIELD,
                Tables.AUTONOMOUS_PRACTICE_HEAD.AUTHORITY,Tables.AUTONOMOUS_PRACTICE_HEAD.IS_SHOW_HIGHLY_ACTIVE,
                Tables.AUTONOMOUS_PRACTICE_HEAD.CONTENT,Tables.AUTONOMOUS_PRACTICE_HEAD.IS_DATABASE,Tables.AUTONOMOUS_PRACTICE_HEAD.SORT)
                .from(Tables.AUTONOMOUS_PRACTICE_HEAD)
                .join(Tables.HEAD_TYPE)
                .on(Tables.AUTONOMOUS_PRACTICE_HEAD.HEAD_TYPE_ID.eq(Tables.HEAD_TYPE.ID))
                .where(Tables.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(autonomousPracticeTemplateId))
                .orderBy(Tables.AUTONOMOUS_PRACTICE_HEAD.SORT.asc())
                .fetch();
        return record12s;
    }

    @Override
    public AutonomousPracticeHeadRecord findByAutonomousPracticeTemplateIdAndTitleVariable(int autonomousPracticeTemplateId, String titleVariable) {
        AutonomousPracticeHeadRecord autonomousPracticeHeadRecord = create.selectFrom(Tables.AUTONOMOUS_PRACTICE_HEAD)
                .where(Tables.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_TEMPLATE_ID.eq(autonomousPracticeTemplateId)
                .and(Tables.AUTONOMOUS_PRACTICE_HEAD.TITLE_VARIABLE.eq(titleVariable)))
                .fetchOne();
        return autonomousPracticeHeadRecord;
    }

    @Override
    public void deleteById(int id) {
        autonomousPracticeHeadDao.deleteById(id);
    }
}
