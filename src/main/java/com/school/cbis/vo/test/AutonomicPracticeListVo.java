package com.school.cbis.vo.test;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-04-22.
 */
public class AutonomicPracticeListVo {
    private int id;
    private String autonomousPracticeTitle;
    private String createTime;
    private String gradeYear;
    private int autonomousPracticeTemplateId;
    private String autonomousPracticeTemplateTitle;
    private Timestamp startTime;
    private Timestamp endTime;
    private String username;
    private String realName;
    private int userTypeId;
    private boolean isOk;
    private String startTimeString;
    private String endTimeString;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutonomousPracticeTitle() {
        return autonomousPracticeTitle;
    }

    public void setAutonomousPracticeTitle(String autonomousPracticeTitle) {
        this.autonomousPracticeTitle = autonomousPracticeTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGradeYear() {
        return gradeYear;
    }

    public void setGradeYear(String gradeYear) {
        this.gradeYear = gradeYear;
    }

    public int getAutonomousPracticeTemplateId() {
        return autonomousPracticeTemplateId;
    }

    public void setAutonomousPracticeTemplateId(int autonomousPracticeTemplateId) {
        this.autonomousPracticeTemplateId = autonomousPracticeTemplateId;
    }

    public String getAutonomousPracticeTemplateTitle() {
        return autonomousPracticeTemplateTitle;
    }

    public void setAutonomousPracticeTemplateTitle(String autonomousPracticeTemplateTitle) {
        this.autonomousPracticeTemplateTitle = autonomousPracticeTemplateTitle;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
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

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeListVo{" +
                "id=" + id +
                ", autonomousPracticeTitle='" + autonomousPracticeTitle + '\'' +
                ", createTime='" + createTime + '\'' +
                ", gradeYear='" + gradeYear + '\'' +
                ", autonomousPracticeTemplateId=" + autonomousPracticeTemplateId +
                ", autonomousPracticeTemplateTitle='" + autonomousPracticeTemplateTitle + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", userTypeId=" + userTypeId +
                ", isOk=" + isOk +
                ", startTimeString='" + startTimeString + '\'' +
                ", endTimeString='" + endTimeString + '\'' +
                '}';
    }
}
