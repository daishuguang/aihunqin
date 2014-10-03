package com.aihunqin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.aihunqin.R;

public class LoginActivity extends Activity {

	TextView back;
	TextView titleTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		back = (TextView) findViewById(R.id.back);
		titleTv = (TextView) findViewById(R.id.titleTv);
		back.setVisibility(View.VISIBLE);
		titleTv.setText("µÇÂ¼");
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
