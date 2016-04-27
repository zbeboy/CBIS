package com.school.cbis.vo.autonomicpractice;

import java.sql.Date;

/**
 * Created by lenovo on 2016-04-23.
 */
public class AutonomicPracticeStudentInfoVo {
    private int id;
    private String studentNumber;
    private String studentName;
    private String gradeName;
    private String studentPhone;
    private String studentEmail;
    private Date studentBirthday;
    private String studentSex;
    private String studentIdentityCard;
    private String studentAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Date getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(Date studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    public String getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }

    public String getStudentIdentityCard() {
        return studentIdentityCard;
    }

    public void setStudentIdentityCard(String studentIdentityCard) {
        this.studentIdentityCard = studentIdentityCard;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeStudentInfoVo{" +
                "id=" + id +
                ", studentNumber='" + studentNumber + '\'' +
                ", studentName='" + studentName + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", studentPhone='" + studentPhone + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                ", studentBirthday=" + studentBirthday +
                ", studentSex='" + studentSex + '\'' +
                ", studentIdentityCard='" + studentIdentityCard + '\'' +
                ", studentAddress='" + studentAddress + '\'' +
                '}';
    }
}
