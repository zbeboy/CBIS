package com.school.cbis.vo.article;

/**
 * Created by lenovo on 2016-03-01.
 */
public class ArticleVo {

    private int id;

    private String bigTitle;

    private String username;

    private String realName;

    private int userTypeId;

    private String date;

    private String articlePhotoUrl;

    private String articleContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBigTitle() {
        return bigTitle;
    }

    public void setBigTitle(String bigTitle) {
        this.bigTitle = bigTitle;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArticlePhotoUrl() {
        return articlePhotoUrl;
    }

    public void setArticlePhotoUrl(String articlePhotoUrl) {
        this.articlePhotoUrl = articlePhotoUrl;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    @Override
    public String toString() {
        return "ArticleVo{" +
                "id=" + id +
                ", bigTitle='" + bigTitle + '\'' +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", userTypeId=" + userTypeId +
                ", date='" + date + '\'' +
                ", articlePhotoUrl='" + articlePhotoUrl + '\'' +
                ", articleContent='" + articleContent + '\'' +
                '}';
    }
}
