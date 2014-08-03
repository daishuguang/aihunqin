package com.common.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.commoncomponent.R;

public class SimpleAdapterTest extends Activity {
	private String[] names = { "��ͷ", "Ū��", "������", "���" };
	private String[] descs = { "�ɰ���С��", "һ���ó����ֵ�Ů��", "һ���ó���ѧ��Ů��", "��������ʫ��" };
	private int[] imageIds = { R.drawable.tiger, R.drawable.nongyu,
			R.drawable.qingzhao, R.drawable.libai };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpleadapterview);
		// ����һ��List���ϣ�List���ϵ�Ԫ����Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < names.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("header", imageIds[i]);
			listItem.put("personName", names[i]);
			listItem.put("desc", descs[i]);
			listItems.add(listItem);
		}
		// ����һ��SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.simple_item, new String[] { "personName", "header",
						"desc" },
				new int[] { R.id.name, R.id.header, R.id.desc });
		ListView list = (ListView) findViewById(R.id.mylist);
		// ΪListView����Adapter
		list.setAdapter(simpleAdapter);
		// ΪListView���б���ĵ����¼����¼�������
		list.setOnItemClickListener(new OnItemClickListener() {
			// ��position�����ʱ�����÷���
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.v("roboce", position + "����");
			}
		});
		// ΪListView���б����ѡ���¼����¼�������
		list.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
}
