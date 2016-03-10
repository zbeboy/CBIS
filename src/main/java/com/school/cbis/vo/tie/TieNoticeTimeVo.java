package com.school.cbis.vo.tie;

/**
 * Created by lenovo on 2016-03-09.
 */
public class TieNoticeTimeVo {
    private int id;

    private String bigTitle;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TieElegantTimeVo{" +
                "id=" + id +
                ", bigTitle='" + bigTitle + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
