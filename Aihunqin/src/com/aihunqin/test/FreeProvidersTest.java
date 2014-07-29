package com.aihunqin.test;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aihunqin.R;

public class FreeProvidersTest extends Activity {

	ListView providers;
	LocationManager lm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.allproviderstest);
		providers = (ListView) findViewById(R.id.providers);
		// ��ȡϵͳ��LocationManager����
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// ����һ��LocationProvider�Ĺ�������
		Criteria cri = new Criteria();
		// ����Ҫ��LocationProvider��������ѵ�
		cri.setCostAllowed(false);
		// ����Ҫ��LocationProvider���ṩ�߶���Ϣ
		cri.setAltitudeRequired(true);
		// ����Ҫ��LocationProvider���ṩ������Ϣ
		cri.setBearingRequired(true);
		// ��ȡϵͳ���и���������LocationProvider������
		List<String> providerNames = lm.getProviders(true);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, providerNames);
		// ʹ��ListView����ʾ���п��õ�LocationProvider
		providers.setAdapter(adapter);
	}
}
