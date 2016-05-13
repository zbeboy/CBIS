/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.Users;
import com.school.cbis.domain.tables.records.UsersRecord;

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
public class UsersDao extends DAOImpl<UsersRecord, com.school.cbis.domain.tables.pojos.Users, String> {

	/**
	 * Create a new UsersDao without any configuration
	 */
	public UsersDao() {
		super(Users.USERS, com.school.cbis.domain.tables.pojos.Users.class);
	}

	/**
	 * Create a new UsersDao with an attached configuration
	 */
	public UsersDao(Configuration configuration) {
		super(Users.USERS, com.school.cbis.domain.tables.pojos.Users.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getId(com.school.cbis.domain.tables.pojos.Users object) {
		return object.getUsername();
	}

	/**
	 * Fetch records that have <code>username IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByUsername(String... values) {
		return fetch(Users.USERS.USERNAME, values);
	}

	/**
	 * Fetch a unique record that has <code>username = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.Users fetchOneByUsername(String value) {
		return fetchOne(Users.USERS.USERNAME, value);
	}

	/**
	 * Fetch records that have <code>password IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByPassword(String... values) {
		return fetch(Users.USERS.PASSWORD, values);
	}

	/**
	 * Fetch records that have <code>enabled IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByEnabled(Byte... values) {
		return fetch(Users.USERS.ENABLED, values);
	}

	/**
	 * Fetch records that have <code>user_type_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByUserTypeId(Integer... values) {
		return fetch(Users.USERS.USER_TYPE_ID, values);
	}

	/**
	 * Fetch records that have <code>real_name IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByRealName(String... values) {
		return fetch(Users.USERS.REAL_NAME, values);
	}

	/**
	 * Fetch records that have <code>mobile IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByMobile(String... values) {
		return fetch(Users.USERS.MOBILE, values);
	}

	/**
	 * Fetch a unique record that has <code>mobile = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.Users fetchOneByMobile(String value) {
		return fetchOne(Users.USERS.MOBILE, value);
	}

	/**
	 * Fetch records that have <code>email IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByEmail(String... values) {
		return fetch(Users.USERS.EMAIL, values);
	}

	/**
	 * Fetch a unique record that has <code>email = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.Users fetchOneByEmail(String value) {
		return fetchOne(Users.USERS.EMAIL, value);
	}

	/**
	 * Fetch records that have <code>birthday IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByBirthday(Date... values) {
		return fetch(Users.USERS.BIRTHDAY, values);
	}

	/**
	 * Fetch records that have <code>head_img IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByHeadImg(String... values) {
		return fetch(Users.USERS.HEAD_IMG, values);
	}

	/**
	 * Fetch records that have <code>sex IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchBySex(String... values) {
		return fetch(Users.USERS.SEX, values);
	}

	/**
	 * Fetch records that have <code>identity_card IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByIdentityCard(String... values) {
		return fetch(Users.USERS.IDENTITY_CARD, values);
	}

	/**
	 * Fetch records that have <code>family_residence IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByFamilyResidence(String... values) {
		return fetch(Users.USERS.FAMILY_RESIDENCE, values);
	}

	/**
	 * Fetch records that have <code>post IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByPost(String... values) {
		return fetch(Users.USERS.POST, values);
	}

	/**
	 * Fetch records that have <code>political_landscape IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByPoliticalLandscape(String... values) {
		return fetch(Users.USERS.POLITICAL_LANDSCAPE, values);
	}

	/**
	 * Fetch records that have <code>religious_belief IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByReligiousBelief(String... values) {
		return fetch(Users.USERS.RELIGIOUS_BELIEF, values);
	}

	/**
	 * Fetch records that have <code>nation IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByNation(String... values) {
		return fetch(Users.USERS.NATION, values);
	}

	/**
	 * Fetch records that have <code>is_check_mobile IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByIsCheckMobile(Byte... values) {
		return fetch(Users.USERS.IS_CHECK_MOBILE, values);
	}

	/**
	 * Fetch records that have <code>is_check_email IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByIsCheckEmail(Byte... values) {
		return fetch(Users.USERS.IS_CHECK_EMAIL, values);
	}

	/**
	 * Fetch records that have <code>mobile_check_key IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByMobileCheckKey(String... values) {
		return fetch(Users.USERS.MOBILE_CHECK_KEY, values);
	}

	/**
	 * Fetch records that have <code>email_check_key IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByEmailCheckKey(String... values) {
		return fetch(Users.USERS.EMAIL_CHECK_KEY, values);
	}

	/**
	 * Fetch records that have <code>password_reset_key IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByPasswordResetKey(String... values) {
		return fetch(Users.USERS.PASSWORD_RESET_KEY, values);
	}

	/**
	 * Fetch records that have <code>mobile_check_key_validity_period IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByMobileCheckKeyValidityPeriod(Timestamp... values) {
		return fetch(Users.USERS.MOBILE_CHECK_KEY_VALIDITY_PERIOD, values);
	}

	/**
	 * Fetch records that have <code>email_check_key_validity_period IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByEmailCheckKeyValidityPeriod(Timestamp... values) {
		return fetch(Users.USERS.EMAIL_CHECK_KEY_VALIDITY_PERIOD, values);
	}

	/**
	 * Fetch records that have <code>password_reset_key__validity_period IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByPasswordResetKey_ValidityPeriod(Timestamp... values) {
		return fetch(Users.USERS.PASSWORD_RESET_KEY__VALIDITY_PERIOD, values);
	}

	/**
	 * Fetch records that have <code>lang_key IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByLangKey(String... values) {
		return fetch(Users.USERS.LANG_KEY, values);
	}

	/**
	 * Fetch records that have <code>persona_introduction IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.Users> fetchByPersonaIntroduction(String... values) {
		return fetch(Users.USERS.PERSONA_INTRODUCTION, values);
	}
}
