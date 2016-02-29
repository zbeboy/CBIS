package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.TieDao;
import com.school.cbis.domain.tables.pojos.Tie;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2016-01-17.
 */
@Service("tieService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TieServiceImpl implements TieService {

    private final DSLContext create;

    private TieDao tieDao;

    @Autowired
    public TieServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.tieDao = new TieDao(configuration);
    }

    @Override
    public void update(Tie tie) {
        tieDao.update(tie);
    }

    @Override
    public Tie findById(int id) {
        Tie tie = tieDao.findById(id);
        return tie;
    }

    @Override
    public List<Tie> findByTieName(String tieName) {
        List<Tie> ties = tieDao.fetchByTieName(tieName);
        return ties;
    }
}
