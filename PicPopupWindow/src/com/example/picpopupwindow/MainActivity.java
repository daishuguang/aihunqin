package com.example.picpopupwindow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView photo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		photo = (ImageView) this.findViewById(R.id.text);
		// �����ֿؼ���Ӽ�������������Զ��崰��
		photo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//ʹ��startActivityForResult����SelectPicPopupWindow�����ص���Activity��ʱ��ͻ����onActivityResult����
				startActivityForResult(new Intent(MainActivity.this,
						SelectPicPopupWindow.class), 1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 1:
			if (data != null) {
				//ȡ�÷��ص�Uri,������ѡ����Ƭ��ʱ�򷵻ص�����Uri��ʽ���������������еû�����Uri�ǿյģ�����Ҫ�ر�ע��
				Uri mImageCaptureUri = data.getData();
				//���ص�Uri��Ϊ��ʱ����ôͼƬ��Ϣ���ݶ�����Uri�л�á����Ϊ�գ���ô���Ǿͽ�������ķ�ʽ��ȡ
				if (mImageCaptureUri != null) {
					Bitmap image;
					try {
						//��������Ǹ���Uri��ȡBitmapͼƬ�ľ�̬����
						image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
						if (image != null) {
							photo.setImageBitmap(image);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Bundle extras = data.getExtras();
					if (extras != null) {
						//��������Щ���պ��ͼƬ��ֱ�Ӵ�ŵ�Bundle�е��������ǿ��Դ��������ȡBitmapͼƬ
						Bitmap image = extras.getParcelable("data");
						if (image != null) {
							photo.setImageBitmap(image);
						}
					}
				}

			}
			break;
		default:
			break;

		}
	}

}
