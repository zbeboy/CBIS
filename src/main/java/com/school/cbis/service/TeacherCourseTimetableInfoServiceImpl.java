package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.StudentDao;
import com.school.cbis.domain.tables.daos.TeacherCourseTimetableInfoDao;
import com.school.cbis.domain.tables.pojos.Student;
import com.school.cbis.domain.tables.pojos.TeacherCourseTimetableInfo;
import com.school.cbis.vo.eadmin.TeacherTimetableListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lenovo on 2016-06-06.
 */
@Service("teacherCourseTimetableInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeacherCourseTimetableInfoServiceImpl implements TeacherCourseTimetableInfoService {

    private final Logger log = LoggerFactory.getLogger(TeacherCourseTimetableInfoServiceImpl.class);

    private final DSLContext create;

    private TeacherCourseTimetableInfoDao teacherCourseTimetableInfoDao;

    @Autowired
    public TeacherCourseTimetableInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.teacherCourseTimetableInfoDao = new TeacherCourseTimetableInfoDao(configuration);
    }

    @Override
    public Result<Record14<Integer, String, String, String,String, String, Timestamp, Integer, Integer, Date, Date, String, String, String>> findByTieIdAndPage(TeacherTimetableListVo teacherTimetableListVo, int tieId) {
        Condition a = Tables.TEACHER_COURSE_TIMETABLE_INFO.TIE_ID.eq((tieId));

        if(StringUtils.hasLength(teacherTimetableListVo.getTimetableInfoFileName())){
            a = a.and(Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME.like("%"+teacherTimetableListVo.getTimetableInfoFileName()+"%"));
        }

        if(StringUtils.hasLength(teacherTimetableListVo.getTimetableInfoTerm())){
            a = a.and(Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM.like("%"+teacherTimetableListVo.getTimetableInfoTerm()+"%"));
        }

        if(StringUtils.hasLength(teacherTimetableListVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+teacherTimetableListVo.getRealName()+"%"));
        }

        int pageNum = teacherTimetableListVo.getPageNum();
        int pageSize = teacherTimetableListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record14<Integer, String, String, String,String, String, Timestamp, Integer, Integer, Date, Date, String, String, String>> record14s = create.select(Tables.TEACHER_COURSE_TIMETABLE_INFO.ID,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_URL,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_PDF,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_SIZE,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DATE,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DOWN_TIMES,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TEACH_TYPE_ID,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TERM_START_TIME,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.TERM_END_TIME,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.FILE_USER,
                Tables.TEACHER_COURSE_TIMETABLE_INFO.FILE_TYPE,
                Tables.USERS.REAL_NAME)
                .from(Tables.TEACHER_COURSE_TIMETABLE_INFO)
                .join(Tables.TEACHER)
                .on(Tables.TEACHER_COURSE_TIMETABLE_INFO.TEACHER_ID.eq(Tables.TEACHER.ID))
                .join(Tables.USERS)
                .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DATE.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return record14s;
    }

    @Override
    public int findByTieIdAndPageCount(TeacherTimetableListVo teacherTimetableListVo, int tieId) {
        Condition a = Tables.TEACHER_COURSE_TIMETABLE_INFO.TIE_ID.eq((tieId));

        if(StringUtils.hasLength(teacherTimetableListVo.getTimetableInfoFileName())){
            a = a.and(Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME.like("%"+teacherTimetableListVo.getTimetableInfoFileName()+"%"));
        }

        if(StringUtils.hasLength(teacherTimetableListVo.getTimetableInfoTerm())){
            a = a.and(Tables.TEACHER_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM.like("%"+teacherTimetableListVo.getTimetableInfoTerm()+"%"));
        }

        if(StringUtils.hasLength(teacherTimetableListVo.getRealName())){
            a = a.and(Tables.USERS.REAL_NAME.like("%"+teacherTimetableListVo.getRealName()+"%"));
        }

        Record1<Integer> record1 = create.selectCount()
                .from(Tables.TEACHER_COURSE_TIMETABLE_INFO)
                .join(Tables.TEACHER)
                .on(Tables.TEACHER_COURSE_TIMETABLE_INFO.TEACHER_ID.eq(Tables.TEACHER.ID))
                .join(Tables.USERS)
                .on(Tables.TEACHER.TEACHER_JOB_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(TeacherCourseTimetableInfo teacherCourseTimetableInfo) {
        teacherCourseTimetableInfoDao.insert(teacherCourseTimetableInfo);
    }

    @Override
    public void update(TeacherCourseTimetableInfo teacherCourseTimetableInfo) {
        teacherCourseTimetableInfoDao.update(teacherCourseTimetableInfo);
    }

    @Override
    public TeacherCourseTimetableInfo findById(int id) {
        TeacherCourseTimetableInfo teacherCourseTimetableInfo = teacherCourseTimetableInfoDao.findById(id);
        return teacherCourseTimetableInfo;
    }

    @Override
    public void deleteById(int id) {
        teacherCourseTimetableInfoDao.deleteById(id);
    }
}
