package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskContent;

import java.util.List;

/**
 * Created by lenovo on 2016-06-02.
 */
public interface TeacherFillTaskContentService {

    /**
     * 根据标题id查询
     * @param teacherFillTaskHeadId
     * @return
     */
    List<TeacherFillTaskContent> findByTeacherFillTaskHeadId(int teacherFillTaskHeadId);
}
