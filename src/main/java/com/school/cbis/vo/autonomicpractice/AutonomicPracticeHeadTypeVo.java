package com.school.cbis.vo.autonomicpractice;

/**
 * Created by lenovo on 2016-05-12.
 */
public class AutonomicPracticeHeadTypeVo {
    private String value;

    private String text;

    private String content;//分割内容 ","

    private String typeValue;

    private String typeName;

    private String databaseTableField;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDatabaseTableField() {
        return databaseTableField;
    }

    public void setDatabaseTableField(String databaseTableField) {
        this.databaseTableField = databaseTableField;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeHeadTypeVo{" +
                "value='" + value + '\'' +
                ", text='" + text + '\'' +
                ", content='" + content + '\'' +
                ", typeValue='" + typeValue + '\'' +
                ", typeName='" + typeName + '\'' +
                ", databaseTableField='" + databaseTableField + '\'' +
                '}';
    }
}
