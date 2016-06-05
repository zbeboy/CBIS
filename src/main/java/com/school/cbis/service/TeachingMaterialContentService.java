package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskContent;
import com.school.cbis.domain.tables.pojos.TeachingMaterialContent;
import com.school.cbis.domain.tables.records.TeacherFillTaskContentRecord;
import com.school.cbis.domain.tables.records.TeachingMaterialContentRecord;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-06-04.
 */
public interface TeachingMaterialContentService {

    /**
     * 保存
     * @param teachingMaterialContent
     */
    void save(TeachingMaterialContent teachingMaterialContent);

    /**
     * 根据标题id删除
     * @param teachingMaterialHeadId
     */
    void deleteByTeachingMaterialHeadId(int teachingMaterialHeadId);

    /**
     * 根据标题id查询
     * @param teachingMaterialHeadId
     * @return
     */
    List<TeachingMaterialContent> findByTeachingMaterialHeadId(int teachingMaterialHeadId);

    /**
     * 更新
     * @param teachingMaterialContent
     */
    void update(TeachingMaterialContent teachingMaterialContent);

    /**
     *根据标题id查询全部
     * @param id
     * @return
     */
    Result<TeachingMaterialContentRecord> findInTeachingMaterialHeadId(List<Integer> id);

    /**
     * 根据标题id和行序查询
     * @param id
     * @param contentX
     * @return
     */
    Result<TeachingMaterialContentRecord> findInTeachingMaterialHeadIdAndContentX(List<Integer> id,int contentX);

    /**
     * 根据标题id 以及行删除
     * @param teachingMaterialHeadId
     * @param contentX
     */
    TeachingMaterialContentRecord findByTeachingMaterialHeadIdAndContentX(int teachingMaterialHeadId, int contentX);
}
