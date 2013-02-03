package com.taskwar.android.model;

import java.io.Serializable;

public class HouseTask implements Serializable {

	private static final long serialVersionUID = -5552951365705149518L;
	
	private int id;
	private String name;
	private int points;
	private String iconName;
	private boolean isPredefined;
	private House house;

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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public boolean isPredefined() {
		return isPredefined;
	}

	public void setPredefined(boolean isPredefined) {
		this.isPredefined = isPredefined;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HouseTask [id=").append(id).append(", name=")
				.append(name).append(", points=").append(points)
				.append(", iconName=").append(iconName)
				.append(", isPredefined=").append(isPredefined)
				.append(", house=").append(house).append("]");
		return builder.toString();
	}

}
