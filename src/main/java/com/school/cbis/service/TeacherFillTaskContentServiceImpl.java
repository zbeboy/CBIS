package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeacherFillTaskContentDao;
import com.school.cbis.domain.tables.daos.TeacherFillTaskHeadDao;
import com.school.cbis.domain.tables.pojos.TeacherFillTaskContent;
import com.school.cbis.domain.tables.records.TeacherFillTaskContentRecord;
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

    @Override
    public void deleteByTeacherFillTaskHeadId(int teacherFillTaskHeadId) {
        create.deleteFrom(Tables.TEACHER_FILL_TASK_CONTENT)
                .where(Tables.TEACHER_FILL_TASK_CONTENT.TEACHER_FILL_TASK_HEAD_ID.eq(teacherFillTaskHeadId))
                .execute();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(TeacherFillTaskContent teacherFillTaskContent) {
        teacherFillTaskContentDao.insert(teacherFillTaskContent);
    }

    @Override
    public void update(TeacherFillTaskContent teacherFillTaskContent) {
        teacherFillTaskContentDao.update(teacherFillTaskContent);
    }

    @Override
    public Result<TeacherFillTaskContentRecord> findInTeacherFillTaskHeadId(List<Integer> id) {
        Result<TeacherFillTaskContentRecord> records =  create.selectFrom(Tables.TEACHER_FILL_TASK_CONTENT)
                .where(Tables.TEACHER_FILL_TASK_CONTENT.TEACHER_FILL_TASK_HEAD_ID.in(id))
                .fetch();
        return records;
    }

    @Override
    public Result<TeacherFillTaskContentRecord> findInTeacherFillTaskHeadIdAndContentX(List<Integer> id, int contentX) {
        Result<TeacherFillTaskContentRecord> records =  create.selectFrom(Tables.TEACHER_FILL_TASK_CONTENT)
                .where(Tables.TEACHER_FILL_TASK_CONTENT.TEACHER_FILL_TASK_HEAD_ID.in(id).and(Tables.TEACHER_FILL_TASK_CONTENT.CONTENT_X.eq(contentX)))
                .fetch();
        return records;
    }

    @Override
    public TeacherFillTaskContentRecord findByTeacherFillTaskHeadIdAndContentX(int teacherFillTaskHeadId, int contentX) {
        TeacherFillTaskContentRecord record = create.selectFrom(Tables.TEACHER_FILL_TASK_CONTENT)
                .where(Tables.TEACHER_FILL_TASK_CONTENT.TEACHER_FILL_TASK_HEAD_ID.eq(teacherFillTaskHeadId).and(Tables.TEACHER_FILL_TASK_CONTENT.CONTENT_X.eq(contentX)))
                .fetchOne();
        return record;
    }
}
