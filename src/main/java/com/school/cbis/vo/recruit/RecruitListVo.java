package com.school.cbis.vo.recruit;

/**
 * Created by lenovo on 2016-05-26.
 */
public class RecruitListVo {
    private int id;
    private String recruitTitle;
    private String fitMajor;
    private String recruitTime;
    private String username;
    private String realName;
    private String createTime;
    private int pageNum;
    private int pageSize;

    //add
    private String recruitDate;
    private String recruitContent;
    private String textLink;
    private String recruitAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecruitTitle() {
        return recruitTitle;
    }

    public void setRecruitTitle(String recruitTitle) {
        this.recruitTitle = recruitTitle;
    }

    public String getFitMajor() {
        return fitMajor;
    }

    public void setFitMajor(String fitMajor) {
        this.fitMajor = fitMajor;
    }

    public String getRecruitTime() {
        return recruitTime;
    }

    public void setRecruitTime(String recruitTime) {
        this.recruitTime = recruitTime;
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

    public String getRecruitDate() {
        return recruitDate;
    }

    public void setRecruitDate(String recruitDate) {
        this.recruitDate = recruitDate;
    }

    public String getRecruitContent() {
        return recruitContent;
    }

    public void setRecruitContent(String recruitContent) {
        this.recruitContent = recruitContent;
    }

    public String getTextLink() {
        return textLink;
    }

    public void setTextLink(String textLink) {
        this.textLink = textLink;
    }

    public String getRecruitAddress() {
        return recruitAddress;
    }

    public void setRecruitAddress(String recruitAddress) {
        this.recruitAddress = recruitAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "RecruitListVo{" +
                "id=" + id +
                ", recruitTitle='" + recruitTitle + '\'' +
                ", fitMajor='" + fitMajor + '\'' +
                ", recruitTime='" + recruitTime + '\'' +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", recruitDate='" + recruitDate + '\'' +
                ", recruitContent='" + recruitContent + '\'' +
                ", textLink='" + textLink + '\'' +
                ", recruitAddress='" + recruitAddress + '\'' +
                '}';
    }
}
