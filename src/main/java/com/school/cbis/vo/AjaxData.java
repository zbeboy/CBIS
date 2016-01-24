package com.school.cbis.vo;

import org.jooq.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-01-09.
 */
public class AjaxData {

    private boolean state;//消息状态
    private String msg;//消息
    private List<Object> result;
    private PaginationData paginationData;

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

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public PaginationData getPaginationData() {
        return paginationData;
    }

    public void setPaginationData(PaginationData paginationData) {
        this.paginationData = paginationData;
    }
}
