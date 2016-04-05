package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.AutonomousPracticeInfoDao;
import com.school.cbis.domain.tables.daos.AutonomousPracticeTemplateDao;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeInfo;
import com.school.cbis.domain.tables.records.AutonomousPracticeInfoRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/4/5.
 */
@Service("autonomousPracticeInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AutonomousPracticeInfoServiceImpl implements AutonomousPracticeInfoService {

    private final Logger log = LoggerFactory.getLogger(AutonomousPracticeInfoServiceImpl.class);

    private final DSLContext create;

    private AutonomousPracticeInfoDao autonomousPracticeTemplateDao;

    @Autowired
    public AutonomousPracticeInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.autonomousPracticeTemplateDao = new AutonomousPracticeInfoDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int save(AutonomousPracticeInfo autonomousPracticeInfo) {
        AutonomousPracticeInfoRecord autonomousPracticeInfoRecord =  create.insertInto(Tables.AUTONOMOUS_PRACTICE_INFO)
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TITLE, autonomousPracticeInfo.getAutonomousPracticeTitle())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.GRADE_YEAR, autonomousPracticeInfo.getGradeYear())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.AUTONOMOUS_PRACTICE_TEMPLATE_ID, autonomousPracticeInfo.getAutonomousPracticeTemplateId())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.START_TIME, autonomousPracticeInfo.getStartTime())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.END_TIME, autonomousPracticeInfo.getEndTime())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.USERS_ID, autonomousPracticeInfo.getUsersId())
                .set(Tables.AUTONOMOUS_PRACTICE_INFO.TIE_ID, autonomousPracticeInfo.getTieId())
                .returning(Tables.ARTICLE_INFO.ID)
                .fetchOne();
        return autonomousPracticeInfoRecord.getId();
    }
}
