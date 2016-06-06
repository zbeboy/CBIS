package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-06.
 */
public class AddStudentTimetableVo {
    private int id;
    private String teachType;
    private String timetableInfoFileName;
    private int gradeId;
    private String timetableInfoTerm;
    private String termStartTime;
    private String termEndTime;
    private String filePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeachType() {
        return teachType;
    }

    public void setTeachType(String teachType) {
        this.teachType = teachType;
    }

    public String getTimetableInfoFileName() {
        return timetableInfoFileName;
    }

    public void setTimetableInfoFileName(String timetableInfoFileName) {
        this.timetableInfoFileName = timetableInfoFileName;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getTimetableInfoTerm() {
        return timetableInfoTerm;
    }

    public void setTimetableInfoTerm(String timetableInfoTerm) {
        this.timetableInfoTerm = timetableInfoTerm;
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

    @Override
    public String toString() {
        return "AddStudentTimetableVo{" +
                "id=" + id +
                ", teachType='" + teachType + '\'' +
                ", timetableInfoFileName='" + timetableInfoFileName + '\'' +
                ", gradeId=" + gradeId +
                ", timetableInfoTerm='" + timetableInfoTerm + '\'' +
                ", termStartTime='" + termStartTime + '\'' +
                ", termEndTime='" + termEndTime + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
