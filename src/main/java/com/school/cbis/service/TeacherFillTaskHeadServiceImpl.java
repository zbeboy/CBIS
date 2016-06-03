package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeacherFillTaskHeadDao;
import com.school.cbis.domain.tables.daos.TeacherFillTaskTemplateDao;
import com.school.cbis.domain.tables.pojos.TeacherFillTaskHead;
import com.school.cbis.domain.tables.records.TeacherFillTaskHeadRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record7;
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
@Service("teacherFillTaskHeadService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeacherFillTaskHeadServiceImpl implements TeacherFillTaskHeadService{

    private final Logger log = LoggerFactory.getLogger(TeacherFillTaskHeadServiceImpl.class);

    private final DSLContext create;

    private TeacherFillTaskHeadDao teacherFillTaskHeadDao;

    @Autowired
    public TeacherFillTaskHeadServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teacherFillTaskHeadDao = new TeacherFillTaskHeadDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int saveAndReturnId(TeacherFillTaskHead teacherFillTaskHead) {
       TeacherFillTaskHeadRecord record = create.insertInto(Tables.TEACHER_FILL_TASK_HEAD)
                .set(Tables.TEACHER_FILL_TASK_HEAD.TITLE,teacherFillTaskHead.getTitle())
                .set(Tables.TEACHER_FILL_TASK_HEAD.TITLE_VARIABLE,teacherFillTaskHead.getTitleVariable())
                .set(Tables.TEACHER_FILL_TASK_HEAD.TEACH_TASK_TITLE_ID,teacherFillTaskHead.getTeachTaskTitleId())
                .set(Tables.TEACHER_FILL_TASK_HEAD.TEACHER_FILL_TASK_TEMPLATE_ID,teacherFillTaskHead.getTeacherFillTaskTemplateId())
                .set(Tables.TEACHER_FILL_TASK_HEAD.IS_ASSIGNMENT,teacherFillTaskHead.getIsAssignment())
                .set(Tables.TEACHER_FILL_TASK_HEAD.SORT,teacherFillTaskHead.getSort())
                .returning(Tables.TEACHER_FILL_TASK_HEAD.ID)
                .fetchOne();
        return record.getId();
    }

    @Override
    public TeacherFillTaskHead findById(int id) {
        TeacherFillTaskHead teacherFillTaskHead = teacherFillTaskHeadDao.findById(id);
        return teacherFillTaskHead;
    }

    @Override
    public void update(TeacherFillTaskHead teacherFillTaskHead) {
        teacherFillTaskHeadDao.update(teacherFillTaskHead);
    }

    @Override
    public void deleteById(int id) {
        teacherFillTaskHeadDao.deleteById(id);
    }

    @Override
    public Result<Record7<Integer, String, String, String, Integer, Byte, Integer>> findByTeacherFillTaskTemplateIdWithTeachTaskInfoTitle(int teacherFillTaskTemplateId) {
        Result<Record7<Integer, String, String, String, Integer, Byte, Integer>> record7s =  create.select(Tables.TEACHER_FILL_TASK_HEAD.ID,Tables.TEACHER_FILL_TASK_HEAD.TITLE,
                Tables.TEACHER_FILL_TASK_HEAD.TITLE_VARIABLE,Tables.TEACH_TASK_TITLE.TITLE.as("teachTaskTitle"),
                Tables.TEACHER_FILL_TASK_HEAD.TEACHER_FILL_TASK_TEMPLATE_ID,Tables.TEACHER_FILL_TASK_HEAD.IS_ASSIGNMENT,
                Tables.TEACHER_FILL_TASK_HEAD.SORT)
                .from(Tables.TEACHER_FILL_TASK_HEAD)
                .leftJoin(Tables.TEACH_TASK_TITLE)
                .on(Tables.TEACHER_FILL_TASK_HEAD.TEACH_TASK_TITLE_ID.eq(Tables.TEACH_TASK_TITLE.ID))
                .where(Tables.TEACHER_FILL_TASK_HEAD.TEACHER_FILL_TASK_TEMPLATE_ID.eq(teacherFillTaskTemplateId))
                .orderBy(Tables.TEACHER_FILL_TASK_HEAD.SORT.asc())
                .fetch();
        return record7s;
    }

    @Override
    public List<TeacherFillTaskHead> findByTeacherFillTaskTemplateId(int teacherFillTaskTemplateId) {
        List<TeacherFillTaskHead> teacherFillTaskHeads = teacherFillTaskHeadDao.fetchByTeacherFillTaskTemplateId(teacherFillTaskTemplateId);
        return teacherFillTaskHeads;
    }
}
