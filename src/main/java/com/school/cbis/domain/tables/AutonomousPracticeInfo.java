/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.AutonomousPracticeInfoRecord;

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
public class AutonomousPracticeInfo extends TableImpl<AutonomousPracticeInfoRecord> {

	private static final long serialVersionUID = -1178769116;

	/**
	 * The reference instance of <code>cbis.autonomous_practice_info</code>
	 */
	public static final AutonomousPracticeInfo AUTONOMOUS_PRACTICE_INFO = new AutonomousPracticeInfo();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<AutonomousPracticeInfoRecord> getRecordType() {
		return AutonomousPracticeInfoRecord.class;
	}

	/**
	 * The column <code>cbis.autonomous_practice_info.id</code>.
	 */
	public final TableField<AutonomousPracticeInfoRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_info.autonomous_practice_title</code>.
	 */
	public final TableField<AutonomousPracticeInfoRecord, String> AUTONOMOUS_PRACTICE_TITLE = createField("autonomous_practice_title", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_info.create_time</code>.
	 */
	public final TableField<AutonomousPracticeInfoRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_info.grade_year</code>.
	 */
	public final TableField<AutonomousPracticeInfoRecord, String> GRADE_YEAR = createField("grade_year", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_info.autonomous_practice_template_id</code>.
	 */
	public final TableField<AutonomousPracticeInfoRecord, Integer> AUTONOMOUS_PRACTICE_TEMPLATE_ID = createField("autonomous_practice_template_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>cbis.autonomous_practice_info.start_time</code>.
	 */
	public final TableField<AutonomousPracticeInfoRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_info.end_time</code>.
	 */
	public final TableField<AutonomousPracticeInfoRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_info.users_id</code>.
	 */
	public final TableField<AutonomousPracticeInfoRecord, String> USERS_ID = createField("users_id", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_info.tie_id</code>.
	 */
	public final TableField<AutonomousPracticeInfoRecord, Integer> TIE_ID = createField("tie_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>cbis.autonomous_practice_info</code> table reference
	 */
	public AutonomousPracticeInfo() {
		this("autonomous_practice_info", null);
	}

	/**
	 * Create an aliased <code>cbis.autonomous_practice_info</code> table reference
	 */
	public AutonomousPracticeInfo(String alias) {
		this(alias, AUTONOMOUS_PRACTICE_INFO);
	}

	private AutonomousPracticeInfo(String alias, Table<AutonomousPracticeInfoRecord> aliased) {
		this(alias, aliased, null);
	}

	private AutonomousPracticeInfo(String alias, Table<AutonomousPracticeInfoRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<AutonomousPracticeInfoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_AUTONOMOUS_PRACTICE_INFO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<AutonomousPracticeInfoRecord> getPrimaryKey() {
		return Keys.KEY_AUTONOMOUS_PRACTICE_INFO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<AutonomousPracticeInfoRecord>> getKeys() {
		return Arrays.<UniqueKey<AutonomousPracticeInfoRecord>>asList(Keys.KEY_AUTONOMOUS_PRACTICE_INFO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<AutonomousPracticeInfoRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<AutonomousPracticeInfoRecord, ?>>asList(Keys.AUTONOMOUS_PRACTICE_INFO_IBFK_3, Keys.AUTONOMOUS_PRACTICE_INFO_IBFK_1, Keys.AUTONOMOUS_PRACTICE_INFO_IBFK_2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutonomousPracticeInfo as(String alias) {
		return new AutonomousPracticeInfo(alias, this);
	}

	/**
	 * Rename this table
	 */
	public AutonomousPracticeInfo rename(String name) {
		return new AutonomousPracticeInfo(name, null);
	}
}
