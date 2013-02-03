package com.taskwar.android.activity;

import static com.taskwar.android.util.DialogUtils.*;

import org.springframework.web.client.RestClientException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.GetChars;
import android.util.Log;
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
import com.taskwar.android.model.User;
import com.taskwar.android.rest.RestClient;
import com.taskwar.android.util.SharedPrefs_;
import com.taskwar.android.util.SharedPrefs_.SharedPrefsEditor_;

@EActivity(R.layout.login)
public class LoginActivity extends Activity {

	private static final String TAG = "LoginActivity";
	
	@RestService
	RestClient restClient;
	
	@ViewById(R.id.login_email_etxt)
	EditText userEmail;
	
	@ViewById(R.id.login_password_etxt)
	EditText userPassword;
	
	@Pref
	SharedPrefs_ sharedPrefs;

	@Click(R.id.login_connect_btn)
	public void loginButtonClick() {
		showDialog(DIALOG_LOGIN_PROGRESS_ID);
		
		login(userEmail.getText().toString(), userPassword.getText().toString());
	}
	
	@Background
	void login(String email, String password) {
		
		User user = restClient.login(email, password);
	
		SharedPrefsEditor_ prefsEditor = sharedPrefs.edit().currentUserId().put(user.getId());
		
		if(user.getHousemateId() != 0) {
			Housemate housemate = restClient.getHousemateById(user.getHousemateId());
			House house = restClient.getHouseById(housemate.getHouseId());
			prefsEditor.currentHouseId().put(house.getId())
				.currentHouseName().put(house.getName())
				.currentHousemateId().put(housemate.getId());
		}
		
		prefsEditor.apply();
	
		afterLogin(user);
	}
	
	@UiThread
	void afterLogin(User user) {
		dismissDialog(DIALOG_LOGIN_PROGRESS_ID);
		
		startActivity(new Intent(this, sharedPrefs.currentHouseId().get() != 0 ? HouseBoardActivity_.class : SelectJoinHouseActivity_.class));
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
		case DIALOG_CLIENT_EXCEPTION:
			dialog = null;
			break;
		case DIALOG_LOGIN_PROGRESS_ID:
			dialog = ProgressDialog.show(LoginActivity.this, "", getString(R.string.progress_login));
			break;
		default:
			throw new IllegalArgumentException();
		}
		return dialog;
	}

}
