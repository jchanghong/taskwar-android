package com.taskwar.android.activity;

import android.app.Activity;
import android.content.Intent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.taskwar.android.R;
import com.taskwar.android.model.House;
import com.taskwar.android.model.User;
import com.taskwar.android.rest.RestClient;
import com.taskwar.android.util.SharedPrefs_;

@EActivity(R.layout.splash)
public class SplashActivity extends Activity {

	@RestService
	RestClient restClient;
	
	@Pref
	SharedPrefs_ sharedPrefs;
	
	@AfterViews
	void afterViews() {
		if (sharedPrefs.userLoggedIn().get()) {
			loadUser();
		} else {
			startActivity(new Intent(this, HomeActivity_.class));
		}
	}
	
	@Background
	void loadUser() {
		afterLoadUser(restClient.getUserById(sharedPrefs.currentUserId().get()));
	}
	
	@UiThread
	void afterLoadUser(User user) {
		if (user.getHousemateId() != 0) {
			loadHouse();
		} else {
			startActivity(new Intent(this, SelectJoinHouseActivity_.class));
		}
	}
	
	@Background
	void loadHouse() {
		afterLoadHouse(restClient.getHouseById(sharedPrefs.currentHouseId().get()));
	}
	
	@UiThread
	void afterLoadHouse(House house) {
		startActivity(new Intent(this, HouseBoardActivity_.class));
	}
}
