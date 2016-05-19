package com.school.cbis.vo.mail;

/**
 * Created by lenovo on 2016-05-19.
 */
public class MailListVo {
    private String acceptUser;
    private String subject;
    private String sendTime;
    private String acceptEmail;
    private String username;
    private String startDate;
    private String endDate;
    private int pageNum;
    private int pageSize;

    public String getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(String acceptUser) {
        this.acceptUser = acceptUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getAcceptEmail() {
        return acceptEmail;
    }

    public void setAcceptEmail(String acceptEmail) {
        this.acceptEmail = acceptEmail;
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
        return "MailListVo{" +
                "acceptUser='" + acceptUser + '\'' +
                ", subject='" + subject + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", acceptEmail='" + acceptEmail + '\'' +
                ", username='" + username + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
