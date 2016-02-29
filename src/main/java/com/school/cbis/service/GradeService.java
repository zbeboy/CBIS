package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Grade;
import com.school.cbis.vo.grade.GradeVo;
import org.jooq.Record7;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-01-17.
 */
public interface GradeService {
    /**
     * 通过班级ID获取班级信息
     *
     * @param id 班级ID
     * @return 班级信息
     */
    Grade findById(int id);

    /**
     * 分页查询班级信息
     *
     * @param gradeVo 班级信息
     * @param tieId   系id
     * @return
     */
    Result<Record7<Integer, Integer, String, String, String, String, String>> findAllByPage(GradeVo gradeVo, int tieId);

    /**
     * 查询总数
     *
     * @param gradeVo 班级信息
     * @param tieId   系id
     * @return
     */
    int findAllByPageCount(GradeVo gradeVo, int tieId);

    /**
     * 保存
     *
     * @param grade 班级信息
     */
    void save(Grade grade);

    /**
     * 更新
     *
     * @param grade 班级信息
     */
    void update(Grade grade);

    /**
     * 通过班级名查询
     *
     * @param gradeName 班级名
     * @return
     */
    List<Grade> findByGradeName(String gradeName);

    /**
     * 通过id删除
     *
     * @param id
     */
    void deleteById(int id);
}
