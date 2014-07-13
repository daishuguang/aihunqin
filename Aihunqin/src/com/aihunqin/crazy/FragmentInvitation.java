package com.aihunqin.crazy;

import com.example.aihunqin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

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
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		button.setVisibility(View.VISIBLE);
	}

}
