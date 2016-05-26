/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.records;


import com.school.cbis.domain.tables.MailboxCount;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


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
public class MailboxCountRecord extends UpdatableRecordImpl<MailboxCountRecord> implements Record6<Integer, String, String, String, Timestamp, String> {

	private static final long serialVersionUID = -320804860;

	/**
	 * Setter for <code>cbis.mailbox_count.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>cbis.mailbox_count.id</code>.
	 */
	@NotNull
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>cbis.mailbox_count.accept_email</code>. 接收邮箱
	 */
	public void setAcceptEmail(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>cbis.mailbox_count.accept_email</code>. 接收邮箱
	 */
	@NotNull
	@Size(max = 64)
	public String getAcceptEmail() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>cbis.mailbox_count.subject</code>. 邮箱标题
	 */
	public void setSubject(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>cbis.mailbox_count.subject</code>. 邮箱标题
	 */
	@Size(max = 500)
	public String getSubject() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>cbis.mailbox_count.content</code>. 内容
	 */
	public void setContent(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>cbis.mailbox_count.content</code>. 内容
	 */
	@Size(max = 65535)
	public String getContent() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>cbis.mailbox_count.send_time</code>.
	 */
	public void setSendTime(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>cbis.mailbox_count.send_time</code>.
	 */
	@NotNull
	public Timestamp getSendTime() {
		return (Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>cbis.mailbox_count.accept_user</code>. 接收者
	 */
	public void setAcceptUser(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>cbis.mailbox_count.accept_user</code>. 接收者
	 */
	@NotNull
	@Size(max = 64)
	public String getAcceptUser() {
		return (String) getValue(5);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, String, String, String, Timestamp, String> fieldsRow() {
		return (Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, String, String, String, Timestamp, String> valuesRow() {
		return (Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return MailboxCount.MAILBOX_COUNT.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return MailboxCount.MAILBOX_COUNT.ACCEPT_EMAIL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return MailboxCount.MAILBOX_COUNT.SUBJECT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return MailboxCount.MAILBOX_COUNT.CONTENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return MailboxCount.MAILBOX_COUNT.SEND_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return MailboxCount.MAILBOX_COUNT.ACCEPT_USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getAcceptEmail();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getSubject();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getContent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value5() {
		return getSendTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getAcceptUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailboxCountRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailboxCountRecord value2(String value) {
		setAcceptEmail(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailboxCountRecord value3(String value) {
		setSubject(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailboxCountRecord value4(String value) {
		setContent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailboxCountRecord value5(Timestamp value) {
		setSendTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailboxCountRecord value6(String value) {
		setAcceptUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailboxCountRecord values(Integer value1, String value2, String value3, String value4, Timestamp value5, String value6) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MailboxCountRecord
	 */
	public MailboxCountRecord() {
		super(MailboxCount.MAILBOX_COUNT);
	}

	/**
	 * Create a detached, initialised MailboxCountRecord
	 */
	public MailboxCountRecord(Integer id, String acceptEmail, String subject, String content, Timestamp sendTime, String acceptUser) {
		super(MailboxCount.MAILBOX_COUNT);

		setValue(0, id);
		setValue(1, acceptEmail);
		setValue(2, subject);
		setValue(3, content);
		setValue(4, sendTime);
		setValue(5, acceptUser);
	}
}
