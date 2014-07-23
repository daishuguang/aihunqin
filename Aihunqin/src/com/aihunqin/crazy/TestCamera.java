package com.aihunqin.crazy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aihunqin.R;

@SuppressLint("SdCardPath")
public class TestCamera extends Activity {
	/** Called when the activity is first created. */

	private Button button;
	private ImageView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_camera);
		button = (Button) findViewById(R.id.btnTakePicture);
		view = (ImageView) findViewById(R.id.imageView);
		Log.v("roboce", "create");
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
			}
		});

	}

	@SuppressLint("SdCardPath")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		Bundle extras = data.getExtras();
		Bitmap b = (Bitmap) extras.get("data");
		view.setImageBitmap(b);
		// if (resultCode == Activity.RESULT_OK) {
		// String sdStatus = Environment.getExternalStorageState();
		// if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
		// return;
		// }
		//
		// String name = new DateFormat().format("yyyyMMdd_hhmmss",
		// Calendar.getInstance(Locale.CHINA))
		// + ".jpg";
		// Toast.makeText(this, name, Toast.LENGTH_LONG).show();
		//
		// Bundle bundle = data.getExtras();
		// Bitmap bitmap = (Bitmap) bundle.get("data");//
		// ��ȡ������ص����ݣ���ת��ΪBitmapͼƬ��ʽ
		// FileOutputStream b = null;
		// File file = new File("/sdcard/myImage/");
		// file.mkdir();// �����ļ���
		// String fileName = "/sdcard/myImage/" + name;
		// try {
		// b = new FileOutputStream(fileName);
		// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// b.flush();
		// b.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
		// }
	}

}
