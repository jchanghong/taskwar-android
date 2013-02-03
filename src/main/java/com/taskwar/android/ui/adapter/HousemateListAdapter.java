package com.taskwar.android.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taskwar.android.R;
import com.taskwar.android.model.Housemate;

public class HousemateListAdapter extends BaseAdapter{
	private List<Housemate> housemateList;
	private LayoutInflater inflater;

	private class ViewHolder {
		private TextView nickname;
		private TextView score;
	}

	public HousemateListAdapter(Context context, List<Housemate> housemateList) {
		inflater = LayoutInflater.from(context);
		this.housemateList = housemateList;
	}

	@Override
	public int getCount() {
		return housemateList.size();
	}

	@Override
	public Object getItem(int position) {
		return housemateList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_housemate_list, null);

			holder = new ViewHolder();
			holder.nickname = (TextView) convertView.findViewById(R.id.row_housemate_list_nickname);
			holder.score = (TextView) convertView.findViewById(R.id.row_housemate_score);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.nickname.setText(housemateList.get(position).getNickname());
		holder.score.setText(housemateList.get(position).getScore() + " pts");

		return convertView;
	}
}
