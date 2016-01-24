package com.school.cbis.service;

import com.school.cbis.domain.tables.records.GradeRecord;

/**
 * Created by lenovo on 2016-01-17.
 */
public interface GradeService {

    /**
     * 通过班级ID获取班级信息
     * @param id 班级ID
     * @return 班级信息
     */
    GradeRecord getGradeInfo(int id);
}
