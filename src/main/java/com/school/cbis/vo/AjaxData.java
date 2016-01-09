package com.school.cbis.vo;

/**
 * Created by lenovo on 2016-01-09.
 */
public class AjaxData {

    private boolean state;//消息状态
    private String msg;//消息

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
