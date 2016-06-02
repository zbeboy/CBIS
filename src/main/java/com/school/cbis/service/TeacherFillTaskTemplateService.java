package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate;
import com.school.cbis.domain.tables.records.TeacherFillTaskTemplateRecord;
import com.school.cbis.vo.eadmin.TeacherFillTemplateListVo;
import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lenovo on 2016-06-02.
 */
public interface TeacherFillTaskTemplateService {

    /**
     * 根据系id分页查询全部
     * @param teacherFillTemplateListVo
     * @param tieId
     * @return
     */
    Result<Record5<Integer,String,Timestamp,String,String>> findByTieIdAndPage(TeacherFillTemplateListVo teacherFillTemplateListVo,int tieId);

    /**
     * 根据系id分页查询全部总数
     * @param teacherFillTemplateListVo
     * @param tieId
     * @return
     */
    int findByTieIdAndPageCount(TeacherFillTemplateListVo teacherFillTemplateListVo,int tieId);

    /**
     * 根据系表主键和模板名查询
     * @param tieId
     * @param title
     * @return
     */
    Result<TeacherFillTaskTemplateRecord> findByTieIdAndTitle(int tieId,String title);

    /**
     * 根据主键,系表主键和模板名查询 注:不等于主键
     * @param tieId
     * @param title
     * @param id
     * @return
     */
    Result<TeacherFillTaskTemplateRecord> findByTieIdAndTitleAndNeId(int tieId,String title,int id);

    /**
     * 保存
     * @param teacherFillTaskTemplate
     */
    void save(TeacherFillTaskTemplate teacherFillTaskTemplate);

    /**
     * 保存后返回id
     * @param teacherFillTaskTemplate
     * @return
     */
    int saveAndReturnId(TeacherFillTaskTemplate teacherFillTaskTemplate);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    TeacherFillTaskTemplate findById(int id);

    /**
     * 更新
     * @param teacherFillTaskTemplate
     */
    void update(TeacherFillTaskTemplate teacherFillTaskTemplate);

    /**
     * 通过系主键查询
     * @param tieId
     * @return
     */
    Result<TeacherFillTaskTemplateRecord> findByTieId(int tieId);

}
