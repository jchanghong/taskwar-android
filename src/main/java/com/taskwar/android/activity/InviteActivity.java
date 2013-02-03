package com.taskwar.android.activity;

import static com.taskwar.android.util.DialogUtils.DIALOG_SELECT_EMAIL_ID;
import static com.taskwar.android.util.DialogUtils.DIALOG_SEND_INVITATIONS_PROGRESS_ID;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OnActivityResult;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.taskwar.android.R;
import com.taskwar.android.model.Invitation;
import com.taskwar.android.rest.RestClient;
import com.taskwar.android.ui.adapter.InvitationListAdapter;
import com.taskwar.android.ui.dialog.EmailListDialog;

@EActivity(R.layout.invite)
public class InviteActivity extends ListActivity {

	private ListView lv;
	private InvitationListAdapter adapter;

	private int currentRowId;

	@RestService
	RestClient restClient;
	
	@AfterViews
	void afterViews() {
		List<Invitation> invitationList = new ArrayList<Invitation>();
		invitationList.add(new Invitation());
		invitationList.add(new Invitation());
		invitationList.add(new Invitation());

		adapter = new InvitationListAdapter(this, invitationList);

		lv = getListView();

		lv.setAdapter(adapter);
	}
	
	@Background
	void sendInvitations(List<Invitation> invitations) {
		for(Invitation invitation : invitations)
			restClient.save(invitation);
		// TODO: Error management
		afterSendInvitation();
	}
	
	@UiThread
	void afterSendInvitation() {
		dismissDialog(DIALOG_SEND_INVITATIONS_PROGRESS_ID);
		startActivity(new Intent(this, HouseBoardActivity_.class));
	}

	@Click
	void inviteBtn() {
		showDialog(DIALOG_SEND_INVITATIONS_PROGRESS_ID);
		sendInvitations(adapter.getInvitationList());
	}

	@OnActivityResult(InvitationListAdapter.PICK_CONTACT)
	void onPickContact(int resultCode, Intent intent) {
		if (resultCode == RESULT_OK) {
			String id = intent.getData().getLastPathSegment();
			// Find Email Addresses
			Cursor emails = getContentResolver().query(Email.CONTENT_URI, null, Email.CONTACT_ID + "=?", new String[] { id }, null);
			if (emails.getCount() == 1) {
				emails.moveToFirst();
				setEmail(emails.getString(emails.getColumnIndex(Email.DATA)));
			} else {
				ArrayList<String> emailList = new ArrayList<String>();
				while (emails.moveToNext()) {
					emailList.add(emails.getString(emails.getColumnIndex(Email.DATA)));
				}

				Bundle bundle = new Bundle();
				bundle.putStringArrayList("emails", emailList);

				showDialog(DIALOG_SELECT_EMAIL_ID, bundle);
			}
			emails.close();
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		Dialog dialog;
		switch (id) {
		case DIALOG_SELECT_EMAIL_ID:
			dialog = new EmailListDialog(InviteActivity.this, bundle.getStringArrayList("emails"), getString(R.string.list_dialog_email_title));
			break;
		case DIALOG_SEND_INVITATIONS_PROGRESS_ID:
			dialog = ProgressDialog.show(InviteActivity.this, "", getString(R.string.progress_sending_invitations));
			break;
		default:
			throw new IllegalArgumentException();
		}
		return dialog;
	}

	public void setCurrentRowId(int currentRowId) {
		this.currentRowId = currentRowId;
	}

	public void setEmail(String email) {
		adapter.setEmailAt(email, currentRowId);
	}
}
