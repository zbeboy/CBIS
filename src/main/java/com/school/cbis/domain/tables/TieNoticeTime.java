/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.TieNoticeTimeRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class TieNoticeTime extends TableImpl<TieNoticeTimeRecord> {

	private static final long serialVersionUID = 1083129542;

	/**
	 * The reference instance of <code>cbis.tie_notice_time</code>
	 */
	public static final TieNoticeTime TIE_NOTICE_TIME = new TieNoticeTime();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TieNoticeTimeRecord> getRecordType() {
		return TieNoticeTimeRecord.class;
	}

	/**
	 * The column <code>cbis.tie_notice_time.id</code>.
	 */
	public final TableField<TieNoticeTimeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.tie_notice_time.time</code>.
	 */
	public final TableField<TieNoticeTimeRecord, String> TIME = createField("time", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false), this, "");

	/**
	 * Create a <code>cbis.tie_notice_time</code> table reference
	 */
	public TieNoticeTime() {
		this("tie_notice_time", null);
	}

	/**
	 * Create an aliased <code>cbis.tie_notice_time</code> table reference
	 */
	public TieNoticeTime(String alias) {
		this(alias, TIE_NOTICE_TIME);
	}

	private TieNoticeTime(String alias, Table<TieNoticeTimeRecord> aliased) {
		this(alias, aliased, null);
	}

	private TieNoticeTime(String alias, Table<TieNoticeTimeRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<TieNoticeTimeRecord, Integer> getIdentity() {
		return Keys.IDENTITY_TIE_NOTICE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TieNoticeTimeRecord> getPrimaryKey() {
		return Keys.KEY_TIE_NOTICE_TIME_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TieNoticeTimeRecord>> getKeys() {
		return Arrays.<UniqueKey<TieNoticeTimeRecord>>asList(Keys.KEY_TIE_NOTICE_TIME_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TieNoticeTime as(String alias) {
		return new TieNoticeTime(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TieNoticeTime rename(String name) {
		return new TieNoticeTime(name, null);
	}
}
