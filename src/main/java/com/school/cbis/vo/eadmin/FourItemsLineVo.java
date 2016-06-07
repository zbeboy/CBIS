package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-07.
 */
public class FourItemsLineVo {
    private int id;
    private int taskTitleId;
    private int taskInfoId;
    private String title;
    private String teachTaskTitle;
    private String content;
    private int contentX;
    private int contentY;
    private String teachType;
    private int pageNum;
    private int pageSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskTitleId() {
        return taskTitleId;
    }

    public void setTaskTitleId(int taskTitleId) {
        this.taskTitleId = taskTitleId;
    }

    public int getTaskInfoId() {
        return taskInfoId;
    }

    public void setTaskInfoId(int taskInfoId) {
        this.taskInfoId = taskInfoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeachTaskTitle() {
        return teachTaskTitle;
    }

    public void setTeachTaskTitle(String teachTaskTitle) {
        this.teachTaskTitle = teachTaskTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentX() {
        return contentX;
    }

    public void setContentX(int contentX) {
        this.contentX = contentX;
    }

    public int getContentY() {
        return contentY;
    }

    public void setContentY(int contentY) {
        this.contentY = contentY;
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
        return "FourItemsLineVo{" +
                "id=" + id +
                ", taskTitleId=" + taskTitleId +
                ", taskInfoId=" + taskInfoId +
                ", title='" + title + '\'' +
                ", teachTaskTitle='" + teachTaskTitle + '\'' +
                ", content='" + content + '\'' +
                ", contentX=" + contentX +
                ", contentY=" + contentY +
                ", teachType='" + teachType + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
