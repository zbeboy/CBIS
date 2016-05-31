package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.TeachTaskGradeCheckDao;
import com.school.cbis.domain.tables.daos.TieElegantDao;
import com.school.cbis.domain.tables.pojos.TeachTaskGradeCheck;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2016-05-30.
 */
@Service("teachTaskGradeCheckService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachTaskGradeCheckServiceImpl implements TeachTaskGradeCheckService {

    private final Logger log = LoggerFactory.getLogger(TeachTaskGradeCheckServiceImpl.class);

    private final DSLContext create;

    private TeachTaskGradeCheckDao teachTaskGradeCheckDao;

    @Autowired
    public TeachTaskGradeCheckServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachTaskGradeCheckDao = new TeachTaskGradeCheckDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(TeachTaskGradeCheck teachTaskGradeCheck) {
        teachTaskGradeCheckDao.insert(teachTaskGradeCheck);
    }
}
