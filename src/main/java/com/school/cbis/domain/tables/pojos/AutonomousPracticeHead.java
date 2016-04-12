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
public class AutonomousPracticeHead implements Serializable {

	private static final long serialVersionUID = 1375002748;

	private Integer id;
	private String  title;
	private String  titleVariable;
	private String  databaseTable;
	private String  databaseTableField;
	private String  authority;
	private Byte    isShowHighlyActive;
	private Byte    isDatabase;
	private String  content;
	private Integer headTypeId;
	private Integer autonomousPracticeInfoId;

	public AutonomousPracticeHead() {}

	public AutonomousPracticeHead(AutonomousPracticeHead value) {
		this.id = value.id;
		this.title = value.title;
		this.titleVariable = value.titleVariable;
		this.databaseTable = value.databaseTable;
		this.databaseTableField = value.databaseTableField;
		this.authority = value.authority;
		this.isShowHighlyActive = value.isShowHighlyActive;
		this.isDatabase = value.isDatabase;
		this.content = value.content;
		this.headTypeId = value.headTypeId;
		this.autonomousPracticeInfoId = value.autonomousPracticeInfoId;
	}

	public AutonomousPracticeHead(
		Integer id,
		String  title,
		String  titleVariable,
		String  databaseTable,
		String  databaseTableField,
		String  authority,
		Byte    isShowHighlyActive,
		Byte    isDatabase,
		String  content,
		Integer headTypeId,
		Integer autonomousPracticeInfoId
	) {
		this.id = id;
		this.title = title;
		this.titleVariable = titleVariable;
		this.databaseTable = databaseTable;
		this.databaseTableField = databaseTableField;
		this.authority = authority;
		this.isShowHighlyActive = isShowHighlyActive;
		this.isDatabase = isDatabase;
		this.content = content;
		this.headTypeId = headTypeId;
		this.autonomousPracticeInfoId = autonomousPracticeInfoId;
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

	@Size(max = 100)
	public String getDatabaseTable() {
		return this.databaseTable;
	}

	public void setDatabaseTable(String databaseTable) {
		this.databaseTable = databaseTable;
	}

	@Size(max = 100)
	public String getDatabaseTableField() {
		return this.databaseTableField;
	}

	public void setDatabaseTableField(String databaseTableField) {
		this.databaseTableField = databaseTableField;
	}

	@NotNull
	@Size(max = 30)
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@NotNull
	public Byte getIsShowHighlyActive() {
		return this.isShowHighlyActive;
	}

	public void setIsShowHighlyActive(Byte isShowHighlyActive) {
		this.isShowHighlyActive = isShowHighlyActive;
	}

	@NotNull
	public Byte getIsDatabase() {
		return this.isDatabase;
	}

	public void setIsDatabase(Byte isDatabase) {
		this.isDatabase = isDatabase;
	}

	@Size(max = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getHeadTypeId() {
		return this.headTypeId;
	}

	public void setHeadTypeId(Integer headTypeId) {
		this.headTypeId = headTypeId;
	}

	@NotNull
	public Integer getAutonomousPracticeInfoId() {
		return this.autonomousPracticeInfoId;
	}

	public void setAutonomousPracticeInfoId(Integer autonomousPracticeInfoId) {
		this.autonomousPracticeInfoId = autonomousPracticeInfoId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("AutonomousPracticeHead (");

		sb.append(id);
		sb.append(", ").append(title);
		sb.append(", ").append(titleVariable);
		sb.append(", ").append(databaseTable);
		sb.append(", ").append(databaseTableField);
		sb.append(", ").append(authority);
		sb.append(", ").append(isShowHighlyActive);
		sb.append(", ").append(isDatabase);
		sb.append(", ").append(content);
		sb.append(", ").append(headTypeId);
		sb.append(", ").append(autonomousPracticeInfoId);

		sb.append(")");
		return sb.toString();
	}
}
