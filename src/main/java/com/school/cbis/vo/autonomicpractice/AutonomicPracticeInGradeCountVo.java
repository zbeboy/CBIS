package com.school.cbis.vo.autonomicpractice;

/**
 * Created by lenovo on 2016-05-08.
 */
public class AutonomicPracticeInGradeCountVo {

    private int gradeId;
    private String gradeName;
    private int autonomousPracticeCount;//已提交学生数
    private int autonomousPracticeCountNo;//未提交人数
    private int gradeCount;//该班级学生数

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

    public int getGradeCount() {
        return gradeCount;
    }

    public void setGradeCount(int gradeCount) {
        this.gradeCount = gradeCount;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeInGradeCountVo{" +
                "gradeId=" + gradeId +
                ", gradeName='" + gradeName + '\'' +
                ", autonomousPracticeCount=" + autonomousPracticeCount +
                ", autonomousPracticeCountNo=" + autonomousPracticeCountNo +
                ", gradeCount=" + gradeCount +
                '}';
    }
}
