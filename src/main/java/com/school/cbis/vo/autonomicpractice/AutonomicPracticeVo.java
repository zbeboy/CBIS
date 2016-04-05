package com.school.cbis.vo.autonomicpractice;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/5.
 */
public class AutonomicPracticeVo {

    private String autonomousPracticeTitle;

    private String[] gradeYear;

    private String autonomousPracticeTemplateId;

    private String startTime;

    private String endTime;

    public String getAutonomousPracticeTitle() {
        return autonomousPracticeTitle;
    }

    public void setAutonomousPracticeTitle(String autonomousPracticeTitle) {
        this.autonomousPracticeTitle = autonomousPracticeTitle;
    }

    public String[] getGradeYear() {
        return gradeYear;
    }

    public void setGradeYear(String[] gradeYear) {
        this.gradeYear = gradeYear;
    }

    public String getAutonomousPracticeTemplateId() {
        return autonomousPracticeTemplateId;
    }

    public void setAutonomousPracticeTemplateId(String autonomousPracticeTemplateId) {
        this.autonomousPracticeTemplateId = autonomousPracticeTemplateId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "AutonomicPracticeVo{" +
                "autonomousPracticeTitle='" + autonomousPracticeTitle + '\'' +
                ", gradeYear=" + Arrays.toString(gradeYear) +
                ", autonomousPracticeTemplateId='" + autonomousPracticeTemplateId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
