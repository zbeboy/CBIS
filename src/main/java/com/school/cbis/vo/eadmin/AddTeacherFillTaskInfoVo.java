package com.school.cbis.vo.eadmin;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-02.
 */
public class AddTeacherFillTaskInfoVo {

    private Integer   id;
    private String    title;
    private Integer   teacherFillTaskTemplateId;
    private String  startTime;
    private String  endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTeacherFillTaskTemplateId() {
        return teacherFillTaskTemplateId;
    }

    public void setTeacherFillTaskTemplateId(Integer teacherFillTaskTemplateId) {
        this.teacherFillTaskTemplateId = teacherFillTaskTemplateId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "AddTeacherFillTaskInfoVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", teacherFillTaskTemplateId=" + teacherFillTaskTemplateId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
