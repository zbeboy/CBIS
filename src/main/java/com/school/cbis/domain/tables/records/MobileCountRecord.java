/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.records;


import com.school.cbis.domain.tables.MobileCount;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class MobileCountRecord extends UpdatableRecordImpl<MobileCountRecord> implements Record5<Integer, String, String, Timestamp, String> {

	private static final long serialVersionUID = 359679471;

	/**
	 * Setter for <code>cbis.mobile_count.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>cbis.mobile_count.id</code>.
	 */
	@NotNull
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>cbis.mobile_count.accept_user</code>. 接收者
	 */
	public void setAcceptUser(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>cbis.mobile_count.accept_user</code>. 接收者
	 */
	@NotNull
	@Size(max = 64)
	public String getAcceptUser() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>cbis.mobile_count.content</code>. 内容
	 */
	public void setContent(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>cbis.mobile_count.content</code>. 内容
	 */
	@Size(max = 65535)
	public String getContent() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>cbis.mobile_count.send_time</code>.
	 */
	public void setSendTime(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>cbis.mobile_count.send_time</code>.
	 */
	@NotNull
	public Timestamp getSendTime() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>cbis.mobile_count.accept_mobile</code>. 接收手机
	 */
	public void setAcceptMobile(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>cbis.mobile_count.accept_mobile</code>. 接收手机
	 */
	@NotNull
	@Size(max = 64)
	public String getAcceptMobile() {
		return (String) getValue(4);
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
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, String, String, Timestamp, String> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, String, String, Timestamp, String> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return MobileCount.MOBILE_COUNT.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return MobileCount.MOBILE_COUNT.ACCEPT_USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return MobileCount.MOBILE_COUNT.CONTENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return MobileCount.MOBILE_COUNT.SEND_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return MobileCount.MOBILE_COUNT.ACCEPT_MOBILE;
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
		return getAcceptUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getContent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getSendTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getAcceptMobile();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MobileCountRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MobileCountRecord value2(String value) {
		setAcceptUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MobileCountRecord value3(String value) {
		setContent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MobileCountRecord value4(Timestamp value) {
		setSendTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MobileCountRecord value5(String value) {
		setAcceptMobile(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MobileCountRecord values(Integer value1, String value2, String value3, Timestamp value4, String value5) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MobileCountRecord
	 */
	public MobileCountRecord() {
		super(MobileCount.MOBILE_COUNT);
	}

	/**
	 * Create a detached, initialised MobileCountRecord
	 */
	public MobileCountRecord(Integer id, String acceptUser, String content, Timestamp sendTime, String acceptMobile) {
		super(MobileCount.MOBILE_COUNT);

		setValue(0, id);
		setValue(1, acceptUser);
		setValue(2, content);
		setValue(3, sendTime);
		setValue(4, acceptMobile);
	}
}
