package com.school.cbis.vo.autonomicpractice;

/**
 * Created by Administrator on 2016/5/11.
 */
public class AutonomousPracticeParamVo {
    private int autonomousPracticeInfoId;
    private String autonomousPracticeTitle;
    private int tieId;
    private String year;
    private int majorId;
    private String majorName;
    private int gradeId;
    private String gradeName;
    private String gradeYear;//允许填报的年级
    private int type;//0是已提交 1未提交
    private String studentNumber;
    private int havePayPageNum;
    private int havePayPageSize = 1;
    private int havePayTotalData;
    private int haveNoPayPageNum;
    private int haveNoPayPageSize = 1;
    private int haveNoPayTotalData;

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

    public int getTieId() {
        return tieId;
    }

    public void setTieId(int tieId) {
        this.tieId = tieId;
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

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeYear() {
        return gradeYear;
    }

    public void setGradeYear(String gradeYear) {
        this.gradeYear = gradeYear;
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
        return "AutonomousPracticeParamVo{" +
                "autonomousPracticeInfoId=" + autonomousPracticeInfoId +
                ", autonomousPracticeTitle='" + autonomousPracticeTitle + '\'' +
                ", tieId=" + tieId +
                ", year='" + year + '\'' +
                ", majorId=" + majorId +
                ", majorName='" + majorName + '\'' +
                ", gradeId=" + gradeId +
                ", gradeName='" + gradeName + '\'' +
                ", gradeYear='" + gradeYear + '\'' +
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
