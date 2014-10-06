package com.ihunqin.crazy;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ihunqin.mm.R;

public class PopupMain extends Activity {
	SelectPopupWindow menuWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup);
		TextView tv = (TextView) this.findViewById(R.id.text);
		// �����ֿؼ���Ӽ�������������Զ��崰��
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menuWindow = new SelectPopupWindow(PopupMain.this, itemsOnClick);
				// ��ʾ����
				menuWindow.showAtLocation(
						PopupMain.this.findViewById(R.id.mainpop),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case 1:
			break;
		case 2:
			Uri uri = data.getData();
			String uristr = uri.toString();
			ContentResolver resolver = getContentResolver();
			Bitmap bm = null;
			try {
				bm = MediaStore.Images.Media.getBitmap(resolver,
						Uri.parse(uristr));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			ImageView image = (ImageView) findViewById(R.id.preview);
			image.setImageBitmap(bm);
			break;
		default:
			break;
		}
	}

	private OnClickListener itemsOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			Intent intent = null;
			switch (v.getId()) {
			case R.id.btn_take_photo:
				// ���������� ActionΪMediaStore.ACTION_IMAGE_CAPTURE,
				// ��Щ��ʹ��������Action���ҷ�������Щ�����л�����⣬��������ѡ�����
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
				break;
			case R.id.btn_pick_photo:
				// ѡ����Ƭ��ʱ��Ҳһ����������Intent.ACTION_GET_CONTENT
				intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, 2);
				break;
			default:
				break;
			}
		}
	};
}
