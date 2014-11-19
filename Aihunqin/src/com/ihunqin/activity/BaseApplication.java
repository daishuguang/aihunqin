package com.ihunqin.activity;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.ihunqin.receiver.ConnectionChangeReceiver;

public class BaseApplication extends Application {
	public ConnectionChangeReceiver myReceiver;

	@Override
	public void onCreate() {
		super.onCreate();
		registerMyReceiver();
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
