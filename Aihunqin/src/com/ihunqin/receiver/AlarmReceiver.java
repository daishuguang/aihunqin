package com.ihunqin.receiver;

import com.example.aihunqin.R;
import com.ihunqin.activity.SplashActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	Vibrator vibrator;
	NotificationManager nm;
	static final int NOTIFICATION_ID = 0x123;

	@Override
	public void onReceive(Context context, Intent intent) {
		// Intent i = new Intent(context, AlarmActivity.class);
		// i.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(i);

		String msg = intent.getStringExtra("msg");
		// Toast.makeText(context, "婚礼任务提醒:" + msg, Toast.LENGTH_LONG).show();
		nm = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		// Notification notify = new NotificationCompat.Builder(context)
		// .setAutoCancel(true).setContentTitle(msg)
		// .setDefaults(Notification.DEFAULT_VIBRATE).build();
		// nm.notify(0, notify);

		/**
		 * 
		 */

		Intent intent2 = new Intent(context, SplashActivity.class);

		PendingIntent pi = PendingIntent.getActivity(context, 0, intent2, 0);

		Notification notify = new Notification(R.drawable.hunqin, msg,
				System.currentTimeMillis());
		notify.setLatestEventInfo(context, "婚庆助手", msg, pi);
		long[] vibrate = new long[] { 1000, 1000, 1000, 1000 };
		notify.vibrate = vibrate;

		nm.notify(NOTIFICATION_ID, notify);

		//
		// NotificationCompat.Builder builder = new NotificationCompat.Builder(
		// context);
		// builder.setDefaults(Notification.DEFAULT_VIBRATE).setAutoCancel(true)
		// .setContentText(msg).setContentTitle(msg);
		//
		// nm.notify(1000, builder.build());

		vibrator = (Vibrator) context
				.getSystemService(Service.VIBRATOR_SERVICE);
		vibrator.vibrate(5000);
		Toast.makeText(context, "婚礼任务提醒", Toast.LENGTH_LONG).show();

	}

}
