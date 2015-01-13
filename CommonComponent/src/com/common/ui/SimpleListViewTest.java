package com.common.ui;

import com.commoncomponent.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SimpleListViewTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewtest);
		ListView list1 = (ListView) findViewById(R.id.list1);
		// ����һ������
		String[] arr1 = { "�����", "��˽�", "ţħ��" };
		// �������װΪArrayAdapter
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				R.layout.array_item, arr1);
		// ΪListView����Adapter
		list1.setAdapter(adapter1);
		ListView list2 = (ListView) findViewById(R.id.list2);
		// ����һ������
		String[] arr2 = { "Java", "Hibernate", "Spring", "Android" };
		// �������װΪArrayAdapter
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				R.layout.checked_item, arr2);
		// ΪListView����Adapter
		list2.setAdapter(adapter2);
	}
}
