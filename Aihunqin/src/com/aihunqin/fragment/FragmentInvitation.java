package com.aihunqin.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aihunqin.crazy.WebActivity;
import com.aihunqin.util.HttpUtil;
import com.example.aihunqin.R;

public class FragmentInvitation extends Fragment {
	TextView invitationid;
	TransferIDListener mCallback;

	public interface TransferIDListener {
		public void onItemClicked(String id, String fragment);
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
		return inflater.inflate(R.layout.fragment_invitation, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		


		Button button2 = (Button) getView().findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked("101", "createnew");
			}
		});
		TextView textView = (TextView) getView().findViewById(R.id.titleTv);
		textView.setText("请帖管理");
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

	void InsertToXML() {

	}

	// xml数据生成
	String WriteToString() {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("utf-8", true);
			serializer.startTag("", "invitations");
			serializer.startTag("", "invitation");

			serializer.startTag("", "username");
			serializer.text("roboce");
			serializer.endTag("", "username");

			serializer.startTag("", "useremail");
			serializer.text("daishuguang@126.com");
			serializer.endTag("", "usermail");

			serializer.endTag("", "invitation");
			serializer.endTag("", "invitations");
			serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	boolean writeToXml(String str) {
		try {
			OutputStream out = getActivity().openFileOutput("users.xml",
					Context.MODE_PRIVATE);

			OutputStreamWriter outWriter = new OutputStreamWriter(out);
			outWriter.write(str);
			outWriter.close();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	void deletefile() {

		getActivity().deleteFile("users.xml");
		String[] filelist = getActivity().fileList();
		String str = null;
		for (int i = 0; i < filelist.length; i++) {
			str += filelist[i] + "\n";
		}
		invitationid.setText(str);
	}
}
