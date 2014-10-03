package com.aihunqin.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aihunqin.util.HttpUtil;
import com.baidu.navisdk.ui.routeguide.subview.r;
import com.example.aihunqin.R;

public class RegisterActivity extends Activity {
	TextView back;
	TextView titleTv;
	TextView rightmenu;
	Button register;
	EditText phonenum;
	EditText password1;
	EditText password2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);

		back = (TextView) findViewById(R.id.back);
		titleTv = (TextView) findViewById(R.id.titleTv);
		register = (Button) findViewById(R.id.register);
		phonenum = (EditText) findViewById(R.id.phonenum);
		password1 = (EditText) findViewById(R.id.password1);
		password2 = (EditText) findViewById(R.id.password2);

		back.setVisibility(View.VISIBLE);
		titleTv.setText("用户注册");

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		register.setOnClickListener(new OnClickListener() {

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
				if (password1.getText().toString().equals("")
						|| password2.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (!password1.getText().toString()
						.equals(password2.getText().toString()))
					Toast.makeText(getApplicationContext(), "密码不一致",
							Toast.LENGTH_SHORT).show();
				String url = "http://home.ihunqin.com/api/passport/register";
				Map<String, String> rawparams = new HashMap<String, String>();
				rawparams.put("mobile", phonenum.getText().toString());
				rawparams.put("password", password1.getText().toString());
				rawparams.put("passwordConfirmed", password2.getText()
						.toString());
				String result = null;
				try {
					register.setText("注册中");
					result = HttpUtil.postRequst(url, rawparams);
					JSONObject json = new JSONObject(result);
					String status = json.getString("Status");
					register.setText("注册");
					if (status.equals("0")) {
						// HttpUtil.httpClient.getCookieStore();
						
					} else {
						Toast.makeText(getApplicationContext(), "注册失败",
								Toast.LENGTH_SHORT).show();
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
