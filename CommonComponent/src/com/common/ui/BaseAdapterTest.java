package com.common.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.commoncomponent.R;

public class BaseAdapterTest extends Activity {
	ListView myList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpleadapterview);
		myList = (ListView) findViewById(R.id.mylist);
		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// // ����һ��LinearLayout,������������������
				// LinearLayout line = new
				// LinearLayout(getApplicationContext());
				// line.setOrientation(LinearLayout.HORIZONTAL);
				// ImageView image = new ImageView(getApplicationContext());
				// image.setImageResource(R.drawable.tiger);
				// TextView text = new TextView(getApplicationContext());
				// text.setText("��" + (position + 1) + "���б���");
				// text.setTextSize(20);
				// text.setTextColor(Color.RED);
				// line.addView(image);
				// line.addView(text);
				// // ����LinearLayoutʵ��
				// return line;
				//
				LayoutInflater inflater = getLayoutInflater();
				View v = inflater.inflate(R.layout.baseadapter_item, null,
						false);
				ImageView image = (ImageView) v.findViewById(R.id.img);
				image.setImageResource(R.drawable.libai);
				TextView txt = (TextView) v.findViewById(R.id.txt);
				txt.setText("hahhah");
				return v;
			}

			// ��д�÷������÷����ķ���ֵ����Ϊ�б����ID
			@Override
			public long getItemId(int position) {

				return position;
			}

			@Override
			public Object getItem(int position) {

				return null;
			}

			@Override
			public int getCount() {
				// ָ��һ������40��
				return 40;
			}
		};
		myList.setAdapter(adapter);
	}
}
