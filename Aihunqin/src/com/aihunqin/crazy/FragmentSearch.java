package com.aihunqin.crazy;

import com.example.aihunqin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentSearch extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView textView = (TextView) getView().findViewById(R.id.titleTv);
		textView.setText("ËÑË÷");
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
