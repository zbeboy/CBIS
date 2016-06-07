package com.school.cbis.vo.eadmin;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-07.
 */
public class AddTeacherFillTaskTemplateVo {
    private Integer id;
    private String title;
    private Timestamp createTime;
    private String createUser;
    private Integer tieId;
    private Integer teachTaskInfoId;
    private String teachType;

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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getTieId() {
        return tieId;
    }

    public void setTieId(Integer tieId) {
        this.tieId = tieId;
    }

    public Integer getTeachTaskInfoId() {
        return teachTaskInfoId;
    }

    public void setTeachTaskInfoId(Integer teachTaskInfoId) {
        this.teachTaskInfoId = teachTaskInfoId;
    }

    public String getTeachType() {
        return teachType;
    }

    public void setTeachType(String teachType) {
        this.teachType = teachType;
    }

    @Override
    public String toString() {
        return "AddTeacherFillTaskTemplateVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", tieId=" + tieId +
                ", teachTaskInfoId=" + teachTaskInfoId +
                ", teachType='" + teachType + '\'' +
                '}';
    }
}
