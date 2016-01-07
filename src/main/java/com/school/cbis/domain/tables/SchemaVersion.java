/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.SchemaVersionRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
		"jOOQ version:3.7.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchemaVersion extends TableImpl<SchemaVersionRecord> {

	private static final long serialVersionUID = -1725248423;

	/**
	 * The reference instance of <code>cbis.schema_version</code>
	 */
	public static final SchemaVersion SCHEMA_VERSION = new SchemaVersion();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<SchemaVersionRecord> getRecordType() {
		return SchemaVersionRecord.class;
	}

	/**
	 * The column <code>cbis.schema_version.version_rank</code>.
	 */
	public final TableField<SchemaVersionRecord, Integer> VERSION_RANK = createField("version_rank", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.schema_version.installed_rank</code>.
	 */
	public final TableField<SchemaVersionRecord, Integer> INSTALLED_RANK = createField("installed_rank", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.schema_version.version</code>.
	 */
	public final TableField<SchemaVersionRecord, String> VERSION = createField("version", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>cbis.schema_version.description</code>.
	 */
	public final TableField<SchemaVersionRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

	/**
	 * The column <code>cbis.schema_version.type</code>.
	 */
	public final TableField<SchemaVersionRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>cbis.schema_version.script</code>.
	 */
	public final TableField<SchemaVersionRecord, String> SCRIPT = createField("script", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false), this, "");

	/**
	 * The column <code>cbis.schema_version.checksum</code>.
	 */
	public final TableField<SchemaVersionRecord, Integer> CHECKSUM = createField("checksum", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>cbis.schema_version.installed_by</code>.
	 */
	public final TableField<SchemaVersionRecord, String> INSTALLED_BY = createField("installed_by", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>cbis.schema_version.installed_on</code>.
	 */
	public final TableField<SchemaVersionRecord, Timestamp> INSTALLED_ON = createField("installed_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>cbis.schema_version.execution_time</code>.
	 */
	public final TableField<SchemaVersionRecord, Integer> EXECUTION_TIME = createField("execution_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.schema_version.success</code>.
	 */
	public final TableField<SchemaVersionRecord, Byte> SUCCESS = createField("success", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "");

	/**
	 * Create a <code>cbis.schema_version</code> table reference
	 */
	public SchemaVersion() {
		this("schema_version", null);
	}

	/**
	 * Create an aliased <code>cbis.schema_version</code> table reference
	 */
	public SchemaVersion(String alias) {
		this(alias, SCHEMA_VERSION);
	}

	private SchemaVersion(String alias, Table<SchemaVersionRecord> aliased) {
		this(alias, aliased, null);
	}

	private SchemaVersion(String alias, Table<SchemaVersionRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<SchemaVersionRecord> getPrimaryKey() {
		return Keys.KEY_SCHEMA_VERSION_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<SchemaVersionRecord>> getKeys() {
		return Arrays.<UniqueKey<SchemaVersionRecord>>asList(Keys.KEY_SCHEMA_VERSION_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersion as(String alias) {
		return new SchemaVersion(alias, this);
	}

	/**
	 * Rename this table
	 */
	public SchemaVersion rename(String name) {
		return new SchemaVersion(name, null);
	}
}
