/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
public class MailboxCount implements Serializable {

	private static final long serialVersionUID = 1104804187;

	private Integer   id;
	private String    acceptEmail;
	private String    subject;
	private String    content;
	private Timestamp sendTime;
	private String    acceptUser;

	public MailboxCount() {}

	public MailboxCount(MailboxCount value) {
		this.id = value.id;
		this.acceptEmail = value.acceptEmail;
		this.subject = value.subject;
		this.content = value.content;
		this.sendTime = value.sendTime;
		this.acceptUser = value.acceptUser;
	}

	public MailboxCount(
		Integer   id,
		String    acceptEmail,
		String    subject,
		String    content,
		Timestamp sendTime,
		String    acceptUser
	) {
		this.id = id;
		this.acceptEmail = acceptEmail;
		this.subject = subject;
		this.content = content;
		this.sendTime = sendTime;
		this.acceptUser = acceptUser;
	}

	@NotNull
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(max = 64)
	public String getAcceptEmail() {
		return this.acceptEmail;
	}

	public void setAcceptEmail(String acceptEmail) {
		this.acceptEmail = acceptEmail;
	}

	@Size(max = 500)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Size(max = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@NotNull
	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	@NotNull
	@Size(max = 64)
	public String getAcceptUser() {
		return this.acceptUser;
	}

	public void setAcceptUser(String acceptUser) {
		this.acceptUser = acceptUser;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("MailboxCount (");

		sb.append(id);
		sb.append(", ").append(acceptEmail);
		sb.append(", ").append(subject);
		sb.append(", ").append(content);
		sb.append(", ").append(sendTime);
		sb.append(", ").append(acceptUser);

		sb.append(")");
		return sb.toString();
	}
}
