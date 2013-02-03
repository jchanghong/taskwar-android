package com.taskwar.android.model;

import java.util.List;

public class IdList {
	private List<Integer> ids;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IdList [ids=").append(ids).append("]");
		return builder.toString();
	}

}
