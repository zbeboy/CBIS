/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.ClassroomCourseTimetableInfo;
import com.school.cbis.domain.tables.records.ClassroomCourseTimetableInfoRecord;

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
public class ClassroomCourseTimetableInfoDao extends DAOImpl<ClassroomCourseTimetableInfoRecord, com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo, Integer> {

	/**
	 * Create a new ClassroomCourseTimetableInfoDao without any configuration
	 */
	public ClassroomCourseTimetableInfoDao() {
		super(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO, com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo.class);
	}

	/**
	 * Create a new ClassroomCourseTimetableInfoDao with an attached configuration
	 */
	public ClassroomCourseTimetableInfoDao(Configuration configuration) {
		super(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO, com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchById(Integer... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo fetchOneById(Integer value) {
		return fetchOne(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.ID, value);
	}

	/**
	 * Fetch records that have <code>tie_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTieId(Integer... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TIE_ID, values);
	}

	/**
	 * Fetch records that have <code>timetable_info_term IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTimetableInfoTerm(String... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM, values);
	}

	/**
	 * Fetch records that have <code>timetable_info_file_url IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTimetableInfoFileUrl(String... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_URL, values);
	}

	/**
	 * Fetch records that have <code>timetable_info_file_pdf IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTimetableInfoFilePdf(String... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_PDF, values);
	}

	/**
	 * Fetch records that have <code>timetable_info_file_size IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTimetableInfoFileSize(String... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_SIZE, values);
	}

	/**
	 * Fetch records that have <code>timetable_info_file_name IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTimetableInfoFileName(String... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME, values);
	}

	/**
	 * Fetch records that have <code>timetable_info_file_date IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTimetableInfoFileDate(Timestamp... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DATE, values);
	}

	/**
	 * Fetch records that have <code>timetable_info_file_down_times IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTimetableInfoFileDownTimes(Integer... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DOWN_TIMES, values);
	}

	/**
	 * Fetch records that have <code>teach_type_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTeachTypeId(Integer... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TEACH_TYPE_ID, values);
	}

	/**
	 * Fetch records that have <code>term_start_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTermStartTime(Date... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TERM_START_TIME, values);
	}

	/**
	 * Fetch records that have <code>term_end_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByTermEndTime(Date... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.TERM_END_TIME, values);
	}

	/**
	 * Fetch records that have <code>file_user IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByFileUser(String... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.FILE_USER, values);
	}

	/**
	 * Fetch records that have <code>file_type IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByFileType(String... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.FILE_TYPE, values);
	}

	/**
	 * Fetch records that have <code>classroom IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ClassroomCourseTimetableInfo> fetchByClassroom(String... values) {
		return fetch(ClassroomCourseTimetableInfo.CLASSROOM_COURSE_TIMETABLE_INFO.CLASSROOM, values);
	}
}
