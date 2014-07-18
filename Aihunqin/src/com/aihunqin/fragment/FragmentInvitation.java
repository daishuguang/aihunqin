package com.aihunqin.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aihunqin.crazy.WebActivity;
import com.aihunqin.util.HttpUtil;
import com.example.aihunqin.R;

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
		TextView backbtn = (TextView) getView().findViewById(R.id.back);
		backbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, new FragmentMain())
						.commit();
			}
		});
		backbtn.setVisibility(View.VISIBLE);
		RelativeLayout layout = (RelativeLayout) getView().findViewById(
				R.id.demolink);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", "http://ruiqinsoft.com:3083/wh/t/66");
				startActivity(intent);
			}
		});

		// Create Invitation
		TextView createInvitationbtn = (TextView) getView().findViewById(
				R.id.rightmenu);
		createInvitationbtn.setVisibility(View.VISIBLE);
		createInvitationbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						String url = "http://www.ruiqinsoft.com:3083/wh/c";
						Map<String, String> rawparams = new HashMap<String, String>();
						rawparams.put("KehuName", "wenhong");
						rawparams.put("BackGroundImageUrl", "beijing");
						rawparams.put("YouKuVideoId", "youkuid");
						rawparams.put("XinlangName", "xl");
						rawparams.put("XinlangMobile", "1333");
						rawparams.put("XinniangName", "xn");
						rawparams.put("XinniangMobile", "133333");
						rawparams.put("WeddingDateNongli", "nongliriqi");
						rawparams.put("WeddingDateTime", "hunlishij");
						rawparams.put("textFieldWeddingTime", "jidianshijian");
						rawparams.put("WeddingLoacation", "123");
						rawparams.put("WeddingMenu", "hunlicaidan");
						rawparams.put("WeddingTables", "hunzuo");
						rawparams.put("LoveWord", "12");
						rawparams.put("LoveWord2", "12");
						rawparams.put("LoveWord3", "12");
						rawparams.put("LoveWord4", "12");
						rawparams.put("HotelName", "jiudianmingzi");
						String result;
						try {
							result = HttpUtil.postRequst(url, rawparams);

							Log.v("roboce", result);
							JSONObject json;
							json = new JSONObject(result);
							String m = json.getString("id");

							Log.v("roboce", m);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						getActivity()
								.getSupportFragmentManager()
								.beginTransaction()
								.replace(R.id.fragment_container,
										new FragmentInvitationCreateNew())
								.addToBackStack(null).commit();
					}
				}).start();

			}
		});
	}
}
