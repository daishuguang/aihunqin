package com.ihunqin.test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.CommonParams.Const.ModelName;
import com.baidu.navisdk.CommonParams.NL_Net_Mode;
import com.baidu.navisdk.comapi.mapcontrol.BNMapController;
import com.baidu.navisdk.comapi.mapcontrol.MapParams.Const.LayerMode;
import com.baidu.navisdk.comapi.routeplan.BNRoutePlaner;
import com.baidu.navisdk.comapi.routeplan.IRouteResultObserver;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.baidu.navisdk.model.NaviDataEngine;
import com.baidu.navisdk.model.RoutePlanModel;
import com.baidu.navisdk.model.datastruct.RoutePlanNode;
import com.baidu.navisdk.ui.widget.RoutePlanObserver;
import com.baidu.nplatform.comapi.map.MapGLSurfaceView;
import com.ihunqin.mm.R;

public class baidunavi extends Activity {
	private MapGLSurfaceView mMapView = null;
	private RoutePlanModel mRoutePlanModel = null;
	private IRouteResultObserver mRouteResultObserver = new IRouteResultObserver() {

		@Override
		public void onRoutePlanYawingSuccess() {
		}

		@Override
		public void onRoutePlanYawingFail() {
		}

		@Override
		public void onRoutePlanSuccess() {
			//
			BNMapController.getInstance().setLayerMode(
					LayerMode.MAP_LAYER_MODE_ROUTE_DETAIL);
			mRoutePlanModel = (RoutePlanModel) NaviDataEngine.getInstance()
					.getModel(ModelName.ROUTE_PLAN);
		}

		@Override
		public void onRoutePlanStart() {
		}

		@Override
		public void onRoutePlanFail() {
		}

		@Override
		public void onRoutePlanCanceled() {
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mMapView = BaiduNaviManager.getInstance().createNMapView(this);
		setContentView(R.layout.baidutest);

	}

	void setRoutePlan() {
		RoutePlanNode startNode = new RoutePlanNode(2253951, 11397348,
				RoutePlanNode.FROM_MAP_POINT, "���ȳ�", "���ȳ�");
		RoutePlanNode endNode = new RoutePlanNode(2248876, 11392576,
				RoutePlanNode.FROM_MAP_POINT, "����Է", "����Է");
		// �����յ���ӵ�nodeList
		ArrayList<RoutePlanNode> nodeList = new ArrayList<RoutePlanNode>(2);
		nodeList.add(startNode);
		nodeList.add(endNode);
		BNRoutePlaner.getInstance().setObserver(
				new RoutePlanObserver(this, null));
		// ������·��ʽ
		BNRoutePlaner.getInstance().setCalcMode(
				NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME);
		// ������·����ص�
		BNRoutePlaner.getInstance()
				.setRouteResultObserver(mRouteResultObserver);
		// �������յ㲢��·
		boolean ret = BNRoutePlaner.getInstance().setPointsToCalcRoute(
				nodeList, NL_Net_Mode.NL_Net_Mode_OnLine);
		if (!ret) {
			Toast.makeText(this, "�滮ʧ��", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		((ViewGroup) (findViewById(R.id.mapview_layout))).addView(mMapView);
		BNMapController.getInstance().onResume();
	}

	@Override
	protected void onPause() {

		super.onPause();
		BNRoutePlaner.getInstance().setRouteResultObserver(null);
		((ViewGroup) (findViewById(R.id.mapview_layout))).removeAllViews();
		BNMapController.getInstance().onPause();
	}
}
