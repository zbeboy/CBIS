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
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MobileCount implements Serializable {

	private static final long serialVersionUID = 2046118166;

	private Integer   id;
	private String    acceptUser;
	private String    content;
	private Timestamp sendTime;
	private String    acceptMobile;

	public MobileCount() {}

	public MobileCount(MobileCount value) {
		this.id = value.id;
		this.acceptUser = value.acceptUser;
		this.content = value.content;
		this.sendTime = value.sendTime;
		this.acceptMobile = value.acceptMobile;
	}

	public MobileCount(
		Integer   id,
		String    acceptUser,
		String    content,
		Timestamp sendTime,
		String    acceptMobile
	) {
		this.id = id;
		this.acceptUser = acceptUser;
		this.content = content;
		this.sendTime = sendTime;
		this.acceptMobile = acceptMobile;
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
	public String getAcceptUser() {
		return this.acceptUser;
	}

	public void setAcceptUser(String acceptUser) {
		this.acceptUser = acceptUser;
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
	public String getAcceptMobile() {
		return this.acceptMobile;
	}

	public void setAcceptMobile(String acceptMobile) {
		this.acceptMobile = acceptMobile;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("MobileCount (");

		sb.append(id);
		sb.append(", ").append(acceptUser);
		sb.append(", ").append(content);
		sb.append(", ").append(sendTime);
		sb.append(", ").append(acceptMobile);

		sb.append(")");
		return sb.toString();
	}
}
