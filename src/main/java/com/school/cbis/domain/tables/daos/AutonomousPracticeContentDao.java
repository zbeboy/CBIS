/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.AutonomousPracticeContent;
import com.school.cbis.domain.tables.records.AutonomousPracticeContentRecord;

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
public class AutonomousPracticeContentDao extends DAOImpl<AutonomousPracticeContentRecord, com.school.cbis.domain.tables.pojos.AutonomousPracticeContent, Integer> {

	/**
	 * Create a new AutonomousPracticeContentDao without any configuration
	 */
	public AutonomousPracticeContentDao() {
		super(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT, com.school.cbis.domain.tables.pojos.AutonomousPracticeContent.class);
	}

	/**
	 * Create a new AutonomousPracticeContentDao with an attached configuration
	 */
	public AutonomousPracticeContentDao(Configuration configuration) {
		super(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT, com.school.cbis.domain.tables.pojos.AutonomousPracticeContent.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.AutonomousPracticeContent object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeContent> fetchById(Integer... values) {
		return fetch(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.AutonomousPracticeContent fetchOneById(Integer value) {
		return fetchOne(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT.ID, value);
	}

	/**
	 * Fetch records that have <code>content IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeContent> fetchByContent(String... values) {
		return fetch(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT.CONTENT, values);
	}

	/**
	 * Fetch records that have <code>autonomous_practice_head_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeContent> fetchByAutonomousPracticeHeadId(Integer... values) {
		return fetch(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_HEAD_ID, values);
	}

	/**
	 * Fetch records that have <code>student_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeContent> fetchByStudentId(Integer... values) {
		return fetch(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT.STUDENT_ID, values);
	}

	/**
	 * Fetch records that have <code>autonomous_practice_info_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.AutonomousPracticeContent> fetchByAutonomousPracticeInfoId(Integer... values) {
		return fetch(AutonomousPracticeContent.AUTONOMOUS_PRACTICE_CONTENT.AUTONOMOUS_PRACTICE_INFO_ID, values);
	}
}
