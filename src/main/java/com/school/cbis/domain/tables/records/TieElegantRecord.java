/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.records;


import com.school.cbis.domain.tables.TieElegant;

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
		"jOOQ version:3.7.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TieElegantRecord extends UpdatableRecordImpl<TieElegantRecord> implements Record3<Integer, Integer, String> {

	private static final long serialVersionUID = -1461714372;

	/**
	 * Setter for <code>cbis.tie_elegant.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>cbis.tie_elegant.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>cbis.tie_elegant.tie_id</code>.
	 */
	public void setTieId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>cbis.tie_elegant.tie_id</code>.
	 */
	public Integer getTieId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>cbis.tie_elegant.tie_elegant</code>.
	 */
	public void setTieElegant(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>cbis.tie_elegant.tie_elegant</code>.
	 */
	public String getTieElegant() {
		return (String) getValue(2);
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
	public Row3<Integer, Integer, String> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, Integer, String> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return TieElegant.TIE_ELEGANT.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return TieElegant.TIE_ELEGANT.TIE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return TieElegant.TIE_ELEGANT.TIE_ELEGANT_;
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
		return getTieElegant();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TieElegantRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TieElegantRecord value2(Integer value) {
		setTieId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TieElegantRecord value3(String value) {
		setTieElegant(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TieElegantRecord values(Integer value1, Integer value2, String value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TieElegantRecord
	 */
	public TieElegantRecord() {
		super(TieElegant.TIE_ELEGANT);
	}

	/**
	 * Create a detached, initialised TieElegantRecord
	 */
	public TieElegantRecord(Integer id, Integer tieId, String tieElegant) {
		super(TieElegant.TIE_ELEGANT);

		setValue(0, id);
		setValue(1, tieId);
		setValue(2, tieElegant);
	}
}
