/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.Teacher;
import com.school.cbis.domain.tables.records.TeacherRecord;

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
public class TeacherDao extends DAOImpl<TeacherRecord, com.school.cbis.domain.tables.pojos.Teacher, Integer> {

	/**
	 * Create a new TeacherDao without any configuration
	 */
	public TeacherDao() {
		super(Teacher.TEACHER, com.school.cbis.domain.tables.pojos.Teacher.class);
	}

	/**
	 * Create a new TeacherDao with an attached configuration
	 */
	public TeacherDao(Configuration configuration) {
		super(Teacher.TEACHER, com.school.cbis.domain.tables.pojos.Teacher.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.Teacher object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Teacher> fetchById(Integer... values) {
		return fetch(Teacher.TEACHER.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.Teacher fetchOneById(Integer value) {
		return fetchOne(Teacher.TEACHER.ID, value);
	}

	/**
	 * Fetch records that have <code>tie_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Teacher> fetchByTieId(Integer... values) {
		return fetch(Teacher.TEACHER.TIE_ID, values);
	}

	/**
	 * Fetch records that have <code>teacher_job_number IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Teacher> fetchByTeacherJobNumber(String... values) {
		return fetch(Teacher.TEACHER.TEACHER_JOB_NUMBER, values);
	}
}
