package com.aihunqin.crazy;

import com.example.aihunqin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentMain extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView textView = (TextView) getView().findViewById(R.id.titleTv);
		textView.setText("Ê×Ò³");
		Button button = (Button) getView().findViewById(R.id.btn);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();

				fragmentManager
						.beginTransaction()
						.hide(fragmentManager
								.findFragmentById(R.id.fragment_main))
						.show(fragmentManager
								.findFragmentById(R.id.fragment_invitation))
						.addToBackStack(null).commit();
			}
		});
	}
}
