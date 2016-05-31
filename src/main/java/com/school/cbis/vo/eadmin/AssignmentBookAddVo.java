package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-05-28.
 */
public class AssignmentBookAddVo {
    private int taskInfoId;
    private String teachTaskTitle;
    private String teachType;
    private String teachTaskTerm;
    private String termStartTime;
    private String termEndTime;
    private String filePath;
    private int yearX;
    private int yearY;
    private int gradeX;
    private int gradeY;
    private int gradeNumX;
    private int gradeNumY;

    public int getTaskInfoId() {
        return taskInfoId;
    }

    public void setTaskInfoId(int taskInfoId) {
        this.taskInfoId = taskInfoId;
    }

    public String getTeachTaskTitle() {
        return teachTaskTitle;
    }

    public void setTeachTaskTitle(String teachTaskTitle) {
        this.teachTaskTitle = teachTaskTitle;
    }

    public String getTeachType() {
        return teachType;
    }

    public void setTeachType(String teachType) {
        this.teachType = teachType;
    }

    public String getTeachTaskTerm() {
        return teachTaskTerm;
    }

    public void setTeachTaskTerm(String teachTaskTerm) {
        this.teachTaskTerm = teachTaskTerm;
    }

    public String getTermStartTime() {
        return termStartTime;
    }

    public void setTermStartTime(String termStartTime) {
        this.termStartTime = termStartTime;
    }

    public String getTermEndTime() {
        return termEndTime;
    }

    public void setTermEndTime(String termEndTime) {
        this.termEndTime = termEndTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getYearX() {
        return yearX;
    }

    public void setYearX(int yearX) {
        this.yearX = yearX;
    }

    public int getYearY() {
        return yearY;
    }

    public void setYearY(int yearY) {
        this.yearY = yearY;
    }

    public int getGradeX() {
        return gradeX;
    }

    public void setGradeX(int gradeX) {
        this.gradeX = gradeX;
    }

    public int getGradeY() {
        return gradeY;
    }

    public void setGradeY(int gradeY) {
        this.gradeY = gradeY;
    }

    public int getGradeNumX() {
        return gradeNumX;
    }

    public void setGradeNumX(int gradeNumX) {
        this.gradeNumX = gradeNumX;
    }

    public int getGradeNumY() {
        return gradeNumY;
    }

    public void setGradeNumY(int gradeNumY) {
        this.gradeNumY = gradeNumY;
    }

    /**
     * 班级和人数是否在同一列
     * @return 是否相同
     */
    public boolean isAlikeGradeOfNum(){
        if(this.gradeX == this.gradeNumX && this.gradeY == this.gradeNumY){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "AssignmentBookAddVo{" +
                "taskInfoId=" + taskInfoId +
                ", teachTaskTitle='" + teachTaskTitle + '\'' +
                ", teachType='" + teachType + '\'' +
                ", teachTaskTerm='" + teachTaskTerm + '\'' +
                ", termStartTime='" + termStartTime + '\'' +
                ", termEndTime='" + termEndTime + '\'' +
                ", filePath='" + filePath + '\'' +
                ", yearX=" + yearX +
                ", yearY=" + yearY +
                ", gradeX=" + gradeX +
                ", gradeY=" + gradeY +
                ", gradeNumX=" + gradeNumX +
                ", gradeNumY=" + gradeNumY +
                '}';
    }
}
