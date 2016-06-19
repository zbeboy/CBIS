package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.ExaminationArrangementHeadDao;
import com.school.cbis.domain.tables.daos.ExaminationArrangementInfoDao;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2016-06-19.
 */
@Service("examinationArrangementHeadService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExaminationArrangementHeadServiceImpl implements ExaminationArrangementHeadService {

    private final Logger log = LoggerFactory.getLogger(ExaminationArrangementHeadServiceImpl.class);

    private final DSLContext create;

    private ExaminationArrangementHeadDao examinationArrangementHeadDao;

    @Autowired
    public ExaminationArrangementHeadServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.examinationArrangementHeadDao = new ExaminationArrangementHeadDao(configuration);
    }
}
