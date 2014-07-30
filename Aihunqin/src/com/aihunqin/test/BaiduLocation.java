package com.aihunqin.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapStatus.Builder;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.aihunqin.R;

public class BaiduLocation extends Activity {
	BaiduMap mBaiduMap = null;
	MapView mMapView = null;
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public TextView mLocationResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.baidulocation);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		mLocationClient = new LocationClient(getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationResult = (TextView) findViewById(R.id.mLocationResult);
		InitLocation();
		mLocationClient.start();
	}

	/**
	 * ʵ�ֶ�λ�ص�����
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
				// ��Ӫ����Ϣ
				sb.append("\noperationers:");
				sb.append(location.getOperators());
			}

			setMarker(location.getLatitude(), location.getLongitude());
			logMsg(sb.toString());
		}
	}

	void setMarker(double lat, double lng) {
		// ����Markker�����
		LatLng point = new LatLng(lat, lng);
		// ����Markerͼ��
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.wechat);
		// ����MarkerOption,�����ڵ�ͼ�����Marker
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);
		// �ڵ�ͼ�����Marker,����ʾ
		mBaiduMap.addOverlay(option);

		MapStatusUpdate mapstatusupdate = MapStatusUpdateFactory
				.newLatLng(point);

		mBaiduMap.setMapStatus(mapstatusupdate);
	}

	/**
	 * ��ʾ�������ַ���
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
		option.setLocationMode(LocationMode.Battery_Saving);// ���ö�λģʽ
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
}
