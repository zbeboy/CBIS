/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.PlaceFileInfoRecord;

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
public class PlaceFileInfo extends TableImpl<PlaceFileInfoRecord> {

	private static final long serialVersionUID = 172840900;

	/**
	 * The reference instance of <code>cbis.place_file_info</code>
	 */
	public static final PlaceFileInfo PLACE_FILE_INFO = new PlaceFileInfo();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<PlaceFileInfoRecord> getRecordType() {
		return PlaceFileInfoRecord.class;
	}

	/**
	 * The column <code>cbis.place_file_info.id</code>.
	 */
	public final TableField<PlaceFileInfoRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.place_file_info.teach_task_info_id</code>.
	 */
	public final TableField<PlaceFileInfoRecord, Integer> TEACH_TASK_INFO_ID = createField("teach_task_info_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.place_file_info.place_file_info_url</code>.
	 */
	public final TableField<PlaceFileInfoRecord, String> PLACE_FILE_INFO_URL = createField("place_file_info_url", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

	/**
	 * The column <code>cbis.place_file_info.place_file_info_size</code>.
	 */
	public final TableField<PlaceFileInfoRecord, String> PLACE_FILE_INFO_SIZE = createField("place_file_info_size", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>cbis.place_file_info.place_file_info_name</code>.
	 */
	public final TableField<PlaceFileInfoRecord, String> PLACE_FILE_INFO_NAME = createField("place_file_info_name", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false), this, "");

	/**
	 * The column <code>cbis.place_file_info.place_file_info_date</code>.
	 */
	public final TableField<PlaceFileInfoRecord, Timestamp> PLACE_FILE_INFO_DATE = createField("place_file_info_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * Create a <code>cbis.place_file_info</code> table reference
	 */
	public PlaceFileInfo() {
		this("place_file_info", null);
	}

	/**
	 * Create an aliased <code>cbis.place_file_info</code> table reference
	 */
	public PlaceFileInfo(String alias) {
		this(alias, PLACE_FILE_INFO);
	}

	private PlaceFileInfo(String alias, Table<PlaceFileInfoRecord> aliased) {
		this(alias, aliased, null);
	}

	private PlaceFileInfo(String alias, Table<PlaceFileInfoRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<PlaceFileInfoRecord, Integer> getIdentity() {
		return Keys.IDENTITY_PLACE_FILE_INFO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<PlaceFileInfoRecord> getPrimaryKey() {
		return Keys.KEY_PLACE_FILE_INFO_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<PlaceFileInfoRecord>> getKeys() {
		return Arrays.<UniqueKey<PlaceFileInfoRecord>>asList(Keys.KEY_PLACE_FILE_INFO_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<PlaceFileInfoRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<PlaceFileInfoRecord, ?>>asList(Keys.PLACE_FILE_INFO_IBFK_1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlaceFileInfo as(String alias) {
		return new PlaceFileInfo(alias, this);
	}

	/**
	 * Rename this table
	 */
	public PlaceFileInfo rename(String name) {
		return new PlaceFileInfo(name, null);
	}
}