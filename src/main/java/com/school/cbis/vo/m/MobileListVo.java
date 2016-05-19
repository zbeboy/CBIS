package com.school.cbis.vo.m;

/**
 * Created by lenovo on 2016-05-19.
 */
public class MobileListVo {
    private String acceptUser;
    private String sendTime;
    private String content;
    private String acceptMobile;
    private String username;//搜索用
    private String startDate;//搜索用
    private String endDate;//搜索用
    private int pageNum;
    private int pageSize;

    public String getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(String acceptUser) {
        this.acceptUser = acceptUser;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAcceptMobile() {
        return acceptMobile;
    }

    public void setAcceptMobile(String acceptMobile) {
        this.acceptMobile = acceptMobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    @Override
    public String toString() {
        return "MobileListVo{" +
                "acceptUser='" + acceptUser + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", content='" + content + '\'' +
                ", acceptMobile='" + acceptMobile + '\'' +
                ", username='" + username + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
