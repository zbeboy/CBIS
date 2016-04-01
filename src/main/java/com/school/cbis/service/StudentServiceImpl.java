package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.StudentDao;
import com.school.cbis.domain.tables.pojos.Student;
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
}
