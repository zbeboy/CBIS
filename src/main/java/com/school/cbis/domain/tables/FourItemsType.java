/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.FourItemsTypeRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
public class FourItemsType extends TableImpl<FourItemsTypeRecord> {

	private static final long serialVersionUID = 361989752;

	/**
	 * The reference instance of <code>cbis.four_items_type</code>
	 */
	public static final FourItemsType FOUR_ITEMS_TYPE = new FourItemsType();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<FourItemsTypeRecord> getRecordType() {
		return FourItemsTypeRecord.class;
	}

	/**
	 * The column <code>cbis.four_items_type.id</code>.
	 */
	public final TableField<FourItemsTypeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.four_items_type.name</code>.
	 */
	public final TableField<FourItemsTypeRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(25).nullable(false), this, "");

	/**
	 * Create a <code>cbis.four_items_type</code> table reference
	 */
	public FourItemsType() {
		this("four_items_type", null);
	}

	/**
	 * Create an aliased <code>cbis.four_items_type</code> table reference
	 */
	public FourItemsType(String alias) {
		this(alias, FOUR_ITEMS_TYPE);
	}

	private FourItemsType(String alias, Table<FourItemsTypeRecord> aliased) {
		this(alias, aliased, null);
	}

	private FourItemsType(String alias, Table<FourItemsTypeRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<FourItemsTypeRecord, Integer> getIdentity() {
		return Keys.IDENTITY_FOUR_ITEMS_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<FourItemsTypeRecord> getPrimaryKey() {
		return Keys.KEY_FOUR_ITEMS_TYPE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<FourItemsTypeRecord>> getKeys() {
		return Arrays.<UniqueKey<FourItemsTypeRecord>>asList(Keys.KEY_FOUR_ITEMS_TYPE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FourItemsType as(String alias) {
		return new FourItemsType(alias, this);
	}

	/**
	 * Rename this table
	 */
	public FourItemsType rename(String name) {
		return new FourItemsType(name, null);
	}
}
