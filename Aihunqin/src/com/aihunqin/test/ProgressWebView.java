package com.aihunqin.test;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressWebView extends WebView {
	private ProgressBar progressbar;
	boolean loadtitle = false;

	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressbar = new ProgressBar(context, null,
				R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, 5, 0, 0));
		addView(progressbar);
		// setWebViewClient(new WebViewClient());
		setWebChromeClient(new CustomWebChromeClient());
	}

	public class CustomWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {

			if (newProgress == 100) {
				progressbar.setVisibility(View.GONE);
			} else {
				if (progressbar.getVisibility() == GONE) {
					progressbar.setVisibility(VISIBLE);
				}
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

	}

}
