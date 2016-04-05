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
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HeadType implements Serializable {

	private static final long serialVersionUID = 764847864;

	private Integer id;
	private String  typeValue;
	private String  typeName;

	public HeadType() {}

	public HeadType(HeadType value) {
		this.id = value.id;
		this.typeValue = value.typeValue;
		this.typeName = value.typeName;
	}

	public HeadType(
		Integer id,
		String  typeValue,
		String  typeName
	) {
		this.id = id;
		this.typeValue = typeValue;
		this.typeName = typeName;
	}

	@NotNull
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(max = 30)
	public String getTypeValue() {
		return this.typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	@NotNull
	@Size(max = 30)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("HeadType (");

		sb.append(id);
		sb.append(", ").append(typeValue);
		sb.append(", ").append(typeName);

		sb.append(")");
		return sb.toString();
	}
}
