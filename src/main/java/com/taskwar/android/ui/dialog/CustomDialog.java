package com.taskwar.android.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.taskwar.android.R;

public class CustomDialog extends Dialog {

	private TextView textView;
	private ImageView imageView;

	private void initializeDialog(Context context) {
		setContentView(R.layout.custom_dialog);
		// there are a lot of settings, for dialog, check them all out!

		// set up text
		textView = (TextView) findViewById(R.id.custom_dialog_txt);

		// set up image view
		imageView = (ImageView) findViewById(R.id.custom_dialog_img);

		// set up button
		Button button = (Button) findViewById(R.id.custom_dialog_btn);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		// Fill in with default values
		setTitle("Taskwar");
		textView.setText("This is a custom dialog");
		imageView.setImageResource(android.R.drawable.ic_dialog_alert);
	}

	public CustomDialog(Context context) {
		super(context);
		initializeDialog(context);
	}

	public CustomDialog(Context context, String text) {
		this(context);
		setText(text);
	}

	public CustomDialog(Context context, int textResourceId) {
		this(context);
		setText(context.getString(textResourceId));
	}

	public CustomDialog(Context context, String text, String title) {
		this(context, text);
		setTitle(title);
	}

	public CustomDialog(Context context, int textResourceId, int titleResourceId) {
		this(context, textResourceId);
		setTitle(context.getString(titleResourceId));
	}

	public CustomDialog(Context context, String text, String title, int imageId) {
		this(context, text, title);
		setImage(imageId);
	}

	public CustomDialog(Context context, int textResourceId, int titleResourceId, int imageResourceId) {
		this(context, textResourceId, titleResourceId);
		setImage(imageResourceId);
	}

	private void setText(String text) {
		textView.setText(text);
	}

	private void setImage(int imageId) {
		imageView.setImageResource(imageId);
	}
}
