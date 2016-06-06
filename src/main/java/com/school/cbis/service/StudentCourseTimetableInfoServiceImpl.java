package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.StudentCourseTimetableInfoDao;
import com.school.cbis.domain.tables.daos.StudentDao;
import com.school.cbis.domain.tables.pojos.StudentCourseTimetableInfo;
import com.school.cbis.vo.eadmin.StudentTimetableListVo;
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

/**
 * Created by lenovo on 2016-06-06.
 */
@Service("studentCourseTimetableInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentCourseTimetableInfoServiceImpl implements StudentCourseTimetableInfoService {

    private final Logger log = LoggerFactory.getLogger(StudentCourseTimetableInfoServiceImpl.class);

    private final DSLContext create;

    private StudentCourseTimetableInfoDao studentCourseTimetableInfoDao;

    @Autowired
    public StudentCourseTimetableInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.studentCourseTimetableInfoDao = new StudentCourseTimetableInfoDao(configuration);
    }

    @Override
    public Result<Record14<Integer, String, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date, String, String>> findByTieIdAndPage(StudentTimetableListVo studentTimetableListVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if(StringUtils.hasLength(studentTimetableListVo.getTimetableInfoFileName())){
            a = a.and(Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME.like("%"+studentTimetableListVo.getTimetableInfoFileName()+"%"));
        }

        if(StringUtils.hasLength(studentTimetableListVo.getTimetableInfoTerm())){
            a = a.and(Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM.like("%"+studentTimetableListVo.getTimetableInfoTerm()+"%"));
        }

        if(StringUtils.hasLength(studentTimetableListVo.getGradeName())){
            a = a.and(Tables.GRADE.GRADE_NAME.like("%"+studentTimetableListVo.getGradeName()+"%"));
        }

        int pageNum = studentTimetableListVo.getPageNum();
        int pageSize = studentTimetableListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<Record14<Integer, String, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date, String, String>> record14s = create.select(Tables.STUDENT_COURSE_TIMETABLE_INFO.ID,Tables.GRADE.GRADE_NAME,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_URL,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_PDF,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_SIZE,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DATE,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DOWN_TIMES,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TEACH_TYPE_ID,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TERM_START_TIME,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.TERM_END_TIME,
                Tables.USERS.REAL_NAME,
                Tables.STUDENT_COURSE_TIMETABLE_INFO.FILE_TYPE)
                .from(Tables.STUDENT_COURSE_TIMETABLE_INFO)
                .join(Tables.GRADE)
                .on(Tables.STUDENT_COURSE_TIMETABLE_INFO.GRADE_ID.eq(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT_COURSE_TIMETABLE_INFO.FILE_USER.eq(Tables.USERS.USERNAME))
                .where(a)
                .orderBy(Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DATE.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return record14s;
    }

    @Override
    public int findByTieIdAndPageCount(StudentTimetableListVo studentTimetableListVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if(StringUtils.hasLength(studentTimetableListVo.getTimetableInfoFileName())){
            a = a.and(Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME.like("%"+studentTimetableListVo.getTimetableInfoFileName()+"%"));
        }

        if(StringUtils.hasLength(studentTimetableListVo.getTimetableInfoTerm())){
            a = a.and(Tables.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM.like("%"+studentTimetableListVo.getTimetableInfoTerm()+"%"));
        }

        if(StringUtils.hasLength(studentTimetableListVo.getGradeName())){
            a = a.and(Tables.GRADE.GRADE_NAME.like("%"+studentTimetableListVo.getGradeName()+"%"));
        }
        Record1<Integer> record1 = create.selectCount()
                .from(Tables.STUDENT_COURSE_TIMETABLE_INFO)
                .join(Tables.GRADE)
                .on(Tables.STUDENT_COURSE_TIMETABLE_INFO.GRADE_ID.eq(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT_COURSE_TIMETABLE_INFO.FILE_USER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(StudentCourseTimetableInfo studentCourseTimetableInfo) {
        studentCourseTimetableInfoDao.insert(studentCourseTimetableInfo);
    }

    @Override
    public StudentCourseTimetableInfo findById(int id) {
        StudentCourseTimetableInfo studentCourseTimetableInfo = studentCourseTimetableInfoDao.findById(id);
        return studentCourseTimetableInfo;
    }

    @Override
    public void update(StudentCourseTimetableInfo studentCourseTimetableInfo) {
        studentCourseTimetableInfoDao.update(studentCourseTimetableInfo);
    }

    @Override
    public void deleteById(int id) {
        studentCourseTimetableInfoDao.deleteById(id);
    }
}
