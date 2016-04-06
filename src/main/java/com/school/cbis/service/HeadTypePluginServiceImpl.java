package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.HeadTypePluginDao;
import com.school.cbis.domain.tables.pojos.HeadTypePlugin;
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
@Service("headTypePluginService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HeadTypePluginServiceImpl implements HeadTypePluginService {

    private final Logger log = LoggerFactory.getLogger(HeadTypePluginServiceImpl.class);

    private final DSLContext create;

    private HeadTypePluginDao headTypePluginDao;

    @Autowired
    public HeadTypePluginServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.headTypePluginDao = new HeadTypePluginDao(configuration);
    }

    @Override
    public List<HeadTypePlugin> findAll() {
        List<HeadTypePlugin> headTypePluginList = headTypePluginDao.findAll();
        return headTypePluginList;
    }
}
