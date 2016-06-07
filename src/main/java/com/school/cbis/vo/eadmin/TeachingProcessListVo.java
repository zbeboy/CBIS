package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-05.
 */
public class TeachingProcessListVo {

    private int id;
    private String teachCourseInfoTerm;
    private String teachCourseInfoFileUrl;
    private String teachCourseInfoFilePdf;
    private String teachCourseInfoFileSize;
    private String teachCourseInfoFileName;
    private String teachCourseInfoFileDate;
    private int teachCourseInfoFileDownTimes;
    private int teachTypeId;
    private String termStartTime;
    private String termEndTime;
    private String realName;
    private String fileType;
    private String teachType;
    private int pageNum;
    private int pageSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeachCourseInfoTerm() {
        return teachCourseInfoTerm;
    }

    public void setTeachCourseInfoTerm(String teachCourseInfoTerm) {
        this.teachCourseInfoTerm = teachCourseInfoTerm;
    }

    public String getTeachCourseInfoFileUrl() {
        return teachCourseInfoFileUrl;
    }

    public void setTeachCourseInfoFileUrl(String teachCourseInfoFileUrl) {
        this.teachCourseInfoFileUrl = teachCourseInfoFileUrl;
    }

    public String getTeachCourseInfoFilePdf() {
        return teachCourseInfoFilePdf;
    }

    public void setTeachCourseInfoFilePdf(String teachCourseInfoFilePdf) {
        this.teachCourseInfoFilePdf = teachCourseInfoFilePdf;
    }

    public String getTeachCourseInfoFileSize() {
        return teachCourseInfoFileSize;
    }

    public void setTeachCourseInfoFileSize(String teachCourseInfoFileSize) {
        this.teachCourseInfoFileSize = teachCourseInfoFileSize;
    }

    public String getTeachCourseInfoFileName() {
        return teachCourseInfoFileName;
    }

    public void setTeachCourseInfoFileName(String teachCourseInfoFileName) {
        this.teachCourseInfoFileName = teachCourseInfoFileName;
    }

    public String getTeachCourseInfoFileDate() {
        return teachCourseInfoFileDate;
    }

    public void setTeachCourseInfoFileDate(String teachCourseInfoFileDate) {
        this.teachCourseInfoFileDate = teachCourseInfoFileDate;
    }

    public int getTeachCourseInfoFileDownTimes() {
        return teachCourseInfoFileDownTimes;
    }

    public void setTeachCourseInfoFileDownTimes(int teachCourseInfoFileDownTimes) {
        this.teachCourseInfoFileDownTimes = teachCourseInfoFileDownTimes;
    }

    public int getTeachTypeId() {
        return teachTypeId;
    }

    public void setTeachTypeId(int teachTypeId) {
        this.teachTypeId = teachTypeId;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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
        return "TeachingProcessListVo{" +
                "id=" + id +
                ", teachCourseInfoTerm='" + teachCourseInfoTerm + '\'' +
                ", teachCourseInfoFileUrl='" + teachCourseInfoFileUrl + '\'' +
                ", teachCourseInfoFilePdf='" + teachCourseInfoFilePdf + '\'' +
                ", teachCourseInfoFileSize='" + teachCourseInfoFileSize + '\'' +
                ", teachCourseInfoFileName='" + teachCourseInfoFileName + '\'' +
                ", teachCourseInfoFileDate='" + teachCourseInfoFileDate + '\'' +
                ", teachCourseInfoFileDownTimes=" + teachCourseInfoFileDownTimes +
                ", teachTypeId=" + teachTypeId +
                ", termStartTime='" + termStartTime + '\'' +
                ", termEndTime='" + termEndTime + '\'' +
                ", realName='" + realName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", teachType='" + teachType + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
