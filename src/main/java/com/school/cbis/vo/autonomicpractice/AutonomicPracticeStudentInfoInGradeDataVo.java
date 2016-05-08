package com.school.cbis.vo.autonomicpractice;

/**
 * Created by lenovo on 2016-05-08.
 */
public class AutonomicPracticeStudentInfoInGradeDataVo {
    private int id;
    private String studentNumber;
    private String studentName;
    private String gradeName;

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

    @Override
    public String toString() {
        return "AutonomicPracticeStudentInfoInGradeDataVo{" +
                "id=" + id +
                ", studentNumber='" + studentNumber + '\'' +
                ", studentName='" + studentName + '\'' +
                ", gradeName='" + gradeName + '\'' +
                '}';
    }
}
