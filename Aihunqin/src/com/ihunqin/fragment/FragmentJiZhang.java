package com.ihunqin.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihunqin.R;
import com.ihunqin.fragment.FragmentInvitation.TransferIDListener;
import com.ihunqin.util.XMLUtil;

public class FragmentJiZhang extends Fragment {
	private TextView textView;
	private TextView back;
	private LinearLayout crash;
	private LinearLayout dash;
	TransferIDListener mCallback;
	private TextView ljincome;
	private TextView ljoutcome;
	private TextView ljsummary;

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

		return inflater.inflate(R.layout.fragment_jizhang, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		back = (TextView) getView().findViewById(R.id.back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();

				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				Fragment fragment_settingname = new FragmentMain();
				fragmentTransaction.replace(R.id.fragment_container,
						fragment_settingname);
				fragmentTransaction.commit();
			}
		});

		textView = (TextView) getView().findViewById(R.id.titleTv);
		textView.setText("ªÈ¿Òº«’À");

		crash = (LinearLayout) getView().findViewById(R.id.crash);
		crash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked("income", "lijinlist");
			}
		});

		dash = (LinearLayout) getView().findViewById(R.id.dash);
		dash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked("outcome", "lijinlist");
			}
		});
		ljincome = (TextView) getView().findViewById(R.id.ljincome);
		ljoutcome = (TextView) getView().findViewById(R.id.ljoutcome);
		ljsummary = (TextView) getView().findViewById(R.id.ljsummary);
		XMLUtil.setFileName("lj.xml");
		int income = XMLUtil.selectDolar("income");
		int outcome = XMLUtil.selectDolar("outcome");
		ljincome.setText(income + "");
		ljoutcome.setText(outcome + "");
		ljsummary.setText((income - outcome) + "");
	}

}
