/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.daos;


import com.school.cbis.domain.tables.TieNoticeAffix;
import com.school.cbis.domain.tables.records.TieNoticeAffixRecord;

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
public class TieNoticeAffixDao extends DAOImpl<TieNoticeAffixRecord, com.school.cbis.domain.tables.pojos.TieNoticeAffix, Integer> {

	/**
	 * Create a new TieNoticeAffixDao without any configuration
	 */
	public TieNoticeAffixDao() {
		super(TieNoticeAffix.TIE_NOTICE_AFFIX, com.school.cbis.domain.tables.pojos.TieNoticeAffix.class);
	}

	/**
	 * Create a new TieNoticeAffixDao with an attached configuration
	 */
	public TieNoticeAffixDao(Configuration configuration) {
		super(TieNoticeAffix.TIE_NOTICE_AFFIX, com.school.cbis.domain.tables.pojos.TieNoticeAffix.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(com.school.cbis.domain.tables.pojos.TieNoticeAffix object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeAffix> fetchById(Integer... values) {
		return fetch(TieNoticeAffix.TIE_NOTICE_AFFIX.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public com.school.cbis.domain.tables.pojos.TieNoticeAffix fetchOneById(Integer value) {
		return fetchOne(TieNoticeAffix.TIE_NOTICE_AFFIX.ID, value);
	}

	/**
	 * Fetch records that have <code>tie_notice_file_url IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeAffix> fetchByTieNoticeFileUrl(String... values) {
		return fetch(TieNoticeAffix.TIE_NOTICE_AFFIX.TIE_NOTICE_FILE_URL, values);
	}

	/**
	 * Fetch records that have <code>tie_notice_file_size IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeAffix> fetchByTieNoticeFileSize(String... values) {
		return fetch(TieNoticeAffix.TIE_NOTICE_AFFIX.TIE_NOTICE_FILE_SIZE, values);
	}

	/**
	 * Fetch records that have <code>tie_notice_file_name IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeAffix> fetchByTieNoticeFileName(String... values) {
		return fetch(TieNoticeAffix.TIE_NOTICE_AFFIX.TIE_NOTICE_FILE_NAME, values);
	}

	/**
	 * Fetch records that have <code>tie_notice_file_date IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeAffix> fetchByTieNoticeFileDate(Timestamp... values) {
		return fetch(TieNoticeAffix.TIE_NOTICE_AFFIX.TIE_NOTICE_FILE_DATE, values);
	}

	/**
	 * Fetch records that have <code>article_info_id IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeAffix> fetchByArticleInfoId(Integer... values) {
		return fetch(TieNoticeAffix.TIE_NOTICE_AFFIX.ARTICLE_INFO_ID, values);
	}

	/**
	 * Fetch records that have <code>file_user IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeAffix> fetchByFileUser(String... values) {
		return fetch(TieNoticeAffix.TIE_NOTICE_AFFIX.FILE_USER, values);
	}

	/**
	 * Fetch records that have <code>file_type IN (values)</code>
	 */
	public List<com.school.cbis.domain.tables.pojos.TieNoticeAffix> fetchByFileType(String... values) {
		return fetch(TieNoticeAffix.TIE_NOTICE_AFFIX.FILE_TYPE, values);
	}
}
