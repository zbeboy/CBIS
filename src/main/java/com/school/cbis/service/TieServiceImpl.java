package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TieDao;
import com.school.cbis.domain.tables.pojos.Tie;
import com.school.cbis.domain.tables.records.TieRecord;
import org.apache.log4j.Logger;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
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

    private static Logger logger = Logger.getLogger(TieServiceImpl.class);

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
    public Result<TieRecord> findByTieName(int id,String tieName) {
       Result<TieRecord> records =  create .selectFrom(Tables.TIE)
                .where(Tables.TIE.ID.ne(id).and(Tables.TIE.TIE_NAME.eq(tieName)))
                .fetch();
        return records;
    }
}
