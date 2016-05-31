/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.TeachTaskGradeCheckRecord;

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
public class TeachTaskGradeCheck extends TableImpl<TeachTaskGradeCheckRecord> {

	private static final long serialVersionUID = -930638614;

	/**
	 * The reference instance of <code>cbis.teach_task_grade_check</code>
	 */
	public static final TeachTaskGradeCheck TEACH_TASK_GRADE_CHECK = new TeachTaskGradeCheck();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TeachTaskGradeCheckRecord> getRecordType() {
		return TeachTaskGradeCheckRecord.class;
	}

	/**
	 * The column <code>cbis.teach_task_grade_check.id</code>.
	 */
	public final TableField<TeachTaskGradeCheckRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.teach_task_grade_check.row_x</code>.
	 */
	public final TableField<TeachTaskGradeCheckRecord, Integer> ROW_X = createField("row_x", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.teach_task_grade_check.teach_task_info_id</code>.
	 */
	public final TableField<TeachTaskGradeCheckRecord, Integer> TEACH_TASK_INFO_ID = createField("teach_task_info_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.teach_task_grade_check.grade</code>.
	 */
	public final TableField<TeachTaskGradeCheckRecord, String> GRADE = createField("grade", org.jooq.impl.SQLDataType.VARCHAR.length(500), this, "");

	/**
	 * The column <code>cbis.teach_task_grade_check.grade_num</code>.
	 */
	public final TableField<TeachTaskGradeCheckRecord, Integer> GRADE_NUM = createField("grade_num", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>cbis.teach_task_grade_check.check_is_right</code>. 是否存在于数据库中
	 */
	public final TableField<TeachTaskGradeCheckRecord, Byte> CHECK_IS_RIGHT = createField("check_is_right", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "是否存在于数据库中");

	/**
	 * The column <code>cbis.teach_task_grade_check.database_grade_id</code>. 数据库中该班级id
	 */
	public final TableField<TeachTaskGradeCheckRecord, Integer> DATABASE_GRADE_ID = createField("database_grade_id", org.jooq.impl.SQLDataType.INTEGER, this, "数据库中该班级id");

	/**
	 * Create a <code>cbis.teach_task_grade_check</code> table reference
	 */
	public TeachTaskGradeCheck() {
		this("teach_task_grade_check", null);
	}

	/**
	 * Create an aliased <code>cbis.teach_task_grade_check</code> table reference
	 */
	public TeachTaskGradeCheck(String alias) {
		this(alias, TEACH_TASK_GRADE_CHECK);
	}

	private TeachTaskGradeCheck(String alias, Table<TeachTaskGradeCheckRecord> aliased) {
		this(alias, aliased, null);
	}

	private TeachTaskGradeCheck(String alias, Table<TeachTaskGradeCheckRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<TeachTaskGradeCheckRecord, Integer> getIdentity() {
		return Keys.IDENTITY_TEACH_TASK_GRADE_CHECK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TeachTaskGradeCheckRecord> getPrimaryKey() {
		return Keys.KEY_TEACH_TASK_GRADE_CHECK_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TeachTaskGradeCheckRecord>> getKeys() {
		return Arrays.<UniqueKey<TeachTaskGradeCheckRecord>>asList(Keys.KEY_TEACH_TASK_GRADE_CHECK_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<TeachTaskGradeCheckRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<TeachTaskGradeCheckRecord, ?>>asList(Keys.TEACH_TASK_GRADE_CHECK_IBFK_1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeachTaskGradeCheck as(String alias) {
		return new TeachTaskGradeCheck(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TeachTaskGradeCheck rename(String name) {
		return new TeachTaskGradeCheck(name, null);
	}
}
