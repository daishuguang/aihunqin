package com.ihunqin.test;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aihunqin.R;
import com.ihunqin.activity.AlarmActivity;
import com.ihunqin.receiver.AlarmReceiver;

public class AlarmTest extends Activity {
	Button setTime;
	AlarmManager aManager;
	Calendar currentTime = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarmtest);
		setTime = (Button) findViewById(R.id.setTime);
		aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

		setTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar currentTime = Calendar.getInstance();
				new TimePickerDialog(AlarmTest.this, 0,
						new OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {

								Intent intent = new Intent(AlarmTest.this,
										AlarmReceiver.class);

								PendingIntent pi = PendingIntent.getActivity(
										AlarmTest.this, 0, intent, 0);

								Calendar c = Calendar.getInstance();
								c.setTimeInMillis(System.currentTimeMillis());
								c.set(Calendar.HOUR, hourOfDay);
								c.set(Calendar.MINUTE, minute);
								aManager.set(AlarmManager.RTC_WAKEUP,
										c.getTimeInMillis(), pi);
								Toast.makeText(getApplication(), "hahah",
										Toast.LENGTH_SHORT).show();
							}
						},
						AlarmTest.this.currentTime.get(Calendar.HOUR_OF_DAY),
						AlarmTest.this.currentTime.get(Calendar.MINUTE), false)
						.show();
			}
		});
	}
}
