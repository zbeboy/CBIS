package com.school.cbis.vo.grade;

/**
 * Created by lenovo on 2016-02-14.
 */
public class GradeVo {
    private int id;
    private int teacherId;
    private String majorName;
    private String year;
    private String gradeName;
    private String gradeHead;
    private String gradeHeadID;
    private int pageIndex;
    private int pageSize;
    private String sortField;
    private String sortOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeHead() {
        return gradeHead;
    }

    public void setGradeHead(String gradeHead) {
        this.gradeHead = gradeHead;
    }

    public String getGradeHeadID() {
        return gradeHeadID;
    }

    public void setGradeHeadID(String gradeHeadID) {
        this.gradeHeadID = gradeHeadID;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "GradeVo{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", majorName='" + majorName + '\'' +
                ", year='" + year + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", gradeHead='" + gradeHead + '\'' +
                ", gradeHeadID='" + gradeHeadID + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
