package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.YardDao;
import com.school.cbis.domain.tables.pojos.Yard;
import org.apache.log4j.Logger;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2016-02-07.
 */
@Service("yardService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class YardServiceImpl implements YardService {

    private static Logger logger = Logger.getLogger(YardServiceImpl.class);

    private final DSLContext create;

    private YardDao yardDao;

    @Autowired
    public YardServiceImpl(DSLContext dslContext,Configuration configuration) {
        this.create = dslContext;
        this.yardDao = new YardDao(configuration);
    }

    @Override
    public List<Yard> findAll() {
        List<Yard> list = yardDao.findAll();
        return list;
    }
}
