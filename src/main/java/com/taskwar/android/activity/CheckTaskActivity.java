package com.taskwar.android.activity;

import static com.taskwar.android.util.DialogUtils.DIALOG_CHECK_TASK_PROGRESS_ID;
import static com.taskwar.android.util.DialogUtils.DIALOG_CHECK_TASK_RETRIEVE_TASKS_ID;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.AfterTextChange;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.taskwar.android.R;
import com.taskwar.android.model.DoneTask;
import com.taskwar.android.model.HouseTask;
import com.taskwar.android.model.IdList;
import com.taskwar.android.rest.RestClient;
import com.taskwar.android.ui.adapter.HouseTaskListAdapter;
import com.taskwar.android.util.SharedPrefs_;

@EActivity(R.layout.check_task)
public class CheckTaskActivity extends ListActivity {

	@ViewById(R.id.check_task_filter)
	EditText editFilter;

	@RestService
	RestClient restClient;
	
	@Pref
	SharedPrefs_ sharedPrefs;
	
	private HouseTaskListAdapter adapter;

	@AfterViews
	void initActivity() {
		showDialog(DIALOG_CHECK_TASK_RETRIEVE_TASKS_ID);

		retrieveHouseTasks(sharedPrefs.currentHouseId().get());
	}
	
	@AfterTextChange(R.id.check_task_filter)
	void afterTextChange() {
		adapter.getFilter().filter(editFilter.getText().toString());
	}
	
	@ItemClick
	void listItemClicked(HouseTask houseTask) {
		DoneTask doneTask = new DoneTask();
		doneTask.setHouseTaskId(houseTask.getId());
		doneTask.setHousemateId(sharedPrefs.currentHousemateId().get());

		showDialog(DIALOG_CHECK_TASK_PROGRESS_ID);
		createDoneTask(doneTask);
	}
	
	@Background
	void createDoneTask(DoneTask doneTask) {
		afterCreateDoneTask(restClient.save(doneTask));
	}

	@UiThread
	void afterCreateDoneTask(DoneTask savedDoneTask) {
		dismissDialog(DIALOG_CHECK_TASK_PROGRESS_ID);
		setResult(RESULT_OK);
		finish();
	}

	@Background
	void retrieveHouseTasks(int houseId) {
		IdList ids = restClient.getHouseTasksOfHouse(houseId);
		// TODO: Get actual objects
		afterRetrieveHouseTasks(new ArrayList<HouseTask>());
	}

	@UiThread
	void afterRetrieveHouseTasks(List<HouseTask> houseTasks) {
		dismissDialog(DIALOG_CHECK_TASK_RETRIEVE_TASKS_ID);
		adapter = new HouseTaskListAdapter(this, houseTasks);
		setListAdapter(adapter);
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog;
		switch (id) {
		case DIALOG_CHECK_TASK_PROGRESS_ID:
			dialog = ProgressDialog.show(this, "", getString(R.string.progress_checking_task));
			break;
		case DIALOG_CHECK_TASK_RETRIEVE_TASKS_ID:
			dialog = ProgressDialog.show(this, "", getString(R.string.progress_retrieving_tasks));
			break;
		default:
			throw new IllegalArgumentException();
		}
		return dialog;
	}
}
