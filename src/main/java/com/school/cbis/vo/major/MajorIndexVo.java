package com.school.cbis.vo.major;

/**
 * Created by lenovo on 2016-03-19.
 */
public class MajorIndexVo {
    private int majorId;
    private String majorName;
    private int articleInfoId;
    private String articleContent;

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public int getArticleInfoId() {
        return articleInfoId;
    }

    public void setArticleInfoId(int articleInfoId) {
        this.articleInfoId = articleInfoId;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    @Override
    public String toString() {
        return "MajorIndexVo{" +
                "majorId=" + majorId +
                ", majorName='" + majorName + '\'' +
                ", articleInfoId=" + articleInfoId +
                ", articleContent='" + articleContent + '\'' +
                '}';
    }
}
