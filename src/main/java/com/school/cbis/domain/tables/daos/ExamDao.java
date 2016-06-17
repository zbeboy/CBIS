/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.Exam;
import com.school.cbis.domain.tables.records.ExamRecord;

import java.sql.Timestamp;
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
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ExamDao extends DAOImpl<ExamRecord, com.school.cbis.domain.tables.pojos.Exam, Integer> {

	/**
	 * Create a new ExamDao without any configuration
	 */
	public ExamDao() {
		super(Exam.EXAM, com.school.cbis.domain.tables.pojos.Exam.class);
	}

	/**
	 * Create a new ExamDao with an attached configuration
	 */
	public ExamDao(Configuration configuration) {
		super(Exam.EXAM, com.school.cbis.domain.tables.pojos.Exam.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.Exam object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Exam> fetchById(Integer... values) {
		return fetch(Exam.EXAM.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.Exam fetchOneById(Integer value) {
		return fetchOne(Exam.EXAM.ID, value);
	}

	/**
	 * Fetch records that have <code>exam_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Exam> fetchByExamTime(Timestamp... values) {
		return fetch(Exam.EXAM.EXAM_TIME, values);
	}

	/**
	 * Fetch records that have <code>exam_address IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Exam> fetchByExamAddress(String... values) {
		return fetch(Exam.EXAM.EXAM_ADDRESS, values);
	}

	/**
	 * Fetch records that have <code>exam_content IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Exam> fetchByExamContent(String... values) {
		return fetch(Exam.EXAM.EXAM_CONTENT, values);
	}

	/**
	 * Fetch records that have <code>exam_title IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Exam> fetchByExamTitle(String... values) {
		return fetch(Exam.EXAM.EXAM_TITLE, values);
	}

	/**
	 * Fetch records that have <code>major_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Exam> fetchByMajorId(Integer... values) {
		return fetch(Exam.EXAM.MAJOR_ID, values);
	}

	/**
	 * Fetch records that have <code>tie_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Exam> fetchByTieId(Integer... values) {
		return fetch(Exam.EXAM.TIE_ID, values);
	}

	/**
	 * Fetch records that have <code>username IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Exam> fetchByUsername(String... values) {
		return fetch(Exam.EXAM.USERNAME, values);
	}

	/**
	 * Fetch records that have <code>create_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Exam> fetchByCreateTime(Timestamp... values) {
		return fetch(Exam.EXAM.CREATE_TIME, values);
	}
}
