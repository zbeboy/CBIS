/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.TeachTaskInfo;
import com.school.cbis.domain.tables.records.TeachTaskInfoRecord;

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
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TeachTaskInfoDao extends DAOImpl<TeachTaskInfoRecord, com.school.cbis.domain.tables.pojos.TeachTaskInfo, Integer> {

	/**
	 * Create a new TeachTaskInfoDao without any configuration
	 */
	public TeachTaskInfoDao() {
		super(TeachTaskInfo.TEACH_TASK_INFO, com.school.cbis.domain.tables.pojos.TeachTaskInfo.class);
	}

	/**
	 * Create a new TeachTaskInfoDao with an attached configuration
	 */
	public TeachTaskInfoDao(Configuration configuration) {
		super(TeachTaskInfo.TEACH_TASK_INFO, com.school.cbis.domain.tables.pojos.TeachTaskInfo.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.TeachTaskInfo object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchById(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.TeachTaskInfo fetchOneById(Integer value) {
		return fetchOne(TeachTaskInfo.TEACH_TASK_INFO.ID, value);
	}

	/**
	 * Fetch records that have <code>tie_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTieId(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TIE_ID, values);
	}

	/**
	 * Fetch records that have <code>teach_task_title IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTeachTaskTitle(String... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TEACH_TASK_TITLE, values);
	}

	/**
	 * Fetch records that have <code>teach_task_file_url IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTeachTaskFileUrl(String... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TEACH_TASK_FILE_URL, values);
	}

	/**
	 * Fetch records that have <code>teach_task_file_size IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTeachTaskFileSize(String... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TEACH_TASK_FILE_SIZE, values);
	}

	/**
	 * Fetch records that have <code>teach_task_file_date IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTeachTaskFileDate(Timestamp... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TEACH_TASK_FILE_DATE, values);
	}

	/**
	 * Fetch records that have <code>teach_task_term IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTeachTaskTerm(String... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TEACH_TASK_TERM, values);
	}

	/**
	 * Fetch records that have <code>teach_task_down_times IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTeachTaskDownTimes(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TEACH_TASK_DOWN_TIMES, values);
	}

	/**
	 * Fetch records that have <code>teach_type_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTeachTypeId(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TEACH_TYPE_ID, values);
	}

	/**
	 * Fetch records that have <code>term_start_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTermStartTime(Date... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TERM_START_TIME, values);
	}

	/**
	 * Fetch records that have <code>term_end_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByTermEndTime(Date... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.TERM_END_TIME, values);
	}

	/**
	 * Fetch records that have <code>file_user IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByFileUser(String... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.FILE_USER, values);
	}

	/**
	 * Fetch records that have <code>file_type IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByFileType(String... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.FILE_TYPE, values);
	}

	/**
	 * Fetch records that have <code>year_x IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByYearX(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.YEAR_X, values);
	}

	/**
	 * Fetch records that have <code>year_y IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByYearY(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.YEAR_Y, values);
	}

	/**
	 * Fetch records that have <code>grade_x IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByGradeX(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.GRADE_X, values);
	}

	/**
	 * Fetch records that have <code>grade_y IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByGradeY(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.GRADE_Y, values);
	}

	/**
	 * Fetch records that have <code>grade_num_x IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByGradeNumX(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.GRADE_NUM_X, values);
	}

	/**
	 * Fetch records that have <code>grade_num_y IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByGradeNumY(Integer... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.GRADE_NUM_Y, values);
	}

	/**
	 * Fetch records that have <code>is_use IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachTaskInfo> fetchByIsUse(Byte... values) {
		return fetch(TeachTaskInfo.TEACH_TASK_INFO.IS_USE, values);
	}
}
