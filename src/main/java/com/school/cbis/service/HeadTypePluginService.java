package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.HeadTypePlugin;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public interface HeadTypePluginService {

    /**
     * 查询全部
     * @return
     */
    List<HeadTypePlugin> findAll();

}
