package com.common.ui;

import com.example.commoncomponent.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ListActivityTest extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// ����ʹ�ò����ļ�
		String[] arr = { "�����", "��˽�", "��ɮ" };
		// ����ArrayAdapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, arr);
		setListAdapter(adapter);
//		setContentView(R.layout.listviewtest);
	}
}
