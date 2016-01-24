/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.records;


import com.school.cbis.domain.tables.SystemInform;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class SystemInformRecord extends UpdatableRecordImpl<SystemInformRecord> implements Record3<Integer, String, Integer> {

	private static final long serialVersionUID = 1895631456;

	/**
	 * Setter for <code>cbis.system_inform.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>cbis.system_inform.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>cbis.system_inform.system_inform</code>.
	 */
	public void setSystemInform(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>cbis.system_inform.system_inform</code>.
	 */
	public String getSystemInform() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>cbis.system_inform.system_inform_show</code>.
	 */
	public void setSystemInformShow(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>cbis.system_inform.system_inform_show</code>.
	 */
	public Integer getSystemInformShow() {
		return (Integer) getValue(2);
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
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, Integer> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, Integer> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return SystemInform.SYSTEM_INFORM.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return SystemInform.SYSTEM_INFORM.SYSTEM_INFORM_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return SystemInform.SYSTEM_INFORM.SYSTEM_INFORM_SHOW;
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
		return getSystemInform();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getSystemInformShow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SystemInformRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SystemInformRecord value2(String value) {
		setSystemInform(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SystemInformRecord value3(Integer value) {
		setSystemInformShow(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SystemInformRecord values(Integer value1, String value2, Integer value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached SystemInformRecord
	 */
	public SystemInformRecord() {
		super(SystemInform.SYSTEM_INFORM);
	}

	/**
	 * Create a detached, initialised SystemInformRecord
	 */
	public SystemInformRecord(Integer id, String systemInform, Integer systemInformShow) {
		super(SystemInform.SYSTEM_INFORM);

		setValue(0, id);
		setValue(1, systemInform);
		setValue(2, systemInformShow);
	}
}
