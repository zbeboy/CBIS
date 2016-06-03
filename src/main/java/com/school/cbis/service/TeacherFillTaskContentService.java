package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskContent;
import com.school.cbis.domain.tables.records.TeacherFillTaskContentRecord;
import org.jooq.Record;
import org.jooq.Result;

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

    /**
     * 根据标题id删除
     * @param teacherFillTaskHeadId
     */
    void deleteByTeacherFillTaskHeadId(int teacherFillTaskHeadId);

    /**
     * 保存
     * @param teacherFillTaskContent
     */
    void save(TeacherFillTaskContent teacherFillTaskContent);

    /**
     * 更新
     * @param teacherFillTaskContent
     */
    void update(TeacherFillTaskContent teacherFillTaskContent);

    /**
     *根据标题id查询全部
     * @param id
     * @return
     */
    Result<TeacherFillTaskContentRecord> findInTeacherFillTaskHeadId(List<Integer> id);

    /**
     * 根据标题id和行序查询
     * @param id
     * @param contentX
     * @return
     */
    Result<TeacherFillTaskContentRecord> findInTeacherFillTaskHeadIdAndContentX(List<Integer> id,int contentX);

    /**
     * 根据标题id 以及行删除
     * @param teacherFillTaskHeadId
     * @param contentX
     */
    TeacherFillTaskContentRecord findByTeacherFillTaskHeadIdAndContentX(int teacherFillTaskHeadId, int contentX);
}
