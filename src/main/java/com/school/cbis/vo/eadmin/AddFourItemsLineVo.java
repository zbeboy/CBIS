package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-07.
 */
public class AddFourItemsLineVo {
    private int fourItemId;
    private int fourItemContentX;
    private String uploadFile;
    private String filePath;
    private int teachTaskInfoId;

    public int getFourItemId() {
        return fourItemId;
    }

    public void setFourItemId(int fourItemId) {
        this.fourItemId = fourItemId;
    }

    public int getFourItemContentX() {
        return fourItemContentX;
    }

    public void setFourItemContentX(int fourItemContentX) {
        this.fourItemContentX = fourItemContentX;
    }

    public String getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getTeachTaskInfoId() {
        return teachTaskInfoId;
    }

    public void setTeachTaskInfoId(int teachTaskInfoId) {
        this.teachTaskInfoId = teachTaskInfoId;
    }

    @Override
    public String toString() {
        return "AddFourItemsLineVo{" +
                "fourItemId=" + fourItemId +
                ", fourItemContentX=" + fourItemContentX +
                ", uploadFile='" + uploadFile + '\'' +
                ", filePath='" + filePath + '\'' +
                ", teachTaskInfoId=" + teachTaskInfoId +
                '}';
    }
}
