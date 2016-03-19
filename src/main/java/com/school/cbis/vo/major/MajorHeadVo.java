package com.school.cbis.vo.major;

/**
 * Created by lenovo on 2016-02-09.
 */
public class MajorHeadVo {
    private int id;
    private String bigTitle;
    private String username;
    private String date;
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

    public String getBigTitle() {
        return bigTitle;
    }

    public void setBigTitle(String bigTitle) {
        this.bigTitle = bigTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        return "MajorHeadVo{" +
                "id=" + id +
                ", bigTitle='" + bigTitle + '\'' +
                ", username='" + username + '\'' +
                ", date='" + date + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
