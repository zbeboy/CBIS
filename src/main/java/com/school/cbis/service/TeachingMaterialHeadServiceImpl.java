package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeachingMaterialHeadDao;
import com.school.cbis.domain.tables.daos.TeachingMaterialInfoDao;
import com.school.cbis.domain.tables.pojos.TeacherFillTaskHead;
import com.school.cbis.domain.tables.pojos.TeachingMaterialHead;
import com.school.cbis.domain.tables.records.TeacherFillTaskHeadRecord;
import com.school.cbis.domain.tables.records.TeachingMaterialHeadRecord;
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

/**
 * Created by lenovo on 2016-06-04.
 */
@Service("teachingMaterialHeadService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachingMaterialHeadServiceImpl implements TeachingMaterialHeadService {

    private final Logger log = LoggerFactory.getLogger(TeachingMaterialHeadServiceImpl.class);

    private final DSLContext create;

    private TeachingMaterialHeadDao teachingMaterialHeadDao;

    @Autowired
    public TeachingMaterialHeadServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachingMaterialHeadDao = new TeachingMaterialHeadDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int saveAndReturnId(TeachingMaterialHead teachingMaterialHead) {
        TeachingMaterialHeadRecord record = create.insertInto(Tables.TEACHING_MATERIAL_HEAD)
                .set(Tables.TEACHING_MATERIAL_HEAD.TITLE,teachingMaterialHead.getTitle())
                .set(Tables.TEACHING_MATERIAL_HEAD.TITLE_VARIABLE,teachingMaterialHead.getTitleVariable())
                .set(Tables.TEACHING_MATERIAL_HEAD.TEACH_TASK_TITLE_ID,teachingMaterialHead.getTeachTaskTitleId())
                .set(Tables.TEACHING_MATERIAL_HEAD.TEACHING_MATERIAL_TEMPLATE_ID,teachingMaterialHead.getTeachingMaterialTemplateId())
                .set(Tables.TEACHING_MATERIAL_HEAD.IS_ASSIGNMENT,teachingMaterialHead.getIsAssignment())
                .set(Tables.TEACHING_MATERIAL_HEAD.SORT,teachingMaterialHead.getSort())
                .returning(Tables.TEACHING_MATERIAL_HEAD.ID)
                .fetchOne();
        return record.getId();
    }

    @Override
    public TeachingMaterialHead findById(int id) {
        TeachingMaterialHead teachingMaterialHead = teachingMaterialHeadDao.findById(id);
        return teachingMaterialHead;
    }

    @Override
    public void update(TeachingMaterialHead teachingMaterialHead) {
        teachingMaterialHeadDao.update(teachingMaterialHead);
    }

    @Override
    public void deleteById(int id) {
        teachingMaterialHeadDao.deleteById(id);
    }

    @Override
    public Result<Record7<Integer, String, String, String, Integer, Byte, Integer>> findByTeachingMaterialTemplateIdWithTeachTaskInfoTitle(int teacherFillTaskTemplateId) {
        Result<Record7<Integer, String, String, String, Integer, Byte, Integer>> record7s =  create.select(Tables.TEACHING_MATERIAL_HEAD.ID,Tables.TEACHING_MATERIAL_HEAD.TITLE,
                Tables.TEACHING_MATERIAL_HEAD.TITLE_VARIABLE,Tables.TEACH_TASK_TITLE.TITLE.as("teachTaskTitle"),
                Tables.TEACHING_MATERIAL_HEAD.TEACHING_MATERIAL_TEMPLATE_ID,Tables.TEACHING_MATERIAL_HEAD.IS_ASSIGNMENT,
                Tables.TEACHING_MATERIAL_HEAD.SORT)
                .from(Tables.TEACHING_MATERIAL_HEAD)
                .leftJoin(Tables.TEACH_TASK_TITLE)
                .on(Tables.TEACHING_MATERIAL_HEAD.TEACH_TASK_TITLE_ID.eq(Tables.TEACH_TASK_TITLE.ID))
                .where(Tables.TEACHING_MATERIAL_HEAD.TEACHING_MATERIAL_TEMPLATE_ID.eq(teacherFillTaskTemplateId))
                .orderBy(Tables.TEACHING_MATERIAL_HEAD.SORT.asc())
                .fetch();
        return record7s;
    }
}
