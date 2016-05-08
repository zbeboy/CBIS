package com.school.cbis.vo.autonomicpractice;

/**
 * Created by lenovo on 2016-05-07.
 */
public class AutonomicPracticeCountVo {
    private int autonomousPracticeInfoId;
    private String autonomousPracticeTitle;
    private String year;
    private int autonomousPracticeCount;//已提交学生数
    private int yearCount;//学生表中该系下，该年级学生数
    private int autonomousPracticeCountNo;//未提交人数

    public int getAutonomousPracticeInfoId() {
        return autonomousPracticeInfoId;
    }

    public void setAutonomousPracticeInfoId(int autonomousPracticeInfoId) {
        this.autonomousPracticeInfoId = autonomousPracticeInfoId;
    }

    public String getAutonomousPracticeTitle() {
        return autonomousPracticeTitle;
    }

    public void setAutonomousPracticeTitle(String autonomousPracticeTitle) {
        this.autonomousPracticeTitle = autonomousPracticeTitle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getAutonomousPracticeCount() {
        return autonomousPracticeCount;
    }

    public void setAutonomousPracticeCount(int autonomousPracticeCount) {
        this.autonomousPracticeCount = autonomousPracticeCount;
    }

    public int getYearCount() {
        return yearCount;
    }

    public void setYearCount(int yearCount) {
        this.yearCount = yearCount;
    }

    public int getAutonomousPracticeCountNo() {
        return autonomousPracticeCountNo;
    }

    public void setAutonomousPracticeCountNo(int autonomousPracticeCountNo) {
        this.autonomousPracticeCountNo = autonomousPracticeCountNo;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeCountVo{" +
                "autonomousPracticeInfoId=" + autonomousPracticeInfoId +
                ", autonomousPracticeTitle='" + autonomousPracticeTitle + '\'' +
                ", year='" + year + '\'' +
                ", autonomousPracticeCount=" + autonomousPracticeCount +
                ", yearCount=" + yearCount +
                ", autonomousPracticeCountNo=" + autonomousPracticeCountNo +
                '}';
    }
}
