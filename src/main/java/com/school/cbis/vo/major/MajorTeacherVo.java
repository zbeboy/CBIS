package com.school.cbis.vo.major;

/**
 * Created by lenovo on 2016-05-22.
 */
public class MajorTeacherVo {
    private String username;
    private String realName;
    private String headImg;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Override
    public String toString() {
        return "MajorTeacherVo{" +
                "username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
