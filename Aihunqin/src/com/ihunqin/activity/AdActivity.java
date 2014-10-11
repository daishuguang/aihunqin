package com.ihunqin.activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.aihunqin.R;
import com.ihunqin.crazy.SinaMain;

public class AdActivity extends Activity {
	ImageView adimg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ad);

		adimg = (ImageView) findViewById(R.id.adimg);
		try {
			FileInputStream fis = openFileInput("boothurl.png");
			Bitmap bmp = BitmapFactory.decodeStream(fis);
			adimg.setImageBitmap(bmp);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/*
				 * Create an Intent that will start the Main WordPress Activity.
				 */

				Intent intent = new Intent(AdActivity.this, SinaMain.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.launch_input,
						R.anim.launch_output);
			}
		}, 2500);
	}
}
