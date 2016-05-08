package com.school.cbis.vo.autonomicpractice;

/**
 * Created by lenovo on 2016-05-08.
 */
public class AutonomicPracticeStudentInfoInMajorVo {
    private int id;
    private int majorId;
    private String year;
    private int type;//0是已提交 1未提交
    private String studentNumber;
    private int havePayPageNum;
    private int havePayPageSize = 20;
    private int havePayTotalData;
    private int haveNoPayPageNum;
    private int haveNoPayPageSize = 20;
    private int haveNoPayTotalData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getHavePayPageNum() {
        return havePayPageNum;
    }

    public void setHavePayPageNum(int havePayPageNum) {
        this.havePayPageNum = havePayPageNum;
    }

    public int getHavePayPageSize() {
        return havePayPageSize;
    }

    public void setHavePayPageSize(int havePayPageSize) {
        this.havePayPageSize = havePayPageSize;
    }

    public int getHavePayTotalData() {
        return havePayTotalData;
    }

    public void setHavePayTotalData(int havePayTotalData) {
        this.havePayTotalData = havePayTotalData;
    }

    public int getHaveNoPayPageNum() {
        return haveNoPayPageNum;
    }

    public void setHaveNoPayPageNum(int haveNoPayPageNum) {
        this.haveNoPayPageNum = haveNoPayPageNum;
    }

    public int getHaveNoPayPageSize() {
        return haveNoPayPageSize;
    }

    public void setHaveNoPayPageSize(int haveNoPayPageSize) {
        this.haveNoPayPageSize = haveNoPayPageSize;
    }

    public int getHaveNoPayTotalData() {
        return haveNoPayTotalData;
    }

    public void setHaveNoPayTotalData(int haveNoPayTotalData) {
        this.haveNoPayTotalData = haveNoPayTotalData;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeStudentInfoInMajorVo{" +
                "id=" + id +
                ", majorId=" + majorId +
                ", year='" + year + '\'' +
                ", type=" + type +
                ", studentNumber='" + studentNumber + '\'' +
                ", havePayPageNum=" + havePayPageNum +
                ", havePayPageSize=" + havePayPageSize +
                ", havePayTotalData=" + havePayTotalData +
                ", haveNoPayPageNum=" + haveNoPayPageNum +
                ", haveNoPayPageSize=" + haveNoPayPageSize +
                ", haveNoPayTotalData=" + haveNoPayTotalData +
                '}';
    }
}
