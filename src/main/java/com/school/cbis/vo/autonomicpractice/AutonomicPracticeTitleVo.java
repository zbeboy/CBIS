package com.school.cbis.vo.autonomicpractice;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/4/6.
 */
public class AutonomicPracticeTitleVo {

    private int autonomousPracticeInfoId;

    private String title;

    private String headType;

    private String headTypePlugin;

    private String headContent;

    private String databaseTable;

    private String databaseField;

    private String[] authority;

    private String edit;

    private String filter;

    private String sort;

    private String visible;

    private String required;

    public int getAutonomousPracticeInfoId() {
        return autonomousPracticeInfoId;
    }

    public void setAutonomousPracticeInfoId(int autonomousPracticeInfoId) {
        this.autonomousPracticeInfoId = autonomousPracticeInfoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadType() {
        return headType;
    }

    public void setHeadType(String headType) {
        this.headType = headType;
    }

    public String getHeadTypePlugin() {
        return headTypePlugin;
    }

    public void setHeadTypePlugin(String headTypePlugin) {
        this.headTypePlugin = headTypePlugin;
    }

    public String getHeadContent() {
        return headContent;
    }

    public void setHeadContent(String headContent) {
        this.headContent = headContent;
    }

    public String getDatabaseTable() {
        return databaseTable;
    }

    public void setDatabaseTable(String databaseTable) {
        this.databaseTable = databaseTable;
    }

    public String getDatabaseField() {
        return databaseField;
    }

    public void setDatabaseField(String databaseField) {
        this.databaseField = databaseField;
    }

    public String[] getAuthority() {
        return authority;
    }

    public void setAuthority(String[] authority) {
        this.authority = authority;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeTitleVo{" +
                "autonomousPracticeInfoId=" + autonomousPracticeInfoId +
                ", title='" + title + '\'' +
                ", headType='" + headType + '\'' +
                ", headTypePlugin='" + headTypePlugin + '\'' +
                ", headContent='" + headContent + '\'' +
                ", databaseTable='" + databaseTable + '\'' +
                ", databaseField='" + databaseField + '\'' +
                ", authority=" + Arrays.toString(authority) +
                ", edit='" + edit + '\'' +
                ", filter='" + filter + '\'' +
                ", sort='" + sort + '\'' +
                ", visible='" + visible + '\'' +
                ", required='" + required + '\'' +
                '}';
    }
}
