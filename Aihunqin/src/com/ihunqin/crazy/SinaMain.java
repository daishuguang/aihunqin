package com.ihunqin.crazy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.aihunqin.R;
import com.ihunqin.activity.BaseApplication;
import com.ihunqin.fragment.FragmentInivitationContent;
import com.ihunqin.fragment.FragmentInvitation.TransferIDListener;
import com.ihunqin.fragment.FragmentInvitationCreateNew;
import com.ihunqin.fragment.FragmentLiJingDetail;
import com.ihunqin.fragment.FragmentLijinList;
import com.ihunqin.fragment.FragmentMain;
import com.ihunqin.fragment.FragmentMore;
import com.ihunqin.fragment.FragmentQRCode;
import com.ihunqin.fragment.FragmentTask;
import com.ihunqin.fragment.FragmentWeddingList;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class SinaMain extends FragmentActivity implements TransferIDListener {
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	Fragment[] mFragments;

	// APP_ID 替换为你的应用从官方网站申请到的合法appId
	private static final String APP_ID = "wx7160a43122ae9274";

	public IWXAPI api;

	private void regToWx() {
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, APP_ID, true);

		// 将应用的appId注册到微信
		api.registerApp(APP_ID);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sina);
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		Fragment fragment_main = new FragmentMain();
		// Add to Activity
		fragmentTransaction.add(R.id.fragment_container, fragment_main);
		fragmentTransaction.commit();
		setFragmentIndicator();

		// 注册到微信
	}

	void setFragmentIndicator() {
		RadioGroup bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
		bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				fragmentTransaction = fragmentManager.beginTransaction();
				Log.v("roboce", checkedId + "");
				switch (checkedId) {
				// Replace Fragment
				case R.id.rbOne:
					Fragment fragment_main = new FragmentMain();
					fragmentTransaction.replace(R.id.fragment_container,
							fragment_main);
					break;
				case R.id.rbTwo:
					// Toast.makeText(SinaMain.this, "敬请期待", Toast.LENGTH_SHORT)
					// .show();
					Fragment fragment_weddingList = new FragmentWeddingList();
					fragmentTransaction.replace(R.id.fragment_container,
							fragment_weddingList);
					break;
				case R.id.rbThree:
					Fragment fragment_more = new FragmentMore();
					fragmentTransaction.replace(R.id.fragment_container,
							fragment_more);
					break;
				default:
					break;
				}
				fragmentTransaction.commit();
			}
		});
	}

	@Override
	public void onItemClicked(String id, String which) {
		Bundle arguments = new Bundle();
		arguments.putString("id", id);
		if (which.equals("createnew")) {
			FragmentInvitationCreateNew fragment = new FragmentInvitationCreateNew();
			fragment.setArguments(arguments);
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, fragment)
					.addToBackStack(null).commit();
		}
		if (which.equals("content")) {
			FragmentInivitationContent fragment = new FragmentInivitationContent();
			fragment.setArguments(arguments);
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, fragment)
					.addToBackStack(null).commit();
		}
		if (which.equals("qrcode")) {
			FragmentQRCode fragment = new FragmentQRCode();
			fragment.setArguments(arguments);
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, fragment)
					.addToBackStack(null).commit();
		}
		if (which.equals("lijinlist")) {
			FragmentLijinList fragment = new FragmentLijinList();
			fragment.setArguments(arguments);
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, fragment)
					.addToBackStack(null).commit();
		}
		if (which.equals("lijindetail")) {
			FragmentLiJingDetail fragment = new FragmentLiJingDetail();
			fragment.setArguments(arguments);
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, fragment)
					.addToBackStack(null).commit();
		}
		if (which.equals("task")) {
			FragmentTask fragment = new FragmentTask();
			fragment.setArguments(arguments);
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, fragment)
					.addToBackStack(null).commit();
		}
	}

	@Override
	protected void onDestroy() {
		// this.unregisterReceiver(((BaseApplication)
		// getApplicationContext()).myReceiver);
		super.onDestroy();
	}

}
