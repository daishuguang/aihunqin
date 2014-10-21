package com.ihunqin.fragment;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihunqin.R;
import com.ihunqin.fragment.FragmentInvitation.TransferIDListener;
import com.ihunqin.model.LiJin;
import com.ihunqin.util.XMLUtil;

public class FragmentLijinList extends Fragment {
	TransferIDListener mCallback;
	private TextView textView;
	private TextView back;
	private TextView rightmenu;
	private MyAdapter adapter;
	private ListView ljlist;

	enum tagtype {
		income, outcome
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (TransferIDListener) activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_lijinlist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		final String id = getArguments().getString("id");
		textView = (TextView) getView().findViewById(R.id.titleTv);
		String tagName = "";
		tagtype tt = id.equals("income") ? tagtype.income : tagtype.outcome;
		switch (tt) {
		case income:
			textView.setText("礼金收入");
			tagName = "income";
			break;
		case outcome:
			tagName = "outcome";
			textView.setText("礼金支出");
			break;
		}
		textView.setVisibility(View.VISIBLE);
		back = (TextView) getView().findViewById(R.id.back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		rightmenu = (TextView) getView().findViewById(R.id.rightmenu);
		rightmenu.setVisibility(View.VISIBLE);
		rightmenu.setText("添加");
		rightmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String uuid = UUID.randomUUID().toString();
				mCallback.onItemClicked(id + uuid, "lijindetail");
			}
		});
		adapter = new MyAdapter(getActivity());
		XMLUtil.setFileName("lj.xml");
		adapter.arr = XMLUtil.selectXML(tagName);
		ljlist = (ListView) getView().findViewById(R.id.ljlist);
		ljlist.setAdapter(adapter);
		ljlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long idd) {
				mCallback.onItemClicked(id + adapter.arr.get(position).getId(),
						"lijindetail");
			}

		});
	}

	private class MyAdapter extends BaseAdapter {
		private Context context;
		
		public ArrayList<LiJin> arr;

		public MyAdapter(Context context) {
			this.context = context;
			arr = new ArrayList<LiJin>();
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
			ViewHolder viewholder = null;
			if (convertView == null) {
				viewholder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_lijin, null);

				viewholder.ljdate = (TextView) convertView
						.findViewById(R.id.ljdate);
				viewholder.ljdate.setText(arr.get(position).getDate());
				viewholder.ljname = (TextView) convertView
						.findViewById(R.id.ljname);

				viewholder.ljdolar = (TextView) convertView
						.findViewById(R.id.ljdolar);
			} else {
				viewholder.ljname.setText(arr.get(position).getName());
				viewholder.ljdolar.setText(arr.get(position).getDolar() + "");
			}
			return convertView;
		}
	}

	private static class ViewHolder {
		TextView ljdate;
		TextView ljname;
		TextView ljdolar;
	}
}
