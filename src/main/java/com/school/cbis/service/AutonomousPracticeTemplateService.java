package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.AutonomousPracticeTemplate;
import com.school.cbis.vo.autonomicpractice.TemplateVo;
import org.jooq.Record4;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public interface AutonomousPracticeTemplateService {

    /**
     * 根据系id查询
     *
     * @return
     */
    List<AutonomousPracticeTemplate> findAllByTieId(int tieId);

    /**
     * 通过系id分页查询
     * @param templateVo
     * @return
     */
    Result<Record4<Integer,String,Timestamp,String>> findAllAndPage(TemplateVo templateVo,int tieId);

    /**
     * 查询总数
     * @param templateVo
     * @return
     */
    int findAllAndCount(TemplateVo templateVo,int tieId);

    /**
     * 通过主键删除
     * @param id
     */
    void deleteById(int id);

    /**
     * 保存
     * @param autonomousPracticeTemplate
     */
    int save(AutonomousPracticeTemplate autonomousPracticeTemplate);
}
