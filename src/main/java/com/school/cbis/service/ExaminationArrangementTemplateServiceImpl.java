package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.ExamDao;
import com.school.cbis.domain.tables.daos.ExaminationArrangementInfoDao;
import com.school.cbis.domain.tables.daos.ExaminationArrangementTemplateDao;
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
@Service("examinationArrangementTemplateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExaminationArrangementTemplateServiceImpl implements ExaminationArrangementTemplateService {

    private final Logger log = LoggerFactory.getLogger(ExaminationArrangementTemplateServiceImpl.class);

    private final DSLContext create;

    private ExaminationArrangementTemplateDao examinationArrangementTemplateDao;

    @Autowired
    public ExaminationArrangementTemplateServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.examinationArrangementTemplateDao = new ExaminationArrangementTemplateDao(configuration);
    }
}
