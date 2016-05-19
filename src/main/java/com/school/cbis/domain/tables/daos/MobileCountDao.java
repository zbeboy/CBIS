/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.MobileCount;
import com.school.cbis.domain.tables.records.MobileCountRecord;

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
public class MobileCountDao extends DAOImpl<MobileCountRecord, com.school.cbis.domain.tables.pojos.MobileCount, Integer> {

	/**
	 * Create a new MobileCountDao without any configuration
	 */
	public MobileCountDao() {
		super(MobileCount.MOBILE_COUNT, com.school.cbis.domain.tables.pojos.MobileCount.class);
	}

	/**
	 * Create a new MobileCountDao with an attached configuration
	 */
	public MobileCountDao(Configuration configuration) {
		super(MobileCount.MOBILE_COUNT, com.school.cbis.domain.tables.pojos.MobileCount.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.MobileCount object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.MobileCount> fetchById(Integer... values) {
		return fetch(MobileCount.MOBILE_COUNT.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.MobileCount fetchOneById(Integer value) {
		return fetchOne(MobileCount.MOBILE_COUNT.ID, value);
	}

	/**
	 * Fetch records that have <code>accept_user IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.MobileCount> fetchByAcceptUser(String... values) {
		return fetch(MobileCount.MOBILE_COUNT.ACCEPT_USER, values);
	}

	/**
	 * Fetch records that have <code>content IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.MobileCount> fetchByContent(String... values) {
		return fetch(MobileCount.MOBILE_COUNT.CONTENT, values);
	}

	/**
	 * Fetch records that have <code>send_time IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.MobileCount> fetchBySendTime(Timestamp... values) {
		return fetch(MobileCount.MOBILE_COUNT.SEND_TIME, values);
	}

	/**
	 * Fetch records that have <code>accept_mobile IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.MobileCount> fetchByAcceptMobile(String... values) {
		return fetch(MobileCount.MOBILE_COUNT.ACCEPT_MOBILE, values);
	}
}