package com.school.cbis.service;

import com.school.cbis.domain.Tables;
import com.school.cbis.domain.tables.daos.StudentDao;
import com.school.cbis.domain.tables.daos.TeacherDao;
import com.school.cbis.domain.tables.pojos.Student;
import com.school.cbis.vo.users.StudentVo;
import org.apache.log4j.Logger;
import org.jooq.*;
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

    private static Logger logger = Logger.getLogger(StudentServiceImpl.class);

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
    public Result<Record6<Integer, String, String, Byte, String,String>> findByTieIdAndPage(StudentVo studentVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if (!StringUtils.isEmpty(studentVo)) {
            if (StringUtils.hasLength(studentVo.getStudentName())) {
                a = a.and(Tables.STUDENT.STUDENT_NAME.like("%" + studentVo.getStudentName() + "%"));
            }

            if (StringUtils.hasLength(studentVo.getStudentNumber())) {
                a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%" + studentVo.getStudentNumber() + "%"));
            }
        }
        int pageNum = studentVo.getPageNum();
        if (pageNum <= 0) {
            pageNum = 1;
        }

        Result<Record6<Integer, String, String, Byte, String,String>> record6s = create.select(Tables.STUDENT.ID,
                Tables.STUDENT.STUDENT_NAME, Tables.STUDENT.STUDENT_NUMBER, Tables.USERS.ENABLED,
                Tables.AUTHORITIES.AUTHORITY,Tables.GRADE.GRADE_NAME)
                .from(Tables.STUDENT)
                .leftJoin(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .leftJoin(Tables.AUTHORITIES)
                .on(Tables.USERS.USERNAME.eq(Tables.AUTHORITIES.USERNAME))
                .leftJoin(Tables.GRADE)
                .on(Tables.STUDENT.GRADE_ID.eq(Tables.GRADE.ID))
                .leftJoin(Tables.MAJOR)
                .on(Tables.GRADE.MAJOR_ID.eq(Tables.MAJOR.ID))
                .where(a)
                .limit((pageNum - 1) * studentVo.getPageSize(), studentVo.getPageSize())
                .fetch();
        return record6s;
    }

    @Override
    public int findByTieIdAndPageCount(StudentVo studentVo, int tieId) {
        Condition a = Tables.MAJOR.TIE_ID.eq(tieId);

        if (!StringUtils.isEmpty(studentVo)) {
            if (StringUtils.hasLength(studentVo.getStudentName())) {
                a = a.and(Tables.STUDENT.STUDENT_NAME.like("%" + studentVo.getStudentName() + "%"));
            }

            if (StringUtils.hasLength(studentVo.getStudentNumber())) {
                a = a.and(Tables.STUDENT.STUDENT_NUMBER.like("%" + studentVo.getStudentNumber() + "%"));
            }
        }

        Record1<Integer> count = create.selectCount()
                .from(Tables.STUDENT)
                .leftJoin(Tables.USERS)
                .on(Tables.STUDENT.STUDENT_NUMBER.eq(Tables.USERS.USERNAME))
                .leftJoin(Tables.AUTHORITIES)
                .on(Tables.USERS.USERNAME.eq(Tables.AUTHORITIES.USERNAME))
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
