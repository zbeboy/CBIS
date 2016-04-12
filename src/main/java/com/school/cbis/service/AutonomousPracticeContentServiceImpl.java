package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.AutonomousPracticeContentDao;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2016-04-12.
 */
@Service("autonomousPracticeContentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AutonomousPracticeContentServiceImpl implements AutonomousPracticeContentService{

    private final Logger log = LoggerFactory.getLogger(AutonomousPracticeContentServiceImpl.class);

    private final DSLContext create;

    private AutonomousPracticeContentDao autonomousPracticeContentDao;

    @Autowired
    public AutonomousPracticeContentServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.autonomousPracticeContentDao = new AutonomousPracticeContentDao(configuration);
    }

    @Override
    public void deleteByAutonomousPracticeHeadId(int autonomousPracticeHeadId) {
        create.deleteFrom(Tables.AUTONOMOUS_PRACTICE_CONTENT).where(Tables.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID.eq(autonomousPracticeHeadId)).execute();
    }
}
