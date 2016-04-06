package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.HeadTypeDao;
import com.school.cbis.domain.tables.pojos.HeadType;
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
@Service("headTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HeadTypeServiceImpl implements HeadTypeService {

    private final Logger log = LoggerFactory.getLogger(HeadTypeServiceImpl.class);

    private final DSLContext create;

    private HeadTypeDao headTypeDao;

    @Autowired
    public HeadTypeServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.headTypeDao = new HeadTypeDao(configuration);
    }

    @Override
    public List<HeadType> findAll() {
        List<HeadType> headTypes = headTypeDao.findAll();
        return headTypes;
    }
}
