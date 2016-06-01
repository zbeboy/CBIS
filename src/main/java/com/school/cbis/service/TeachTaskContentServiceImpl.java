package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeachTaskContentDao;
import com.school.cbis.domain.tables.daos.TeachTaskTitleDao;
import com.school.cbis.domain.tables.pojos.TeachTaskContent;
import com.school.cbis.domain.tables.records.TeachTaskContentRecord;
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
 * Created by lenovo on 2016-05-28.
 */
@Service("teachTaskContentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachTaskContentServiceImpl implements TeachTaskContentService {

    private final Logger log = LoggerFactory.getLogger(TeachTaskContentServiceImpl.class);

    private final DSLContext create;

    private TeachTaskContentDao teachTaskContentDao;

    @Autowired
    public TeachTaskContentServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachTaskContentDao = new TeachTaskContentDao(configuration);
    }

    @Override
    public void save(TeachTaskContent teachTaskContent) {
        teachTaskContentDao.insert(teachTaskContent);
    }

    @Override
    public Result<TeachTaskContentRecord> findInTeachTaskTitleId(List<Integer> teachTaskTitleId) {
        Result<TeachTaskContentRecord> records = create.selectFrom(Tables.TEACH_TASK_CONTENT)
                .where(Tables.TEACH_TASK_CONTENT.TEACH_TASK_TITLE_ID.in(teachTaskTitleId))
                .fetch();
        return records;
    }
}
