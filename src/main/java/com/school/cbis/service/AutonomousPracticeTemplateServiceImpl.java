package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.AutonomousPracticeTemplateDao;
import com.school.cbis.domain.tables.pojos.AutonomousPracticeTemplate;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
@Service("autonomousPracticeTemplateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AutonomousPracticeTemplateServiceImpl implements AutonomousPracticeTemplateService {

    private final Logger log = LoggerFactory.getLogger(AutonomousPracticeTemplateServiceImpl.class);

    private final DSLContext create;

    private AutonomousPracticeTemplateDao autonomousPracticeTemplateDao;

    @Autowired
    public AutonomousPracticeTemplateServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.autonomousPracticeTemplateDao = new AutonomousPracticeTemplateDao(configuration);
    }

    @Override
    public Result<Record4<Integer,String,Integer,Timestamp>> findAllByTieId(int tieId) {
        Result<Record4<Integer,String,Integer,Timestamp>> record4s = create.select(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.ID,Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_TEMPLATE_TITLE,Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_INFO_ID,
                Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME)
                .from(Tables.AUTONOMOUS_PRACTICE_TEMPLATE)
                .join(Tables.AUTONOMOUS_PRACTICE_INFO)
                .on(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.AUTONOMOUS_PRACTICE_INFO_ID.eq(Tables.AUTONOMOUS_PRACTICE_INFO.ID))
                .where(Tables.AUTONOMOUS_PRACTICE_INFO.TIE_ID.eq(tieId))
                .orderBy(Tables.AUTONOMOUS_PRACTICE_TEMPLATE.CREATE_TIME.desc())
                .fetch();
        return record4s;
    }
}
