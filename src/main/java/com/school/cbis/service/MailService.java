package com.school.cbis.service;

import com.school.cbis.domain.tables.pojos.Users;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface MailService {

    /**
     * 发送邮件
     * @param to
     * @param subject
     * @param content
     * @param isMultipart
     * @param isHtml
     */
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    /**
     * 发送激活邮件
     * @param users
     * @param baseUrl
     */
    void sendActivationEmail(Users users, String baseUrl);

    /**
     * 发送账号创建成功邮件
     * @param users
     * @param baseUrl
     */
    void sendCreationEmail(Users users, String baseUrl);

    /**
     * 发送密码重置邮件
     * @param users
     * @param baseUrl
     */
    void sendPasswordResetMail(Users users, String baseUrl);

    /**
     * 发送邮箱验证邮件
     * @param users
     * @param baseUrl
     */
    void sendValidEmailMail(Users users, String baseUrl);

    /**
     * 阿里云邮箱服务
     * @param users
     * @param subject
     * @param content
     */
    void sendAliDMMail(Users users, String subject, String content);

    /**
     * 发送教学任务书 上传成功与不消息
     * @param users
     * @param baseUrl
     * @param msg
     */
    void sendTeachTaskMsg(Users users,String baseUrl,String msg);
}
