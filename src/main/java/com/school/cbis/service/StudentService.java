package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Student;
import com.school.cbis.vo.autonomicpractice.AutonomousPracticeParamVo;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-03-01.
 */
public interface StudentService {

    /**
     * 通过学生号查询
     *
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
    Result<Record5<Integer, String, String, Byte, String>> findByTieIdAndPage(String studentName, String studentNumber, int pageNum, int pageSize, int tieId);

    /**
     * 通过系id查询学生总数
     *
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(String studentName, String studentNumber, int tieId);

    /**
     * 保存学生数据
     *
     * @param student
     */
    void save(Student student);

    /**
     * 根据id查询学生信息 包括班级信息
     * @param id
     * @return
     */
    Record findById(int id);

    /**
     * 通过年级，系id，查询该系下，该年级有多少学生
     * @param year
     * @param tieId
     * @return
     */
    int findByYearAndTieIdCount(String year,int tieId);

    /**
     * 通过班级id，系id，查询该系下，该班级有多少学生
     * @param gradeId
     * @return
     */
    int findByGradeIdAndTieIdCount(int gradeId);

    /**
     * 查询自主实习已提交学生信息
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    Result<Record4<Integer,String,String,String>> findByYearAndTieIdInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId);

    /**
     * 查询自主实习未提交学生信息
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    Result<Record4<Integer,String,String,String>> findByYearAndTieIdNotInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo,int tieId,List<Integer> studentId);

    /**
     * 查询自主实习已提交学生信息总数
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    int findByYearAndTieIdInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId);
    /**
     * 查询自主实习未提交学生信息总数
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    int findByYearAndTieIdNotInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo,int tieId,List<Integer> studentId);

    /**
     * 查询该专业 该年级下有多少学生
     * @param majorId
     * @param year
     * @return
     */
    int findByMajorIdCount(int majorId,String year);

    /**
     * 查询自主实习已提交学生信息  专业用
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    Result<Record4<Integer,String,String,String>> findByMajorIdAndTieIdAndYearInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId);

    /**
     * 查询自主实习未提交学生信息 专业用
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    Result<Record4<Integer,String,String,String>> findByMajorIdAndTieIdAndYearNotInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo,int tieId,List<Integer> studentId);

    /**
     * 查询自主实习已提交学生信息总数 专业用
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    int findByMajorIdAndTieIdAndYearInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId);
    /**
     * 查询自主实习未提交学生信息总数 专业用
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    int findByMajorIdAndTieIdAndYearNotInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo,int tieId,List<Integer> studentId);

    /**
     * 查询该班级下有多少学生
     * @param gradeId
     * @return
     */
    int findByGradeIdCount(int gradeId);

    /**
     * 查询自主实习已提交学生信息  班级用
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    Result<Record4<Integer,String,String,String>> findByGradeIdIdAndTieIdAndYearInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId);

    /**
     * 查询自主实习未提交学生信息 班级用
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    Result<Record4<Integer,String,String,String>> findByGradeIdAndTieIdAndYearNotInStudentId(AutonomousPracticeParamVo autonomousPracticeParamVo,int tieId,List<Integer> studentId);

    /**
     * 查询自主实习已提交学生信息总数 班级用
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    int findByGradeIdIdAndTieIdAndYearInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo, int tieId, List<Integer> studentId);
    /**
     * 查询自主实习未提交学生信息总数 班级用
     * @param autonomousPracticeParamVo
     * @param tieId
     * @param studentId
     * @return
     */
    int findByGradeIdAndTieIdAndYearNotInStudentIdCount(AutonomousPracticeParamVo autonomousPracticeParamVo,int tieId,List<Integer> studentId);
}
