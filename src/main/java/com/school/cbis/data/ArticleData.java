package com.school.cbis.data;

import org.apache.log4j.Logger;

/**
 * Created by lenovo on 2016-01-12.
 * 文章数据模板
 */
public class ArticleData {

    private static Logger logger = Logger.getLogger(ArticleData.class);

    private String bigTitle;//标题
    private String articleContent;//内容
    private String articlePhotoUrl;//图片路径
    private String subTitle;//子标题
    private String subContent;//子内容
    private String articleType;//文章类型

    public String getBigTitle() {
        return bigTitle;
    }

    public void setBigTitle(String bigTitle) {
        this.bigTitle = bigTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticlePhotoUrl() {
        return articlePhotoUrl;
    }

    public void setArticlePhotoUrl(String articlePhotoUrl) {
        this.articlePhotoUrl = articlePhotoUrl;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
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
                "bigTitle='" + bigTitle + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", articlePhotoUrl='" + articlePhotoUrl + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", subContent='" + subContent + '\'' +
                ", articleType='" + articleType + '\'' +
                '}';
    }
}
