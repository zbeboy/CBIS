package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.ClassroomCourseTimetableInfoDao;
import com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo;
import com.school.cbis.domain.tables.records.ClassroomCourseTimetableInfoRecord;
import com.school.cbis.vo.eadmin.ClassroomTimetableListVo;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by lenovo on 2016-06-06.
 */
@Service("classroomCourseTimetableInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ClassroomCourseTimetableInfoServiceImpl implements ClassroomCourseTimetableInfoService {

    private final Logger log = LoggerFactory.getLogger(ClassroomCourseTimetableInfoServiceImpl.class);

    private final DSLContext create;

    private ClassroomCourseTimetableInfoDao classroomCourseTimetableInfoDao;

    @Autowired
    public ClassroomCourseTimetableInfoServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.classroomCourseTimetableInfoDao = new ClassroomCourseTimetableInfoDao(configuration);
    }

    @Override
    public Result<ClassroomCourseTimetableInfoRecord> findByTieIdAndTeachTypeIdAndPage(ClassroomTimetableListVo classroomTimetableListVo, int tieId) {
        Condition a = Tables.CLASSROOM_COURSE_TIMETABLE_INFO.TIE_ID.eq((tieId)).and(Tables.CLASSROOM_COURSE_TIMETABLE_INFO.TEACH_TYPE_ID.eq(classroomTimetableListVo.getTeachTypeId()));

        if(StringUtils.hasLength(classroomTimetableListVo.getTimetableInfoFileName())){
            a = a.and(Tables.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME.like("%"+classroomTimetableListVo.getTimetableInfoFileName()+"%"));
        }

        if(StringUtils.hasLength(classroomTimetableListVo.getTimetableInfoTerm())){
            a = a.and(Tables.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM.like("%"+classroomTimetableListVo.getTimetableInfoTerm()+"%"));
        }

        if(StringUtils.hasLength(classroomTimetableListVo.getClassroom())){
            a = a.and(Tables.CLASSROOM_COURSE_TIMETABLE_INFO.CLASSROOM.like("%"+classroomTimetableListVo.getClassroom()+"%"));
        }

        int pageNum = classroomTimetableListVo.getPageNum();
        int pageSize = classroomTimetableListVo.getPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }

        Result<ClassroomCourseTimetableInfoRecord> record14s =
                create.selectFrom(Tables.CLASSROOM_COURSE_TIMETABLE_INFO)
                .where(a)
                .orderBy(Tables.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DATE.desc())
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return record14s;
    }

    @Override
    public int findByTieIdAndTeachTypeIdAndPageCount(ClassroomTimetableListVo classroomTimetableListVo, int tieId) {
        Condition a = Tables.CLASSROOM_COURSE_TIMETABLE_INFO.TIE_ID.eq((tieId)).and(Tables.CLASSROOM_COURSE_TIMETABLE_INFO.TEACH_TYPE_ID.eq(classroomTimetableListVo.getTeachTypeId()));

        if(StringUtils.hasLength(classroomTimetableListVo.getTimetableInfoFileName())){
            a = a.and(Tables.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME.like("%"+classroomTimetableListVo.getTimetableInfoFileName()+"%"));
        }

        if(StringUtils.hasLength(classroomTimetableListVo.getTimetableInfoTerm())){
            a = a.and(Tables.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM.like("%"+classroomTimetableListVo.getTimetableInfoTerm()+"%"));
        }

        if(StringUtils.hasLength(classroomTimetableListVo.getClassroom())){
            a = a.and(Tables.CLASSROOM_COURSE_TIMETABLE_INFO.CLASSROOM.like("%"+classroomTimetableListVo.getClassroom()+"%"));
        }
        Record1<Integer> record1 = create.selectCount()
                .from(Tables.CLASSROOM_COURSE_TIMETABLE_INFO)
                .where(a)
                .fetchOne();
        return record1.value1();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(ClassroomCourseTimetableInfo classroomCourseTimetableInfo) {
        classroomCourseTimetableInfoDao.insert(classroomCourseTimetableInfo);
    }

    @Override
    public void update(ClassroomCourseTimetableInfo classroomCourseTimetableInfo) {
        classroomCourseTimetableInfoDao.update(classroomCourseTimetableInfo);
    }

    @Override
    public ClassroomCourseTimetableInfo findById(int id) {
        ClassroomCourseTimetableInfo classroomCourseTimetableInfo = classroomCourseTimetableInfoDao.findById(id);
        return classroomCourseTimetableInfo;
    }

    @Override
    public void deleteById(int id) {
        classroomCourseTimetableInfoDao.deleteById(id);
    }
}
