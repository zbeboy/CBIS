package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.TeacherDao;
import com.school.cbis.domain.tables.pojos.Teacher;
import com.school.cbis.domain.tables.records.TeacherRecord;
import org.apache.poi.ss.formula.functions.T;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lenovo on 2016-02-15.
 */
@Service("teacherService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeacherServiceImpl implements TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final DSLContext create;

    private TeacherDao teacherDao;

    @Autowired
    public TeacherServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teacherDao = new TeacherDao(configuration);
    }

    @Override
    public  Result<Record4<Integer,Integer,String,String>> findByTieIdAndTeacherName(String teacherName, int tieId) {
        Result<Record4<Integer,Integer,String,String>> records = create.select(Tables.TEACHER.ID,Tables.TEACHER.TIE_ID,Tables.TEACHER.TEACHER_JOB_NUMBER,Tables.USERS.REAL_NAME)
                .from(Tables.TEACHER)
                .join(Tables.USERS)
                .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                .where(Tables.USERS.REAL_NAME.like("%" + teacherName + "%").and(Tables.TEACHER.TIE_ID.eq(tieId))).fetch();
        return records;
    }

    @Override
    public List<Teacher> findByTeacherJobNumber(String teacherJobNumber) {
        List<Teacher> teachers = teacherDao.fetchByTeacherJobNumber(teacherJobNumber);
        return teachers;
    }

    @Override
    public Teacher findById(int id) {
        Teacher teacher = teacherDao.findById(id);
        return teacher;
    }

    @Override
    public Result<Record4<Integer, String, String, Byte>> findByTieIdAndPage(String teacherName, String teacherJobNumber, int pageNum, int pageSize, int tieId) {
        Condition a = Tables.TEACHER.TIE_ID.eq(tieId);


        if (StringUtils.hasLength(teacherName)) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + teacherName + "%"));
        }

        if (StringUtils.hasLength(teacherJobNumber)) {
            a = a.and(Tables.TEACHER.TEACHER_JOB_NUMBER.like("%" + teacherJobNumber + "%"));
        }

        if (pageNum <= 0) {
            pageNum = 1;
        }

        Result<Record4<Integer, String, String, Byte>> record4s = create.select(Tables.TEACHER.ID,
                Tables.USERS.REAL_NAME, Tables.TEACHER.TEACHER_JOB_NUMBER, Tables.USERS.ENABLED)
                .from(Tables.TEACHER)
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .limit((pageNum - 1) * pageSize, pageSize)
                .fetch();
        return record4s;
    }

    @Override
    public int findByTieIdAndPageCount(String teacherName, String teacherJobNumber, int tieId) {
        Condition a = Tables.TEACHER.TIE_ID.eq(tieId);


        if (StringUtils.hasLength(teacherName)) {
            a = a.and(Tables.USERS.REAL_NAME.like("%" + teacherName + "%"));
        }

        if (StringUtils.hasLength(teacherJobNumber)) {
            a = a.and(Tables.TEACHER.TEACHER_JOB_NUMBER.like("%" + teacherJobNumber + "%"));
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.TEACHER)
                .leftJoin(Tables.USERS)
                .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return count.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Teacher teacher) {
        teacherDao.insert(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        teacherDao.update(teacher);
    }

    @Override
    public Result<Record2<Integer,String>> findByTieIdWithTeacherName(String teacherName,int tieId) {
        Condition a = Tables.TIE.ID.eq(tieId);
        if(StringUtils.hasLength(teacherName)){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+teacherName+"%"));
        }
        Result<Record2<Integer,String>> record2s = create.select(Tables.TEACHER.ID,Tables.USERS.REAL_NAME)
                .from(Tables.TEACHER)
                .join(Tables.TIE)
                .on(Tables.TEACHER.TIE_ID.eq(Tables.TIE.ID))
                .join(Tables.USERS)
                .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetch();
        return record2s;
    }
}
