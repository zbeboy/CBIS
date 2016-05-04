package com.school.cbis.data;

/**
 * Created by Administrator on 2016/4/6.
 */
public class SelectData {

    private int id;

    private String value;

    private String text;

    private boolean selected;

    public SelectData(){

    }

    public SelectData(int id, String value, String text, boolean selected) {
        this.id = id;
        this.value = value;
        this.text = text;
        this.selected = selected;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "SelectData{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", text='" + text + '\'' +
                ", selected=" + selected +
                '}';
    }
}
