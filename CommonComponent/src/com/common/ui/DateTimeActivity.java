package com.common.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.commoncomponent.R;

public class DateTimeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_date_time);
		Button b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = View.inflate(getApplicationContext(),
						R.layout.date_time_picker, null);
				final DatePicker datePicker = (DatePicker) view
						.findViewById(R.id.new_act_date_picker);
				final TimePicker timePicker = (TimePicker) view
						.findViewById(R.id.new_act_time_picker);

				timePicker.setIs24HourView(true);
				// Init DatePicker
				int year;
				int month;
				int day;

				// Build DateTimeDialog
				AlertDialog.Builder builder = new Builder(DateTimeActivity.this);
				builder.setView(view);
				builder.setTitle("…Ë÷√ ±º‰");
				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				builder.show();
			}
		});
	}

}
