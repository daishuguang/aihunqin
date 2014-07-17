package com.aihunqin.crazy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.aihunqin.fragment.FragmentMain;
import com.aihunqin.fragment.FragmentMore;
import com.aihunqin.fragment.FragmentWeddingList;
import com.example.aihunqin.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class SinaMain extends FragmentActivity {
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	Fragment[] mFragments;

	// APP_ID �滻Ϊ���Ӧ�ôӹٷ���վ���뵽�ĺϷ�appId
	private static final String APP_ID = "wx7160a43122ae9274";

	// IWXAPI �ǵ�����app��΢��ͨ�ŵ�openapi�ӿ�
	private IWXAPI api;

	private void regToWx() {
		// ͨ��WXAPIFactory��������ȡIWXAPI��ʵ��
		api = WXAPIFactory.createWXAPI(this, APP_ID, true);
		// ��Ӧ�õ�appIdע�ᵽ΢��
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

		// ע�ᵽ΢��
		regToWx();
	}

	void setFragmentIndicator() {
		RadioGroup bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
		RadioButton rbOne = (RadioButton) findViewById(R.id.rbOne);
		RadioButton rbTwo = (RadioButton) findViewById(R.id.rbTwo);
		RadioButton rbThree = (RadioButton) findViewById(R.id.rbThree);

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

}
