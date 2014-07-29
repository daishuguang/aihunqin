package com.aihunqin.test;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.example.aihunqin.R;

public class BaiduTest extends Activity {

	MapView mMapView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
		// ע��÷���Ҫ��setContentView����֮ǰʵ��
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.baidutest);
		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy(),ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {

		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {

		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView.onPause(),ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}
}
