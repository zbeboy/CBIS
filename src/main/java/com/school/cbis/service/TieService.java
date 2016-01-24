package com.school.cbis.service;

import com.school.cbis.domain.tables.records.TieRecord;

/**
 * Created by lenovo on 2016-01-17.
 */
public interface TieService {
    /**
     * 更新系表
     * @param tieRecord
     * @return 是否成功更新
     */
    boolean updateTie(TieRecord tieRecord);

    /**
     * 通过ID获取系信息
     * @param id
     * @return 系信息
     */
    TieRecord getTieInfo(int id);
}
