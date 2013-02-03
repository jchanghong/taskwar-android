package com.taskwar.android.model;

import java.io.Serializable;
import java.util.List;

public class Housemate implements Serializable {

	private static final long serialVersionUID = 6105880163969771472L;
	
	private int id;
	private int score;
	private int houseId;
	private int userId;
	private String nickname;
	private boolean owner;
	private List<Integer> doneTasksIds;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public List<Integer> getDoneTasksIds() {
		return doneTasksIds;
	}

	public void setDoneTasksIds(List<Integer> doneTasksIds) {
		this.doneTasksIds = doneTasksIds;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Housemate [id=").append(id).append(", score=")
				.append(score).append(", houseId=").append(houseId)
				.append(", userId=").append(userId).append(", nickname=")
				.append(nickname).append(", owner=").append(owner)
				.append(", doneTasksIds=").append(doneTasksIds).append("]");
		return builder.toString();
	}

}
