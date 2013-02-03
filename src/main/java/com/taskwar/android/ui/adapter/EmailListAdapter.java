package com.taskwar.android.ui.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.taskwar.android.R;
import com.taskwar.android.activity.InviteActivity;
import com.taskwar.android.util.DialogUtils;

public class EmailListAdapter extends BaseAdapter {

	private List<String> emailList;
	private LayoutInflater inflater;
	private InviteActivity activity;

	private class ViewHolder {
		private RadioButton radioButton;
	}

	public EmailListAdapter(InviteActivity activity, List<String> emailList) {
		inflater = LayoutInflater.from(activity);
		this.emailList = emailList;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return emailList.size();
	}

	@Override
	public Object getItem(int position) {
		return emailList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_email_list, null);

			holder = new ViewHolder();
			holder.radioButton = (RadioButton) convertView.findViewById(R.id.row_email_list_btn);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final ViewHolder finalHolder = holder;

		holder.radioButton.setText(emailList.get(position));
		holder.radioButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.setEmail(finalHolder.radioButton.getText().toString());
				activity.dismissDialog(DialogUtils.DIALOG_SELECT_EMAIL_ID);
			}
		});
		
		return convertView;
	}
}