package com.school.cbis.service;

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
     * @return Users password
     */
    String getPassword();

    /**
     * 根据用户名更新用户密码
     *
     * @return 更新成功消息
     */
    boolean updatePassword(String username,String newPassword);

}
