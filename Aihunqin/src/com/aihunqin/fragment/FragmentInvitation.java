package com.aihunqin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aihunqin.crazy.WebActivity;
import com.example.aihunqin.R;

public class FragmentInvitation extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_invitation, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView textView = (TextView) getView().findViewById(R.id.titleTv);
		textView.setText("«ÎÃ˚π‹¿Ì");
		TextView button = (TextView) getView().findViewById(R.id.back);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, new FragmentMain())
						.commit();
			}
		});
		button.setVisibility(View.VISIBLE);
		RelativeLayout layout = (RelativeLayout) getView().findViewById(
				R.id.demolink);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", "http://ruiqinsoft.com:3083/wh/t/66");
				startActivity(intent);
			}
		});
	}

}
