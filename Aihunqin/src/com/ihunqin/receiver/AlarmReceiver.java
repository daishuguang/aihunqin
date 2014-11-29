package com.ihunqin.receiver;

import com.ihunqin.activity.AlarmActivity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	Vibrator vibrator;

	@Override
	public void onReceive(Context context, Intent intent) {
		// Intent i = new Intent(context, AlarmActivity.class);
		// i.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(i);

		String msg = intent.getStringExtra("msg");
		Toast.makeText(context, "婚礼任务提醒:" + msg, Toast.LENGTH_SHORT).show();
		vibrator = (Vibrator) context
				.getSystemService(Service.VIBRATOR_SERVICE);
		vibrator.vibrate(5000);
		// Toast.makeText(context, "婚礼任务提醒", Toast.LENGTH_LONG).show();
	}

}
