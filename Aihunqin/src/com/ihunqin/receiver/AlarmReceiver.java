package com.ihunqin.receiver;

import com.ihunqin.activity.AlarmActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, AlarmActivity.class);
		i.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

}
