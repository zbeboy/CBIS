package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Users;

/**
 * Created by lenovo on 2016-05-17.
 */
public interface MobileService {

    /**
     * 发送短信
     * @param users
     * @param content
     */
    void sendShortMessage(Users users, String content);

    /**
     * 发送短信验证码
     * @param users
     * @param verificationCode
     */
    void sendValidMobileShortMessage(Users users,String verificationCode);
}
