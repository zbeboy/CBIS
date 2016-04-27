package com.school.cbis.vo.autonomicpractice;

/**
 * Created by lenovo on 2016-04-12.
 */
public class TemplateVo {

    private int id;
    private String autonomousPracticeTemplateTitle;
    private String createTime;
    private String username;
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

    public String getAutonomousPracticeTemplateTitle() {
        return autonomousPracticeTemplateTitle;
    }

    public void setAutonomousPracticeTemplateTitle(String autonomousPracticeTemplateTitle) {
        this.autonomousPracticeTemplateTitle = autonomousPracticeTemplateTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return "TemplateVo{" +
                "id=" + id +
                ", autonomousPracticeTemplateTitle='" + autonomousPracticeTemplateTitle + '\'' +
                ", createTime='" + createTime + '\'' +
                ", username='" + username + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
