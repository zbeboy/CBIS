package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-05-29.
 */
public class AssignmentBookListVo {
    private int id;
    private String realName;
    private String teachTaskTitle;
    private String teachTaskTerm;
    private String termStartTime;
    private String termEndTime;
    private int pageNum;
    private int pageSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTeachTaskTitle() {
        return teachTaskTitle;
    }

    public void setTeachTaskTitle(String teachTaskTitle) {
        this.teachTaskTitle = teachTaskTitle;
    }

    public String getTeachTaskTerm() {
        return teachTaskTerm;
    }

    public void setTeachTaskTerm(String teachTaskTerm) {
        this.teachTaskTerm = teachTaskTerm;
    }

    public String getTermStartTime() {
        return termStartTime;
    }

    public void setTermStartTime(String termStartTime) {
        this.termStartTime = termStartTime;
    }

    public String getTermEndTime() {
        return termEndTime;
    }

    public void setTermEndTime(String termEndTime) {
        this.termEndTime = termEndTime;
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

    @Override
    public String toString() {
        return "AssignmentBookListVo{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", teachTaskTitle='" + teachTaskTitle + '\'' +
                ", teachTaskTerm='" + teachTaskTerm + '\'' +
                ", termStartTime='" + termStartTime + '\'' +
                ", termEndTime='" + termEndTime + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
