package com.common.web;

import com.commoncomponent.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TitleProgressBar extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar);
		ProgressWebView webview = (ProgressWebView) findViewById(R.id.progresswebview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl("http://www.baidu.com");
		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}
}
