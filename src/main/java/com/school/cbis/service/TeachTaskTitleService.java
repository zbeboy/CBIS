package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.TeachTaskTitle;

/**
 * Created by lenovo on 2016-05-28.
 */
public interface TeachTaskTitleService {

    /**
     * 保存
     * @param teachTaskTitle
     */
    int saveAndReturnId(TeachTaskTitle teachTaskTitle);
}
