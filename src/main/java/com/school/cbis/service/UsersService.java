package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Users;
import org.jooq.Record;
import org.jooq.Result;

/**
 * Created by lenovo on 2016-01-05.
 */
public interface UsersService {
    /**
     * 从 spring security 获取用户名
     *
     * @return Users username
     */
    String getUserName();

    /**
     * 从 spring security 获取密码
     *
     * @return Users password
     */
    String getPassword();

    /**
     * 根据用户名获取Users表完整信息
     *
     * @param username
     * @return 用户信息
     */
    Users findByUsername(String username);

    /**
     * 检查是否有权限
     *
     * @return
     */
    boolean isAuthenticated();

    /**
     * 检查当前用户是否有此权限
     *
     * @param authority
     * @return
     */
    boolean isCurrentUserInRole(String authority);

    /**
     * 根据用户名更新用户密码
     *
     * @return 更新成功消息
     */
    void update(Users users);

    /**
     * 通过用户名获得用户完整有效信息，包括所在院，系，班级信息
     *
     * @param username
     * @return 用户完整有效信息
     */
    Result<Record> findAll(String username);

    /**
     * 保存用户
     *
     * @param users
     */
    void save(Users users);
}
