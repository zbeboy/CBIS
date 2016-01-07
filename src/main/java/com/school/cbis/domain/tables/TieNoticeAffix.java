/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.TieNoticeAffixRecord;

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
		"jOOQ version:3.7.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TieNoticeAffix extends TableImpl<TieNoticeAffixRecord> {

	private static final long serialVersionUID = 669839384;

	/**
	 * The reference instance of <code>cbis.tie_notice_affix</code>
	 */
	public static final TieNoticeAffix TIE_NOTICE_AFFIX = new TieNoticeAffix();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TieNoticeAffixRecord> getRecordType() {
		return TieNoticeAffixRecord.class;
	}

	/**
	 * The column <code>cbis.tie_notice_affix.id</code>.
	 */
	public final TableField<TieNoticeAffixRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.tie_notice_affix.tie_notice_file_url</code>.
	 */
	public final TableField<TieNoticeAffixRecord, String> TIE_NOTICE_FILE_URL = createField("tie_notice_file_url", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

	/**
	 * The column <code>cbis.tie_notice_affix.tie_notice_file_size</code>.
	 */
	public final TableField<TieNoticeAffixRecord, String> TIE_NOTICE_FILE_SIZE = createField("tie_notice_file_size", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>cbis.tie_notice_affix.tie_notice_file_name</code>.
	 */
	public final TableField<TieNoticeAffixRecord, String> TIE_NOTICE_FILE_NAME = createField("tie_notice_file_name", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false), this, "");

	/**
	 * The column <code>cbis.tie_notice_affix.tie_notice_file_date</code>.
	 */
	public final TableField<TieNoticeAffixRecord, Timestamp> TIE_NOTICE_FILE_DATE = createField("tie_notice_file_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * The column <code>cbis.tie_notice_affix.tie_notice_id</code>.
	 */
	public final TableField<TieNoticeAffixRecord, Integer> TIE_NOTICE_ID = createField("tie_notice_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>cbis.tie_notice_affix</code> table reference
	 */
	public TieNoticeAffix() {
		this("tie_notice_affix", null);
	}

	/**
	 * Create an aliased <code>cbis.tie_notice_affix</code> table reference
	 */
	public TieNoticeAffix(String alias) {
		this(alias, TIE_NOTICE_AFFIX);
	}

	private TieNoticeAffix(String alias, Table<TieNoticeAffixRecord> aliased) {
		this(alias, aliased, null);
	}

	private TieNoticeAffix(String alias, Table<TieNoticeAffixRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<TieNoticeAffixRecord, Integer> getIdentity() {
		return Keys.IDENTITY_TIE_NOTICE_AFFIX;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TieNoticeAffixRecord> getPrimaryKey() {
		return Keys.KEY_TIE_NOTICE_AFFIX_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TieNoticeAffixRecord>> getKeys() {
		return Arrays.<UniqueKey<TieNoticeAffixRecord>>asList(Keys.KEY_TIE_NOTICE_AFFIX_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<TieNoticeAffixRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<TieNoticeAffixRecord, ?>>asList(Keys.TIE_NOTICE_AFFIX_IBFK_1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TieNoticeAffix as(String alias) {
		return new TieNoticeAffix(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TieNoticeAffix rename(String name) {
		return new TieNoticeAffix(name, null);
	}
}
