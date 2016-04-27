package com.school.cbis.vo.autonomicpractice;

import java.util.Arrays;

/**
 * Created by lenovo on 2016-04-17.
 */
public class AutonomousPracticeHeadListVo {
    private int templateId;
    private String title;
    private String authority;
    private Byte isDatabase;
    private int headTypeSelect;
    private String selectContentInput;
    private int databaseTableSelect;
    private String databaseFieldSelect;
    private Byte isShowHighlyActive;
    private int sort;

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Byte getIsDatabase() {
        return isDatabase;
    }

    public void setIsDatabase(Byte isDatabase) {
        this.isDatabase = isDatabase;
    }

    public int getHeadTypeSelect() {
        return headTypeSelect;
    }

    public void setHeadTypeSelect(int headTypeSelect) {
        this.headTypeSelect = headTypeSelect;
    }

    public String getSelectContentInput() {
        return selectContentInput;
    }

    public void setSelectContentInput(String selectContentInput) {
        this.selectContentInput = selectContentInput;
    }

    public int getDatabaseTableSelect() {
        return databaseTableSelect;
    }

    public void setDatabaseTableSelect(int databaseTableSelect) {
        this.databaseTableSelect = databaseTableSelect;
    }

    public String getDatabaseFieldSelect() {
        return databaseFieldSelect;
    }

    public void setDatabaseFieldSelect(String databaseFieldSelect) {
        this.databaseFieldSelect = databaseFieldSelect;
    }

    public Byte getIsShowHighlyActive() {
        return isShowHighlyActive;
    }

    public void setIsShowHighlyActive(Byte isShowHighlyActive) {
        this.isShowHighlyActive = isShowHighlyActive;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "AutonomousPracticeHeadListVo{" +
                "templateId=" + templateId +
                ", title='" + title + '\'' +
                ", authority='" + authority + '\'' +
                ", isDatabase=" + isDatabase +
                ", headTypeSelect=" + headTypeSelect +
                ", selectContentInput='" + selectContentInput + '\'' +
                ", databaseTableSelect=" + databaseTableSelect +
                ", databaseFieldSelect='" + databaseFieldSelect + '\'' +
                ", isShowHighlyActive=" + isShowHighlyActive +
                ", sort=" + sort +
                '}';
    }
}
