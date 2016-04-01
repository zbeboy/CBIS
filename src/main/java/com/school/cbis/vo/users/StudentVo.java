package com.school.cbis.vo.users;

import com.school.cbis.domain.tables.records.AuthoritiesRecord;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 2016-03-21.
 */
public class StudentVo {
    private int id;
    private String studentName;
    private String studentNumber;
    private String gradeName;
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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
        return "StudentVo{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalData=" + totalData +
                '}';
    }
}
