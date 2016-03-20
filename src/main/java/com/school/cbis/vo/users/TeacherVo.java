package com.school.cbis.vo.users;

/**
 * Created by lenovo on 2016-02-17.
 */
public class TeacherVo {
    private int id;
    private String teacherName;
    private String teacherJobNumber;
    private boolean enabled;
    private String authority;
    private int pageNum = 1;
    private int pageSize = 6;
    private int totalData;

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

    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    @Override
    public String toString() {
        return "TeacherVo{" +
                "id=" + id +
                ", teacherName='" + teacherName + '\'' +
                ", teacherJobNumber='" + teacherJobNumber + '\'' +
                ", enabled=" + enabled +
                ", authority='" + authority + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalData=" + totalData +
                '}';
    }
}
