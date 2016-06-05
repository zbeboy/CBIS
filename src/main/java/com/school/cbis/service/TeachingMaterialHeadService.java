package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskHead;
import com.school.cbis.domain.tables.pojos.TeachingMaterialHead;
import org.jooq.Record7;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-06-04.
 */
public interface TeachingMaterialHeadService {

    /**
     * 保存
     */
    int saveAndReturnId(TeachingMaterialHead teachingMaterialHead);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    TeachingMaterialHead findById(int id);

    /**
     * 更新
     * @param teachingMaterialHead
     */
    void update(TeachingMaterialHead teachingMaterialHead);

    /**
     * 根据主键删除
     * @param id
     */
    void deleteById(int id);

    /**
     * 根据模板id查询
     * @param teacherFillTaskTemplateId
     * @return
     */
    Result<Record7<Integer,String,String,String,Integer,Byte,Integer>> findByTeachingMaterialTemplateIdWithTeachTaskInfoTitle(int teacherFillTaskTemplateId);

    /**
     * 根据模板id查询
     * @param teachingMaterialTemplateId
     * @return
     */
    List<TeachingMaterialHead> findByTeachingMaterialTemplateId(int teachingMaterialTemplateId);
}
