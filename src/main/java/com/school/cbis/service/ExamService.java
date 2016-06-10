package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Exam;
import com.school.cbis.vo.exam.ExamListVo;
import org.jooq.Record11;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by guipeng on 2016/6/8.
 */
public interface ExamService {

    /**
     * 通过系id查询
     * @param examListVo
     * @param tieId
     * @return
     */
    Result<Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String>> findByTieIdAndPage(ExamListVo examListVo, int tieId);

    /**
     * 通过系id查询总数
     * @param examListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(ExamListVo examListVo, int tieId);

    /**
     * 通过id删除
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    Exam findById(int id);

    /**
     * 保存
     * @param exam
     */
    void save(Exam exam);

    /**
     * 更新
     * @param exam
     */
    void update(Exam exam);

    /**
     * 通过主键查找
     * @param id
     * @return
     */
    Record11<Integer, Integer, Timestamp, String, String, String, Integer, String, String, Timestamp, String> findByIdWithUserAndMajor(int id);
}
