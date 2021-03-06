/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.TeacherFillTaskHead;
import com.school.cbis.domain.tables.records.TeacherFillTaskHeadRecord;

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
public class TeacherFillTaskHeadDao extends DAOImpl<TeacherFillTaskHeadRecord, com.school.cbis.domain.tables.pojos.TeacherFillTaskHead, Integer> {

	/**
	 * Create a new TeacherFillTaskHeadDao without any configuration
	 */
	public TeacherFillTaskHeadDao() {
		super(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD, com.school.cbis.domain.tables.pojos.TeacherFillTaskHead.class);
	}

	/**
	 * Create a new TeacherFillTaskHeadDao with an attached configuration
	 */
	public TeacherFillTaskHeadDao(Configuration configuration) {
		super(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD, com.school.cbis.domain.tables.pojos.TeacherFillTaskHead.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.TeacherFillTaskHead object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskHead> fetchById(Integer... values) {
		return fetch(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.TeacherFillTaskHead fetchOneById(Integer value) {
		return fetchOne(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD.ID, value);
	}

	/**
	 * Fetch records that have <code>title IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskHead> fetchByTitle(String... values) {
		return fetch(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD.TITLE, values);
	}

	/**
	 * Fetch records that have <code>title_variable IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskHead> fetchByTitleVariable(String... values) {
		return fetch(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD.TITLE_VARIABLE, values);
	}

	/**
	 * Fetch records that have <code>teach_task_title_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskHead> fetchByTeachTaskTitleId(Integer... values) {
		return fetch(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD.TEACH_TASK_TITLE_ID, values);
	}

	/**
	 * Fetch records that have <code>teacher_fill_task_template_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskHead> fetchByTeacherFillTaskTemplateId(Integer... values) {
		return fetch(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD.TEACHER_FILL_TASK_TEMPLATE_ID, values);
	}

	/**
	 * Fetch records that have <code>is_assignment IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskHead> fetchByIsAssignment(Byte... values) {
		return fetch(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD.IS_ASSIGNMENT, values);
	}

	/**
	 * Fetch records that have <code>sort IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeacherFillTaskHead> fetchBySort(Integer... values) {
		return fetch(TeacherFillTaskHead.TEACHER_FILL_TASK_HEAD.SORT, values);
	}
}
