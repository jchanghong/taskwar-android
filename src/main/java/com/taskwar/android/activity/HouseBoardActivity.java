package com.taskwar.android.activity;

import static com.taskwar.android.util.DialogUtils.DIALOG_DONE_TASKS_RETRIEVAL_PROGRESS_ID;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OnActivityResult;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.taskwar.android.R;
import com.taskwar.android.model.DoneTask;
import com.taskwar.android.model.House;
import com.taskwar.android.model.Housemate;
import com.taskwar.android.model.IdList;
import com.taskwar.android.rest.RestClient;
import com.taskwar.android.ui.adapter.DoneTaskListAdapter;
import com.taskwar.android.ui.adapter.HousemateListAdapter;
import com.taskwar.android.util.SharedPrefs_;

@EActivity(R.layout.house_board)
@OptionsMenu(R.menu.menu)
public class HouseBoardActivity extends ListActivity {
	public final static int LAUNCH_CHECK_TASK_ACTIVITY = 1;

	@ViewById(R.id.house_board_done_tasks_progress)
	ProgressBar doneTasksProgress;

	@ViewById(R.id.house_board_housemates_progress)
	ProgressBar housematesProgress;

	@ViewById(R.id.house_board_housemates)
	ListView housematesList;

	@ViewById(R.id.house_board_title)
	TextView title;

	@RestService
	RestClient restClient;

	@Pref
	SharedPrefs_ sharedPrefs;
	
	int errorDialog;
	House currentHouse;

	@AfterViews
	void afterViews() {
		title.setText(sharedPrefs.currentHouseName().get());

		updateContent();
	}

	@OnActivityResult(LAUNCH_CHECK_TASK_ACTIVITY)
	void afterCheckTaskActivity(int resultCode) {
		if (resultCode == RESULT_OK) {
			Toast.makeText(HouseBoardActivity.this, getString(R.string.toast_task_checked), Toast.LENGTH_LONG).show();

			updateContent();
		}
	}

	void updateContent() {
		startRetrievingDoneTasks(sharedPrefs.currentHouseId().get());
		startRetrievingHousemates(sharedPrefs.currentHouseId().get());
	}

	@UiThread
	void startRetrievingDoneTasks(int houseId) {
		doneTasksProgress.setVisibility(View.VISIBLE);
		retrieveDoneTasks(houseId);
	}

	@Background
	void retrieveDoneTasks(int houseId) {
		IdList doneTasksIds = restClient.getDoneTasksOfHouse(houseId);
		// TODO: Retrieve actual objects
		afterRetrieveDoneTasks(new ArrayList<DoneTask>());
	}

	@UiThread
	void afterRetrieveDoneTasks(List<DoneTask> doneTasks) {
		setListAdapter(new DoneTaskListAdapter(this, doneTasks));
		doneTasksProgress.setVisibility(View.INVISIBLE);
	}

	@UiThread
	void startRetrievingHousemates(int houseId) {
		housematesProgress.setVisibility(View.VISIBLE);
		retrieveHousemates(houseId);
	}

	@Background
	void retrieveHousemates(int houseId) {
		IdList housematesIds = restClient.getHousematesOfHouse(houseId);
		// TODO: Retrieve actual objects
		afterRetrieveHousemates(new ArrayList<Housemate>());
	}

	@UiThread
	void afterRetrieveHousemates(List<Housemate> housemates) {
		housematesProgress.setVisibility(View.INVISIBLE);
		housematesList.setAdapter(new HousemateListAdapter(this, housemates));
	}

	@Click(R.id.house_board_check_task_btn)
	void checkTaskButtonClick() {
		startActivityForResult(new Intent(this, CheckTaskActivity_.class), LAUNCH_CHECK_TASK_ACTIVITY);
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog;
		switch (id) {
		case DIALOG_DONE_TASKS_RETRIEVAL_PROGRESS_ID:
			dialog = ProgressDialog.show(HouseBoardActivity.this, "", getString(R.string.progress_retrieving_done_tasks));
			break;
		default:
			throw new IllegalArgumentException();
		}
		return dialog;
	}

	@OptionsItem(R.id.menu_logout)
	void menuLogout() {
		sharedPrefs.clear();
		startActivity(new Intent(HouseBoardActivity.this, HomeActivity.class));
	}

	@OptionsItem(R.id.menu_refresh)
	void menuRefresh() {
		updateContent();
	}

}
