/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.ExaminationArrangementTemplate;
import com.school.cbis.domain.tables.records.ExaminationArrangementTemplateRecord;

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
public class ExaminationArrangementTemplateDao extends DAOImpl<ExaminationArrangementTemplateRecord, com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate, Integer> {

	/**
	 * Create a new ExaminationArrangementTemplateDao without any configuration
	 */
	public ExaminationArrangementTemplateDao() {
		super(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE, com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate.class);
	}

	/**
	 * Create a new ExaminationArrangementTemplateDao with an attached configuration
	 */
	public ExaminationArrangementTemplateDao(Configuration configuration) {
		super(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE, com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate> fetchById(Integer... values) {
		return fetch(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate fetchOneById(Integer value) {
		return fetchOne(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.ID, value);
	}

	/**
	 * Fetch records that have <code>title IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate> fetchByTitle(String... values) {
		return fetch(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.TITLE, values);
	}

	/**
	 * Fetch records that have <code>create_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate> fetchByCreateTime(Timestamp... values) {
		return fetch(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.CREATE_TIME, values);
	}

	/**
	 * Fetch records that have <code>create_user IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate> fetchByCreateUser(String... values) {
		return fetch(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.CREATE_USER, values);
	}

	/**
	 * Fetch records that have <code>tie_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate> fetchByTieId(Integer... values) {
		return fetch(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.TIE_ID, values);
	}

	/**
	 * Fetch records that have <code>teach_task_info_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.ExaminationArrangementTemplate> fetchByTeachTaskInfoId(Integer... values) {
		return fetch(ExaminationArrangementTemplate.EXAMINATION_ARRANGEMENT_TEMPLATE.TEACH_TASK_INFO_ID, values);
	}
}