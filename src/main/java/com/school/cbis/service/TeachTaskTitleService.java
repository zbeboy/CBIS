package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeachTaskTitle;

import java.util.List;

/**
 * Created by lenovo on 2016-05-28.
 */
public interface TeachTaskTitleService {

    /**
     * 保存
     * @param teachTaskTitle
     */
    int saveAndReturnId(TeachTaskTitle teachTaskTitle);

    /**
     * 根据教学任务书id查询
     * @param teachTaskInfoId
     * @return
     */
    List<TeachTaskTitle> findByTeachTaskInfoId(int teachTaskInfoId);
}
