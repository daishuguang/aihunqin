package com.ihunqin.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.*;
import com.baidu.location.LocationClientOption.*;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
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

		setRoutePlan(point);
	}

	void setRoutePlan(LatLng point) {
		// �ݳ���·�滮
		RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
		OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
				// ��ȡ����·�߹滮���
			}

			@Override
			public void onGetTransitRouteResult(TransitRouteResult arg0) {
				// ��ȡ��������·���滮���
			}

			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
				// ��ȡ�ݳ�·�߹滮���
			}
		};
		// ���üݳ���·�滮����������
		mSearch.setOnGetRoutePlanResultListener(listener);
		// ׼���������յ���Ϣ
		PlanNode stNode = PlanNode.withLocation(point);
		LatLng endpoint = new LatLng(121.473605, 31.232176);
		PlanNode enNode = PlanNode.withLocation(endpoint);
		mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(
				enNode));
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
