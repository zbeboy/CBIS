/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.TeachingMaterialHead;
import com.school.cbis.domain.tables.records.TeachingMaterialHeadRecord;

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
public class TeachingMaterialHeadDao extends DAOImpl<TeachingMaterialHeadRecord, com.school.cbis.domain.tables.pojos.TeachingMaterialHead, Integer> {

	/**
	 * Create a new TeachingMaterialHeadDao without any configuration
	 */
	public TeachingMaterialHeadDao() {
		super(TeachingMaterialHead.TEACHING_MATERIAL_HEAD, com.school.cbis.domain.tables.pojos.TeachingMaterialHead.class);
	}

	/**
	 * Create a new TeachingMaterialHeadDao with an attached configuration
	 */
	public TeachingMaterialHeadDao(Configuration configuration) {
		super(TeachingMaterialHead.TEACHING_MATERIAL_HEAD, com.school.cbis.domain.tables.pojos.TeachingMaterialHead.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.TeachingMaterialHead object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachingMaterialHead> fetchById(Integer... values) {
		return fetch(TeachingMaterialHead.TEACHING_MATERIAL_HEAD.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.TeachingMaterialHead fetchOneById(Integer value) {
		return fetchOne(TeachingMaterialHead.TEACHING_MATERIAL_HEAD.ID, value);
	}

	/**
	 * Fetch records that have <code>title IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachingMaterialHead> fetchByTitle(String... values) {
		return fetch(TeachingMaterialHead.TEACHING_MATERIAL_HEAD.TITLE, values);
	}

	/**
	 * Fetch records that have <code>title_variable IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachingMaterialHead> fetchByTitleVariable(String... values) {
		return fetch(TeachingMaterialHead.TEACHING_MATERIAL_HEAD.TITLE_VARIABLE, values);
	}

	/**
	 * Fetch records that have <code>teach_task_title_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachingMaterialHead> fetchByTeachTaskTitleId(Integer... values) {
		return fetch(TeachingMaterialHead.TEACHING_MATERIAL_HEAD.TEACH_TASK_TITLE_ID, values);
	}

	/**
	 * Fetch records that have <code>teaching_material_template_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachingMaterialHead> fetchByTeachingMaterialTemplateId(Integer... values) {
		return fetch(TeachingMaterialHead.TEACHING_MATERIAL_HEAD.TEACHING_MATERIAL_TEMPLATE_ID, values);
	}

	/**
	 * Fetch records that have <code>is_assignment IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachingMaterialHead> fetchByIsAssignment(Byte... values) {
		return fetch(TeachingMaterialHead.TEACHING_MATERIAL_HEAD.IS_ASSIGNMENT, values);
	}

	/**
	 * Fetch records that have <code>sort IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TeachingMaterialHead> fetchBySort(Integer... values) {
		return fetch(TeachingMaterialHead.TEACHING_MATERIAL_HEAD.SORT, values);
	}
}