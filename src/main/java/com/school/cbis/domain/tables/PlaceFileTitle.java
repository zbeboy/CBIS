/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables;


import com.school.cbis.domain.Cbis;
import com.school.cbis.domain.Keys;
import com.school.cbis.domain.tables.records.PlaceFileTitleRecord;

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
public class PlaceFileTitle extends TableImpl<PlaceFileTitleRecord> {

	private static final long serialVersionUID = -1097442540;

	/**
	 * The reference instance of <code>cbis.place_file_title</code>
	 */
	public static final PlaceFileTitle PLACE_FILE_TITLE = new PlaceFileTitle();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<PlaceFileTitleRecord> getRecordType() {
		return PlaceFileTitleRecord.class;
	}

	/**
	 * The column <code>cbis.place_file_title.id</code>.
	 */
	public final TableField<PlaceFileTitleRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.place_file_title.title</code>.
	 */
	public final TableField<PlaceFileTitleRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(150), this, "");

	/**
	 * The column <code>cbis.place_file_title.title_x</code>.
	 */
	public final TableField<PlaceFileTitleRecord, Integer> TITLE_X = createField("title_x", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.place_file_title.title_y</code>.
	 */
	public final TableField<PlaceFileTitleRecord, Integer> TITLE_Y = createField("title_y", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>cbis.place_file_title.title_lx</code>.
	 */
	public final TableField<PlaceFileTitleRecord, Integer> TITLE_LX = createField("title_lx", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>cbis.place_file_title.title_ly</code>.
	 */
	public final TableField<PlaceFileTitleRecord, Integer> TITLE_LY = createField("title_ly", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>cbis.place_file_title.title_font</code>.
	 */
	public final TableField<PlaceFileTitleRecord, String> TITLE_FONT = createField("title_font", org.jooq.impl.SQLDataType.VARCHAR.length(25), this, "");

	/**
	 * The column <code>cbis.place_file_title.title_font_size</code>.
	 */
	public final TableField<PlaceFileTitleRecord, String> TITLE_FONT_SIZE = createField("title_font_size", org.jooq.impl.SQLDataType.VARCHAR.length(25), this, "");

	/**
	 * The column <code>cbis.place_file_title.title_font_color</code>.
	 */
	public final TableField<PlaceFileTitleRecord, String> TITLE_FONT_COLOR = createField("title_font_color", org.jooq.impl.SQLDataType.VARCHAR.length(25), this, "");

	/**
	 * The column <code>cbis.place_file_title.title_background</code>.
	 */
	public final TableField<PlaceFileTitleRecord, String> TITLE_BACKGROUND = createField("title_background", org.jooq.impl.SQLDataType.VARCHAR.length(25), this, "");

	/**
	 * The column <code>cbis.place_file_title.title_is_big</code>.
	 */
	public final TableField<PlaceFileTitleRecord, Byte> TITLE_IS_BIG = createField("title_is_big", org.jooq.impl.SQLDataType.TINYINT, this, "");

	/**
	 * The column <code>cbis.place_file_title.title_is_italic</code>.
	 */
	public final TableField<PlaceFileTitleRecord, Byte> TITLE_IS_ITALIC = createField("title_is_italic", org.jooq.impl.SQLDataType.TINYINT, this, "");

	/**
	 * The column <code>cbis.place_file_title.is_edit</code>.
	 */
	public final TableField<PlaceFileTitleRecord, Byte> IS_EDIT = createField("is_edit", org.jooq.impl.SQLDataType.TINYINT, this, "");

	/**
	 * The column <code>cbis.place_file_title.place_file_info_id</code>.
	 */
	public final TableField<PlaceFileTitleRecord, Integer> PLACE_FILE_INFO_ID = createField("place_file_info_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>cbis.place_file_title</code> table reference
	 */
	public PlaceFileTitle() {
		this("place_file_title", null);
	}

	/**
	 * Create an aliased <code>cbis.place_file_title</code> table reference
	 */
	public PlaceFileTitle(String alias) {
		this(alias, PLACE_FILE_TITLE);
	}

	private PlaceFileTitle(String alias, Table<PlaceFileTitleRecord> aliased) {
		this(alias, aliased, null);
	}

	private PlaceFileTitle(String alias, Table<PlaceFileTitleRecord> aliased, Field<?>[] parameters) {
		super(alias, Cbis.CBIS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<PlaceFileTitleRecord, Integer> getIdentity() {
		return Keys.IDENTITY_PLACE_FILE_TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<PlaceFileTitleRecord> getPrimaryKey() {
		return Keys.KEY_PLACE_FILE_TITLE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<PlaceFileTitleRecord>> getKeys() {
		return Arrays.<UniqueKey<PlaceFileTitleRecord>>asList(Keys.KEY_PLACE_FILE_TITLE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<PlaceFileTitleRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<PlaceFileTitleRecord, ?>>asList(Keys.PLACE_FILE_TITLE_IBFK_1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlaceFileTitle as(String alias) {
		return new PlaceFileTitle(alias, this);
	}

	/**
	 * Rename this table
	 */
	public PlaceFileTitle rename(String name) {
		return new PlaceFileTitle(name, null);
	}
}
