/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

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
public class Exam implements Serializable {

	private static final long serialVersionUID = -791930900;

	private Integer   id;
	private Timestamp examTime;
	private String    examAddress;
	private String    examContent;
	private String    examTitle;
	private Integer   majorId;
	private Integer   tieId;
	private String    username;
	private Timestamp createTime;

	public Exam() {}

	public Exam(Exam value) {
		this.id = value.id;
		this.examTime = value.examTime;
		this.examAddress = value.examAddress;
		this.examContent = value.examContent;
		this.examTitle = value.examTitle;
		this.majorId = value.majorId;
		this.tieId = value.tieId;
		this.username = value.username;
		this.createTime = value.createTime;
	}

	public Exam(
		Integer   id,
		Timestamp examTime,
		String    examAddress,
		String    examContent,
		String    examTitle,
		Integer   majorId,
		Integer   tieId,
		String    username,
		Timestamp createTime
	) {
		this.id = id;
		this.examTime = examTime;
		this.examAddress = examAddress;
		this.examContent = examContent;
		this.examTitle = examTitle;
		this.majorId = majorId;
		this.tieId = tieId;
		this.username = username;
		this.createTime = createTime;
	}

	@NotNull
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	public Timestamp getExamTime() {
		return this.examTime;
	}

	public void setExamTime(Timestamp examTime) {
		this.examTime = examTime;
	}

	@Size(max = 500)
	public String getExamAddress() {
		return this.examAddress;
	}

	public void setExamAddress(String examAddress) {
		this.examAddress = examAddress;
	}

	@Size(max = 65535)
	public String getExamContent() {
		return this.examContent;
	}

	public void setExamContent(String examContent) {
		this.examContent = examContent;
	}

	@Size(max = 100)
	public String getExamTitle() {
		return this.examTitle;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	@NotNull
	public Integer getMajorId() {
		return this.majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	@NotNull
	public Integer getTieId() {
		return this.tieId;
	}

	public void setTieId(Integer tieId) {
		this.tieId = tieId;
	}

	@NotNull
	@Size(max = 64)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Exam (");

		sb.append(id);
		sb.append(", ").append(examTime);
		sb.append(", ").append(examAddress);
		sb.append(", ").append(examContent);
		sb.append(", ").append(examTitle);
		sb.append(", ").append(majorId);
		sb.append(", ").append(tieId);
		sb.append(", ").append(username);
		sb.append(", ").append(createTime);

		sb.append(")");
		return sb.toString();
	}
}
