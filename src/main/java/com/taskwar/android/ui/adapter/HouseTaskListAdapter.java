package com.taskwar.android.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.taskwar.android.R;
import com.taskwar.android.model.HouseTask;

public class HouseTaskListAdapter extends ArrayAdapter<HouseTask> {

	private List<HouseTask> originalList;
	private List<HouseTask> taskList;
	private List<HouseTask> filteredList;
	private LayoutInflater inflater;
	private Filter filter;

	private final Object lock = new Object();

	private class ViewHolder {
		private TextView taskName;
		private TextView taskPoints;
		private TextView taskId;
	}

	public HouseTaskListAdapter(Activity context, List<HouseTask> taskList) {
		super(context, 0);
		inflater = LayoutInflater.from(context);
		this.taskList = originalList = taskList;
	}

	@Override
	public int getCount() {
		return taskList.size();
	}

	@Override
	public HouseTask getItem(int position) {
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
			convertView = inflater.inflate(R.layout.row_task_list, null);

			holder = new ViewHolder();
			holder.taskName = (TextView) convertView.findViewById(R.id.row_task_list_name);
			holder.taskPoints = (TextView) convertView.findViewById(R.id.row_task_list_points);
			holder.taskId = (TextView) convertView.findViewById(R.id.row_task_list_id);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.taskName.setText(taskList.get(position).getName());
		holder.taskPoints.setText(taskList.get(position).getPoints() + " points");
		holder.taskId.setText(Integer.toString(taskList.get(position).getId()));

		return convertView;
	}

	@Override
	public Filter getFilter() {
		if (filter == null)
			filter = new HouseTaskNameFilter();
		return filter;
	}

	private class HouseTaskNameFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// NOTE: this function is *always* called from a background thread,
			// and not the UI thread.
			FilterResults result = new FilterResults();
			if (constraint != null && constraint.toString().length() > 0) {
				Log.i(getClass().getSimpleName(), "Filtering !");
				synchronized (lock) {
					filteredList = new ArrayList<HouseTask>();
					for (HouseTask ht : taskList) {
						if (ht.getName().toLowerCase(Locale.getDefault()).contains(constraint.toString().toLowerCase(Locale.getDefault()))) {
							Log.d(getClass().getSimpleName(), "Adding a task");
							filteredList.add(ht);
						}
					}
				}
				taskList = filteredList;
				result.count = filteredList.size();
				result.values = filteredList;
			} else {
				taskList = originalList;
				result.count = taskList.size();
				result.values = taskList;
			}
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults result) {
			if (result.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}
}
