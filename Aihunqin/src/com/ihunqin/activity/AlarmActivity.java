package com.ihunqin.activity;

import com.example.aihunqin.R;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

public class AlarmActivity extends Activity {
	Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_common_launcher);
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
		Toast.makeText(getApplication(), "ªÈ¿Ò»ŒŒÒÃ·–—", Toast.LENGTH_SHORT).show();
	}

}
