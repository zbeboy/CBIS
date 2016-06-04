package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeacherFillTaskContent;
import com.school.cbis.domain.tables.pojos.TeachingMaterialContent;

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
}
