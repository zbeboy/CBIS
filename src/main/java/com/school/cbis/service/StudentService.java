package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Student;

import java.util.List;

/**
 * Created by lenovo on 2016-03-01.
 */
public interface StudentService {

    /**
     * 通过学生号查询
     * @param studentNumber
     * @return
     */
    List<Student> findByStudentNumber(String studentNumber);
}
