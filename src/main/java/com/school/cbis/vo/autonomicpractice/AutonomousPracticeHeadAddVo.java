package com.school.cbis.vo.autonomicpractice;

/**
 * Created by lenovo on 2016-04-23.
 */
public class AutonomousPracticeHeadAddVo {
    private int id;
    private String title;
    private String titleVariable;
    private String typeValue;
    private String databaseTable;
    private String databaseTableField;
    private String authority;
    private Byte isShowHighlyActive;
    private String content;
    private Byte isDatabase;
    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleVariable() {
        return titleVariable;
    }

    public void setTitleVariable(String titleVariable) {
        this.titleVariable = titleVariable;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getDatabaseTable() {
        return databaseTable;
    }

    public void setDatabaseTable(String databaseTable) {
        this.databaseTable = databaseTable;
    }

    public String getDatabaseTableField() {
        return databaseTableField;
    }

    public void setDatabaseTableField(String databaseTableField) {
        this.databaseTableField = databaseTableField;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Byte getIsShowHighlyActive() {
        return isShowHighlyActive;
    }

    public void setIsShowHighlyActive(Byte isShowHighlyActive) {
        this.isShowHighlyActive = isShowHighlyActive;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getIsDatabase() {
        return isDatabase;
    }

    public void setIsDatabase(Byte isDatabase) {
        this.isDatabase = isDatabase;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "AutonomousPracticeHeadAddVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", titleVariable='" + titleVariable + '\'' +
                ", typeValue='" + typeValue + '\'' +
                ", databaseTable='" + databaseTable + '\'' +
                ", databaseTableField='" + databaseTableField + '\'' +
                ", authority='" + authority + '\'' +
                ", isShowHighlyActive=" + isShowHighlyActive +
                ", content='" + content + '\'' +
                ", isDatabase=" + isDatabase +
                ", sort=" + sort +
                '}';
    }
}
