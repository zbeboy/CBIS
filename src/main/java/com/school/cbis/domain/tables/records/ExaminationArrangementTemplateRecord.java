/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.records;


import com.school.cbis.domain.tables.ExaminationArrangementTemplate;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class ExaminationArrangementTemplateRecord extends UpdatableRecordImpl<ExaminationArrangementTemplateRecord> implements Record6<Integer, String, Timestamp, String, Integer, Integer> {

	private static final long serialVersionUID = 460344975;

	/**
	 * Setter for <code>cbis.examination_arrangement_template.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>cbis.examination_arrangement_template.id</code>.
	 */
	@NotNull
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>cbis.examination_arrangement_template.title</code>.
	 */
	public void setTitle(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>cbis.examination_arrangement_template.title</code>.
	 */
	@NotNull
	@Size(max = 50)
	public String getTitle() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>cbis.examination_arrangement_template.create_time</code>.
	 */
	public void setCreateTime(Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>cbis.examination_arrangement_template.create_time</code>.
	 */
	@NotNull
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>cbis.examination_arrangement_template.create_user</code>.
	 */
	public void setCreateUser(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>cbis.examination_arrangement_template.create_user</code>.
	 */
	@NotNull
	@Size(max = 64)
	public String getCreateUser() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>cbis.examination_arrangement_template.tie_id</code>.
	 */
	public void setTieId(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>cbis.examination_arrangement_template.tie_id</code>.
	 */
	@NotNull
	public Integer getTieId() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>cbis.examination_arrangement_template.teach_task_info_id</code>.
	 */
	public void setTeachTaskInfoId(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>cbis.examination_arrangement_template.teach_task_info_id</code>.
	 */
	@NotNull
	public Integer getTeachTaskInfoId() {
		return (Integer) getValue(5);
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
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, String, Timestamp, String, Integer, Integer> fieldsRow() {
		return (Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, String, Timestamp, String, Integer, Integer> valuesRow() {
		return (Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field3() {
		return ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.CREATE_USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field5() {
		return ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.TIE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.TEACH_TASK_INFO_ID;
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
	public String value2() {
		return getTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value3() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getCreateUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value5() {
		return getTieId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value6() {
		return getTeachTaskInfoId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationArrangementTemplateRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationArrangementTemplateRecord value2(String value) {
		setTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationArrangementTemplateRecord value3(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationArrangementTemplateRecord value4(String value) {
		setCreateUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationArrangementTemplateRecord value5(Integer value) {
		setTieId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationArrangementTemplateRecord value6(Integer value) {
		setTeachTaskInfoId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationArrangementTemplateRecord values(Integer value1, String value2, Timestamp value3, String value4, Integer value5, Integer value6) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ExaminationArrangementTemplateRecord
	 */
	public ExaminationArrangementTemplateRecord() {
		super(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE);
	}

	/**
	 * Create a detached, initialised ExaminationArrangementTemplateRecord
	 */
	public ExaminationArrangementTemplateRecord(Integer id, String title, Timestamp createTime, String createUser, Integer tieId, Integer teachTaskInfoId) {
		super(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE);

		setValue(0, id);
		setValue(1, title);
		setValue(2, createTime);
		setValue(3, createUser);
		setValue(4, tieId);
		setValue(5, teachTaskInfoId);
	}
}
