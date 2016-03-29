package com.school.cbis.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-01-09.
 * ajax消息以及数据封装
 */
public class AjaxData<T> {

    private final Logger log = LoggerFactory.getLogger(AjaxData.class);

    private boolean state;//消息状态
    private String msg;//消息
    private Map<String,Object> single;//map数据
    private List<T> result;//list数据
    private PaginationData paginationData;//分页数据

    public AjaxData<T> success(){
        this.state = true;
        return this;
    }

    public AjaxData<T> fail(){
        this.state = false;
        return this;
    }

    public AjaxData<T> msg(String msg){
        this.msg = msg;
        return this;
    }

    public AjaxData<T> mapData(Map<String,Object> map){
        this.single = map;
        return this;
    }

    public AjaxData<T> listData(List<T> list){
        this.result = list;
        return this;
    }

    public AjaxData<T> paginationData(PaginationData paginationData){
        this.paginationData = paginationData;
        return this;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public PaginationData getPaginationData() {
        return paginationData;
    }

    public void setPaginationData(PaginationData paginationData) {
        this.paginationData = paginationData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getSingle() {
        return single;
    }

    public void setSingle(Map<String, Object> single) {
        this.single = single;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AjaxData{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", single=" + single +
                ", result=" + result +
                ", paginationData=" + paginationData +
                '}';
    }
}
