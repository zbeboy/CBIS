package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Teacher;
import com.school.cbis.domain.tables.records.TeacherRecord;
import com.school.cbis.vo.users.TeacherVo;
import org.jooq.Record5;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-02-15.
 */
public interface TeacherService {
    /**
     * 通过系id和教师姓名模糊查询
     *
     * @param teacherName
     * @param tieId
     * @return
     */
    Result<TeacherRecord> findByTieIdAndTearchName(String teacherName, int tieId);

    /**
     * 通过教师工号查询
     *
     * @param teacherJobNumber
     * @return
     */
    List<Teacher> findByTeacherJobNumber(String teacherJobNumber);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Teacher findById(int id);

    /**
     * 通过系id查询教师
     *
     * @param tieId
     * @return
     */
    Result<Record5<Integer, String, String, Byte, String>> findByTieIdAndPage(TeacherVo teacherVo, int tieId);

    /**
     * 通过系id查询教师总数
     *
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(TeacherVo teacherVo, int tieId);

    /**
     * 保存教师信息
     *
     * @param teacher
     */
    void save(Teacher teacher);
}
