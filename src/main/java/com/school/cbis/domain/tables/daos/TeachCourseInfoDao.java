/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.TeachCourseInfo;
import com.school.cbis.domain.tables.records.TeachCourseInfoRecord;

import java.sql.Date;
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
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TeachCourseInfoDao extends DAOImpl<TeachCourseInfoRecord, com.school.cbis.domain.tables.pojos.TeachCourseInfo, Integer> {

	/**
	 * Create a new TeachCourseInfoDao without any configuration
	 */
	public TeachCourseInfoDao() {
		super(TeachCourseInfo.TEACH_COURSE_INFO, com.school.cbis.domain.tables.pojos.TeachCourseInfo.class);
	}

	/**
	 * Create a new TeachCourseInfoDao with an attached configuration
	 */
	public TeachCourseInfoDao(Configuration configuration) {
		super(TeachCourseInfo.TEACH_COURSE_INFO, com.school.cbis.domain.tables.pojos.TeachCourseInfo.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.TeachCourseInfo object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchById(Integer... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.TeachCourseInfo fetchOneById(Integer value) {
		return fetchOne(TeachCourseInfo.TEACH_COURSE_INFO.ID, value);
	}

	/**
	 * Fetch records that have <code>tie_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTieId(Integer... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TIE_ID, values);
	}

	/**
	 * Fetch records that have <code>teach_course_info_term IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTeachCourseInfoTerm(String... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TEACH_COURSE_INFO_TERM, values);
	}

	/**
	 * Fetch records that have <code>teach_course_info_file_url IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTeachCourseInfoFileUrl(String... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_URL, values);
	}

	/**
	 * Fetch records that have <code>teach_course_info_file_pdf IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTeachCourseInfoFilePdf(String... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_PDF, values);
	}

	/**
	 * Fetch records that have <code>teach_course_info_file_size IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTeachCourseInfoFileSize(String... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_SIZE, values);
	}

	/**
	 * Fetch records that have <code>teach_course_info_file_name IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTeachCourseInfoFileName(String... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_NAME, values);
	}

	/**
	 * Fetch records that have <code>teach_course_info_file_date IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTeachCourseInfoFileDate(Timestamp... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_DATE, values);
	}

	/**
	 * Fetch records that have <code>teach_course_info_file_down_times IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTeachCourseInfoFileDownTimes(Integer... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TEACH_COURSE_INFO_FILE_DOWN_TIMES, values);
	}

	/**
	 * Fetch records that have <code>teach_type_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTeachTypeId(Integer... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TEACH_TYPE_ID, values);
	}

	/**
	 * Fetch records that have <code>term_start_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTermStartTime(Date... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TERM_START_TIME, values);
	}

	/**
	 * Fetch records that have <code>term_end_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByTermEndTime(Date... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.TERM_END_TIME, values);
	}

	/**
	 * Fetch records that have <code>file_user IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByFileUser(String... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.FILE_USER, values);
	}

	/**
	 * Fetch records that have <code>file_type IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachCourseInfo> fetchByFileType(String... values) {
		return fetch(TeachCourseInfo.TEACH_COURSE_INFO.FILE_TYPE, values);
	}
}