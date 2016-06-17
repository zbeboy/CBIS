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
public class FourItems implements Serializable {

	private static final long serialVersionUID = 1338696640;

	private Integer   id;
	private Integer   teachTaskInfoId;
	private Integer   contentX;
	private String    fourItemsFileUrl;
	private String    fourItemsFileSize;
	private String    fourItemsFileName;
	private Timestamp fourItemsFileDate;
	private String    fileUser;
	private String    fileType;

	public FourItems() {}

	public FourItems(FourItems value) {
		this.id = value.id;
		this.teachTaskInfoId = value.teachTaskInfoId;
		this.contentX = value.contentX;
		this.fourItemsFileUrl = value.fourItemsFileUrl;
		this.fourItemsFileSize = value.fourItemsFileSize;
		this.fourItemsFileName = value.fourItemsFileName;
		this.fourItemsFileDate = value.fourItemsFileDate;
		this.fileUser = value.fileUser;
		this.fileType = value.fileType;
	}

	public FourItems(
		Integer   id,
		Integer   teachTaskInfoId,
		Integer   contentX,
		String    fourItemsFileUrl,
		String    fourItemsFileSize,
		String    fourItemsFileName,
		Timestamp fourItemsFileDate,
		String    fileUser,
		String    fileType
	) {
		this.id = id;
		this.teachTaskInfoId = teachTaskInfoId;
		this.contentX = contentX;
		this.fourItemsFileUrl = fourItemsFileUrl;
		this.fourItemsFileSize = fourItemsFileSize;
		this.fourItemsFileName = fourItemsFileName;
		this.fourItemsFileDate = fourItemsFileDate;
		this.fileUser = fileUser;
		this.fileType = fileType;
	}

	@NotNull
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	public Integer getTeachTaskInfoId() {
		return this.teachTaskInfoId;
	}

	public void setTeachTaskInfoId(Integer teachTaskInfoId) {
		this.teachTaskInfoId = teachTaskInfoId;
	}

	@NotNull
	public Integer getContentX() {
		return this.contentX;
	}

	public void setContentX(Integer contentX) {
		this.contentX = contentX;
	}

	@NotNull
	@Size(max = 500)
	public String getFourItemsFileUrl() {
		return this.fourItemsFileUrl;
	}

	public void setFourItemsFileUrl(String fourItemsFileUrl) {
		this.fourItemsFileUrl = fourItemsFileUrl;
	}

	@Size(max = 50)
	public String getFourItemsFileSize() {
		return this.fourItemsFileSize;
	}

	public void setFourItemsFileSize(String fourItemsFileSize) {
		this.fourItemsFileSize = fourItemsFileSize;
	}

	@NotNull
	@Size(max = 30)
	public String getFourItemsFileName() {
		return this.fourItemsFileName;
	}

	public void setFourItemsFileName(String fourItemsFileName) {
		this.fourItemsFileName = fourItemsFileName;
	}

	@NotNull
	public Timestamp getFourItemsFileDate() {
		return this.fourItemsFileDate;
	}

	public void setFourItemsFileDate(Timestamp fourItemsFileDate) {
		this.fourItemsFileDate = fourItemsFileDate;
	}

	@NotNull
	@Size(max = 64)
	public String getFileUser() {
		return this.fileUser;
	}

	public void setFileUser(String fileUser) {
		this.fileUser = fileUser;
	}

	@Size(max = 15)
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("FourItems (");

		sb.append(id);
		sb.append(", ").append(teachTaskInfoId);
		sb.append(", ").append(contentX);
		sb.append(", ").append(fourItemsFileUrl);
		sb.append(", ").append(fourItemsFileSize);
		sb.append(", ").append(fourItemsFileName);
		sb.append(", ").append(fourItemsFileDate);
		sb.append(", ").append(fileUser);
		sb.append(", ").append(fileType);

		sb.append(")");
		return sb.toString();
	}
}
