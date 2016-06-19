/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.ExaminationArrangementTemplateRecord;

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
public class ExaminationArrangementTemplate extends TableImpl<ExaminationArrangementTemplateRecord> {

	private static final long serialVersionUID = 1121376626;

	/**
	 * The reference instance of <code>cbis.examination_arrangement_template</code>
	 */
	public static final ExaminationArrangementTemplate EXAMINATION_ARRANGEMENT_TEMPLATE = new ExaminationArrangementTemplate();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ExaminationArrangementTemplateRecord> getRecordType() {
		return ExaminationArrangementTemplateRecord.class;
	}

	/**
	 * The column <code>cbis.examination_arrangement_template.id</code>.
	 */
	public final TableField<ExaminationArrangementTemplateRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.examination_arrangement_template.title</code>.
	 */
	public final TableField<ExaminationArrangementTemplateRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>cbis.examination_arrangement_template.create_time</code>.
	 */
	public final TableField<ExaminationArrangementTemplateRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>cbis.examination_arrangement_template.create_user</code>.
	 */
	public final TableField<ExaminationArrangementTemplateRecord, String> CREATE_USER = createField("create_user", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * The column <code>cbis.examination_arrangement_template.tie_id</code>.
	 */
	public final TableField<ExaminationArrangementTemplateRecord, Integer> TIE_ID = createField("tie_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.examination_arrangement_template.teach_task_info_id</code>.
	 */
	public final TableField<ExaminationArrangementTemplateRecord, Integer> TEACH_TASK_INFO_ID = createField("teach_task_info_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>cbis.examination_arrangement_template</code> table reference
	 */
	public ExaminationArrangementTemplate() {
		this("examination_arrangement_template", null);
	}

	/**
	 * Create an aliased <code>cbis.examination_arrangement_template</code> table reference
	 */
	public ExaminationArrangementTemplate(String alias) {
		this(alias, EXAMINATION_ARRANGEMENT_TEMPLATE);
	}

	private ExaminationArrangementTemplate(String alias, Table<ExaminationArrangementTemplateRecord> aliased) {
		this(alias, aliased, null);
	}

	private ExaminationArrangementTemplate(String alias, Table<ExaminationArrangementTemplateRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ExaminationArrangementTemplateRecord, Integer> getIdentity() {
		return Keys.IDENTITY_EXAMINATION_ARRANGEMENT_TEMPLATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ExaminationArrangementTemplateRecord> getPrimaryKey() {
		return Keys.KEY_EXAMINATION_ARRANGEMENT_TEMPLATE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ExaminationArrangementTemplateRecord>> getKeys() {
		return Arrays.<UniqueKey<ExaminationArrangementTemplateRecord>>asList(Keys.KEY_EXAMINATION_ARRANGEMENT_TEMPLATE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<ExaminationArrangementTemplateRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<ExaminationArrangementTemplateRecord, ?>>asList(Keys.EXAMINATION_ARRANGEMENT_TEMPLATE_IBFK_1, Keys.EXAMINATION_ARRANGEMENT_TEMPLATE_IBFK_2, Keys.EXAMINATION_ARRANGEMENT_TEMPLATE_IBFK_3);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationArrangementTemplate as(String alias) {
		return new ExaminationArrangementTemplate(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ExaminationArrangementTemplate rename(String name) {
		return new ExaminationArrangementTemplate(name, null);
	}
}
