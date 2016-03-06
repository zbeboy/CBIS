package com.school.cbis.service;

import com.school.cbis.domain.tables.daos.StudentDao;
import com.school.cbis.domain.tables.daos.TeacherDao;
import com.school.cbis.domain.tables.pojos.Student;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2016-03-01.
 */
@Service("studentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentServiceImpl implements StudentService {
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
}
