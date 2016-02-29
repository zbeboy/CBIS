package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Tie;

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
     * 通过系名查询
     * @param tieName
     * @return
     */
    List<Tie> findByTieName(String tieName);
}
