package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Yard;

import java.util.List;

/**
 * Created by lenovo on 2016-02-07.
 */
public interface YardService {
    /**
     * 查询所有院信息
     *
     * @return
     */
    List<Yard> findAll();
}
