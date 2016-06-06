package com.school.cbis.vo.eadmin;

import java.sql.Timestamp;

/**
 * Created by lenovo on 2016-06-06.
 */
public class RelatedDownloadListVo {
    private Integer id;
    private String fileUrl;
    private String fileSize;
    private String fileName;
    private String fileDate;
    private Integer fileDownTimes;
    private Integer teachTypeId;
    private String realName;
    private String fileType;
    private String remark;
    private int pageNum;
    private int pageSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public Integer getFileDownTimes() {
        return fileDownTimes;
    }

    public void setFileDownTimes(Integer fileDownTimes) {
        this.fileDownTimes = fileDownTimes;
    }

    public Integer getTeachTypeId() {
        return teachTypeId;
    }

    public void setTeachTypeId(Integer teachTypeId) {
        this.teachTypeId = teachTypeId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
