package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-05.
 */
public class AddTeachingMaterialInfoVo {
    private Integer   id;
    private String    title;
    private Integer   teachingMaterialTemplateId;
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

    public Integer getTeachingMaterialTemplateId() {
        return teachingMaterialTemplateId;
    }

    public void setTeachingMaterialTemplateId(Integer teachingMaterialTemplateId) {
        this.teachingMaterialTemplateId = teachingMaterialTemplateId;
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
        return "AddTeachingMaterialInfoVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", teachingMaterialTemplateId=" + teachingMaterialTemplateId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
