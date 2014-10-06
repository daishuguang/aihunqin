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
		// 把文字控件添加监听，点击弹出自定义窗口
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menuWindow = new SelectPopupWindow(PopupMain.this, itemsOnClick);
				// 显示窗口
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
				// 拍照我们用 Action为MediaStore.ACTION_IMAGE_CAPTURE,
				// 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
				break;
			case R.id.btn_pick_photo:
				// 选择照片的时候也一样，我们用Intent.ACTION_GET_CONTENT
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
