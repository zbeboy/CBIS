package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.ExaminationArrangementContentDao;
import com.school.cbis.domain.tables.daos.ExaminationArrangementHeadDao;
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
@Service("examinationArrangementContentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExaminationArrangementContentServiceImpl implements ExaminationArrangementContentService {

    private final Logger log = LoggerFactory.getLogger(ExaminationArrangementContentServiceImpl.class);

    private final DSLContext create;

    private ExaminationArrangementContentDao examinationArrangementContentDao;

    @Autowired
    public ExaminationArrangementContentServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.examinationArrangementContentDao = new ExaminationArrangementContentDao(configuration);
    }
}
