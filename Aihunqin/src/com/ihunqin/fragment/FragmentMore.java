package com.ihunqin.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.*;
import android.telephony.SmsManager;
import android.view.View.OnClickListener;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import com.ihunqin.crazy.WebActivity;
import com.ihunqin.crazy.WenhongLocation;
import com.example.aihunqin.R;

public class FragmentMore extends Fragment {
	TextView wenhonglocation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_more, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		wenhonglocation = (TextView) getView().findViewById(
				R.id.wenhonglocation);
		wenhonglocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

				if (networkInfo != null && networkInfo.isConnected()) {
					//
				} else {
					Toast.makeText(getActivity(), "����δ����", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				Intent intent = new Intent();
				intent.setClass(getActivity(), WenhongLocation.class);
				startActivity(intent);
			}
		});
		TextView aboutus = (TextView) getView().findViewById(R.id.aboutus);
		aboutus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentmanager = getActivity()
						.getSupportFragmentManager();
				FragmentTransaction fragmenttransaction = fragmentmanager
						.beginTransaction();
				fragmenttransaction.replace(R.id.fragment_container,
						new FragmentAboutus());
				fragmenttransaction.commit();
			}
		});

		TextView offic = (TextView) getView().findViewById(R.id.offic);
		offic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", "http://www.sh-wenhong.com/");
				startActivity(intent);
			}
		});
		TextView callphone = (TextView) getView().findViewById(R.id.callphone);
		callphone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ "4006-513520"));
				startActivity(intent);
			}
		});

		TextView mail = (TextView) getView().findViewById(R.id.mail);
		mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri
						.parse("mailto:daishuguang4461@126.com"));
				startActivity(intent);
			}
		});

		TextView sms = (TextView) getView().findViewById(R.id.sms);
		sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setType("vnd.android-dir/mms-sms");
				startActivity(intent);
			}
		});
		TextView wechat = (TextView) getView().findViewById(R.id.wechat);
		wechat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}
}