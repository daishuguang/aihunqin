package com.aihunqin.crazy;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.example.aihunqin.R;

public class TestCamera extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	// camera��
	private Camera camera = null;

	// �̳�surfaceView���Զ���View���ڴ�������ͼƬ
	private CameraView cv = null;

	// �ص��õ�picture,ʵ����ߵ�onPictureTaken����������byte[]���鼴Ϊ�������ȡ����ͼƬ��Ϣ
	private Camera.PictureCallback picture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// ��Ҫ���ǽ�ͼƬת��Ϊdrawable,����Ϊ�̶�����ı���(չʾͼƬ),��ȻҲ����ֱ���ڲ����ļ����һ��surfaceView��ʹ�á�
			
		}
	};

	// ��Ҫ��SurfaceView,����չʾԤ��ͼƬ��camera�Ŀ���
	class CameraView extends SurfaceView {

		public CameraView(Context context) {
			super(context);
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFormat(PixelFormat.TRANSPARENT);
		setContentView(R.layout.layout_camera);

	}
}
