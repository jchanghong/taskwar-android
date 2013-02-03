package com.taskwar.android.model;

import java.io.Serializable;
import java.util.Date;

public class DoneTask implements Serializable {

	private static final long serialVersionUID = 9011943113594998660L;
	
	private int id;
	private Date date;
	private int points;
	private String name;
	private String iconName;
	private int housemateId;
	private int houseTaskId;
	
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
	public int getHousemateId() {
		return housemateId;
	}
	public void setHousemateId(int housemateId) {
		this.housemateId = housemateId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getHouseTaskId() {
		return houseTaskId;
	}
	public void setHouseTaskId(int houseTaskId) {
		this.houseTaskId = houseTaskId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DoneTask [id=").append(id).append(", name=")
				.append(name).append(", points=").append(points)
				.append(", iconName=").append(iconName)
				.append(", housemateId=").append(housemateId).append(", date=")
				.append(date).append(", houseTaskId=").append(houseTaskId)
				.append("]");
		return builder.toString();
	}

}
