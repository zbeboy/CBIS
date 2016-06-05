package com.school.cbis.vo.eadmin;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-05.
 */
public class TeachingMaterialListVo {
    private int id;
    private int templateId;
    private int taskInfoId;
    private String title;
    private String templateTitle;
    private String teachTaskTitle;
    private Timestamp startTime;
    private Timestamp endTime;
    private String startTimeString;
    private String endTimeString;
    private String realName;
    private int pageNum;
    private int pageSize;
    private boolean isOk;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getTaskInfoId() {
        return taskInfoId;
    }

    public void setTaskInfoId(int taskInfoId) {
        this.taskInfoId = taskInfoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getTeachTaskTitle() {
        return teachTaskTitle;
    }

    public void setTeachTaskTitle(String teachTaskTitle) {
        this.teachTaskTitle = teachTaskTitle;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    @Override
    public String toString() {
        return "TeachingMaterialListVo{" +
                "id=" + id +
                ", templateId=" + templateId +
                ", taskInfoId=" + taskInfoId +
                ", title='" + title + '\'' +
                ", templateTitle='" + templateTitle + '\'' +
                ", teachTaskTitle='" + teachTaskTitle + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startTimeString='" + startTimeString + '\'' +
                ", endTimeString='" + endTimeString + '\'' +
                ", realName='" + realName + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", isOk=" + isOk +
                '}';
    }
}
