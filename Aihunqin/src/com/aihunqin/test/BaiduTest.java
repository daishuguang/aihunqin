package com.aihunqin.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.aihunqin.test.BaiduLocation.MyLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.aihunqin.R;

public class BaiduTest extends Activity {
	BaiduMap mBaiduMap = null;
	MapView mMapView = null;

	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public TextView mLocationResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要在setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.baidutest);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);

		mLocationClient = new LocationClient(getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationResult = (TextView) findViewById(R.id.mLocationResult);
		InitLocation();
		mLocationClient.start();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy(),实现地图生命周期管理
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
		// 在activity执行onPause时执行mMapView.onPause(),实现地图生命周期管理
		mMapView.onPause();
	}

	/**
	 * 实现定位回调监听
	 * 
	 * @author Alex
	 * 
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time:");
			sb.append(location.getTime());
			sb.append("\nerror code:");
			sb.append(location.getLocType());
			sb.append("\nlatitude:");
			sb.append(location.getLatitude());
			sb.append("\nlontitude:");
			sb.append(location.getLongitude());
			sb.append("\nradius:");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr:");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers:");
				sb.append(location.getOperators());
			}
			logMsg(sb.toString());
		}
	}

	/**
	 * 显示请求你字符串
	 * 
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if (mLocationResult != null)
				mLocationResult.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);// 设置定位模式
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
}
