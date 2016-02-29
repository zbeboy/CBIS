package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Authorities;
import com.school.cbis.domain.tables.records.AuthoritiesRecord;
import org.jooq.Result;

import java.util.List;

/**
 * Created by lenovo on 2016-02-21.
 */
public interface AuthoritiesService {
    /**
     * 通过用户名查询
     *
     * @param username
     * @return
     */
    Result<AuthoritiesRecord> findByUsername(String username);

    /**
     * 通过用户名删除
     *
     * @param username 用户名
     */
    void delete(String username);

    /**
     * 批量保存
     *
     * @param authorities 权限信息
     */
    void save(List<Authorities> authorities);
}
