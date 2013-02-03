package com.taskwar.android.model;

import java.io.Serializable;
import java.util.List;

public class House implements Serializable {

	private static final long serialVersionUID = -379159135827625345L;
	
	private int id;
	private String name;
	private String password;
	private List<Integer> houseMatesIds;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Integer> getHouseMatesIds() {
		return houseMatesIds;
	}

	public void setHouseMatesIds(List<Integer> houseMatesIds) {
		this.houseMatesIds = houseMatesIds;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("House [id=").append(id).append(", name=").append(name)
				.append(", password=").append(password)
				.append(", houseMatesIds=").append(houseMatesIds).append("]");
		return builder.toString();
	}

}
