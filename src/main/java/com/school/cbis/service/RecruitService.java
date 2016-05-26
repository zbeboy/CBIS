package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Recruit;
import com.school.cbis.domain.tables.records.RecruitRecord;
import com.school.cbis.vo.recruit.RecruitListVo;
import org.jooq.Record11;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-05-26.
 */
public interface RecruitService {

    /**
     * 通过系id查询
     * @param recruitListVo
     * @param tieId
     * @return
     */
    Result<Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String>> findByTieIdAndPage(RecruitListVo recruitListVo, int tieId);

    /**
     * 通过系id查询总数
     * @param recruitListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(RecruitListVo recruitListVo,int tieId);

    /**
     * 通过id删除
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    Recruit findById(int id);

    /**
     * 保存
     * @param recruit
     */
    void save(Recruit recruit);

    /**
     * 更新
     * @param recruit
     */
    void update(Recruit recruit);

    /**
     * 通过主键查找
     * @param id
     * @return
     */
    Record11<Integer, Integer, Timestamp, String, String, String, String, String, String, Timestamp, String> findByIdWithUser(int id);
}
