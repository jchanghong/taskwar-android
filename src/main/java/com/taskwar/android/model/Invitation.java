package com.taskwar.android.model;

public class Invitation {
	private int id;
	private String email;
	private int housemate_id;
	private int house_id;

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

	public int getHousemate_id() {
		return housemate_id;
	}

	public void setHousemate_id(int housemate_id) {
		this.housemate_id = housemate_id;
	}

	public int getHouse_id() {
		return house_id;
	}

	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invitation [id=").append(id).append(", email=")
				.append(email).append(", housemate_id=").append(housemate_id)
				.append(", house_id=").append(house_id).append("]");
		return builder.toString();
	}

}
