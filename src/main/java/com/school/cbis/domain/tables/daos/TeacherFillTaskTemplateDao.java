/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.TeacherFillTaskTemplate;
import com.school.cbis.domain.tables.records.TeacherFillTaskTemplateRecord;

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
public class TeacherFillTaskTemplateDao extends DAOImpl<TeacherFillTaskTemplateRecord, com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate, Integer> {

	/**
	 * Create a new TeacherFillTaskTemplateDao without any configuration
	 */
	public TeacherFillTaskTemplateDao() {
		super(TeacherFillTaskTemplate.TEACHER_FILL_TASK_TEMPLATE, com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate.class);
	}

	/**
	 * Create a new TeacherFillTaskTemplateDao with an attached configuration
	 */
	public TeacherFillTaskTemplateDao(Configuration configuration) {
		super(TeacherFillTaskTemplate.TEACHER_FILL_TASK_TEMPLATE, com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate> fetchById(Integer... values) {
		return fetch(TeacherFillTaskTemplate.TEACHER_FILL_TASK_TEMPLATE.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate fetchOneById(Integer value) {
		return fetchOne(TeacherFillTaskTemplate.TEACHER_FILL_TASK_TEMPLATE.ID, value);
	}

	/**
	 * Fetch records that have <code>title IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate> fetchByTitle(String... values) {
		return fetch(TeacherFillTaskTemplate.TEACHER_FILL_TASK_TEMPLATE.TITLE, values);
	}

	/**
	 * Fetch records that have <code>create_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate> fetchByCreateTime(Timestamp... values) {
		return fetch(TeacherFillTaskTemplate.TEACHER_FILL_TASK_TEMPLATE.CREATE_TIME, values);
	}

	/**
	 * Fetch records that have <code>create_user IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate> fetchByCreateUser(String... values) {
		return fetch(TeacherFillTaskTemplate.TEACHER_FILL_TASK_TEMPLATE.CREATE_USER, values);
	}

	/**
	 * Fetch records that have <code>tie_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate> fetchByTieId(Integer... values) {
		return fetch(TeacherFillTaskTemplate.TEACHER_FILL_TASK_TEMPLATE.TIE_ID, values);
	}

	/**
	 * Fetch records that have <code>teach_task_info_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskTemplate> fetchByTeachTaskInfoId(Integer... values) {
		return fetch(TeacherFillTaskTemplate.TEACHER_FILL_TASK_TEMPLATE.TEACH_TASK_INFO_ID, values);
	}
}
