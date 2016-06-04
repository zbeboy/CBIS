package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.TeachingMaterialInfoDao;
import com.school.cbis.domain.tables.daos.TeachingMaterialTemplateDao;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2016-06-04.
 */
@Service("teachingMaterialInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachingMaterialInfoServiceImpl implements TeachingMaterialInfoService {

    private final Logger log = LoggerFactory.getLogger(TeachingMaterialInfoServiceImpl.class);

    private final DSLContext create;

    private TeachingMaterialInfoDao teachingMaterialInfoDao;

    @Autowired
    public TeachingMaterialInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachingMaterialInfoDao = new TeachingMaterialInfoDao(configuration);
    }
}
