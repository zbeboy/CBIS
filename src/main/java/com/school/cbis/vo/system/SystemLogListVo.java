package com.school.cbis.vo.system;

/**
 * Created by lenovo on 2016-05-26.
 */
public class SystemLogListVo {
    private String username;
    private String operationBehavior;
    private String startTime;
    private String endTime;
    private int pageNum;
    private int pageSize;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperationBehavior() {
        return operationBehavior;
    }

    public void setOperationBehavior(String operationBehavior) {
        this.operationBehavior = operationBehavior;
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
        return "SystemLogListVo{" +
                "username='" + username + '\'' +
                ", operationBehavior='" + operationBehavior + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
