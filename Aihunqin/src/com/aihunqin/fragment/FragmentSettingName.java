package com.aihunqin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aihunqin.R;

public class FragmentSettingName extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.fragment_settingname, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((TextView) getView().findViewById(R.id.titleTv)).setText("ÕÕÆ¬ºÍÃû×Ö");
		((TextView) getView().findViewById(R.id.back))
				.setVisibility(View.VISIBLE);
		((TextView) getView().findViewById(R.id.rightmenu)).setText("±£´æ");
		((TextView) getView().findViewById(R.id.rightmenu))
				.setVisibility(View.VISIBLE);
	}
}
