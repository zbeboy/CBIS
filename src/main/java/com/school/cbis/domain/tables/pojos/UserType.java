/**
 * This class is generated by jOOQ
 */
package com.school.cbis.domain.tables.pojos;


import java.io.Serializable;

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
public class UserType implements Serializable {

	private static final long serialVersionUID = 498751189;

	private Integer id;
	private String  name;

	public UserType() {}

	public UserType(UserType value) {
		this.id = value.id;
		this.name = value.name;
	}

	public UserType(
		Integer id,
		String  name
	) {
		this.id = id;
		this.name = name;
	}

	@NotNull
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(max = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("UserType (");

		sb.append(id);
		sb.append(", ").append(name);

		sb.append(")");
		return sb.toString();
	}
}
