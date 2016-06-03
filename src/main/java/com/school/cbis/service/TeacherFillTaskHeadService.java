package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskHead;
import org.jooq.Record;
import org.jooq.Record7;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-06-02.
 */
public interface TeacherFillTaskHeadService {

    /**
     * 保存
     */
    int saveAndReturnId(TeacherFillTaskHead teacherFillTaskHead);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    TeacherFillTaskHead findById(int id);

    /**
     * 更新
     * @param teacherFillTaskHead
     */
    void update(TeacherFillTaskHead teacherFillTaskHead);

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
    Result<Record7<Integer,String,String,String,Integer,Byte,Integer>> findByTeacherFillTaskTemplateIdWithTeachTaskInfoTitle(int teacherFillTaskTemplateId);

    /**
     * 根据模板id查询
     * @param teacherFillTaskTemplateId
     * @return
     */
    List<TeacherFillTaskHead> findByTeacherFillTaskTemplateId(int teacherFillTaskTemplateId);
}
