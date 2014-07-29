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
		// 获取系统的LocationManager对象
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 获取系统所有的LocationProvider的名称
		List<String> providerNames = lm.getAllProviders();
		ArrayAdapter<String> adapters = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, providerNames);
		// 使用ListView来显示所有可用的LocationProvider
		providers.setAdapter(adapters);
		
		//获取基于GPS的LocationProvider
//		LocationProvider locProvider=lm.getProvider(LocationManager.GPS_PROVIDER);
	}
}
