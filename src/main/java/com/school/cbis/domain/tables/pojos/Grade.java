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
public class Grade implements Serializable {

	private static final long serialVersionUID = -1016092535;

	private Integer id;
	private Integer majorId;
	private String  year;
	private String  gradeName;
	private String  gradeHead;

	public Grade() {}

	public Grade(Grade value) {
		this.id = value.id;
		this.majorId = value.majorId;
		this.year = value.year;
		this.gradeName = value.gradeName;
		this.gradeHead = value.gradeHead;
	}

	public Grade(
		Integer id,
		Integer majorId,
		String  year,
		String  gradeName,
		String  gradeHead
	) {
		this.id = id;
		this.majorId = majorId;
		this.year = year;
		this.gradeName = gradeName;
		this.gradeHead = gradeHead;
	}

	@NotNull
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	public Integer getMajorId() {
		return this.majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	@NotNull
	@Size(max = 20)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@NotNull
	@Size(max = 70)
	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	@NotNull
	@Size(max = 50)
	public String getGradeHead() {
		return this.gradeHead;
	}

	public void setGradeHead(String gradeHead) {
		this.gradeHead = gradeHead;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Grade (");

		sb.append(id);
		sb.append(", ").append(majorId);
		sb.append(", ").append(year);
		sb.append(", ").append(gradeName);
		sb.append(", ").append(gradeHead);

		sb.append(")");
		return sb.toString();
	}
}
