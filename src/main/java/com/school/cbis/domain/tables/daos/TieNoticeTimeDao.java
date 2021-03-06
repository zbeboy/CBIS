/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.TieNoticeTime;
import com.school.cbis.domain.tables.records.TieNoticeTimeRecord;

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
public class TieNoticeTimeDao extends DAOImpl<TieNoticeTimeRecord, com.school.cbis.domain.tables.pojos.TieNoticeTime, Integer> {

	/**
	 * Create a new TieNoticeTimeDao without any configuration
	 */
	public TieNoticeTimeDao() {
		super(TieNoticeTime.TIE_NOTICE_TIME, com.school.cbis.domain.tables.pojos.TieNoticeTime.class);
	}

	/**
	 * Create a new TieNoticeTimeDao with an attached configuration
	 */
	public TieNoticeTimeDao(Configuration configuration) {
		super(TieNoticeTime.TIE_NOTICE_TIME, com.school.cbis.domain.tables.pojos.TieNoticeTime.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.TieNoticeTime object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeTime> fetchById(Integer... values) {
		return fetch(TieNoticeTime.TIE_NOTICE_TIME.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.TieNoticeTime fetchOneById(Integer value) {
		return fetchOne(TieNoticeTime.TIE_NOTICE_TIME.ID, value);
	}

	/**
	 * Fetch records that have <code>time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeTime> fetchByTime(String... values) {
		return fetch(TieNoticeTime.TIE_NOTICE_TIME.TIME, values);
	}
}
