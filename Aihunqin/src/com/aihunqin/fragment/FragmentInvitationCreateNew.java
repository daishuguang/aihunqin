package com.aihunqin.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aihunqin.crazy.SinaMain;
import com.aihunqin.crazy.WebActivity;
import com.aihunqin.fragment.FragmentInvitation.TransferIDListener;
import com.example.aihunqin.R;

public class FragmentInvitationCreateNew extends Fragment {
	String id = "";
	TransferIDListener mCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (TransferIDListener) activity;
			Log.v("roboce", "onattach");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_invitation_createnew,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		id = getArguments().getString("id");
		Log.v("roboce", "ID:" + id);
		TextView back = (TextView) getView().findViewById(R.id.back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.fragment_container,
								new FragmentInvitation()).commit();
			}
		});
		TextView title = (TextView) getView().findViewById(R.id.titleTv);
		title.setText("请帖管理");
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});

		// 底部菜单
		Button editcontent = (Button) getView().findViewById(R.id.editcontent);
		editcontent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked(id, "content");
			}
		});
		Button preview = (Button) getView().findViewById(R.id.preview);
		preview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", "http://www.ruiqinsoft.com:3083/wh/t/"
						+ id);
				startActivity(intent);
			}
		});

		Button gest = (Button) getView().findViewById(R.id.gest);
		gest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link",
						"http://www.ruiqinsoft.com:3083/guest/v/" + id);
				startActivity(intent);
			}
		});

		Button blessing = (Button) getView().findViewById(R.id.blessing);
		blessing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link",
						"http://www.ruiqinsoft.com:3083/follower/v/" + id);
				startActivity(intent);
			}
		});
	}
}
