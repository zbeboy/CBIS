package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-06.
 */
public class ClassroomTimetableListVo {
    private int id;
    private String timetableInfoTerm;
    private String timetableInfoFileUrl;
    private String timetableInfoFilePdf;
    private String timetableInfoFileSize;
    private String timetableInfoFileName;
    private String timetableInfoFileDate;
    private Integer timetableInfoFileDownTimes;
    private String classroom;
    private String termStartTime;
    private String termEndTime;
    private String fileUser;
    private String fileType;
    private String realName;
    private int teachTypeId;
    private String teachType;
    private int pageNum;
    private int pageSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimetableInfoTerm() {
        return timetableInfoTerm;
    }

    public void setTimetableInfoTerm(String timetableInfoTerm) {
        this.timetableInfoTerm = timetableInfoTerm;
    }

    public String getTimetableInfoFileUrl() {
        return timetableInfoFileUrl;
    }

    public void setTimetableInfoFileUrl(String timetableInfoFileUrl) {
        this.timetableInfoFileUrl = timetableInfoFileUrl;
    }

    public String getTimetableInfoFilePdf() {
        return timetableInfoFilePdf;
    }

    public void setTimetableInfoFilePdf(String timetableInfoFilePdf) {
        this.timetableInfoFilePdf = timetableInfoFilePdf;
    }

    public String getTimetableInfoFileSize() {
        return timetableInfoFileSize;
    }

    public void setTimetableInfoFileSize(String timetableInfoFileSize) {
        this.timetableInfoFileSize = timetableInfoFileSize;
    }

    public String getTimetableInfoFileName() {
        return timetableInfoFileName;
    }

    public void setTimetableInfoFileName(String timetableInfoFileName) {
        this.timetableInfoFileName = timetableInfoFileName;
    }

    public String getTimetableInfoFileDate() {
        return timetableInfoFileDate;
    }

    public void setTimetableInfoFileDate(String timetableInfoFileDate) {
        this.timetableInfoFileDate = timetableInfoFileDate;
    }

    public Integer getTimetableInfoFileDownTimes() {
        return timetableInfoFileDownTimes;
    }

    public void setTimetableInfoFileDownTimes(Integer timetableInfoFileDownTimes) {
        this.timetableInfoFileDownTimes = timetableInfoFileDownTimes;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
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

    public String getFileUser() {
        return fileUser;
    }

    public void setFileUser(String fileUser) {
        this.fileUser = fileUser;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getTeachTypeId() {
        return teachTypeId;
    }

    public void setTeachTypeId(int teachTypeId) {
        this.teachTypeId = teachTypeId;
    }

    public String getTeachType() {
        return teachType;
    }

    public void setTeachType(String teachType) {
        this.teachType = teachType;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "ClassroomTimetableListVo{" +
                "id=" + id +
                ", timetableInfoTerm='" + timetableInfoTerm + '\'' +
                ", timetableInfoFileUrl='" + timetableInfoFileUrl + '\'' +
                ", timetableInfoFilePdf='" + timetableInfoFilePdf + '\'' +
                ", timetableInfoFileSize='" + timetableInfoFileSize + '\'' +
                ", timetableInfoFileName='" + timetableInfoFileName + '\'' +
                ", timetableInfoFileDate='" + timetableInfoFileDate + '\'' +
                ", timetableInfoFileDownTimes=" + timetableInfoFileDownTimes +
                ", classroom='" + classroom + '\'' +
                ", termStartTime='" + termStartTime + '\'' +
                ", termEndTime='" + termEndTime + '\'' +
                ", fileUser='" + fileUser + '\'' +
                ", fileType='" + fileType + '\'' +
                ", realName='" + realName + '\'' +
                ", teachTypeId=" + teachTypeId +
                ", teachType='" + teachType + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
