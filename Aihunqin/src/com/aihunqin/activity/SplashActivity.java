package com.aihunqin.activity;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.aihunqin.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;

public class SplashActivity extends Activity {
	Handler mHandler = new Handler();
	static final String TAG = "WebGameActivity";
	Animation animationGone;
	int LOAD_DISPLAY_TIME = 4000;

	// Baidu Location
	LocationClient mLocationClient = null;
	BDLocationListener mListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_common_launcher);

		/**
		 * Handler
		 */
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/*
				 * Create an Intent that will start the Main WordPress Activity.
				 */
				Intent intent = new Intent(SplashActivity.this,
						LoginActivity.class);
				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();
				SplashActivity.this.overridePendingTransition(
						R.anim.launch_input, R.anim.launch_output);
			}
		}, LOAD_DISPLAY_TIME);
	}

}
