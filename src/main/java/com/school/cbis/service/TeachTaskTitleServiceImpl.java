package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeachTaskInfoDao;
import com.school.cbis.domain.tables.daos.TeachTaskTitleDao;
import com.school.cbis.domain.tables.pojos.TeachTaskTitle;
import com.school.cbis.domain.tables.records.TeachTaskInfoRecord;
import com.school.cbis.domain.tables.records.TeachTaskTitleRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2016-05-28.
 */
@Service("teachTaskTitleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachTaskTitleServiceImpl implements TeachTaskTitleService {

    private final Logger log = LoggerFactory.getLogger(TeachTaskTitleServiceImpl.class);

    private final DSLContext create;

    private TeachTaskTitleDao teachTaskTitleDao;

    @Autowired
    public TeachTaskTitleServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachTaskTitleDao = new TeachTaskTitleDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int saveAndReturnId(TeachTaskTitle teachTaskTitle) {
        TeachTaskTitleRecord teachTaskTitleRecord = create.insertInto(Tables.TEACH_TASK_TITLE)
                .set(Tables.TEACH_TASK_TITLE.TITLE, teachTaskTitle.getTitle())
                .set(Tables.TEACH_TASK_TITLE.TITLE_X, teachTaskTitle.getTitleX())
                .set(Tables.TEACH_TASK_TITLE.TITLE_Y, teachTaskTitle.getTitleY())
                .set(Tables.TEACH_TASK_TITLE.TEACH_TASK_INFO_ID, teachTaskTitle.getTeachTaskInfoId())
                .returning(Tables.TEACH_TASK_TITLE.ID)
                .fetchOne();
        return teachTaskTitleRecord.getId();
    }
}
