package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-02.
 */
public class TeacherFillTemplateListVo {
    private int id;
    private String realName;
    private String title;
    private String teachTaskTitle;
    private String createTime;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return "TeacherFillTemplateListVo{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", title='" + title + '\'' +
                ", teachTaskTitle='" + teachTaskTitle + '\'' +
                ", createTime='" + createTime + '\'' +
                ", teachTypeId=" + teachTypeId +
                ", teachType='" + teachType + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
