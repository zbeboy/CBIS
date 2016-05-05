package com.school.cbis.vo.autonomicpractice;

/**
 * Created by Administrator on 2016/5/5.
 */
public class AutonomicPracticeTeacherVo {
    private int autonomousPracticeInfoId;
    private int studentId;
    private String title;
    private String titleVariable;
    private String typeName;
    private String typeValue;
    private String databaseTable;
    private String databaseTableField;
    private String authority;
    private String headContent;
    private String content;
    private Byte isDatabase;
    private Byte isRequired;

    public int getAutonomousPracticeInfoId() {
        return autonomousPracticeInfoId;
    }

    public void setAutonomousPracticeInfoId(int autonomousPracticeInfoId) {
        this.autonomousPracticeInfoId = autonomousPracticeInfoId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getHeadContent() {
        return headContent;
    }

    public void setHeadContent(String headContent) {
        this.headContent = headContent;
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

    public Byte getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Byte isRequired) {
        this.isRequired = isRequired;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeTeacherVo{" +
                "autonomousPracticeInfoId=" + autonomousPracticeInfoId +
                ", studentId=" + studentId +
                ", title='" + title + '\'' +
                ", titleVariable='" + titleVariable + '\'' +
                ", typeName='" + typeName + '\'' +
                ", typeValue='" + typeValue + '\'' +
                ", databaseTable='" + databaseTable + '\'' +
                ", databaseTableField='" + databaseTableField + '\'' +
                ", authority='" + authority + '\'' +
                ", headContent='" + headContent + '\'' +
                ", content='" + content + '\'' +
                ", isDatabase=" + isDatabase +
                ", isRequired=" + isRequired +
                '}';
    }
}
