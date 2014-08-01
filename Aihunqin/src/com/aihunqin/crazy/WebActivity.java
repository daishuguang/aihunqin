package com.aihunqin.crazy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aihunqin.R;

public class WebActivity extends Activity {
	boolean loadtitle = false;
	ProgressBar progressbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);
		WebView webview = (WebView) findViewById(R.id.webdemo);
		webview.getSettings().setJavaScriptEnabled(true);
		progressbar = (ProgressBar) findViewById(R.id.webprogress);
		String url = getIntent().getExtras().getString("link");
		webview.loadUrl(url);

		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webview.setWebChromeClient(new WebChromeClient() {
			public void onReceivedTitle(WebView view, String title) {
				if (!loadtitle) {
					((TextView) findViewById(R.id.titleTv)).setText(title);
					loadtitle = true;
				}
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {

				if (newProgress == 100) {
					progressbar.setVisibility(View.GONE);
				} else {
					if (progressbar.getVisibility() == View.GONE) {
						progressbar.setVisibility(View.VISIBLE);
					}
					progressbar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

		});
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
