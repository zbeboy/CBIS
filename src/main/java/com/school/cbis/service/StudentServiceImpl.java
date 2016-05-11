package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.StudentDao;
import com.school.cbis.domain.tables.pojos.Student;
import com.school.cbis.vo.autonomicpractice.AutonomousPracticeParamVo;
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
 * Created by lenovo on 2016-03-01.
 */
@Service("studentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final DSLContext create;

    private StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.studentDao = new StudentDao(configuration);
    }

    @Override
    public List<Student> findByStudentNumber(String studentNumber) {
        List<Student> students = studentDao.fetchByStudentNumber(studentNumber);
        return students;
    }

    @Override
    public Result<Record5<Integer, String, String, Byte, String>> findByTieIdAndPage(String studentName, String studentNumber, int pageNum, int pageSize, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if (StringUtils.hasLength(studentName)) {
            a = a.and(Tables.STUDENT.STUDENT_NAME.like("%" + studentName + "%"));
        }

        if (StringUtils.hasLength(studentNumber)) {
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%" + studentNumber + "%"));
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }

        Result<Record5<Integer, String, String, Byte, String>> record5s = create.select(Tables.STUDENT.ID,
                Tables.STUDENT.STUDENT_NAME, Tables.STUDENT.STUDENT_NUMBER, Tables.USERS.ENABLED,
                Tables.GRADE.GRADE_NAME)
                .from(Tables.STUDENT)
                .leftJoin(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .leftJoin(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .leftJoin(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .where(a)
                .limit((pageNum - 1) * pageSize, pageSize)
                .fetch();
        return record5s;
    }

    @Override
    public int findByTieIdAndPageCount(String studentName, String studentNumber, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if (StringUtils.hasLength(studentName)) {
            a = a.and(Tables.STUDENT.STUDENT_NAME.like("%" + studentName + "%"));
        }

        if (StringUtils.hasLength(studentNumber)) {
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%" + studentNumber + "%"));
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.STUDENT)
                .leftJoin(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .leftJoin(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .leftJoin(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .where(a)
                .fetchOne();
        return count.value1();
    }

    @Override
    public void save(Student student) {
        studentDao.insert(student);
    }

    @Override
    public Record findById(int id) {
        Record record = create.select()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.TIE)
                .on(Tables.MAJOR.TIE_ID.equal(Tables.TIE.ID))
                .join(Tables.YARD)
                .on(Tables.TIE.YARD_ID.equal(Tables.YARD.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(Tables.STUDENT.ID.eq(id))
                .fetchOne();
        return record;
    }

    @Override
    public int findByYearAndTieIdCount(String year, int tieId) {
        Record1<Integer> count = create.selectCount()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(Tables.GRADE.YEAR.eq(year).and(Tables.MAJOR.TIE_ID.eq(tieId)))
                .fetchOne();
        return count.value1();
    }

    @Override
    public int findByGradeIdAndTieIdCount(int gradeId) {
        Record1<Integer> count = create.selectCount()
                .from(Tables.STUDENT)
                .where(Tables.STUDENT.GRADE_ID.eq(gradeId))
                .fetchOne();
        return count.value1();
    }

    @Override
    public Result<Record4<Integer,String,String,String>> findByYearAndTieIdInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.in(studentId));
        if(autonomousPracticeParamVo.getType() == 0&&StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        int pageNum = autonomousPracticeParamVo.getHavePayPageNum();
        int pageSize = autonomousPracticeParamVo.getHavePayPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }
        Result<Record4<Integer,String,String,String>> records = create.select(Tables.STUDENT.ID,Tables.STUDENT.STUDENT_NUMBER,Tables.STUDENT.STUDENT_NAME,Tables.GRADE.GRADE_NAME)
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return records;
    }

    @Override
    public Result<Record4<Integer,String,String,String>> findByYearAndTieIdNotInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.notIn(studentId));
        if(autonomousPracticeParamVo.getType() == 1 && StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        int pageNum = autonomousPracticeParamVo.getHaveNoPayPageNum();
        int pageSize = autonomousPracticeParamVo.getHaveNoPayPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }
        Result<Record4<Integer,String,String,String>> records = create.select(Tables.STUDENT.ID,Tables.STUDENT.STUDENT_NUMBER,Tables.STUDENT.STUDENT_NAME,Tables.GRADE.GRADE_NAME)
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return records;
    }

    @Override
    public int findByYearAndTieIdInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.in(studentId));
        if(autonomousPracticeParamVo.getType() == 0&&StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        Record1<Integer> count = create.selectCount()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return count.value1();
    }

    @Override
    public int findByYearAndTieIdNotInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.notIn(studentId));
        if(autonomousPracticeParamVo.getType() == 1 && StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
       Record1<Integer> count =  create.selectCount()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return count.value1();
    }

    @Override
    public int findByMajorIdCount(int majorId,String year) {
        Record1<Integer> count = create.selectCount()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .where(Tables.GRADE.MAJOR_ID.eq(majorId).and(Tables.GRADE.YEAR.eq(year)))
                .fetchOne();
        return count.value1();
    }

    @Override
    public Result<Record4<Integer, String, String, String>> findByMajorIdAndTieIdAndYearInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.MAJOR_ID.eq(autonomousPracticeParamVo.getMajorId()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.in(studentId).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        if(autonomousPracticeParamVo.getType() == 0&&StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        int pageNum = autonomousPracticeParamVo.getHavePayPageNum();
        int pageSize = autonomousPracticeParamVo.getHavePayPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }
        Result<Record4<Integer,String,String,String>> records = create.select(Tables.STUDENT.ID,Tables.STUDENT.STUDENT_NUMBER,Tables.STUDENT.STUDENT_NAME,Tables.GRADE.GRADE_NAME)
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return records;
    }

    @Override
    public Result<Record4<Integer, String, String, String>> findByMajorIdAndTieIdAndYearNotInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.MAJOR_ID.eq(autonomousPracticeParamVo.getMajorId()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.notIn(studentId).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        if(autonomousPracticeParamVo.getType() == 1 && StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        int pageNum = autonomousPracticeParamVo.getHaveNoPayPageNum();
        int pageSize = autonomousPracticeParamVo.getHaveNoPayPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }
        Result<Record4<Integer,String,String,String>> records = create.select(Tables.STUDENT.ID,Tables.STUDENT.STUDENT_NUMBER,Tables.STUDENT.STUDENT_NAME,Tables.GRADE.GRADE_NAME)
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .limit((pageNum-1)*pageSize,pageSize)
                .fetch();
        return records;
    }

    @Override
    public int findByMajorIdAndTieIdAndYearInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.MAJOR_ID.eq(autonomousPracticeParamVo.getMajorId()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.in(studentId).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        if(autonomousPracticeParamVo.getType() == 0&&StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        Record1<Integer> count = create.selectCount()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return count.value1();
    }

    @Override
    public int findByMajorIdAndTieIdAndYearNotInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.MAJOR_ID.eq(autonomousPracticeParamVo.getMajorId()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.notIn(studentId).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        if(autonomousPracticeParamVo.getType() == 1 && StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        Record1<Integer> count =  create.selectCount()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return count.value1();
    }

    @Override
    public int findByGradeIdCount(int gradeId) {
        Record1<Integer> count = create.selectCount()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .where(Tables.GRADE.ID.eq(gradeId))
                .fetchOne();
        return count.value1();
    }

    @Override
    public Result<Record4<Integer, String, String, String>> findByGradeIdIdAndTieIdAndYearInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.ID.eq(autonomousPracticeParamVo.getGradeId()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.in(studentId).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        if(autonomousPracticeParamVo.getType() == 0&&StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        int pageNum = autonomousPracticeParamVo.getHavePayPageNum();
        int pageSize = autonomousPracticeParamVo.getHavePayPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }
        Result<Record4<Integer,String,String,String>> records = create.select(Tables.STUDENT.ID,Tables.STUDENT.STUDENT_NUMBER,Tables.STUDENT.STUDENT_NAME,Tables.GRADE.GRADE_NAME)
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetch();
        return records;
    }

    @Override
    public Result<Record4<Integer, String, String, String>> findByGradeIdAndTieIdAndYearNotInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.ID.eq(autonomousPracticeParamVo.getGradeId()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.notIn(studentId).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        if(autonomousPracticeParamVo.getType() == 1 && StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        int pageNum = autonomousPracticeParamVo.getHaveNoPayPageNum();
        int pageSize = autonomousPracticeParamVo.getHaveNoPayPageSize();
        if(pageNum<=0){
            pageNum = 1;
        }
        Result<Record4<Integer,String,String,String>> records = create.select(Tables.STUDENT.ID,Tables.STUDENT.STUDENT_NUMBER,Tables.STUDENT.STUDENT_NAME,Tables.GRADE.GRADE_NAME)
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetch();
        return records;
    }

    @Override
    public int findByGradeIdIdAndTieIdAndYearInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.ID.eq(autonomousPracticeParamVo.getGradeId()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.in(studentId).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        if(autonomousPracticeParamVo.getType() == 0&&StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        Record1<Integer> count = create.selectCount()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return count.value1();
    }

    @Override
    public int findByGradeIdAndTieIdAndYearNotInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId) {
        Condition a = Tables.GRADE.ID.eq(autonomousPracticeParamVo.getGradeId()).and(Tables.MAJOR.TIE_ID.eq(tieId)).and(Tables.STUDENT.ID.notIn(studentId).and(Tables.GRADE.YEAR.eq(autonomousPracticeParamVo.getYear())));
        if(autonomousPracticeParamVo.getType() == 1 && StringUtils.hasLength(autonomousPracticeParamVo.getStudentNumber())){
            a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%"+autonomousPracticeParamVo.getStudentNumber()+"%"));
        }
        Record1<Integer> count =  create.selectCount()
                .from(Tables.STUDENT)
                .join(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.equal(Tables.GRADE.ID))
                .join(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.equal(Tables.MAJOR.ID))
                .join(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .where(a)
                .fetchOne();
        return count.value1();
    }
}
