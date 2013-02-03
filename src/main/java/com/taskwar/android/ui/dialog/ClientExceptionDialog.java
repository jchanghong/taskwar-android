package com.taskwar.android.ui.dialog;

import android.content.Context;

import com.taskwar.android.R;

public class ClientExceptionDialog extends CustomDialog {
	public ClientExceptionDialog(Context context) {
		super(context, context.getString(R.string.alert_client_exception), context.getString(R.string.alert_client_exception_title));
	}
}
