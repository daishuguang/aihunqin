package com.aihunqin.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

	public static boolean isOnline(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();
		return (networkinfo != null && networkinfo.isConnected());
	}
}
