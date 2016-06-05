package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-05.
 */
public class AddTeachingProcessVo {

    private int id;
    private String teachType;
    private String teachCourseInfoFileName;
    private String teachCourseInfoTerm;
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

    public String getTeachCourseInfoFileName() {
        return teachCourseInfoFileName;
    }

    public void setTeachCourseInfoFileName(String teachCourseInfoFileName) {
        this.teachCourseInfoFileName = teachCourseInfoFileName;
    }

    public String getTeachCourseInfoTerm() {
        return teachCourseInfoTerm;
    }

    public void setTeachCourseInfoTerm(String teachCourseInfoTerm) {
        this.teachCourseInfoTerm = teachCourseInfoTerm;
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
        return "AddTeachingProcessVo{" +
                "id=" + id +
                ", teachType='" + teachType + '\'' +
                ", teachCourseInfoFileName='" + teachCourseInfoFileName + '\'' +
                ", teachCourseInfoTerm='" + teachCourseInfoTerm + '\'' +
                ", termStartTime='" + termStartTime + '\'' +
                ", termEndTime='" + termEndTime + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
