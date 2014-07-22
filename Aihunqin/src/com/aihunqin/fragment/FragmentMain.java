package com.aihunqin.fragment;

import com.example.aihunqin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentMain extends Fragment {
	OnClickListener settingnameListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ImageButton xizhi = (ImageButton) getView().findViewById(R.id.xizhi);
		xizhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();

				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				Fragment fragment_invitation = new FragmentInvitation();
				fragmentTransaction.replace(R.id.fragment_container,
						fragment_invitation);
				fragmentTransaction.commit();
			}
		});

		ImageButton zuowei = (ImageButton) getView().findViewById(R.id.zuowei);
		zuowei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();

				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				Fragment fragment_settingname = new FragmentSettingName();
				fragmentTransaction.replace(R.id.fragment_container,
						fragment_settingname);
				fragmentTransaction.commit();
			}
		});

		// Setting bridegroom name
		TextView bridegroom = (TextView) getView().findViewById(
				R.id.bridegroomname);
		bridegroom.setOnClickListener(settingnameListener);
		// Setting bride name
		TextView bride = (TextView) getView().findViewById(R.id.bridename);
		bride.setOnClickListener(settingnameListener);

	}
}
