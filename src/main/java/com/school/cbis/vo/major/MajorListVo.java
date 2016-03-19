package com.school.cbis.vo.major;

/**
 * Created by lenovo on 2016-03-18.
 */
public class MajorListVo {
    private int id;
    private String majorName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    @Override
    public String toString() {
        return "MajorListVo{" +
                "id=" + id +
                ", majorName='" + majorName + '\'' +
                '}';
    }
}
