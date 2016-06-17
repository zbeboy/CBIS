/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.TeachingMaterialHeadRecord;

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
public class TeachingMaterialHead extends TableImpl<TeachingMaterialHeadRecord> {

	private static final long serialVersionUID = -136738086;

	/**
	 * The reference instance of <code>cbis.teaching_material_head</code>
	 */
	public static final TeachingMaterialHead TEACHING_MATERIAL_HEAD = new TeachingMaterialHead();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TeachingMaterialHeadRecord> getRecordType() {
		return TeachingMaterialHeadRecord.class;
	}

	/**
	 * The column <code>cbis.teaching_material_head.id</code>.
	 */
	public final TableField<TeachingMaterialHeadRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.teaching_material_head.title</code>.
	 */
	public final TableField<TeachingMaterialHeadRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>cbis.teaching_material_head.title_variable</code>.
	 */
	public final TableField<TeachingMaterialHeadRecord, String> TITLE_VARIABLE = createField("title_variable", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>cbis.teaching_material_head.teach_task_title_id</code>.
	 */
	public final TableField<TeachingMaterialHeadRecord, Integer> TEACH_TASK_TITLE_ID = createField("teach_task_title_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>cbis.teaching_material_head.teaching_material_template_id</code>.
	 */
	public final TableField<TeachingMaterialHeadRecord, Integer> TEACHING_MATERIAL_TEMPLATE_ID = createField("teaching_material_template_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.teaching_material_head.is_assignment</code>.
	 */
	public final TableField<TeachingMaterialHeadRecord, Byte> IS_ASSIGNMENT = createField("is_assignment", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "");

	/**
	 * The column <code>cbis.teaching_material_head.sort</code>.
	 */
	public final TableField<TeachingMaterialHeadRecord, Integer> SORT = createField("sort", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * Create a <code>cbis.teaching_material_head</code> table reference
	 */
	public TeachingMaterialHead() {
		this("teaching_material_head", null);
	}

	/**
	 * Create an aliased <code>cbis.teaching_material_head</code> table reference
	 */
	public TeachingMaterialHead(String alias) {
		this(alias, TEACHING_MATERIAL_HEAD);
	}

	private TeachingMaterialHead(String alias, Table<TeachingMaterialHeadRecord> aliased) {
		this(alias, aliased, null);
	}

	private TeachingMaterialHead(String alias, Table<TeachingMaterialHeadRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<TeachingMaterialHeadRecord, Integer> getIdentity() {
		return Keys.IDENTITY_TEACHING_MATERIAL_HEAD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TeachingMaterialHeadRecord> getPrimaryKey() {
		return Keys.KEY_TEACHING_MATERIAL_HEAD_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TeachingMaterialHeadRecord>> getKeys() {
		return Arrays.<UniqueKey<TeachingMaterialHeadRecord>>asList(Keys.KEY_TEACHING_MATERIAL_HEAD_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<TeachingMaterialHeadRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<TeachingMaterialHeadRecord, ?>>asList(Keys.TEACHING_MATERIAL_HEAD_IBFK_1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TeachingMaterialHead as(String alias) {
		return new TeachingMaterialHead(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TeachingMaterialHead rename(String name) {
		return new TeachingMaterialHead(name, null);
	}
}
