package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.AutonomousPracticeHeadDao;
import com.school.cbis.domain.tables.daos.AutonomousPracticeInfoDao;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeHead;
import org.jooq.Configuration;
import org.jooq.DSLContext;
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
    public void save(AutonomousPracticeHead autonomousPracticeHead) {
        autonomousPracticeHeadDao.insert(autonomousPracticeHead);
    }

    @Override
    public List<AutonomousPracticeHead> findByAutonomousPracticeInfoId(int autonomousPracticeInfoId) {
        List<AutonomousPracticeHead> autonomousPracticeHeads = autonomousPracticeHeadDao.fetchByAutonomousPracticeInfoId(autonomousPracticeInfoId);
        return autonomousPracticeHeads;
    }

    @Override
    public void deleteByAutonomousPracticeInfoId(int autonomousPracticeInfoId) {
        create.deleteFrom(Tables.AUTONOMOUS_PRACTICE_HEAD).where(Tables.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_INFO_ID.eq(autonomousPracticeInfoId)).execute();
    }
}
