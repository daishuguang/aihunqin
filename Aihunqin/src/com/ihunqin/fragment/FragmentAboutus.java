package com.ihunqin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ihunqin.crazy.WebActivity;
import com.ihunqin.mm.R;

public class FragmentAboutus extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_aboutus, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView ryapp = (TextView) getView().findViewById(R.id.ryapp);
		ryapp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", "http://www.ryapp.cn");
				startActivity(intent);

			}
		});

		TextView law = (TextView) getView().findViewById(R.id.law);
		law.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", "http://www.sh-wenhong.com/");
				startActivity(intent);
			}
		});
		TextView back = (TextView) getView().findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentmanager = getActivity()
						.getSupportFragmentManager();
				FragmentTransaction fragmenttransaction = fragmentmanager
						.beginTransaction();
				fragmenttransaction.replace(R.id.fragment_container,
						new FragmentMore()).commit();
			}
		});

	}
}
