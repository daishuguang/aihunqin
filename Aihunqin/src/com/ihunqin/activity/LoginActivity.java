package com.ihunqin.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihunqin.R;
import com.ihunqin.crazy.SinaMain;
import com.ihunqin.util.HttpUtil;
import com.ihunqin.util.NetworkUtil;

public class LoginActivity extends Activity {

	TextView back;
	TextView titleTv;
	TextView rightmenu;
	EditText phonenum;
	EditText password;
	Button login1;
	ProgressDialog p1;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		p1 = new ProgressDialog(this);
		phonenum = (EditText) findViewById(R.id.phonenum);
		password = (EditText) findViewById(R.id.password);
		login1 = (Button) findViewById(R.id.login1);

		titleTv = (TextView) findViewById(R.id.titleTv);
		rightmenu = (TextView) findViewById(R.id.rightmenu);

		titleTv.setText("用户登录");
		rightmenu.setText("注册");
		rightmenu.setVisibility(View.VISIBLE);
		rightmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(intent);
			}
		});

		login1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (NetworkUtil.isOnline(LoginActivity.this)) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					if (phonenum.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(), "手机号不能为空",
								Toast.LENGTH_SHORT).show();
						return;
					}

					if (!phonenum.getText().toString()
							.matches("[1][358]\\d{9}")) {
						Toast.makeText(getApplicationContext(), "手机号不正确",
								Toast.LENGTH_SHORT).show();
						return;
					}

					if (password.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(), "密码不能为空",
								Toast.LENGTH_SHORT).show();
						return;
					}
					p1.setMessage("正在登录中");
					p1.show();
					final String url = "http://home.ihunqin.com/api/passport/signin";
					final Map<String, String> rawparams = new HashMap<String, String>();
					rawparams.put("mobile", phonenum.getText().toString());
					rawparams.put("password", password.getText().toString());

					Async task = new Async(url, rawparams);
					task.execute();

				} else {
					Toast.makeText(LoginActivity.this, "未连接到网络",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

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
			p1.dismiss();
			JSONObject json;
			try {
				json = new JSONObject(result);

				String status = json.getString("Status");
				if (status.equals("0")) {

					preferences = getSharedPreferences("userinfo", MODE_PRIVATE);
					editor = preferences.edit();
					editor.putString("mobile", phonenum.getText().toString());
					editor.putString("password", password.getText().toString());
					editor.putString("userid", json.getString("Data"));
					editor.commit();
					Intent intent = new Intent(LoginActivity.this,
							AdActivity.class);
					LoginActivity.this.startActivity(intent);
					LoginActivity.this.finish();
				} else {
					Toast.makeText(LoginActivity.this, "用户名或密码错误",
							Toast.LENGTH_SHORT).show();
				}
				if (!(json.get("DataExt").equals(null))) {
					JSONObject dataext = json.getJSONObject("DataExt");
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
	}
}
