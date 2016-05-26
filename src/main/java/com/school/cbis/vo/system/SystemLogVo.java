package com.school.cbis.vo.system;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-05-26.
 */
public class SystemLogVo {

    private Integer   id;
    private String    username;
    private String    operationBehavior;
    private String createTime;
    private Integer   tieId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperationBehavior() {
        return operationBehavior;
    }

    public void setOperationBehavior(String operationBehavior) {
        this.operationBehavior = operationBehavior;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getTieId() {
        return tieId;
    }

    public void setTieId(Integer tieId) {
        this.tieId = tieId;
    }

    @Override
    public String toString() {
        return "SystemLogVo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", operationBehavior='" + operationBehavior + '\'' +
                ", createTime='" + createTime + '\'' +
                ", tieId=" + tieId +
                '}';
    }
}
