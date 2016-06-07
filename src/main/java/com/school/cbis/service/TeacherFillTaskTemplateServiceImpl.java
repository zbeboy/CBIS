package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeacherDao;
import com.school.cbis.domain.tables.daos.TeacherFillTaskTemplateDao;
import com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate;
import com.school.cbis.domain.tables.records.TeacherFillTaskTemplateRecord;
import com.school.cbis.vo.eadmin.TeacherFillTemplateListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lenovo on 2016-06-02.
 */
@Service("teacherFillTaskTemplateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeacherFillTaskTemplateServiceImpl implements TeacherFillTaskTemplateService {

    private final Logger log = LoggerFactory.getLogger(TeacherFillTaskTemplateServiceImpl.class);

    private final DSLContext create;

    private TeacherFillTaskTemplateDao teacherFillTaskTemplateDao;

    @Autowired
    public TeacherFillTaskTemplateServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teacherFillTaskTemplateDao = new TeacherFillTaskTemplateDao(configuration);
    }

    @Override
    public Result<Record5<Integer, String, Timestamp, String, String>> findByTieIdAndTeachTypeIdAndPage(TeacherFillTemplateListVo teacherFillTemplateListVo, int tieId) {
        Condition a = Tables.TEACHER_FILL_TASK_TEMPLATE.TIE_ID.eq(tieId).and(Tables.TEACH_TASK_INFO.TEACH_TYPE_ID.eq(teacherFillTemplateListVo.getTeachTypeId()));
        if (StringUtils.hasLength(teacherFillTemplateListVo.getTitle())) {
            a = a.and(Tables.TEACHER_FILL_TASK_TEMPLATE.TITLE.like("%" + teacherFillTemplateListVo.getTitle() + "%"));
        }

        if (StringUtils.hasLength(teacherFillTemplateListVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + teacherFillTemplateListVo.getRealName() + "%"));
        }

        int pageNum = teacherFillTemplateListVo.getPageNum();
        int pageSize = teacherFillTemplateListVo.getPageSize();

        if (pageNum <= 0) {
            pageNum = 1;
        }

        Result<Record5<Integer, String, Timestamp, String, String>> record5s = create.select(Tables.TEACHER_FILL_TASK_TEMPLATE.ID, Tables.TEACHER_FILL_TASK_TEMPLATE.TITLE,
                Tables.TEACHER_FILL_TASK_TEMPLATE.CREATE_TIME, Tables.USERS.REAL_NAME,
                Tables.TEACH_TASK_INFO.TEACH_TASK_TITLE)
                .from(Tables.TEACHER_FILL_TASK_TEMPLATE)
                .join(Tables.USERS)
                .on((Tables.TEACHER_FILL_TASK_TEMPLATE.CREATE_USER.eq(Tables.USERS.USERNAME)))
                .join(Tables.TEACH_TASK_INFO)
                .on(Tables.TEACHER_FILL_TASK_TEMPLATE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .where(a)
                .orderBy(Tables.TEACHER_FILL_TASK_TEMPLATE.CREATE_TIME.desc())
                .limit((pageNum - 1) * pageSize, pageSize)
                .fetch();

        return record5s;
    }

    @Override
    public int findByTieIdAndTeachTypeIdAndPageCount(TeacherFillTemplateListVo teacherFillTemplateListVo, int tieId) {
        Condition a = Tables.TEACHER_FILL_TASK_TEMPLATE.TIE_ID.eq(tieId).and(Tables.TEACH_TASK_INFO.TEACH_TYPE_ID.eq(teacherFillTemplateListVo.getTeachTypeId()));
        if (StringUtils.hasLength(teacherFillTemplateListVo.getTitle())) {
            a = a.and(Tables.TEACHER_FILL_TASK_TEMPLATE.TITLE.like("%" + teacherFillTemplateListVo.getTitle() + "%"));
        }

        if (StringUtils.hasLength(teacherFillTemplateListVo.getRealName())) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + teacherFillTemplateListVo.getRealName() + "%"));
        }

       Record1<Integer> record1 = create.selectCount()
                .from(Tables.TEACHER_FILL_TASK_TEMPLATE)
                .join(Tables.USERS)
                .on((Tables.TEACHER_FILL_TASK_TEMPLATE.CREATE_USER.eq(Tables.USERS.USERNAME)))
                .join(Tables.TEACH_TASK_INFO)
                .on(Tables.TEACHER_FILL_TASK_TEMPLATE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Override
    public Result<TeacherFillTaskTemplateRecord> findByTieIdAndTitle(int tieId, String title) {
        Result<TeacherFillTaskTemplateRecord> records = create.selectFrom(Tables.TEACHER_FILL_TASK_TEMPLATE)
                .where(Tables.TEACHER_FILL_TASK_TEMPLATE.TIE_ID.eq(tieId).and(Tables.TEACHER_FILL_TASK_TEMPLATE.TITLE.eq(title)))
                .fetch();
        return records;
    }

    @Override
    public Result<TeacherFillTaskTemplateRecord> findByTieIdAndTitleAndNeId(int tieId, String title, int id) {
        Result<TeacherFillTaskTemplateRecord> records = create.selectFrom(Tables.TEACHER_FILL_TASK_TEMPLATE)
                .where(Tables.TEACHER_FILL_TASK_TEMPLATE.TIE_ID.eq(tieId).and(Tables.TEACHER_FILL_TASK_TEMPLATE.TITLE.eq(title)).and(Tables.TEACHER_FILL_TASK_TEMPLATE.ID.ne(id)))
                .fetch();
        return records;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(TeacherFillTaskTemplate teacherFillTaskTemplate) {
        teacherFillTaskTemplateDao.insert(teacherFillTaskTemplate);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public int saveAndReturnId(TeacherFillTaskTemplate teacherFillTaskTemplate) {
       TeacherFillTaskTemplateRecord teacherFillTaskTemplateRecord = create.insertInto(Tables.TEACHER_FILL_TASK_TEMPLATE)
                .set(Tables.TEACHER_FILL_TASK_TEMPLATE.TITLE,teacherFillTaskTemplate.getTitle())
                .set(Tables.TEACHER_FILL_TASK_TEMPLATE.CREATE_TIME,teacherFillTaskTemplate.getCreateTime())
                .set(Tables.TEACHER_FILL_TASK_TEMPLATE.CREATE_USER,teacherFillTaskTemplate.getCreateUser())
                .set(Tables.TEACHER_FILL_TASK_TEMPLATE.TIE_ID,teacherFillTaskTemplate.getTieId())
                .set(Tables.TEACHER_FILL_TASK_TEMPLATE.TEACH_TASK_INFO_ID,teacherFillTaskTemplate.getTeachTaskInfoId())
                .returning(Tables.TEACHER_FILL_TASK_TEMPLATE.ID)
                .fetchOne();
        return teacherFillTaskTemplateRecord.getId();
    }

    @Override
    public TeacherFillTaskTemplate findById(int id) {
        TeacherFillTaskTemplate teacherFillTaskTemplate = teacherFillTaskTemplateDao.findById(id);
        return teacherFillTaskTemplate;
    }

    @Override
    public void update(TeacherFillTaskTemplate teacherFillTaskTemplate) {
        teacherFillTaskTemplateDao.update(teacherFillTaskTemplate);
    }

    @Override
    public Result<Record6<Integer,String,Timestamp,String,Integer,Integer>> findByTieIdAndTeachTypeId(int tieId,int teachTypeId) {
        Result<Record6<Integer,String,Timestamp,String,Integer,Integer>> records =
                create.select(Tables.TEACHER_FILL_TASK_TEMPLATE.ID,
                        Tables.TEACHER_FILL_TASK_TEMPLATE.TITLE,
                        Tables.TEACHER_FILL_TASK_TEMPLATE.CREATE_TIME,
                        Tables.TEACHER_FILL_TASK_TEMPLATE.CREATE_USER,
                        Tables.TEACHER_FILL_TASK_TEMPLATE.TIE_ID,
                        Tables.TEACHER_FILL_TASK_TEMPLATE.TEACH_TASK_INFO_ID)
                    .from(Tables.TEACHER_FILL_TASK_TEMPLATE)
                        .join(Tables.TEACH_TASK_INFO)
                        .on(Tables.TEACHER_FILL_TASK_TEMPLATE.TEACH_TASK_INFO_ID.eq(Tables.TEACH_TASK_INFO.ID))
                .where(Tables.TEACHER_FILL_TASK_TEMPLATE.TIE_ID.eq(tieId).and(Tables.TEACH_TASK_INFO.TEACH_TYPE_ID.eq(teachTypeId)))
                .orderBy(Tables.TEACHER_FILL_TASK_TEMPLATE.CREATE_TIME.desc())
                .fetch();
        return records;
    }
}
