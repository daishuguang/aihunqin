package com.aihunqin.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aihunqin.util.HttpUtil;
import com.example.aihunqin.R;

public class LoginActivity extends Activity {

	TextView back;
	TextView titleTv;
	TextView rightmenu;
	EditText phonenum;
	EditText password;
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);

		phonenum = (EditText) findViewById(R.id.phonenum);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		back = (TextView) findViewById(R.id.back);
		titleTv = (TextView) findViewById(R.id.titleTv);
		rightmenu = (TextView) findViewById(R.id.rightmenu);

		back.setVisibility(View.VISIBLE);
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

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (phonenum.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "手机号不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (!phonenum.getText().toString().matches("[1][358]\\d{9}")) {
					Toast.makeText(getApplicationContext(), "手机号不正确",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (password.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String url = "http://home.ihunqin.com/api/passport/signin";
				Map<String, String> rawparams = new HashMap<String, String>();
				rawparams.put("mobile", phonenum.getText().toString());
				rawparams.put("password", password.getText().toString());
				String result = null;
				try {
					result = HttpUtil.postRequst(url, rawparams);

					JSONObject json = new JSONObject(result);
					String status = json.getString("Status");

					if (status.equals("0")) {
						HttpUtil.httpClient.getCookieStore();
					}
					String data = json.getString("Data");
					if (!(json.get("DataExt").equals(null))) {
						JSONObject dataext = json.getJSONObject("DataExt");
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}
}
