package com.taskwar.android.activity;

import static com.taskwar.android.util.DialogUtils.DIALOG_CREATE_ACCOUNT_PASSWORD_VALIDATION_ID;
import static com.taskwar.android.util.DialogUtils.DIALOG_CREATE_ACCOUNT_PROGRESS_ID;
import static com.taskwar.android.util.ValidationUtils.validateEquals;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.taskwar.android.model.User;
import com.taskwar.android.rest.RestClient;
import com.taskwar.android.ui.dialog.CustomDialog;
import com.taskwar.android.util.SharedPrefs_;

@EActivity(R.layout.create_account)
public class CreateAccountActivity extends Activity {

	@ViewById(R.id.create_account_email_etxt)
	EditText emailText;

	@ViewById(R.id.create_account_password_etxt)
	EditText passwordText;

	@ViewById(R.id.create_account_confirm_password_etxt)
	EditText confirmPasswordText;

	@RestService
	RestClient restClient;

	@Pref
	SharedPrefs_ sharedPrefs;
	
	@Background
	void createAccount(User user) {
		User newUser = restClient.save(user);
		sharedPrefs.edit()
			.currentUserId().put(newUser.getId())
			.userLoggedIn().put(true);
		afterCreateUser();
	}

	@UiThread
	void afterCreateUser() {
		dismissDialog(DIALOG_CREATE_ACCOUNT_PROGRESS_ID);
		startActivity(new Intent(this, SelectJoinHouseActivity_.class));
	}

	@Click(R.id.create_account_create_btn)
	void createButtonClick() {
		String password = passwordText.getText().toString();
		String confirmPassword = confirmPasswordText.getText().toString();

		boolean isValid = validateEquals(password, confirmPassword);

		if (isValid) {
			// Showing Progress Dialog
			showDialog(DIALOG_CREATE_ACCOUNT_PROGRESS_ID);

			User user = new User();
			user.setEmail(emailText.getText().toString());
			user.setPassword(password);
			// Calling webservice in asynctask
			createAccount(user);
		} else {
			showDialog(DIALOG_CREATE_ACCOUNT_PASSWORD_VALIDATION_ID);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog;
		switch (id) {
		case DIALOG_CREATE_ACCOUNT_PASSWORD_VALIDATION_ID:
			dialog = new CustomDialog(this, R.string.alert_validation_password, R.string.alert_validation_title);
			break;
		case DIALOG_CREATE_ACCOUNT_PROGRESS_ID:
			dialog = ProgressDialog.show(this, "", getString(R.string.progress_creating_account));
			break;
		default:
			throw new IllegalArgumentException();
		}
		return dialog;
	}
}
