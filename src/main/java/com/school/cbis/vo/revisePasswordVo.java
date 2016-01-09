package com.school.cbis.vo;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by lenovo on 2016-01-09.
 */
public class RevisePasswordVo {

    @Pattern(regexp = "^[\\w]{6,20}$",message = "密码长度为6~20长度！")
    private String oldPassword;

    @Pattern(regexp = "^[\\w]{6,20}$",message = "密码长度为6~20长度！")
    private String newPassword;

    @Pattern(regexp = "^[\\w]{6,20}$",message = "密码长度为6~20长度！")
    private String okPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOkPassword() {
        return okPassword;
    }

    public void setOkPassword(String okPassword) {
        this.okPassword = okPassword;
    }

    @Override
    public String toString() {
        return "RevisePasswordVo{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", okPassword='" + okPassword + '\'' +
                '}';
    }
}
