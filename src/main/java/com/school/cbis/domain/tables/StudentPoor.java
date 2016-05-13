/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.StudentPoorRecord;

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
public class StudentPoor extends TableImpl<StudentPoorRecord> {

	private static final long serialVersionUID = -1822346363;

	/**
	 * The reference instance of <code>cbis.student_poor</code>
	 */
	public static final StudentPoor STUDENT_POOR = new StudentPoor();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<StudentPoorRecord> getRecordType() {
		return StudentPoorRecord.class;
	}

	/**
	 * The column <code>cbis.student_poor.id</code>.
	 */
	public final TableField<StudentPoorRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.student_poor.title</code>. 是每年系统自动生成一条记录?(如:2015-2016学年贫困程度)
	 */
	public final TableField<StudentPoorRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "是每年系统自动生成一条记录?(如:2015-2016学年贫困程度)");

	/**
	 * The column <code>cbis.student_poor.content</code>.
	 */
	public final TableField<StudentPoorRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>cbis.student_poor.student_id</code>.
	 */
	public final TableField<StudentPoorRecord, Integer> STUDENT_ID = createField("student_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>cbis.student_poor</code> table reference
	 */
	public StudentPoor() {
		this("student_poor", null);
	}

	/**
	 * Create an aliased <code>cbis.student_poor</code> table reference
	 */
	public StudentPoor(String alias) {
		this(alias, STUDENT_POOR);
	}

	private StudentPoor(String alias, Table<StudentPoorRecord> aliased) {
		this(alias, aliased, null);
	}

	private StudentPoor(String alias, Table<StudentPoorRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<StudentPoorRecord, Integer> getIdentity() {
		return Keys.IDENTITY_STUDENT_POOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<StudentPoorRecord> getPrimaryKey() {
		return Keys.KEY_STUDENT_POOR_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<StudentPoorRecord>> getKeys() {
		return Arrays.<UniqueKey<StudentPoorRecord>>asList(Keys.KEY_STUDENT_POOR_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<StudentPoorRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<StudentPoorRecord, ?>>asList(Keys.STUDENT_POOR_IBFK_1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentPoor as(String alias) {
		return new StudentPoor(alias, this);
	}

	/**
	 * Rename this table
	 */
	public StudentPoor rename(String name) {
		return new StudentPoor(name, null);
	}
}
