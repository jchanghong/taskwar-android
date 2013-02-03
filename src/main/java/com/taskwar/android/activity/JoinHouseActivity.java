package com.taskwar.android.activity;

import static com.taskwar.android.util.DialogUtils.DIALOG_JOIN_HOUSE_PROGRESS_ID;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.taskwar.android.R;
import com.taskwar.android.model.House;
import com.taskwar.android.model.Housemate;
import com.taskwar.android.rest.RestClient;
import com.taskwar.android.util.SharedPrefs_;

@EActivity(R.layout.join_house)
public class JoinHouseActivity extends Activity {
	
	@ViewById(R.id.join_house_name_etxt)
	EditText houseName;
	
	@ViewById(R.id.join_house_password_etxt)
	EditText housePassword;

	@ViewById(R.id.join_house_housemate_nickname_etxt)
	EditText housemateNickname;
	
	@RestService
	RestClient restClient;
	
	@Pref
	SharedPrefs_ sharedPrefs;
	
	@Background
	void joinHouse(String houseName, String housePassword) {
		House joinedHouse = restClient.houseExists(houseName,housePassword);
		sharedPrefs.edit()
					.currentHouseId().put(joinedHouse.getId())
					.currentHouseName().put(joinedHouse.getName())
					.apply();
		afterJoinHouse(joinedHouse);
	}
	
	@UiThread
	void afterJoinHouse(House house) {
		if (house == null) {
			dismissDialog(DIALOG_JOIN_HOUSE_PROGRESS_ID);
		} else {
			Housemate housemate = new Housemate();
			housemate.setNickname(housemateNickname.getText().toString());
			housemate.setOwner(false);
			housemate.setUserId(sharedPrefs.currentUserId().get());
			housemate.setHouseId(house.getId());
			
			createHousemate(housemate);
		}
	}
	
	@Background
	void createHousemate(Housemate housemate){
		Housemate newHousemate = restClient.save(housemate);
		sharedPrefs.currentHousemateId().put(newHousemate.getId());
		afterCreateHousemate(newHousemate);
	}
	
	void afterCreateHousemate(Housemate housemate) {
		if (housemate == null) {
			// Rollback modifications
		} else {
			dismissDialog(DIALOG_JOIN_HOUSE_PROGRESS_ID);
			startActivity(new Intent(this, HouseBoardActivity_.class));
		}
	}
	
	@Click(R.id.join_house_join_btn)
	void onJoinButtonClick() {
		showDialog(DIALOG_JOIN_HOUSE_PROGRESS_ID);

		joinHouse(houseName.getText().toString(), housePassword.getText().toString());
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		Dialog dialog;
		switch (id) {
		case DIALOG_JOIN_HOUSE_PROGRESS_ID:
			dialog = ProgressDialog.show(JoinHouseActivity.this, "", getString(R.string.progress_joining_house));
			break;
		default:
			throw new IllegalArgumentException();
		}
		return dialog;
	}

}
