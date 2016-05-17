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
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Student implements Serializable {

	private static final long serialVersionUID = -1835322735;

	private Integer id;
	private String  studentNumber;
	private Integer gradeId;
	private String  dormitoryNumber;
	private String  parentName;
	private String  parentContactPhone;
	private String  placeOrigin;
	private String  problemSituation;

	public Student() {}

	public Student(Student value) {
		this.id = value.id;
		this.studentNumber = value.studentNumber;
		this.gradeId = value.gradeId;
		this.dormitoryNumber = value.dormitoryNumber;
		this.parentName = value.parentName;
		this.parentContactPhone = value.parentContactPhone;
		this.placeOrigin = value.placeOrigin;
		this.problemSituation = value.problemSituation;
	}

	public Student(
		Integer id,
		String  studentNumber,
		Integer gradeId,
		String  dormitoryNumber,
		String  parentName,
		String  parentContactPhone,
		String  placeOrigin,
		String  problemSituation
	) {
		this.id = id;
		this.studentNumber = studentNumber;
		this.gradeId = gradeId;
		this.dormitoryNumber = dormitoryNumber;
		this.parentName = parentName;
		this.parentContactPhone = parentContactPhone;
		this.placeOrigin = placeOrigin;
		this.problemSituation = problemSituation;
	}

	@NotNull
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(max = 25)
	public String getStudentNumber() {
		return this.studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	@NotNull
	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	@Size(max = 50)
	public String getDormitoryNumber() {
		return this.dormitoryNumber;
	}

	public void setDormitoryNumber(String dormitoryNumber) {
		this.dormitoryNumber = dormitoryNumber;
	}

	@Size(max = 10)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Size(max = 15)
	public String getParentContactPhone() {
		return this.parentContactPhone;
	}

	public void setParentContactPhone(String parentContactPhone) {
		this.parentContactPhone = parentContactPhone;
	}

	@Size(max = 500)
	public String getPlaceOrigin() {
		return this.placeOrigin;
	}

	public void setPlaceOrigin(String placeOrigin) {
		this.placeOrigin = placeOrigin;
	}

	@Size(max = 500)
	public String getProblemSituation() {
		return this.problemSituation;
	}

	public void setProblemSituation(String problemSituation) {
		this.problemSituation = problemSituation;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Student (");

		sb.append(id);
		sb.append(", ").append(studentNumber);
		sb.append(", ").append(gradeId);
		sb.append(", ").append(dormitoryNumber);
		sb.append(", ").append(parentName);
		sb.append(", ").append(parentContactPhone);
		sb.append(", ").append(placeOrigin);
		sb.append(", ").append(problemSituation);

		sb.append(")");
		return sb.toString();
	}
}
