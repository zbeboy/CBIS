package com.school.cbis.data;

/**
 * Created by Administrator on 2016/4/6.
 */
public class SelectData {

    private int id;

    private String value;

    private String text;

    public SelectData(){

    }

    public SelectData(int id, String value, String text) {
        this.id = id;
        this.value = value;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "SelectData{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
