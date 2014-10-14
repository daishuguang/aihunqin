package com.ihunqin.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.ad;
import com.example.aihunqin.R;

public class FragmentZuowei extends Fragment {

	private TextView back;
	private TextView rightmenu;
	private RadioButton radio_zuowei;
	private RadioButton radio_caidan;
	private RadioGroup radio_zuoweiandcaidan;
	private TextView showzuowei;
	private LinearLayout showcaidan;
	private ImageButton addcaidan;
	private EditText caiming;
	private ListView listcontent;
	public MyAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_zuoandcai, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		back = (TextView) getView().findViewById(R.id.back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();

				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				Fragment fragment_settingname = new FragmentMain();
				fragmentTransaction.replace(R.id.fragment_container,
						fragment_settingname);
				fragmentTransaction.commit();
			}
		});

		rightmenu = (TextView) getView().findViewById(R.id.rightmenu);
		rightmenu.setText("添加酒席");
		rightmenu.setVisibility(View.VISIBLE);

		radio_zuowei = (RadioButton) getView().findViewById(R.id.radio0);
		radio_caidan = (RadioButton) getView().findViewById(R.id.radio1);

		showzuowei = (TextView) getView().findViewById(R.id.showzuowei);
		showcaidan = (LinearLayout) getView().findViewById(R.id.showcaidan);

		caiming = (EditText) getView().findViewById(R.id.caiming);

		addcaidan = (ImageButton) getView().findViewById(R.id.addcaidan);
		addcaidan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String caimingstr = caiming.getText().toString();
				if (caimingstr.equals("")) {
					Toast.makeText(getActivity(), "菜名不能为空", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(getActivity(), caimingstr,
							Toast.LENGTH_SHORT).show();
					adapter.arr.add(caimingstr);
					adapter.notifyDataSetChanged();
					caiming.setText("");
				}
			}
		});

		radio_zuoweiandcaidan = (RadioGroup) getView().findViewById(
				R.id.radio_zuoweiandcaidan);
		radio_zuoweiandcaidan
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.radio0:
							rightmenu.setVisibility(View.VISIBLE);
							showzuowei.setVisibility(View.VISIBLE);
							showcaidan.setVisibility(View.GONE);
							break;
						case R.id.radio1:
							rightmenu.setVisibility(View.INVISIBLE);
							showzuowei.setVisibility(View.GONE);
							showcaidan.setVisibility(View.VISIBLE);
							break;
						}
					}
				});

		listcontent = (ListView) getView().findViewById(R.id.listcontent);
		adapter = new MyAdapter(getActivity());
		listcontent.setAdapter(adapter);

	}

	private class MyAdapter extends BaseAdapter {
		private Context context;
		TextView caidanming;
		ImageButton del;
		TextView xuhao;
		public ArrayList<String> arr;

		public MyAdapter(Context context) {
			this.context = context;
			arr = new ArrayList<String>();
		}

		@Override
		public int getCount() {

			return arr.size();
		}

		@Override
		public Object getItem(int position) {

			return arr.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_caidan, null);
			}
			caidanming = (TextView) convertView.findViewById(R.id.caidanitem);
			caidanming.setText(arr.get(position));
			xuhao = (TextView) convertView.findViewById(R.id.xuhao);
			xuhao.setText((position + 1) + "");
			del = (ImageButton) convertView.findViewById(R.id.delimage);
			del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					arr.remove(position);
					adapter.notifyDataSetChanged();
				}
			});

			return convertView;
		}
	}

}
