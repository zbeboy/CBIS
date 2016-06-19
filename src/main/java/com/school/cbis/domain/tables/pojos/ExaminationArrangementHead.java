/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ExaminationArrangementHead implements Serializable {

	private static final long serialVersionUID = 1625910163;

	private Integer id;
	private String  title;
	private String  titleVariable;
	private Integer teachTaskTitleId;
	private Integer examinationArrangementTemplateId;
	private Byte    isAssignment;
	private Integer sort;

	public ExaminationArrangementHead() {}

	public ExaminationArrangementHead(ExaminationArrangementHead value) {
		this.id = value.id;
		this.title = value.title;
		this.titleVariable = value.titleVariable;
		this.teachTaskTitleId = value.teachTaskTitleId;
		this.examinationArrangementTemplateId = value.examinationArrangementTemplateId;
		this.isAssignment = value.isAssignment;
		this.sort = value.sort;
	}

	public ExaminationArrangementHead(
		Integer id,
		String  title,
		String  titleVariable,
		Integer teachTaskTitleId,
		Integer examinationArrangementTemplateId,
		Byte    isAssignment,
		Integer sort
	) {
		this.id = id;
		this.title = title;
		this.titleVariable = titleVariable;
		this.teachTaskTitleId = teachTaskTitleId;
		this.examinationArrangementTemplateId = examinationArrangementTemplateId;
		this.isAssignment = isAssignment;
		this.sort = sort;
	}

	@NotNull
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(max = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	@Size(max = 50)
	public String getTitleVariable() {
		return this.titleVariable;
	}

	public void setTitleVariable(String titleVariable) {
		this.titleVariable = titleVariable;
	}

	public Integer getTeachTaskTitleId() {
		return this.teachTaskTitleId;
	}

	public void setTeachTaskTitleId(Integer teachTaskTitleId) {
		this.teachTaskTitleId = teachTaskTitleId;
	}

	@NotNull
	public Integer getExaminationArrangementTemplateId() {
		return this.examinationArrangementTemplateId;
	}

	public void setExaminationArrangementTemplateId(Integer examinationArrangementTemplateId) {
		this.examinationArrangementTemplateId = examinationArrangementTemplateId;
	}

	@NotNull
	public Byte getIsAssignment() {
		return this.isAssignment;
	}

	public void setIsAssignment(Byte isAssignment) {
		this.isAssignment = isAssignment;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ExaminationArrangementHead (");

		sb.append(id);
		sb.append(", ").append(title);
		sb.append(", ").append(titleVariable);
		sb.append(", ").append(teachTaskTitleId);
		sb.append(", ").append(examinationArrangementTemplateId);
		sb.append(", ").append(isAssignment);
		sb.append(", ").append(sort);

		sb.append(")");
		return sb.toString();
	}
}