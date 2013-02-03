package com.taskwar.android.ui.adapter;

import java.util.List;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.taskwar.android.R;
import com.taskwar.android.activity.InviteActivity;
import com.taskwar.android.model.Invitation;

public class InvitationListAdapter extends BaseAdapter {

	public static final int PICK_CONTACT = 0;
	
	private List<Invitation> invitationsList;
	private LayoutInflater inflater;
	private InviteActivity activity;

	private class ViewHolder {
		private EditText email;
		private ImageButton fromContactBtn;
	}

	public InvitationListAdapter(InviteActivity activity, List<Invitation> mailList) {
		inflater = LayoutInflater.from(activity);
		this.invitationsList = mailList;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return invitationsList.size();
	}

	@Override
	public Invitation getItem(int position) {
		return invitationsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_invite_list, null);

			holder = new ViewHolder();
			holder.email = (EditText) convertView.findViewById(R.id.row_invite_email);
			holder.fromContactBtn = (ImageButton) convertView.findViewById(R.id.row_invite_contact_btn);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.email.setText(invitationsList.get(position).getEmail());
		holder.email.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				invitationsList.get(position).setEmail(((EditText) v).getText().toString());
				//invitationsList.get(position).setHouse(HouseService.getInstance().getCurrentHouse(activity));
				//invitationsList.get(position).setHousemate(HousemateService.getInstance().getCurrentHousemate(activity));
			}
		});

		holder.fromContactBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.setCurrentRowId(position);
				activity.startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACT);
			}
		});

		return convertView;
	}
	
	public void setEmailAt(String email, int position){
//		invitationsList.set(position, new Invitation(email, 
//						HouseService.getInstance().getCurrentHouse(activity), 
//						HousemateService.getInstance().getCurrentHousemate(activity)));
		notifyDataSetChanged();
	}

	public List<Invitation> getInvitationList() {
		return invitationsList;
	}
}
