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
		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy(),ʵ�ֵ�ͼ�������ڹ���
	}
}
