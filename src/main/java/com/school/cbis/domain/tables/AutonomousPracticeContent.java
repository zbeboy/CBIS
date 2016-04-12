/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.AutonomousPracticeContentRecord;

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
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AutonomousPracticeContent extends TableImpl<AutonomousPracticeContentRecord> {

	private static final long serialVersionUID = 1158885233;

	/**
	 * The reference instance of <code>cbis.autonomous_practice_content</code>
	 */
	public static final AutonomousPracticeContent AUTONOMOUS_PRACTICE_CONTENT = new AutonomousPracticeContent();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<AutonomousPracticeContentRecord> getRecordType() {
		return AutonomousPracticeContentRecord.class;
	}

	/**
	 * The column <code>cbis.autonomous_practice_content.id</code>.
	 */
	public final TableField<AutonomousPracticeContentRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_content.cotent</code>.
	 */
	public final TableField<AutonomousPracticeContentRecord, String> COTENT = createField("cotent", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_content.row_older</code>.
	 */
	public final TableField<AutonomousPracticeContentRecord, Integer> ROW_OLDER = createField("row_older", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_content.autonomous_practice_head_id</code>.
	 */
	public final TableField<AutonomousPracticeContentRecord, Integer> AUTONOMOUS_PRACTICE_HEAD_ID = createField("autonomous_practice_head_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.autonomous_practice_content.student_id</code>.
	 */
	public final TableField<AutonomousPracticeContentRecord, Integer> STUDENT_ID = createField("student_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>cbis.autonomous_practice_content</code> table reference
	 */
	public AutonomousPracticeContent() {
		this("autonomous_practice_content", null);
	}

	/**
	 * Create an aliased <code>cbis.autonomous_practice_content</code> table reference
	 */
	public AutonomousPracticeContent(String alias) {
		this(alias, AUTONOMOUS_PRACTICE_CONTENT);
	}

	private AutonomousPracticeContent(String alias, Table<AutonomousPracticeContentRecord> aliased) {
		this(alias, aliased, null);
	}

	private AutonomousPracticeContent(String alias, Table<AutonomousPracticeContentRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<AutonomousPracticeContentRecord, Integer> getIdentity() {
		return Keys.IDENTITY_AUTONOMOUS_PRACTICE_CONTENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<AutonomousPracticeContentRecord> getPrimaryKey() {
		return Keys.KEY_AUTONOMOUS_PRACTICE_CONTENT_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<AutonomousPracticeContentRecord>> getKeys() {
		return Arrays.<UniqueKey<AutonomousPracticeContentRecord>>asList(Keys.KEY_AUTONOMOUS_PRACTICE_CONTENT_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<AutonomousPracticeContentRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<AutonomousPracticeContentRecord, ?>>asList(Keys.AUTONOMOUS_PRACTICE_CONTENT_IBFK_1, Keys.AUTONOMOUS_PRACTICE_CONTENT_IBFK_2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutonomousPracticeContent as(String alias) {
		return new AutonomousPracticeContent(alias, this);
	}

	/**
	 * Rename this table
	 */
	public AutonomousPracticeContent rename(String name) {
		return new AutonomousPracticeContent(name, null);
	}
}
