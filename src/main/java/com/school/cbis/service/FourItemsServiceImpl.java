package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.FourItemsDao;
import com.school.cbis.domain.tables.daos.GradeDao;
import com.school.cbis.domain.tables.pojos.FourItems;
import com.school.cbis.domain.tables.records.FourItemsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2016-06-07.
 */
@Service("fourItemsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FourItemsServiceImpl implements FourItemsService {

    private final Logger log = LoggerFactory.getLogger(FourItemsServiceImpl.class);

    private final DSLContext create;


    private FourItemsDao fourItemsDao;

    @Autowired
    public FourItemsServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.fourItemsDao = new FourItemsDao(configuration);
    }

    @Override
    public Result<FourItemsRecord> findByTeachTaskInfoIdInContentX(int teachTaskInfoId, List<Integer> contentX) {
        Result<FourItemsRecord> records = create.selectFrom(Tables.FOUR_ITEMS)
                .where(Tables.FOUR_ITEMS.TEACH_TASK_INFO_ID.eq(teachTaskInfoId).and(Tables.FOUR_ITEMS.CONTENT_X.in(contentX)))
                .fetch();
        return records;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(FourItems fourItems) {
        fourItemsDao.insert(fourItems);
    }

    @Override
    public void update(FourItems fourItems) {
        fourItemsDao.update(fourItems);
    }

    @Override
    public FourItems findById(int id) {
        FourItems fourItems = fourItemsDao.findById(id);
        return fourItems;
    }

    @Override
    public void deleteById(int id) {
        fourItemsDao.deleteById(id);
    }
}
