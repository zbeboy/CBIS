package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Grade;
import com.school.cbis.domain.tables.records.GradeRecord;
import com.school.cbis.vo.grade.GradeVo;
import org.jooq.*;

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
    Result<Record6<Integer, Integer, String, String, String, String>> findAllByPage(GradeVo gradeVo, int tieId);

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
     * 检验班级名是否存在使用，注意是查询该id以外的班级名
     *
     * @param id
     * @param gradeName
     * @return
     */
    List<GradeRecord> findByGradeNameAndId(int id, String gradeName);

    /**
     * 根据班级名查询
     *
     * @param gradeName
     * @return
     */
    List<Grade> findByGradeName(String gradeName);

    /**
     * 通过id删除
     *
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询根据系id
     * @param tieId
     * @return
     */
    Result<Record2<Integer,String>> findByTieId(int tieId);
}
