package com.school.cbis.service;

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
@Service("examinationArrangementInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExaminationArrangementInfoServiceImpl implements ExaminationArrangementInfoService {

    private final Logger log = LoggerFactory.getLogger(ExaminationArrangementInfoServiceImpl.class);

    private final DSLContext create;

    private ExaminationArrangementInfoDao examinationArrangementInfoDao;

    @Autowired
    public ExaminationArrangementInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.examinationArrangementInfoDao = new ExaminationArrangementInfoDao(configuration);
    }
}
