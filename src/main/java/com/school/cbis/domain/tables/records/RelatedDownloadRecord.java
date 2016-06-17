/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.records;


import com.school.cbis.domain.tables.RelatedDownload;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


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
public class RelatedDownloadRecord extends UpdatableRecordImpl<RelatedDownloadRecord> implements Record11<Integer, Integer, String, String, String, Timestamp, Integer, Integer, String, String, String> {

	private static final long serialVersionUID = -1326498442;

	/**
	 * Setter for <code>cbis.related_download.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>cbis.related_download.id</code>.
	 */
	@NotNull
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>cbis.related_download.tie_id</code>.
	 */
	public void setTieId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>cbis.related_download.tie_id</code>.
	 */
	@NotNull
	public Integer getTieId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>cbis.related_download.file_url</code>.
	 */
	public void setFileUrl(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>cbis.related_download.file_url</code>.
	 */
	@NotNull
	@Size(max = 500)
	public String getFileUrl() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>cbis.related_download.file_size</code>.
	 */
	public void setFileSize(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>cbis.related_download.file_size</code>.
	 */
	@Size(max = 50)
	public String getFileSize() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>cbis.related_download.file_name</code>.
	 */
	public void setFileName(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>cbis.related_download.file_name</code>.
	 */
	@NotNull
	@Size(max = 30)
	public String getFileName() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>cbis.related_download.file_date</code>.
	 */
	public void setFileDate(Timestamp value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>cbis.related_download.file_date</code>.
	 */
	public Timestamp getFileDate() {
		return (Timestamp) getValue(5);
	}

	/**
	 * Setter for <code>cbis.related_download.file_down_times</code>.
	 */
	public void setFileDownTimes(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>cbis.related_download.file_down_times</code>.
	 */
	public Integer getFileDownTimes() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>cbis.related_download.teach_type_id</code>.
	 */
	public void setTeachTypeId(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>cbis.related_download.teach_type_id</code>.
	 */
	@NotNull
	public Integer getTeachTypeId() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>cbis.related_download.file_user</code>.
	 */
	public void setFileUser(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>cbis.related_download.file_user</code>.
	 */
	@NotNull
	@Size(max = 64)
	public String getFileUser() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>cbis.related_download.file_type</code>.
	 */
	public void setFileType(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>cbis.related_download.file_type</code>.
	 */
	@Size(max = 15)
	public String getFileType() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>cbis.related_download.remark</code>.
	 */
	public void setRemark(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>cbis.related_download.remark</code>.
	 */
	@Size(max = 100)
	public String getRemark() {
		return (String) getValue(10);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record11 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Integer, String, String, String, Timestamp, Integer, Integer, String, String, String> fieldsRow() {
		return (Row11) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Integer, String, String, String, Timestamp, Integer, Integer, String, String, String> valuesRow() {
		return (Row11) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return RelatedDownload.RELATED_DOWNLOAD.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return RelatedDownload.RELATED_DOWNLOAD.TIE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return RelatedDownload.RELATED_DOWNLOAD.FILE_URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return RelatedDownload.RELATED_DOWNLOAD.FILE_SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return RelatedDownload.RELATED_DOWNLOAD.FILE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field6() {
		return RelatedDownload.RELATED_DOWNLOAD.FILE_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return RelatedDownload.RELATED_DOWNLOAD.FILE_DOWN_TIMES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return RelatedDownload.RELATED_DOWNLOAD.TEACH_TYPE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return RelatedDownload.RELATED_DOWNLOAD.FILE_USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return RelatedDownload.RELATED_DOWNLOAD.FILE_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return RelatedDownload.RELATED_DOWNLOAD.REMARK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getTieId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getFileUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getFileSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getFileName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value6() {
		return getFileDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getFileDownTimes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getTeachTypeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getFileUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getFileType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getRemark();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value2(Integer value) {
		setTieId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value3(String value) {
		setFileUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value4(String value) {
		setFileSize(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value5(String value) {
		setFileName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value6(Timestamp value) {
		setFileDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value7(Integer value) {
		setFileDownTimes(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value8(Integer value) {
		setTeachTypeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value9(String value) {
		setFileUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value10(String value) {
		setFileType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord value11(String value) {
		setRemark(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RelatedDownloadRecord values(Integer value1, Integer value2, String value3, String value4, String value5, Timestamp value6, Integer value7, Integer value8, String value9, String value10, String value11) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached RelatedDownloadRecord
	 */
	public RelatedDownloadRecord() {
		super(RelatedDownload.RELATED_DOWNLOAD);
	}

	/**
	 * Create a detached, initialised RelatedDownloadRecord
	 */
	public RelatedDownloadRecord(Integer id, Integer tieId, String fileUrl, String fileSize, String fileName, Timestamp fileDate, Integer fileDownTimes, Integer teachTypeId, String fileUser, String fileType, String remark) {
		super(RelatedDownload.RELATED_DOWNLOAD);

		setValue(0, id);
		setValue(1, tieId);
		setValue(2, fileUrl);
		setValue(3, fileSize);
		setValue(4, fileName);
		setValue(5, fileDate);
		setValue(6, fileDownTimes);
		setValue(7, teachTypeId);
		setValue(8, fileUser);
		setValue(9, fileType);
		setValue(10, remark);
	}
}
