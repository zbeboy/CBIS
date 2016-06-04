package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeacherDao;
import com.school.cbis.domain.tables.daos.TeachingMaterialTemplateDao;
import com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate;
import com.school.cbis.domain.tables.pojos.TeachingMaterialTemplate;
import com.school.cbis.domain.tables.records.TeacherFillTaskTemplateRecord;
import com.school.cbis.domain.tables.records.TeachingMaterialTemplateRecord;
import com.school.cbis.vo.eadmin.TeachingMaterialTemplateListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-04.
 */
@Service("teachingMaterialTemplateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeachingMaterialTemplateServiceImpl implements TeachingMaterialTemplateService {

    private final Logger log = LoggerFactory.getLogger(TeachingMaterialTemplateServiceImpl.class);

    private final DSLContext create;

    private TeachingMaterialTemplateDao teachingMaterialTemplateDao;

    @Autowired
    public TeachingMaterialTemplateServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teachingMaterialTemplateDao = new TeachingMaterialTemplateDao(configuration);
    }

    @Override
    public Result<Record5<Integer, String, Timestamp, String, String>> findByTieIdAndPage(TeachingMaterialTemplateListVo teachingMaterialTemplateListVo, int tieId) {
        Condition a = Tables.TEACHING_MATERIAL_TEMPLATE.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(teachingMaterialTemplateListVo.getTitle())) {
            a = a.and(Tables.TEACHING_MATERIAL_TEMPLATE.TITLE.like("%" + teachingMaterialTemplateListVo.getTitle() + "%"));
        }

        if (StringUtils.hasLength(teachingMaterialTemplateListVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + teachingMaterialTemplateListVo.getRealName() + "%"));
        }

        int pageNum = teachingMaterialTemplateListVo.getPageNum();
        int pageSize = teachingMaterialTemplateListVo.getPageSize();

        if (pageNum <= 0) {
            pageNum = 1;
        }

        Result<Record5<Integer, String, Timestamp, String, String>> record5s = create.select(Tables.TEACHING_MATERIAL_TEMPLATE.ID, Tables.TEACHING_MATERIAL_TEMPLATE.TITLE,
                Tables.TEACHING_MATERIAL_TEMPLATE.CREATE_TIME, Tables.USERS.REAL_NAME,
                Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE)
                .from(Tables.TEACHING_MATERIAL_TEMPLATE)
                .join(Tables.USERS)
                .on((Tables.TEACHING_MATERIAL_TEMPLATE.CREATE_USER.eq(Tables.USERS.USERNAME)))
                .join(Tables.TEACH_TASK_INFO)
                .on(Tables.TEACHING_MATERIAL_TEMPLATE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .where(a)
                .orderBy(Tables.TEACHING_MATERIAL_TEMPLATE.CREATE_TIME.desc())
                .limit((pageNum - 1) * pageSize, pageSize)
                .fetch();

        return record5s;
    }

    @Override
    public int findByTieIdAndPageCount(TeachingMaterialTemplateListVo teachingMaterialTemplateListVo, int tieId) {
        Condition a = Tables.TEACHING_MATERIAL_TEMPLATE.TIE_ID.eq(tieId);
        if (StringUtils.hasLength(teachingMaterialTemplateListVo.getTitle())) {
            a = a.and(Tables.TEACHING_MATERIAL_TEMPLATE.TITLE.like("%" + teachingMaterialTemplateListVo.getTitle() + "%"));
        }

        if (StringUtils.hasLength(teachingMaterialTemplateListVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + teachingMaterialTemplateListVo.getRealName() + "%"));
        }

        Record1<Integer> record1 = create.selectCount()
                .from(Tables.TEACHING_MATERIAL_TEMPLATE)
                .join(Tables.USERS)
                .on((Tables.TEACHING_MATERIAL_TEMPLATE.CREATE_USER.eq(Tables.USERS.USERNAME)))
                .join(Tables.TEACH_TASK_INFO)
                .on(Tables.TEACHING_MATERIAL_TEMPLATE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Override
    public Result<TeachingMaterialTemplateRecord> findByTieIdAndTitle(int tieId, String title) {
        Result<TeachingMaterialTemplateRecord> records = create.selectFrom(Tables.TEACHING_MATERIAL_TEMPLATE)
                .where(Tables.TEACHING_MATERIAL_TEMPLATE.TIE_ID.eq(tieId).and(Tables.TEACHING_MATERIAL_TEMPLATE.TITLE.eq(title)))
                .fetch();
        return records;
    }

    @Override
    public Result<TeachingMaterialTemplateRecord> findByTieIdAndTitleAndNeId(int tieId, String title, int id) {
        Result<TeachingMaterialTemplateRecord> records = create.selectFrom(Tables.TEACHING_MATERIAL_TEMPLATE)
                .where(Tables.TEACHING_MATERIAL_TEMPLATE.TIE_ID.eq(tieId).and(Tables.TEACHING_MATERIAL_TEMPLATE.TITLE.eq(title)).and(Tables.TEACHING_MATERIAL_TEMPLATE.ID.ne(id)))
                .fetch();
        return records;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int saveAndReturnId(TeachingMaterialTemplate teachingMaterialTemplate) {
        TeachingMaterialTemplateRecord teachingMaterialTemplateRecord = create.insertInto(Tables.TEACHING_MATERIAL_TEMPLATE)
                .set(Tables.TEACHING_MATERIAL_TEMPLATE.TITLE,teachingMaterialTemplate.getTitle())
                .set(Tables.TEACHING_MATERIAL_TEMPLATE.CREATE_TIME,teachingMaterialTemplate.getCreateTime())
                .set(Tables.TEACHING_MATERIAL_TEMPLATE.CREATE_USER,teachingMaterialTemplate.getCreateUser())
                .set(Tables.TEACHING_MATERIAL_TEMPLATE.TIE_ID,teachingMaterialTemplate.getTieId())
                .set(Tables.TEACHING_MATERIAL_TEMPLATE.TEACH_TASK_INFO_ID,teachingMaterialTemplate.getTeachTaskInfoId())
                .returning(Tables.TEACHING_MATERIAL_TEMPLATE.ID)
                .fetchOne();
        return teachingMaterialTemplateRecord.getId();
    }

    @Override
    public TeachingMaterialTemplate findById(int id) {
        TeachingMaterialTemplate teachingMaterialTemplate = teachingMaterialTemplateDao.findById(id);
        return teachingMaterialTemplate;
    }

    @Override
    public void update(TeachingMaterialTemplate teachingMaterialTemplate) {
        teachingMaterialTemplateDao.update(teachingMaterialTemplate);
    }
}
