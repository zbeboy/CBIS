package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.HeadType;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public interface HeadTypeService {

    /**
     * 查询全部
     * @return
     */
    List<HeadType> findAll();

}
