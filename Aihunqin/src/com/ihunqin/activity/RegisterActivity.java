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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihunqin.R;
import com.ihunqin.activity.LoginActivity.Async;
import com.ihunqin.crazy.SinaMain;
import com.ihunqin.util.HttpUtil;
import com.ihunqin.util.NetworkUtil;

public class RegisterActivity extends Activity {
	TextView back;
	TextView titleTv;
	TextView rightmenu;
	Button register;
	Button jihuo;
	EditText phonenum;
	EditText password1;
	EditText password2;
	EditText jihuoma;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	LinearLayout jihuoinfo;
	ProgressDialog p1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);

		jihuoinfo = (LinearLayout) findViewById(R.id.jihuoinfo);
		back = (TextView) findViewById(R.id.back);
		titleTv = (TextView) findViewById(R.id.titleTv);
		register = (Button) findViewById(R.id.register);
		phonenum = (EditText) findViewById(R.id.phonenum);
		password1 = (EditText) findViewById(R.id.password1);
		password2 = (EditText) findViewById(R.id.password2);
		jihuoma = (EditText) findViewById(R.id.jihuoma);
		jihuo = (Button) findViewById(R.id.jihuo);
		back.setVisibility(View.VISIBLE);
		titleTv.setText("�û�ע��");

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				register.setEnabled(false);
				jihuoinfo.setVisibility(View.VISIBLE);
				if (NetworkUtil.isOnline(RegisterActivity.this)) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

					if (phonenum.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(), "�ֻ��Ų���Ϊ��",
								Toast.LENGTH_SHORT).show();
						return;
					}

					if (!phonenum.getText().toString()
							.matches("[1][358]\\d{9}")) {
						Toast.makeText(getApplicationContext(), "�ֻ��Ų���ȷ",
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (password1.getText().toString().equals("")
							|| password2.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(), "���벻��Ϊ��",
								Toast.LENGTH_SHORT).show();
						return;
					}

					if (!password1.getText().toString()
							.equals(password2.getText().toString()))
						Toast.makeText(getApplicationContext(), "���벻һ��",
								Toast.LENGTH_SHORT).show();
					String url = "http://home.ihunqin.com/api/passport/register";
					Map<String, String> rawparams = new HashMap<String, String>();
					rawparams.put("mobile", phonenum.getText().toString());
					rawparams.put("password", password1.getText().toString());
					rawparams.put("passwordConfirmed", password2.getText()
							.toString());
					String result = null;
					try {

						result = HttpUtil.postRequst(url, rawparams);
						JSONObject json = new JSONObject(result);
						String status = json.getString("Status");
						if (status.equals("0")) {
							// HttpUtil.httpClient.getCookieStore();
							preferences = getSharedPreferences("userinfo",
									MODE_PRIVATE);
							editor = preferences.edit();
							editor.putString("mobile", phonenum.getText()
									.toString());
							editor.putString("password", password1.getText()
									.toString());
							editor.putString("userid", json.getString("Data"));
							editor.commit();

							register.setEnabled(false);
							jihuoinfo.setVisibility(View.VISIBLE);

						} else {
							Toast.makeText(getApplicationContext(), "ע��ʧ��",
									Toast.LENGTH_SHORT).show();
						}
						if (!(json.get("DataExt").equals(null))) {
							JSONObject dataext = json.getJSONObject("DataExt");
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				} else {
					Toast.makeText(RegisterActivity.this, "δ���ӵ�����",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		jihuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String jihuomacode = jihuoma.getText().toString();
				if (jihuomacode.equals("")) {
					Toast.makeText(RegisterActivity.this, "�����벻��Ϊ��",
							Toast.LENGTH_SHORT).show();
					return;
				}
				String url = "http://home.ihunqin.com/api/user/active";
				HashMap<String, String> rawParams = new HashMap<String, String>();
				rawParams.put("activeCode", jihuomacode);

				p1.setMessage("���ڼ�����");
				p1.show();
				Async task = new Async(url, rawParams);
				task.execute();
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
					JSONObject dataExt = json.getJSONObject("DataExt");
					String boothURL =dataExt.getString("BoothURL");
					
					Intent intent = new Intent(RegisterActivity.this,
							AdActivity.class);
					RegisterActivity.this.startActivity(intent);
					RegisterActivity.this.finish();
				} else {
					Toast.makeText(RegisterActivity.this, "����ʧ��",
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
	}
}
