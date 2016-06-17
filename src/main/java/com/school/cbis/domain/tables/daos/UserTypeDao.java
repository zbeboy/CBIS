/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.UserType;
import com.school.cbis.domain.tables.records.UserTypeRecord;

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
public class UserTypeDao extends DAOImpl<UserTypeRecord, com.school.cbis.domain.tables.pojos.UserType, Integer> {

	/**
	 * Create a new UserTypeDao without any configuration
	 */
	public UserTypeDao() {
		super(UserType.USER_TYPE, com.school.cbis.domain.tables.pojos.UserType.class);
	}

	/**
	 * Create a new UserTypeDao with an attached configuration
	 */
	public UserTypeDao(Configuration configuration) {
		super(UserType.USER_TYPE, com.school.cbis.domain.tables.pojos.UserType.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.UserType object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.UserType> fetchById(Integer... values) {
		return fetch(UserType.USER_TYPE.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.UserType fetchOneById(Integer value) {
		return fetchOne(UserType.USER_TYPE.ID, value);
	}

	/**
	 * Fetch records that have <code>name IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.UserType> fetchByName(String... values) {
		return fetch(UserType.USER_TYPE.NAME, values);
	}
}
