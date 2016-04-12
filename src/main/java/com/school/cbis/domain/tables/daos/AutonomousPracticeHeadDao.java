/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.AutonomousPracticeHead;
import com.school.cbis.domain.tables.records.AutonomousPracticeHeadRecord;

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
public class AutonomousPracticeHeadDao extends DAOImpl<AutonomousPracticeHeadRecord, com.school.cbis.domain.tables.pojos.AutonomousPracticeHead, Integer> {

	/**
	 * Create a new AutonomousPracticeHeadDao without any configuration
	 */
	public AutonomousPracticeHeadDao() {
		super(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD, com.school.cbis.domain.tables.pojos.AutonomousPracticeHead.class);
	}

	/**
	 * Create a new AutonomousPracticeHeadDao with an attached configuration
	 */
	public AutonomousPracticeHeadDao(Configuration configuration) {
		super(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD, com.school.cbis.domain.tables.pojos.AutonomousPracticeHead.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.AutonomousPracticeHead object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchById(Integer... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.AutonomousPracticeHead fetchOneById(Integer value) {
		return fetchOne(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.ID, value);
	}

	/**
	 * Fetch records that have <code>title IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByTitle(String... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.TITLE, values);
	}

	/**
	 * Fetch records that have <code>title_variable IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByTitleVariable(String... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.TITLE_VARIABLE, values);
	}

	/**
	 * Fetch records that have <code>database_table IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByDatabaseTable(String... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.DATABASE_TABLE, values);
	}

	/**
	 * Fetch records that have <code>database_table_field IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByDatabaseTableField(String... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.DATABASE_TABLE_FIELD, values);
	}

	/**
	 * Fetch records that have <code>authority IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByAuthority(String... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.AUTHORITY, values);
	}

	/**
	 * Fetch records that have <code>is_show_highly_active IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByIsShowHighlyActive(Byte... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.IS_SHOW_HIGHLY_ACTIVE, values);
	}

	/**
	 * Fetch records that have <code>is_database IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByIsDatabase(Byte... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.IS_DATABASE, values);
	}

	/**
	 * Fetch records that have <code>content IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByContent(String... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.CONTENT, values);
	}

	/**
	 * Fetch records that have <code>head_type_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByHeadTypeId(Integer... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.HEAD_TYPE_ID, values);
	}

	/**
	 * Fetch records that have <code>autonomous_practice_info_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeHead> fetchByAutonomousPracticeInfoId(Integer... values) {
		return fetch(AutonomousPracticeHead.AUTONOMOUS_PRACTICE_HEAD.AUTONOMOUS_PRACTICE_INFO_ID, values);
	}
}
