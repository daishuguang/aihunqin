package com.ihunqin.activity;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.ihunqin.receiver.ConnectionChangeReceiver;

public abstract class BaseActivity extends Activity {
	private ConnectionChangeReceiver myReceiver;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Detect network
	 */
	private void registerMyReceiver() {
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		myReceiver = new ConnectionChangeReceiver();
		this.registerReceiver(myReceiver, filter);
	}
}
