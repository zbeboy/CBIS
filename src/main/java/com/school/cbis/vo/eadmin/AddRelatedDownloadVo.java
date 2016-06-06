package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-06.
 */
public class AddRelatedDownloadVo {
    private int id;
    private String teachType;
    private String fileName;
    private String remark;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "AddRelatedDownloadVo{" +
                "id=" + id +
                ", teachType='" + teachType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", remark='" + remark + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
