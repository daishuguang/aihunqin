package com.aihunqin.crazy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.aihunqin.R;

public class CameraActivity extends Activity {
	Button btn1;
	static final int MEDIA_TYPE_IMAGE = 1;
	static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	Uri fileUri;
	static String path = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameraapp);
		btn1 = (Button) findViewById(R.id.camerabtn);

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Create intent to take a picture and return control to the
				// calling application
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// create a file to save the image
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				// set the image file name
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

				// start the image capture Intent
				startActivityForResult(intent,
						CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

			}
		});
	}

	/** Create a file Uri for saving an image or video */
	private Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */

	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"Aihunqin");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			path = mediaStorageDir.getPath() + File.separator + "IMG_"
					+ timeStamp + ".jpg";
			Log.v("roboce", path);
			mediaFile = new File(path);
		} else {
			return null;
		}
		return mediaFile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to the fileUri specified int the
				// Intent
				ImageView imageview = (ImageView) findViewById(R.id.cameraviews);
				// int width = imageview.getWidth();
				// int height = imageview.getHeight();
				//
				// BitmapFactory.Options factoryOptions = new
				// BitmapFactory.Options();
				//
				// factoryOptions.inJustDecodeBounds = true;
				//
				// BitmapFactory.decodeFile(fileUri.getPath(), factoryOptions);
				// int imageWidth = factoryOptions.outWidth;
				// int imageHeight = factoryOptions.outHeight;
				//
				// int scaleFactor = Math.min(imageWidth / width, imageHeight
				// / height);
				//
				// factoryOptions.inJustDecodeBounds = true;
				// factoryOptions.inSampleSize = scaleFactor;
				// factoryOptions.inPurgeable = true;
				//
				// Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
				// factoryOptions);

				// imageview.setImageBitmap(bitmap);

				Log.v("roboce", "path2:" + path);
				File file = new File(path);
				if (file.exists()) {
					Log.v("roboce", "file exist");
					Bitmap bmp = BitmapFactory.decodeFile(path);
					imageview.setImageBitmap(bmp);
				}
				// Uri uri = Uri.fromFile(file);
				// imageview.setImageURI(uri);
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				ImageView imageview = (ImageView) findViewById(R.id.cameraviews);
				Log.v("roboce", "path2:" + path);
				File file = new File(path);
				Uri uri = Uri.fromFile(file);
				imageview.setImageURI(uri);

			} else {
				// Image capture failed, advise user
			}
		}

	}
}
