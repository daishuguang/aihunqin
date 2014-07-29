package com.aihunqin.fragment;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aihunqin.crazy.SelectPopupWindow;
import com.aihunqin.crazy.WebActivity;
import com.aihunqin.fragment.FragmentInvitation.TransferIDListener;
import com.example.aihunqin.R;

public class FragmentInvitationCreateNew extends Fragment {
	String id = "";
	TransferIDListener mCallback;
	ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9;
	SelectPopupWindow menuWindow;
	Uri fileUri;
	static final int MEDIA_TYPE_IMAGE = 1;
	static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	int viewid;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (TransferIDListener) activity;
			Log.v("roboce", "onattach");
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_MAIN.jpg");
		} else {
			return null;
		}
		return mediaFile;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_invitation_createnew,
				container, false);
	}

	String[] items = new String[] { "拍照", "从相册选择" };

	OnClickListener clickListiener = new OnClickListener() {

		@Override
		public void onClick(final View v) {
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					viewid = v.getId();
					switch (which) {
					case 0:
						break;
					case 1:
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_PICK);
						intent.setType("image/*");
						intent.putExtra("view", viewid);
						startActivityForResult(intent, 2);
						break;
					}
				}
			}).create().show();

		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case 1:
			break;
		case 2:
			fileUri = data.getData();
			((ImageView) getView().findViewById(viewid)).setImageURI(fileUri);
			break;
		}

	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		id = getArguments().getString("id");
		Log.v("roboce", "ID:" + id);
		TextView back = (TextView) getView().findViewById(R.id.back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.fragment_container,
								new FragmentInvitation()).commit();
			}
		});
		TextView title = (TextView) getView().findViewById(R.id.titleTv);
		title.setText("请帖管理");
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});

		// 底部菜单
		Button editcontent = (Button) getView().findViewById(R.id.editcontent);
		editcontent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked(id, "content");
			}
		});
		Button preview = (Button) getView().findViewById(R.id.preview);
		preview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", "http://www.ruiqinsoft.com:3083/wh/t/"
						+ id);
				startActivity(intent);
			}
		});

		Button gest = (Button) getView().findViewById(R.id.gest);
		gest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link",
						"http://www.ruiqinsoft.com:3083/guest/v/" + id);
				startActivity(intent);
			}
		});

		Button blessing = (Button) getView().findViewById(R.id.blessing);
		blessing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link",
						"http://www.ruiqinsoft.com:3083/follower/v/" + id);
				startActivity(intent);
			}
		});
		img1 = (ImageView) getView().findViewById(R.id.img1);
		img2 = (ImageView) getView().findViewById(R.id.img2);
		img3 = (ImageView) getView().findViewById(R.id.img3);
		img4 = (ImageView) getView().findViewById(R.id.img4);
		img5 = (ImageView) getView().findViewById(R.id.img5);
		img6 = (ImageView) getView().findViewById(R.id.img6);
		img7 = (ImageView) getView().findViewById(R.id.img7);
		img8 = (ImageView) getView().findViewById(R.id.img8);
		img9 = (ImageView) getView().findViewById(R.id.img9);

		img1.setOnClickListener(clickListiener);
		img2.setOnClickListener(clickListiener);
		img3.setOnClickListener(clickListiener);
		img4.setOnClickListener(clickListiener);
		img5.setOnClickListener(clickListiener);
		img6.setOnClickListener(clickListiener);
		img7.setOnClickListener(clickListiener);
		img8.setOnClickListener(clickListiener);
		img9.setOnClickListener(clickListiener);
	}
}
