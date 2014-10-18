package com.ihunqin.fragment;

import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aihunqin.R;
import com.ihunqin.fragment.FragmentInvitation.TransferIDListener;

public class FragmentLijinList extends Fragment {
	TransferIDListener mCallback;
	private TextView textView;
	private TextView back;
	private TextView rightmenu;

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
		tagtype tt = id.equals("income") ? tagtype.income : tagtype.outcome;
		switch (tt) {
		case income:
			textView.setText("礼金收入");
			break;
		case outcome:
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

	}
}
