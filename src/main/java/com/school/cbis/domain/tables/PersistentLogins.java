/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.PersistentLoginsRecord;

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
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PersistentLogins extends TableImpl<PersistentLoginsRecord> {

	private static final long serialVersionUID = 1269720947;

	/**
	 * The reference instance of <code>cbis.persistent_logins</code>
	 */
	public static final PersistentLogins PERSISTENT_LOGINS = new PersistentLogins();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<PersistentLoginsRecord> getRecordType() {
		return PersistentLoginsRecord.class;
	}

	/**
	 * The column <code>cbis.persistent_logins.username</code>.
	 */
	public final TableField<PersistentLoginsRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * The column <code>cbis.persistent_logins.series</code>.
	 */
	public final TableField<PersistentLoginsRecord, String> SERIES = createField("series", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * The column <code>cbis.persistent_logins.token</code>.
	 */
	public final TableField<PersistentLoginsRecord, String> TOKEN = createField("token", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * The column <code>cbis.persistent_logins.last_used</code>.
	 */
	public final TableField<PersistentLoginsRecord, Timestamp> LAST_USED = createField("last_used", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>cbis.persistent_logins</code> table reference
	 */
	public PersistentLogins() {
		this("persistent_logins", null);
	}

	/**
	 * Create an aliased <code>cbis.persistent_logins</code> table reference
	 */
	public PersistentLogins(String alias) {
		this(alias, PERSISTENT_LOGINS);
	}

	private PersistentLogins(String alias, Table<PersistentLoginsRecord> aliased) {
		this(alias, aliased, null);
	}

	private PersistentLogins(String alias, Table<PersistentLoginsRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<PersistentLoginsRecord> getPrimaryKey() {
		return Keys.KEY_PERSISTENT_LOGINS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<PersistentLoginsRecord>> getKeys() {
		return Arrays.<UniqueKey<PersistentLoginsRecord>>asList(Keys.KEY_PERSISTENT_LOGINS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PersistentLogins as(String alias) {
		return new PersistentLogins(alias, this);
	}

	/**
	 * Rename this table
	 */
	public PersistentLogins rename(String name) {
		return new PersistentLogins(name, null);
	}
}
