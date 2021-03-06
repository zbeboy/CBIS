/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.ExaminationArrangementInfo;
import com.school.cbis.domain.tables.records.ExaminationArrangementInfoRecord;

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
public class ExaminationArrangementInfoDao extends DAOImpl<ExaminationArrangementInfoRecord, com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo, Integer> {

	/**
	 * Create a new ExaminationArrangementInfoDao without any configuration
	 */
	public ExaminationArrangementInfoDao() {
		super(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO, com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo.class);
	}

	/**
	 * Create a new ExaminationArrangementInfoDao with an attached configuration
	 */
	public ExaminationArrangementInfoDao(Configuration configuration) {
		super(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO, com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo> fetchById(Integer... values) {
		return fetch(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo fetchOneById(Integer value) {
		return fetchOne(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO.ID, value);
	}

	/**
	 * Fetch records that have <code>title IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo> fetchByTitle(String... values) {
		return fetch(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO.TITLE, values);
	}

	/**
	 * Fetch records that have <code>create_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo> fetchByCreateTime(Timestamp... values) {
		return fetch(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO.CREATE_TIME, values);
	}

	/**
	 * Fetch records that have <code>examination_arrangement_template_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo> fetchByExaminationArrangementTemplateId(Integer... values) {
		return fetch(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO.EXAMINATION_ARRANGEMENT_TEMPLATE_ID, values);
	}

	/**
	 * Fetch records that have <code>start_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo> fetchByStartTime(Timestamp... values) {
		return fetch(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO.START_TIME, values);
	}

	/**
	 * Fetch records that have <code>end_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo> fetchByEndTime(Timestamp... values) {
		return fetch(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO.END_TIME, values);
	}

	/**
	 * Fetch records that have <code>users_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo> fetchByUsersId(String... values) {
		return fetch(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO.USERS_ID, values);
	}

	/**
	 * Fetch records that have <code>tie_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementInfo> fetchByTieId(Integer... values) {
		return fetch(ExaminationArrangementInfo.EXAMINATION_ARRANGEMENT_INFO.TIE_ID, values);
	}
}
