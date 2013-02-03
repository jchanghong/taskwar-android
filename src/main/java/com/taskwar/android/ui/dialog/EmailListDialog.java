package com.taskwar.android.ui.dialog;

import java.util.List;

import android.app.Dialog;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.taskwar.android.R;
import com.taskwar.android.activity.InviteActivity;
import com.taskwar.android.ui.adapter.EmailListAdapter;

public class EmailListDialog extends Dialog{
	
	private ListView listView;
	
	private void initializeDialog(InviteActivity activity, ListAdapter adapter, String title) {
		setContentView(R.layout.list_dialog);

		listView = (ListView) findViewById(R.id.list_dialog_list);

		setTitle(title);
		listView.setAdapter(adapter);
	}

	public EmailListDialog(InviteActivity activity, List<String> emailList) {
		this(activity, emailList, activity.getString(R.string.list_dialog_title));
	}

	public EmailListDialog(InviteActivity activity, List<String> emailList, String title) {
		super(activity);
		initializeDialog(activity, new EmailListAdapter(activity, emailList), title);
	}
}
