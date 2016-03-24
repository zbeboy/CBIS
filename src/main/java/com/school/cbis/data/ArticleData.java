package com.school.cbis.data;

/**
 * Created by lenovo on 2016-01-12.
 * 文章数据模板
 */
public class ArticleData {
    private String title;//标题
    private String summary;//内容
    private String picPath;//图片路径
    private String subTitle;//子标题
    private String subPage;//子内容
    private String articleType;//文章类型

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubPage() {
        return subPage;
    }

    public void setSubPage(String subPage) {
        this.subPage = subPage;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    @Override
    public String toString() {
        return "ArticleData{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", picPath='" + picPath + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", subPage='" + subPage + '\'' +
                ", articleType='" + articleType + '\'' +
                '}';
    }
}
