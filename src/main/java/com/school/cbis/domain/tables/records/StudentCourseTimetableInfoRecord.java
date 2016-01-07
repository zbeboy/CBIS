/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.records;


import com.school.cbis.domain.tables.StudentCourseTimetableInfo;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;


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
public class StudentCourseTimetableInfoRecord extends UpdatableRecordImpl<StudentCourseTimetableInfoRecord> implements Record12<Integer, Integer, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date> {

	private static final long serialVersionUID = 2086828110;

	/**
	 * Setter for <code>cbis.student_course_timetable_info.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.grade_id</code>.
	 */
	public void setGradeId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.grade_id</code>.
	 */
	public Integer getGradeId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.timetable_info_term</code>.
	 */
	public void setTimetableInfoTerm(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.timetable_info_term</code>.
	 */
	public String getTimetableInfoTerm() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.timetable_info_file_url</code>.
	 */
	public void setTimetableInfoFileUrl(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.timetable_info_file_url</code>.
	 */
	public String getTimetableInfoFileUrl() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.timetable_info_file_pdf</code>.
	 */
	public void setTimetableInfoFilePdf(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.timetable_info_file_pdf</code>.
	 */
	public String getTimetableInfoFilePdf() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.timetable_info_file_size</code>.
	 */
	public void setTimetableInfoFileSize(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.timetable_info_file_size</code>.
	 */
	public String getTimetableInfoFileSize() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.timetable_info_file_name</code>.
	 */
	public void setTimetableInfoFileName(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.timetable_info_file_name</code>.
	 */
	public String getTimetableInfoFileName() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.timetable_info_file_date</code>.
	 */
	public void setTimetableInfoFileDate(Timestamp value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.timetable_info_file_date</code>.
	 */
	public Timestamp getTimetableInfoFileDate() {
		return (Timestamp) getValue(7);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.timetable_info_file_down_times</code>.
	 */
	public void setTimetableInfoFileDownTimes(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.timetable_info_file_down_times</code>.
	 */
	public Integer getTimetableInfoFileDownTimes() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.teach_type_id</code>.
	 */
	public void setTeachTypeId(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.teach_type_id</code>.
	 */
	public Integer getTeachTypeId() {
		return (Integer) getValue(9);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.term_start_time</code>.
	 */
	public void setTermStartTime(Date value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.term_start_time</code>.
	 */
	public Date getTermStartTime() {
		return (Date) getValue(10);
	}

	/**
	 * Setter for <code>cbis.student_course_timetable_info.term_end_time</code>.
	 */
	public void setTermEndTime(Date value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>cbis.student_course_timetable_info.term_end_time</code>.
	 */
	public Date getTermEndTime() {
		return (Date) getValue(11);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record12 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<Integer, Integer, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date> fieldsRow() {
		return (Row12) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<Integer, Integer, String, String, String, String, String, Timestamp, Integer, Integer, Date, Date> valuesRow() {
		return (Row12) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.GRADE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_TERM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_PDF;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field8() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TIMETABLE_INFO_FILE_DOWN_TIMES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TEACH_TYPE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field11() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TERM_START_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field12() {
		return StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO.TERM_END_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getGradeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getTimetableInfoTerm();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getTimetableInfoFileUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getTimetableInfoFilePdf();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getTimetableInfoFileSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getTimetableInfoFileName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value8() {
		return getTimetableInfoFileDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getTimetableInfoFileDownTimes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getTeachTypeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value11() {
		return getTermStartTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value12() {
		return getTermEndTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value2(Integer value) {
		setGradeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value3(String value) {
		setTimetableInfoTerm(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value4(String value) {
		setTimetableInfoFileUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value5(String value) {
		setTimetableInfoFilePdf(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value6(String value) {
		setTimetableInfoFileSize(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value7(String value) {
		setTimetableInfoFileName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value8(Timestamp value) {
		setTimetableInfoFileDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value9(Integer value) {
		setTimetableInfoFileDownTimes(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value10(Integer value) {
		setTeachTypeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value11(Date value) {
		setTermStartTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord value12(Date value) {
		setTermEndTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentCourseTimetableInfoRecord values(Integer value1, Integer value2, String value3, String value4, String value5, String value6, String value7, Timestamp value8, Integer value9, Integer value10, Date value11, Date value12) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		value12(value12);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached StudentCourseTimetableInfoRecord
	 */
	public StudentCourseTimetableInfoRecord() {
		super(StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO);
	}

	/**
	 * Create a detached, initialised StudentCourseTimetableInfoRecord
	 */
	public StudentCourseTimetableInfoRecord(Integer id, Integer gradeId, String timetableInfoTerm, String timetableInfoFileUrl, String timetableInfoFilePdf, String timetableInfoFileSize, String timetableInfoFileName, Timestamp timetableInfoFileDate, Integer timetableInfoFileDownTimes, Integer teachTypeId, Date termStartTime, Date termEndTime) {
		super(StudentCourseTimetableInfo.STUDENT_COURSE_TIMETABLE_INFO);

		setValue(0, id);
		setValue(1, gradeId);
		setValue(2, timetableInfoTerm);
		setValue(3, timetableInfoFileUrl);
		setValue(4, timetableInfoFilePdf);
		setValue(5, timetableInfoFileSize);
		setValue(6, timetableInfoFileName);
		setValue(7, timetableInfoFileDate);
		setValue(8, timetableInfoFileDownTimes);
		setValue(9, teachTypeId);
		setValue(10, termStartTime);
		setValue(11, termEndTime);
	}
}
