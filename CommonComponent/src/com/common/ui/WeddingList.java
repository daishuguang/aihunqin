package com.common.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.commoncomponent.R;

public class WeddingList extends Activity {
	ListView myList;
	TextView listid, listdate, listtitle, listinvitor, listdrink;
	ImageView listimg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpleadapterview);
		// Data

		myList = (ListView) findViewById(R.id.mylist);
		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = getLayoutInflater();
				View itemview = inflater.inflate(
						R.layout.simple_invitation_item, null);
				// AbsListView.LayoutParams param = new
				// AbsListView.LayoutParams(
				// LinearLayout.LayoutParams.MATCH_PARENT, 150);
				// itemview.setLayoutParams(param);
				listid = (TextView) itemview.findViewById(R.id.listid);
				listid.setText("第" + position + "张");
				listimg = (ImageView) itemview.findViewById(R.id.listimg);
				listimg.setImageResource(R.drawable.nongyu);
				listdate = (TextView) itemview.findViewById(R.id.listdate);
				listdate.setText("创建日期:" + "2014.04.14");
				listtitle = (TextView) itemview.findViewById(R.id.listtitle);
				listtitle.setText("婚贴标题");
				listinvitor = (TextView) itemview
						.findViewById(R.id.listinvitor);
				listinvitor.setText("新郎名");
				listdrink = (TextView) itemview.findViewById(R.id.listdrink);
				listdrink.setText("新娘名");
				return itemview;

				// LayoutInflater inflater = getLayoutInflater();
				// View v = inflater.inflate(R.layout.baseadapter_item, null);
				// AbsListView.LayoutParams param = new
				// AbsListView.LayoutParams(
				// LinearLayout.LayoutParams.MATCH_PARENT, 150);
				// v.setLayoutParams(param);
				// ImageView image = (ImageView) v.findViewById(R.id.img);
				// image.setImageResource(R.drawable.libai);
				// TextView txt = (TextView) v.findViewById(R.id.txt);
				// txt.setText("hahhah");
				// return v;
			}

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

				return 20;
			}
		};
		myList.setAdapter(adapter);
	}
}
