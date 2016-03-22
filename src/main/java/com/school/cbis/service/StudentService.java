package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Student;
import com.school.cbis.vo.users.StudentVo;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Result;

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

    /**
     * 通过系id查询学生
     *
     * @param tieId
     * @return
     */
    Result<Record6<Integer, String, String, Byte, String,String>> findByTieIdAndPage(StudentVo studentVo, int tieId);

    /**
     * 通过系id查询学生总数
     *
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(StudentVo studentVo, int tieId);

    /**
     * 保存学生数据
     * @param student
     */
    void save(Student student);
}
