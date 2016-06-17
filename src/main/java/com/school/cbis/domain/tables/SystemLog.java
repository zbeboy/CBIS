/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.SystemLogRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SystemLog extends TableImpl<SystemLogRecord> {

	private static final long serialVersionUID = -36091124;

	/**
	 * The reference instance of <code>cbis.system_log</code>
	 */
	public static final SystemLog SYSTEM_LOG = new SystemLog();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<SystemLogRecord> getRecordType() {
		return SystemLogRecord.class;
	}

	/**
	 * The column <code>cbis.system_log.id</code>.
	 */
	public final TableField<SystemLogRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.system_log.username</code>. 操作人
	 */
	public final TableField<SystemLogRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "操作人");

	/**
	 * The column <code>cbis.system_log.operation_behavior</code>. 操作行为
	 */
	public final TableField<SystemLogRecord, String> OPERATION_BEHAVIOR = createField("operation_behavior", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "操作行为");

	/**
	 * The column <code>cbis.system_log.create_time</code>.
	 */
	public final TableField<SystemLogRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>cbis.system_log.tie_id</code>.
	 */
	public final TableField<SystemLogRecord, Integer> TIE_ID = createField("tie_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>cbis.system_log</code> table reference
	 */
	public SystemLog() {
		this("system_log", null);
	}

	/**
	 * Create an aliased <code>cbis.system_log</code> table reference
	 */
	public SystemLog(String alias) {
		this(alias, SYSTEM_LOG);
	}

	private SystemLog(String alias, Table<SystemLogRecord> aliased) {
		this(alias, aliased, null);
	}

	private SystemLog(String alias, Table<SystemLogRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<SystemLogRecord, Integer> getIdentity() {
		return Keys.IDENTITY_SYSTEM_LOG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<SystemLogRecord> getPrimaryKey() {
		return Keys.KEY_SYSTEM_LOG_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<SystemLogRecord>> getKeys() {
		return Arrays.<UniqueKey<SystemLogRecord>>asList(Keys.KEY_SYSTEM_LOG_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<SystemLogRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<SystemLogRecord, ?>>asList(Keys.SYSTEM_LOG_IBFK_1, Keys.SYSTEM_LOG_IBFK_2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SystemLog as(String alias) {
		return new SystemLog(alias, this);
	}

	/**
	 * Rename this table
	 */
	public SystemLog rename(String name) {
		return new SystemLog(name, null);
	}
}
