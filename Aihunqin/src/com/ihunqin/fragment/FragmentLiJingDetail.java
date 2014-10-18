package com.ihunqin.fragment;

import org.w3c.dom.Document;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aihunqin.R;
import com.ihunqin.fragment.FragmentInvitation.TransferIDListener;
import com.ihunqin.util.XMLUtil;

public class FragmentLiJingDetail extends Fragment {
	private TransferIDListener mCallback;
	private TextView textView;
	private TextView back;
	private TextView rightmenu;
	private Button saveitem;
	private String itemid = null;

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

		return inflater
				.inflate(R.layout.fragment_lijindetail, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		final String id = getArguments().getString("id");
		textView = (TextView) getView().findViewById(R.id.titleTv);
		final int index = id.indexOf("income");
		if (index != -1) {
			textView.setText("礼金收入");
			itemid = id.substring(6);
		} else {
			textView.setText("礼金支出");
			itemid = id.substring(7);
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
		rightmenu.setText("删除");
		rightmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked(id, "lijindetail");
			}
		});
		saveitem = (Button) getView().findViewById(R.id.saveitem);
		saveitem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tagName = null;
				if (index != -1) {
					tagName = "income";
				} else {
					tagName = "outcome";
				}
				if (XMLUtil.IsExist(tagName, itemid)) {
					Document document = XMLUtil.loadInit("lj.xml");
					XMLUtil.saveXML(document);
				}
			}
		});
	}
}
