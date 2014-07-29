package com.aihunqin.test;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.map.MapView;
import com.example.aihunqin.R;

public class BaiduTest extends Activity {
	MapView mMapView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.baidutest);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy(),实现地图生命周期管理
	}
}
