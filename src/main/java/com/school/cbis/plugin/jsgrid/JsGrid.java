package com.school.cbis.plugin.jsgrid;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016-02-28.
 */
public class JsGrid<T> {

    private  Map<String, Object> map;

    public JsGrid(){

    }

    public JsGrid(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 加载数据
     *
     * @param data
     * @param count
     * @return
     */
    public Map<String, Object> loadData(List<T> data, int count) {
        this.map.put("data", data);
        this.map.put("itemsCount", count);
        return this.map;
    }

    /**
     * 删除数据
     *
     * @param t
     * @return
     */
    public T deleteItem(T t) {
        return t;
    }

    /**
     * 插入数据
     * @param t
     * @return
     */
    public T insertItem(T t){return t;}

    /**
     * 更新数据
     * @param t
     * @return
     */
    public T updateItem(T t){
        return t;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
