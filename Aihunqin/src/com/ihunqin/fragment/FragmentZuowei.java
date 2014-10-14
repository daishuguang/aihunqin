package com.ihunqin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aihunqin.R;

public class FragmentZuowei extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_zuoandcai, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		FragmentTabHost tabHost = (FragmentTabHost) getView().findViewById(
				android.R.id.tabhost);

		tabHost.setup(getActivity(), getActivity().getSupportFragmentManager(),
				R.id.tabzuowei);
	}

}
