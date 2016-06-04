package com.school.cbis.vo.eadmin;

/**
 * Created by lenovo on 2016-06-04.
 */
public class TeachingMaterialTemplateTitleUpdateVo {
    private Integer id;
    private String  title;
    private String  titleVariable;
    private String teachTaskTitle;
    private Integer teachingMaterialTemplateId;
    private Byte    isAssignment;
    private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleVariable() {
        return titleVariable;
    }

    public void setTitleVariable(String titleVariable) {
        this.titleVariable = titleVariable;
    }

    public String getTeachTaskTitle() {
        return teachTaskTitle;
    }

    public void setTeachTaskTitle(String teachTaskTitle) {
        this.teachTaskTitle = teachTaskTitle;
    }

    public Integer getTeachingMaterialTemplateId() {
        return teachingMaterialTemplateId;
    }

    public void setTeachingMaterialTemplateId(Integer teachingMaterialTemplateId) {
        this.teachingMaterialTemplateId = teachingMaterialTemplateId;
    }

    public Byte getIsAssignment() {
        return isAssignment;
    }

    public void setIsAssignment(Byte isAssignment) {
        this.isAssignment = isAssignment;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "TeachingMaterialTemplateTitleUpdateVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", titleVariable='" + titleVariable + '\'' +
                ", teachTaskTitle='" + teachTaskTitle + '\'' +
                ", teachingMaterialTemplateId=" + teachingMaterialTemplateId +
                ", isAssignment=" + isAssignment +
                ", sort=" + sort +
                '}';
    }
}
