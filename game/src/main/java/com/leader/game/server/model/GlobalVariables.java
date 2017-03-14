package com.leader.game.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.leader.core.db.GameEntity;

@Entity
@Table(name = "GLOBAL_VARIABLES")
@NamedQueries({ // start
		@NamedQuery(name = "GlobalVariables.findAll", query = "SELECT r FROM GlobalVariables r") // \n
})
public class GlobalVariables implements GameEntity {
	private static final long serialVersionUID = 6957843503947662387L;

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "GLOBAL_KEY")
	private GlobalKey key;

	@Column(name = "GLOBAL_VALUE")
	private String value;

	/**
	 * @return the key
	 */
	public final GlobalKey getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public final void setKey(GlobalKey key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public final String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public final void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GlobalVariables [key=").append(key).append(", value=").append(value).append("]");
		return builder.toString();
	}

}
