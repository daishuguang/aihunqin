package com.ihunqin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.example.aihunqin.R;
import com.ihunqin.crazy.SinaMain;

public class AdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_activity);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/*
				 * Create an Intent that will start the Main WordPress Activity.
				 */
				Intent intent = new Intent(AdActivity.this, SinaMain.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.launch_input,
						R.anim.launch_output);
			}
		}, 3000);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		Intent intent = new Intent(this, SinaMain.class);
		startActivity(intent);
		finish();
		return super.onTouchEvent(event);
	}

}
