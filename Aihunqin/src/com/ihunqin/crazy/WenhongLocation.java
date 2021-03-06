package com.ihunqin.crazy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class WenhongLocation extends Activity {
	BaiduMap mBaiduMap = null;
	MapView mMapView = null;
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public TextView mLocationResult, back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.layout_wenhonglocation);

		back = (TextView) findViewById(R.id.back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		mLocationClient = new LocationClient(getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		InitLocation();
		mLocationClient.start();

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

			setMarker(location.getLatitude(), location.getLongitude());
			// logMsg(sb.toString());
		}
	}

	void setMarker(double lat, double lng) {
		// 定义Markker坐标点
		LatLng point = new LatLng(lat, lng);
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.startpoint);
		// 构建MarkerOption,用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);
		// 在地图上添加Marker,并显示
		mBaiduMap.addOverlay(option);

		MapStatusUpdate mapstatusupdate = MapStatusUpdateFactory
				.newLatLng(point);

		mBaiduMap.setMapStatus(mapstatusupdate);

		// setRoutePlan(point);
	}

	void setRoutePlan(LatLng point) {
		// 驾车线路规划
		RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
		OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
				// 获取步行路线规划结果
			}

			@Override
			public void onGetTransitRouteResult(TransitRouteResult arg0) {
				// 获取公交换乘路径规划结果
			}

			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
				// 获取驾车路线规划结果
			}
		};
		// 设置驾车线路规划检索监听者
		mSearch.setOnGetRoutePlanResultListener(listener);
		// 准备检索起、终点信息
		PlanNode stNode = PlanNode.withLocation(point);
		LatLng endpoint = new LatLng(121.473605, 31.232176);
		PlanNode enNode = PlanNode.withLocation(endpoint);
		mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(
				enNode));
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
