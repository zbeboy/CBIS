package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.AutonomousPracticeHead;
import com.school.cbis.domain.tables.records.AutonomousPracticeHeadRecord;
import org.jooq.*;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public interface AutonomousPracticeHeadService {

    /**
     * 保存标题
     * @param autonomousPracticeHead
     */
    int save(AutonomousPracticeHead autonomousPracticeHead);

    /**
     * 通过自主实习模板表主键查询
     * @param autonomousPracticeTemplateId
     * @return
     */
    List<AutonomousPracticeHead> findByAutonomousPracticeTemplateId(int autonomousPracticeTemplateId);

    /**
     * 通过自主实习模板表主键删除
     * @param autonomousPracticeTemplateId
     */
    void deleteByAutonomousPracticeTemplateId(int autonomousPracticeTemplateId);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    AutonomousPracticeHead findById(int id);

    /**
     * 更新
     * @param autonomousPracticeHead
     */
    void update(AutonomousPracticeHead autonomousPracticeHead);

    /**
     * 通过模板id查询标题包括类型
     * @param autonomousPracticeTemplateId
     * @return
     */
    Result<Record14<Integer, String, String, String, String, String, String, String, Byte, String, Byte, Integer, Byte,Integer>> findByAutonomousPracticeTemplateIdWithHeadTypeId(int autonomousPracticeTemplateId);

    /**
     * 通过模板id和变量名查询
     * @param autonomousPracticeTemplateId
     * @param titleVariable
     * @return
     */
    AutonomousPracticeHeadRecord findByAutonomousPracticeTemplateIdAndTitleVariable(int autonomousPracticeTemplateId, String titleVariable);

    /**
     * 通过id删除标题
     * @param id
     */
    void deleteById(int id);

    /**
     * 根据标题信息表主键查询
     * @param autonomousPracticeInfoId
     * @return headId title
     */
    Result<Record2<Integer,String>> findByAutonomousPracticeInfoId(int autonomousPracticeInfoId);

    /**
     * 查询该模板对应的高效工作区标题
     * @param autonomousPracticeTemplateId
     * @param isShowHighlyActive
     * @return
     */
    Result<Record> findByAutonomousPracticeTemplateIdAndIsShowHighlyActive(int autonomousPracticeTemplateId,Byte isShowHighlyActive);
}
