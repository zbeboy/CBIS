package com.school.cbis.data;

/**
 * Created by lenovo on 2016-04-30.
 */
public class CheckboxData {
    private String value;
    private boolean checked;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "CheckboxData{" +
                "value='" + value + '\'' +
                ", checked=" + checked +
                '}';
    }
}
