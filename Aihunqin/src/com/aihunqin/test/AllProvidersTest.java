package com.aihunqin.test;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aihunqin.R;

public class AllProvidersTest extends Activity {
	ListView providers;
	LocationManager lm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.allproviderstest);
		providers = (ListView) findViewById(R.id.providers);
		// ��ȡϵͳ��LocationManager����
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// ��ȡϵͳ���е�LocationProvider������
		List<String> providerNames = lm.getAllProviders();
		ArrayAdapter<String> adapters = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, providerNames);
		// ʹ��ListView����ʾ���п��õ�LocationProvider
		providers.setAdapter(adapters);
		
		//��ȡ����GPS��LocationProvider
//		LocationProvider locProvider=lm.getProvider(LocationManager.GPS_PROVIDER);
	}
}
