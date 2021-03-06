package com.ihunqin.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.aihunqin.R;
import com.ihunqin.util.HttpUtil;
import com.ihunqin.util.NetworkUtil;

public class SplashActivity extends Activity {
	Handler mHandler = new Handler();
	static final String TAG = "WebGameActivity";
	Animation animationGone;
	int LOAD_DISPLAY_TIME = 1000;

	// Baidu Location
	LocationClient mLocationClient = null;
	BDLocationListener mListener = null;

	SharedPreferences preferences;

	class Async extends AsyncTask<Void, Void, String> {
		String url;
		Map<String, String> raw;

		Async(String url, Map<String, String> raw) {
			this.url = url;
			this.raw = raw;
		}

		@Override
		protected String doInBackground(Void... params) {
			String result = "";
			try {

				result = HttpUtil.postRequst(url, raw);

			} catch (Exception e) {

				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			JSONObject json;
			try {
				json = new JSONObject(result);

				String status = json.getString("Status");
				if (status.equals("0")) {

					/**
					 * Handler
					 */
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							/*
							 * Create an Intent that will start the Main
							 * WordPress Activity.
							 */
							Intent intent = new Intent(SplashActivity.this,
									AdActivity.class);
							SplashActivity.this.startActivity(intent);
							SplashActivity.this.finish();
							SplashActivity.this.overridePendingTransition(
									R.anim.launch_input, R.anim.launch_output);
						}
					}, LOAD_DISPLAY_TIME);
				} else {

					/**
					 * Handler
					 */
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							/*
							 * Create an Intent that will start the Main
							 * WordPress Activity.
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
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_common_launcher);

		preferences = getSharedPreferences("userinfo", MODE_PRIVATE);
		String mobile = preferences.getString("mobile", "");
		String password = preferences.getString("password", "");
		final String url = "http://home.ihunqin.com/api/passport/signin";
		final Map<String, String> rawparams = new HashMap<String, String>();
		rawparams.put("mobile", mobile);
		rawparams.put("password", password);
		if (NetworkUtil.isOnline(this)) {
			// Register wechat
			// APP_ID
			Async task = new Async(url, rawparams);
			task.execute();
		} else {
			showDialog();
		}

	}

	/**
	 * Network is not available
	 */
	private void showDialog() {
		Toast.makeText(this, "未连接到网络", Toast.LENGTH_LONG).show();
		AlertDialog builder = new AlertDialog.Builder(this).setTitle("错误")
				.setMessage("请您确认已开通手机上网功能，并在手机上设置正确的上网方式。")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).show();
	}
}
