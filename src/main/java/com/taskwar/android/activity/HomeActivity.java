package com.taskwar.android.activity;

import android.app.Activity;
import android.content.Intent;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.taskwar.android.R;

@EActivity(R.layout.home)
public class HomeActivity extends Activity {

	@Click(R.id.home_create_account_btn)
	public void onCreateAccountButtonClick() {
		startActivity(new Intent(this, CreateAccountActivity_.class));
	}

	@Click(R.id.home_connect_btn)
	void onConnectButtonClick() {
		startActivity(new Intent(this, LoginActivity_.class));
	}
}
