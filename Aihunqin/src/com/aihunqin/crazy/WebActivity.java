package com.aihunqin.crazy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.aihunqin.R;

public class WebActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.webview_activity);
		WebView webview = (WebView) findViewById(R.id.webdemo);
		String url = getIntent().getExtras().getString("link");
		webview.loadUrl(url);
		TextView title = (TextView) findViewById(R.id.back);
		title.setText("Íê³É");
		title.setVisibility(View.VISIBLE);

		title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
