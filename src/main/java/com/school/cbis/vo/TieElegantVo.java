package com.school.cbis.vo;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-01-21.
 */
public class TieElegantVo {
    private int id;
    private String bigTitle;
    private String username;
    private int userTypeId;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBigTitle() {
        return bigTitle;
    }

    public void setBigTitle(String bigTitle) {
        this.bigTitle = bigTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TieElegantVo{" +
                "id=" + id +
                ", bigTitle='" + bigTitle + '\'' +
                ", username='" + username + '\'' +
                ", userTypeId=" + userTypeId +
                ", date=" + date +
                '}';
    }
}
