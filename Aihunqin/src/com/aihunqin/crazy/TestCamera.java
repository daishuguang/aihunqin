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

	// camera类
	private Camera camera = null;

	// 继承surfaceView的自定义View用于存放照相的图片
	private CameraView cv = null;

	// 回调用的picture,实现里边的onPictureTaken方法，其中byte[]数组即为照相机获取到的图片信息
	private Camera.PictureCallback picture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// 主要就是将图片转化为drawable,设置为固定区域的背景(展示图片),当然也可以直接在布局文件里放一个surfaceView供使用。
			
		}
	};

	// 主要的SurfaceView,负责展示预览图片，camera的开关
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
