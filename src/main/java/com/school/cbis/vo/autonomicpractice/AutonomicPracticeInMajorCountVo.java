package com.school.cbis.vo.autonomicpractice;

/**
 * Created by lenovo on 2016-05-08.
 */
public class AutonomicPracticeInMajorCountVo {
    private int autonomousPracticeInfoId;
    private String year;
    private int majorId;
    private String majorName;
    private int autonomousPracticeCount;//已提交学生数
    private int autonomousPracticeCountNo;//未提交人数
    private int majorCount;//该专业学生数

    public int getAutonomousPracticeInfoId() {
        return autonomousPracticeInfoId;
    }

    public void setAutonomousPracticeInfoId(int autonomousPracticeInfoId) {
        this.autonomousPracticeInfoId = autonomousPracticeInfoId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public int getAutonomousPracticeCount() {
        return autonomousPracticeCount;
    }

    public void setAutonomousPracticeCount(int autonomousPracticeCount) {
        this.autonomousPracticeCount = autonomousPracticeCount;
    }

    public int getAutonomousPracticeCountNo() {
        return autonomousPracticeCountNo;
    }

    public void setAutonomousPracticeCountNo(int autonomousPracticeCountNo) {
        this.autonomousPracticeCountNo = autonomousPracticeCountNo;
    }

    public int getMajorCount() {
        return majorCount;
    }

    public void setMajorCount(int majorCount) {
        this.majorCount = majorCount;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeInMajorCountVo{" +
                "autonomousPracticeInfoId=" + autonomousPracticeInfoId +
                ", year='" + year + '\'' +
                ", majorId=" + majorId +
                ", majorName='" + majorName + '\'' +
                ", autonomousPracticeCount=" + autonomousPracticeCount +
                ", autonomousPracticeCountNo=" + autonomousPracticeCountNo +
                ", majorCount=" + majorCount +
                '}';
    }
}
