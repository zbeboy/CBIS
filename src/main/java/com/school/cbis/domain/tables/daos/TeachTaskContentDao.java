/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.TeachTaskContent;
import com.school.cbis.domain.tables.records.TeachTaskContentRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class TeachTaskContentDao extends DAOImpl<TeachTaskContentRecord, com.school.cbis.domain.tables.pojos.TeachTaskContent, Integer> {

	/**
	 * Create a new TeachTaskContentDao without any configuration
	 */
	public TeachTaskContentDao() {
		super(TeachTaskContent.TEACH_TASK_CONTENT, com.school.cbis.domain.tables.pojos.TeachTaskContent.class);
	}

	/**
	 * Create a new TeachTaskContentDao with an attached configuration
	 */
	public TeachTaskContentDao(Configuration configuration) {
		super(TeachTaskContent.TEACH_TASK_CONTENT, com.school.cbis.domain.tables.pojos.TeachTaskContent.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.TeachTaskContent object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchById(Integer... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.TeachTaskContent fetchOneById(Integer value) {
		return fetchOne(TeachTaskContent.TEACH_TASK_CONTENT.ID, value);
	}

	/**
	 * Fetch records that have <code>teach_task_title_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByTeachTaskTitleId(Integer... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.TEACH_TASK_TITLE_ID, values);
	}

	/**
	 * Fetch records that have <code>content IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContent(String... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT, values);
	}

	/**
	 * Fetch records that have <code>content_x IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentX(Integer... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_X, values);
	}

	/**
	 * Fetch records that have <code>content_y IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentY(Integer... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_Y, values);
	}

	/**
	 * Fetch records that have <code>content_lx IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentLx(Integer... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_LX, values);
	}

	/**
	 * Fetch records that have <code>content_ly IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentLy(Integer... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_LY, values);
	}

	/**
	 * Fetch records that have <code>content_font IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentFont(String... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_FONT, values);
	}

	/**
	 * Fetch records that have <code>content_font_size IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentFontSize(String... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_FONT_SIZE, values);
	}

	/**
	 * Fetch records that have <code>content_font_color IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentFontColor(String... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_FONT_COLOR, values);
	}

	/**
	 * Fetch records that have <code>content_font_background IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentFontBackground(String... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_FONT_BACKGROUND, values);
	}

	/**
	 * Fetch records that have <code>content_is_big IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentIsBig(Byte... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_IS_BIG, values);
	}

	/**
	 * Fetch records that have <code>content_is_italic IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByContentIsItalic(Byte... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.CONTENT_IS_ITALIC, values);
	}

	/**
	 * Fetch records that have <code>is_edit IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskContent> fetchByIsEdit(Byte... values) {
		return fetch(TeachTaskContent.TEACH_TASK_CONTENT.IS_EDIT, values);
	}
}