package com.school.cbis.vo.article;

/**
 * Created by lenovo on 2016-05-24.
 */
public class UsersArticleVo {
    private String username;
    private String realName;
    private String bigTitle;
    private int id;//文章id
    private int pageNum;
    private int pageSize;
    private String userType;

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

    public String getBigTitle() {
        return bigTitle;
    }

    public void setBigTitle(String bigTitle) {
        this.bigTitle = bigTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UsersArticleVo{" +
                "username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", bigTitle='" + bigTitle + '\'' +
                ", id=" + id +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", userType='" + userType + '\'' +
                '}';
    }
}
