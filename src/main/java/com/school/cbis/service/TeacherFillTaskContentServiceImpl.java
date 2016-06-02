package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.TeacherFillTaskContentDao;
import com.school.cbis.domain.tables.daos.TeacherFillTaskHeadDao;
import com.school.cbis.domain.tables.pojos.TeacherFillTaskContent;
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
 * Created by lenovo on 2016-06-02.
 */
@Service("teacherFillTaskContentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeacherFillTaskContentServiceImpl implements TeacherFillTaskContentService{

    private final Logger log = LoggerFactory.getLogger(TeacherFillTaskContentServiceImpl.class);

    private final DSLContext create;

    private TeacherFillTaskContentDao teacherFillTaskContentDao;

    @Autowired
    public TeacherFillTaskContentServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teacherFillTaskContentDao = new TeacherFillTaskContentDao(configuration);
    }

    @Override
    public List<TeacherFillTaskContent> findByTeacherFillTaskHeadId(int teacherFillTaskHeadId) {
        List<TeacherFillTaskContent> teacherFillTaskContents = teacherFillTaskContentDao.fetchByTeacherFillTaskHeadId(teacherFillTaskHeadId);
        return teacherFillTaskContents;
    }
}
