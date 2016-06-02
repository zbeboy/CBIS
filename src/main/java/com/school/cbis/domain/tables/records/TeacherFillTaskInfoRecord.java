/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.records;


import com.school.cbis.domain.tables.TeacherFillTaskInfo;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


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
public class TeacherFillTaskInfoRecord extends UpdatableRecordImpl<TeacherFillTaskInfoRecord> implements Record9<Integer, String, Timestamp, Integer, Timestamp, Timestamp, String, Integer, Byte> {

	private static final long serialVersionUID = 2002223983;

	/**
	 * Setter for <code>cbis.teacher_fill_task_info.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>cbis.teacher_fill_task_info.id</code>.
	 */
	@NotNull
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>cbis.teacher_fill_task_info.title</code>.
	 */
	public void setTitle(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>cbis.teacher_fill_task_info.title</code>.
	 */
	@NotNull
	@Size(max = 100)
	public String getTitle() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>cbis.teacher_fill_task_info.create_time</code>.
	 */
	public void setCreateTime(Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>cbis.teacher_fill_task_info.create_time</code>.
	 */
	@NotNull
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>cbis.teacher_fill_task_info.teacher_fill_task_template_id</code>.
	 */
	public void setTeacherFillTaskTemplateId(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>cbis.teacher_fill_task_info.teacher_fill_task_template_id</code>.
	 */
	@NotNull
	public Integer getTeacherFillTaskTemplateId() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>cbis.teacher_fill_task_info.start_time</code>.
	 */
	public void setStartTime(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>cbis.teacher_fill_task_info.start_time</code>.
	 */
	@NotNull
	public Timestamp getStartTime() {
		return (Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>cbis.teacher_fill_task_info.end_time</code>.
	 */
	public void setEndTime(Timestamp value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>cbis.teacher_fill_task_info.end_time</code>.
	 */
	@NotNull
	public Timestamp getEndTime() {
		return (Timestamp) getValue(5);
	}

	/**
	 * Setter for <code>cbis.teacher_fill_task_info.users_id</code>.
	 */
	public void setUsersId(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>cbis.teacher_fill_task_info.users_id</code>.
	 */
	@NotNull
	@Size(max = 64)
	public String getUsersId() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>cbis.teacher_fill_task_info.tie_id</code>.
	 */
	public void setTieId(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>cbis.teacher_fill_task_info.tie_id</code>.
	 */
	@NotNull
	public Integer getTieId() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>cbis.teacher_fill_task_info.is_ok</code>. 是否系上已确认
	 */
	public void setIsOk(Byte value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>cbis.teacher_fill_task_info.is_ok</code>. 是否系上已确认
	 */
	@NotNull
	public Byte getIsOk() {
		return (Byte) getValue(8);
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
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row9<Integer, String, Timestamp, Integer, Timestamp, Timestamp, String, Integer, Byte> fieldsRow() {
		return (Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row9<Integer, String, Timestamp, Integer, Timestamp, Timestamp, String, Integer, Byte> valuesRow() {
		return (Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field3() {
		return TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO.TEACHER_FILL_TASK_TEMPLATE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO.START_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field6() {
		return TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO.END_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO.USERS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO.TIE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field9() {
		return TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO.IS_OK;
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
	public Integer value4() {
		return getTeacherFillTaskTemplateId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value5() {
		return getStartTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value6() {
		return getEndTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getUsersId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getTieId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value9() {
		return getIsOk();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord value2(String value) {
		setTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord value3(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord value4(Integer value) {
		setTeacherFillTaskTemplateId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord value5(Timestamp value) {
		setStartTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord value6(Timestamp value) {
		setEndTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord value7(String value) {
		setUsersId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord value8(Integer value) {
		setTieId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord value9(Byte value) {
		setIsOk(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeacherFillTaskInfoRecord values(Integer value1, String value2, Timestamp value3, Integer value4, Timestamp value5, Timestamp value6, String value7, Integer value8, Byte value9) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TeacherFillTaskInfoRecord
	 */
	public TeacherFillTaskInfoRecord() {
		super(TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO);
	}

	/**
	 * Create a detached, initialised TeacherFillTaskInfoRecord
	 */
	public TeacherFillTaskInfoRecord(Integer id, String title, Timestamp createTime, Integer teacherFillTaskTemplateId, Timestamp startTime, Timestamp endTime, String usersId, Integer tieId, Byte isOk) {
		super(TeacherFillTaskInfo.TEACHER_FILL_TASK_INFO);

		setValue(0, id);
		setValue(1, title);
		setValue(2, createTime);
		setValue(3, teacherFillTaskTemplateId);
		setValue(4, startTime);
		setValue(5, endTime);
		setValue(6, usersId);
		setValue(7, tieId);
		setValue(8, isOk);
	}
}