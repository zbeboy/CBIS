package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-06.
 */
public class TeacherInfoVo {
    private int id;
    private int majorId;
    private String realName;
    private String majorName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    @Override
    public String toString() {
        return "TeacherInfoVo{" +
                "id=" + id +
                ", majorId=" + majorId +
                ", realName='" + realName + '\'' +
                ", majorName='" + majorName + '\'' +
                '}';
    }
}
