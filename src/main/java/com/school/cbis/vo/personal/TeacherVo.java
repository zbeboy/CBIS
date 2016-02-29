package com.school.cbis.vo.personal;

/**
 * Created by lenovo on 2016-02-17.
 */
public class TeacherVo {
    private int id;
    private String teacherName;
    private String teacherJobNumber;
    private boolean enabled;
    private String authority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherJobNumber() {
        return teacherJobNumber;
    }

    public void setTeacherJobNumber(String teacherJobNumber) {
        this.teacherJobNumber = teacherJobNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "TeacherVo{" +
                "id=" + id +
                ", teacherName='" + teacherName + '\'' +
                ", teacherJobNumber='" + teacherJobNumber + '\'' +
                ", enabled=" + enabled +
                ", authority='" + authority + '\'' +
                '}';
    }
}
