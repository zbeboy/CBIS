package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Tie;
import com.school.cbis.domain.tables.records.TieRecord;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-01-17.
 */
public interface TieService {
    /**
     * 更新系表
     *
     * @param tie
     * @return 是否成功更新
     */
    void update(Tie tie);

    /**
     * 通过ID获取系信息
     *
     * @param id
     * @return 系信息
     */
    Tie findById(int id);

    /**
     * 检验系名用，注意是不等于该id
     *
     * @param id
     * @param tieName
     * @return
     */
    Result<TieRecord> findByTieName(int id, String tieName);
}
