package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate;
import com.school.cbis.domain.tables.pojos.TeachingMaterialTemplate;
import com.school.cbis.domain.tables.records.TeacherFillTaskTemplateRecord;
import com.school.cbis.domain.tables.records.TeachingMaterialTemplateRecord;
import com.school.cbis.vo.eadmin.TeacherFillTemplateListVo;
import com.school.cbis.vo.eadmin.TeachingMaterialTemplateListVo;
import org.jooq.Record5;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-04.
 */
public interface TeachingMaterialTemplateService {

    /**
     * 根据系id分页查询全部
     * @param teachingMaterialTemplateListVo
     * @param tieId
     * @return
     */
    Result<Record5<Integer,String,Timestamp,String,String>> findByTieIdAndPage(TeachingMaterialTemplateListVo teachingMaterialTemplateListVo, int tieId);

    /**
     * 根据系id分页查询全部总数
     * @param teachingMaterialTemplateListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(TeachingMaterialTemplateListVo teachingMaterialTemplateListVo,int tieId);

    /**
     * 根据系表主键和模板名查询
     * @param tieId
     * @param title
     * @return
     */
    Result<TeachingMaterialTemplateRecord> findByTieIdAndTitle(int tieId, String title);

    /**
     * 根据主键,系表主键和模板名查询 注:不等于主键
     * @param tieId
     * @param title
     * @param id
     * @return
     */
    Result<TeachingMaterialTemplateRecord> findByTieIdAndTitleAndNeId(int tieId,String title,int id);

    /**
     * 保存后返回id
     * @param teachingMaterialTemplate
     * @return
     */
    int saveAndReturnId(TeachingMaterialTemplate teachingMaterialTemplate);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    TeachingMaterialTemplate findById(int id);

    /**
     * 更新
     * @param teachingMaterialTemplate
     */
    void update(TeachingMaterialTemplate teachingMaterialTemplate);

    /**
     * 通过系主键查询
     * @param tieId
     * @return
     */
    Result<TeachingMaterialTemplateRecord> findByTieId(int tieId);
}
