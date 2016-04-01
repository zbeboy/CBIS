/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.records;


import com.school.cbis.domain.tables.AutonomousPracticeContent;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class AutonomousPracticeContentRecord extends UpdatableRecordImpl<AutonomousPracticeContentRecord> implements Record3<Integer, String, Integer> {

	private static final long serialVersionUID = 734607037;

	/**
	 * Setter for <code>cbis.autonomous_practice_content.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>cbis.autonomous_practice_content.id</code>.
	 */
	@NotNull
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>cbis.autonomous_practice_content.cotent</code>.
	 */
	public void setCotent(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>cbis.autonomous_practice_content.cotent</code>.
	 */
	@Size(max = 200)
	public String getCotent() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>cbis.autonomous_practice_content.autonomous_practice_head_id</code>.
	 */
	public void setAutonomousPracticeHeadId(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>cbis.autonomous_practice_content.autonomous_practice_head_id</code>.
	 */
	@NotNull
	public Integer getAutonomousPracticeHeadId() {
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
		return AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT.COTENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID;
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
		return getCotent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getAutonomousPracticeHeadId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutonomousPracticeContentRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutonomousPracticeContentRecord value2(String value) {
		setCotent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutonomousPracticeContentRecord value3(Integer value) {
		setAutonomousPracticeHeadId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutonomousPracticeContentRecord values(Integer value1, String value2, Integer value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AutonomousPracticeContentRecord
	 */
	public AutonomousPracticeContentRecord() {
		super(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT);
	}

	/**
	 * Create a detached, initialised AutonomousPracticeContentRecord
	 */
	public AutonomousPracticeContentRecord(Integer id, String cotent, Integer autonomousPracticeHeadId) {
		super(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT);

		setValue(0, id);
		setValue(1, cotent);
		setValue(2, autonomousPracticeHeadId);
	}
}
