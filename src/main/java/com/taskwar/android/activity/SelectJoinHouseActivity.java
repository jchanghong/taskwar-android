package com.taskwar.android.activity;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.taskwar.android.R;

import android.app.Activity;
import android.content.Intent;

@EActivity(R.layout.select_join_house)
public class SelectJoinHouseActivity extends Activity {

	@Click(R.id.select_join_house_join_btn)
	public void joinButtonClick() {
		startActivity(new Intent(this, JoinHouseActivity_.class));
	}

	@Click(R.id.select_join_house_create_btn)
	public void createButtonClick() {
		startActivity(new Intent(this, CreateHouseActivity_.class));
	}
}
