package com.taskwar.android.activity;

import static com.taskwar.android.util.DialogUtils.DIALOG_CREATE_HOUSE_PASSWORD_VALIDATION_ID;
import static com.taskwar.android.util.DialogUtils.DIALOG_CREATE_HOUSE_PROGRESS_ID;
import static com.taskwar.android.util.ValidationUtils.validateEquals;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.taskwar.android.R;
import com.taskwar.android.model.House;
import com.taskwar.android.model.Housemate;
import com.taskwar.android.rest.RestClient;
import com.taskwar.android.ui.dialog.CustomDialog;
import com.taskwar.android.util.SharedPrefs_;

@EActivity(R.layout.create_house)
public class CreateHouseActivity extends Activity {

	@ViewById(R.id.create_house_name_etxt)
	EditText houseName;
	
	@ViewById(R.id.create_house_password_etxt)
	EditText housePassword;
	
	@ViewById(R.id.create_house_password_confirmation_etxt)
	EditText housePasswordConfirmation;
	
	@ViewById(R.id.create_house_housemate_nickname_etxt)
	EditText housemateNickname;
	
	@RestService
	RestClient restClient;
	
	@Pref
	SharedPrefs_ sharedPrefs;
	
	@Background
	void createHouse(House house) {
		House newHouse = restClient.save(house);
		sharedPrefs.edit()
					.currentHouseId().put(newHouse.getId())
					.currentHouseName().put(newHouse.getName())
					.apply();
		afterHouseCreation(newHouse.getId());
	}
	
	@UiThread
	void afterHouseCreation(int houseId) {
		Housemate housemate = new Housemate();
		housemate.setNickname(housemateNickname.getText().toString());
		housemate.setOwner(true);
		housemate.setUserId(sharedPrefs.currentUserId().get());
		housemate.setHouseId(houseId);
		
		createHousemate(housemate);
	}
	
	@Background
	void createHousemate(Housemate housemate) {
		Housemate newHousemate = restClient.save(housemate);
		sharedPrefs.currentHousemateId().put(newHousemate.getId());
		afterHousemateCreation();
	}
	
	@UiThread
	void afterHousemateCreation() {
		dismissDialog(DIALOG_CREATE_HOUSE_PROGRESS_ID);
		startActivity(new Intent(this, InviteActivity_.class));
	}
	
	public void onCreateButtonClick(View v) {

		boolean isValid = validateEquals(housePassword.getText().toString(), housePasswordConfirmation.getText().toString());

		if (isValid) {
			showDialog(DIALOG_CREATE_HOUSE_PROGRESS_ID);

			House newHouse = new House();
			newHouse.setName(houseName.getText().toString());
			newHouse.setPassword(housePassword.getText().toString());
			
			createHouse(newHouse);
		} else {
			showDialog(DIALOG_CREATE_HOUSE_PASSWORD_VALIDATION_ID);
		}
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
		case DIALOG_CREATE_HOUSE_PASSWORD_VALIDATION_ID:
			dialog = new CustomDialog(CreateHouseActivity.this, R.string.alert_validation_password, R.string.alert_validation_title);
			break;
		case DIALOG_CREATE_HOUSE_PROGRESS_ID:
			dialog = ProgressDialog.show(CreateHouseActivity.this, "", getString(R.string.progress_creating_house));
			break;
		default:
			throw new IllegalArgumentException();
		}
		return dialog;
	}
}
