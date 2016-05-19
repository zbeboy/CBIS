package com.school.cbis.vo.personal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by lenovo on 2016-05-19.
 */
public class ResetPasswordVo {
    @NotNull
    private String username;

    @Pattern(regexp = "^[\\w]{6,20}$",message = "密码长度为6~20长度！")
    private String newPassword;

    @Pattern(regexp = "^[\\w]{6,20}$",message = "密码长度为6~20长度！")
    private String okPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return "ResetPasswordVo{" +
                "username='" + username + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", okPassword='" + okPassword + '\'' +
                '}';
    }
}
