package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Major;
import com.school.cbis.vo.major.*;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lenovo on 2016-02-07.
 */
public interface MajorService {
    /**
     * 通过系参数分页查询
     *
     * @param majorVo 专业信息
     * @param tieId   系id
     * @return
     */
    Result<Record2<Integer, String>> findByTieIdByPage(MajorVo majorVo, int tieId);

    /**
     * 分页查询总数
     *
     * @param majorVo 专业信息
     * @param tieId   系id
     * @return
     */
    int findByTieIdCount(MajorVo majorVo, int tieId);

    /**
     * 保存专业信息
     *
     * @param major 专业信息
     */
    void saveMajor(Major major);

    /**
     * 更新专业信息
     *
     * @param major 专业信息
     */
    void update(Major major);

    /**
     * 通过id查询专业信息
     *
     * @param id 专业id
     * @return
     */
    Major findById(int id);

    /**
     * 通过id删除专业信息
     *
     * @param id 专业id
     */
    void deleteById(int id);

    /**
     * 通过专业名查询
     *
     * @param majorName 专业名
     * @return
     */
    List<Major> findByMajorName(String majorName);

    /**
     * 查询所有专业信息
     *
     * @return
     */
    List<Major> findAll();

    /**
     * 查询专业简介
     *
     * @param majorIntroduceVo
     * @param tieId
     * @return
     */
    Result<Record4<Integer, String, String, Timestamp>> findAllWithIntroduceByPage(MajorIntroduceVo majorIntroduceVo, int tieId);

    /**
     * 查询专业简介总数
     *
     * @param majorIntroduceVo
     * @param tieId
     * @return
     */
    int findAllWithIntroduceByPageCount(MajorIntroduceVo majorIntroduceVo, int tieId);

    /**
     * 查询专业带头人简介
     *
     * @param majorHeadVo
     * @param tieId
     * @return
     */
    Result<Record4<Integer, String, String, Timestamp>> findAllWithHeadByPage(MajorHeadVo majorHeadVo, int tieId);

    /**
     * 专业带头人简介总数
     *
     * @param majorHeadVo
     * @param tieId
     * @return
     */
    int findAllWithHeadByPageCount(MajorHeadVo majorHeadVo, int tieId);

    /**
     * 查询专业培养目标简介
     *
     * @param majorTrainingGoalVo
     * @param tieId
     * @return
     */
    Result<Record4<Integer, String, String, Timestamp>> findAllWithTrainingGoalByPage(MajorTrainingGoalVo majorTrainingGoalVo, int tieId);

    /**
     * 专业培养目标简介总数
     *
     * @param majorTrainingGoalVo
     * @param tieId
     * @return
     */
    int findAllWithTrainingGoalByPageCount(MajorTrainingGoalVo majorTrainingGoalVo, int tieId);

    /**
     * 查询专业特色简介
     *
     * @param majorTraitVo
     * @param tieId
     * @return
     */
    Result<Record4<Integer, String, String, Timestamp>> findAllWithTraitByPage(MajorTraitVo majorTraitVo, int tieId);

    /**
     * 专业特色简介总数
     *
     * @param majorTraitVo
     * @param tieId
     * @return
     */
    int findAllWithTraitByPageCount(MajorTraitVo majorTraitVo, int tieId);

    /**
     * 通过系id查询
     *
     * @param tieId
     * @return
     */
    List<Major> findByTieId(int tieId);

    /**
     * 系列表数据
     *
     * @param tieId
     * @return
     */
    Result<Record2<Integer, String>> findByTieIdToList(int tieId);

    /**
     * 通过系id和文章关联批量查询数据
     *
     * @param tieId
     * @return
     */
    Result<Record4<Integer, String, Integer, String>> findByTieIdWithArticleAndPage(int tieId, int pageNum, int pageSize);
}
