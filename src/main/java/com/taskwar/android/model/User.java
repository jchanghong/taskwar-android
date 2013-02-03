package com.taskwar.android.model;

public class User {
	private int id;
	private String email;
	private String password;
	private int housemateId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getHousemateId() {
		return housemateId;
	}

	public void setHousemateId(int housemateId) {
		this.housemateId = housemateId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", email=").append(email)
				.append(", password=").append(password)
				.append(", housemateId=").append(housemateId).append("]");
		return builder.toString();
	}

}
