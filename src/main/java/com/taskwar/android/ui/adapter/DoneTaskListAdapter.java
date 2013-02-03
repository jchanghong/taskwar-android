package com.taskwar.android.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taskwar.android.R;
import com.taskwar.android.model.DoneTask;

public class DoneTaskListAdapter extends BaseAdapter {

	private List<DoneTask> taskList;
	private LayoutInflater inflater;

	private class ViewHolder {
		private TextView taskName;
		private TextView taskPoints;
		private TextView taskId;
		private TextView taskOwner;
	}

	public DoneTaskListAdapter(Context context, List<DoneTask> taskList) {
		inflater = LayoutInflater.from(context);
		this.taskList = taskList;
	}

	@Override
	public int getCount() {
		return taskList.size();
	}

	@Override
	public Object getItem(int position) {
		return taskList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_done_task_list, null);

			holder = new ViewHolder();
			holder.taskName = (TextView) convertView.findViewById(R.id.row_done_task_list_name);
			holder.taskPoints = (TextView) convertView.findViewById(R.id.row_done_task_list_points);
			holder.taskId = (TextView) convertView.findViewById(R.id.row_done_task_list_id);
			holder.taskOwner = (TextView) convertView.findViewById(R.id.row_done_task_list_owner);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.taskName.setText(taskList.get(position).getName());
		holder.taskPoints.setText(taskList.get(position).getPoints() + " points");
		holder.taskId.setText(Integer.toString(taskList.get(position).getId()));
		//holder.taskOwner.setText(taskList.get(position).getHousemate().getNickname());

		return convertView;
	}
}