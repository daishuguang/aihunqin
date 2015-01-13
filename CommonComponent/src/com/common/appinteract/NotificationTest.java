package com.common.appinteract;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.commoncomponent.R;

public class NotificationTest extends Activity {

	static final int NOTIFICATION_ID = 0x123;
	NotificationManager nm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationlayout);
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Button b1 = (Button) findViewById(R.id.send);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Notification notify = new NotificationCompat.Builder(
				// getApplicationContext()).setAutoCancel(true)
				// .setTicker("有新消息").setSmallIcon(R.drawable.ic_launcher)
				// .setContentTitle("一条新通知")
				// .setContentText("恭喜你，您加薪了，工资增加")
				// .setWhen(System.currentTimeMillis())
				// .setVibrate(new long[] { 0, 50, 100, 150 }).build();

				Intent intent = new Intent(NotificationTest.this,
						NotificationTest.class);

				PendingIntent pi = PendingIntent.getActivity(
						NotificationTest.this, 0, intent, 0);

				Notification notify = new Notification(R.drawable.ic_launcher,
						"Notification", System.currentTimeMillis());
				notify.setLatestEventInfo(getApplicationContext(),
						"contentTitle", "contentText", pi);
				long[] vibrate = new long[] { 1000, 1000, 1000, 1000 };
				notify.vibrate = vibrate;

				nm.notify(NOTIFICATION_ID, notify);
			}
		});
	}
}
