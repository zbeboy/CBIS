package com.school.cbis.vo.autonomicpractice;

/**
 * Created by lenovo on 2016-04-12.
 */
public class TemplateVo {

    private int id;
    private String autonomousPracticeTemplateTitle;
    private String createTime;
    private String realName;
    private int pageNum;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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
                ", realName='" + realName + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
