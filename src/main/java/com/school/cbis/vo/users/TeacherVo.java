package com.school.cbis.vo.users;

import com.school.cbis.domain.tables.records.AuthoritiesRecord;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 2016-02-17.
 */
public class TeacherVo {
    private int id;
    private String teacherName;
    private String teacherJobNumber;
    private boolean enabled;
    private List<String> authorities;
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

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
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
                ", authorities=" + authorities +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalData=" + totalData +
                '}';
    }
}
