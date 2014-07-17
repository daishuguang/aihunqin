package com.aihunqin.fragment;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aihunqin.R;

public class FragmentInivitationContent extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_invitation_content,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((TextView) getView().findViewById(R.id.titleTv)).setText("��������");

		TextView back = (TextView) getView().findViewById(R.id.back);
		back.setText("����");
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

		final EditText weddingdate = (EditText) getView().findViewById(
				R.id.weddingdate);
		// weddingdate.setOnFocusChangeListener(new OnFocusChangeListener() {
		//
		// @Override
		// public void onFocusChange(View v, boolean hasFocus) {
		//
		// if (hasFocus) {
		// hideIM(weddingdate);
		// ImageButton clear = (ImageButton) getView().findViewById(
		// R.id.clear_weddingdate);
		// if (weddingdate.getText().equals("")) {
		// clear.setVisibility(View.VISIBLE);
		// } else {
		// clear.setVisibility(View.INVISIBLE);
		// }
		// }
		// }
		// });

		weddingdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar rightNow = Calendar.getInstance();

				new DatePickerDialog(getActivity(), new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						weddingdate.setText(year + "��" + (monthOfYear + 1)
								+ "��" + dayOfMonth + "��");
					}
				}
				// ���ó�ʼ����
						, rightNow.get(Calendar.YEAR), rightNow
								.get(Calendar.MONTH), rightNow
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		TextView rightmenu = ((TextView) getView().findViewById(R.id.rightmenu));
		rightmenu.setText("�ύ");
		rightmenu.setVisibility(View.VISIBLE);
		rightmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				weddingdate.requestFocus();
			}
		});

		final EditText weddingchinese = (EditText) getView().findViewById(
				R.id.weddingdatechinese);
		weddingchinese.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar rightNow = Calendar.getInstance();

				new DatePickerDialog(getActivity(), new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						weddingchinese.setText(year + "��" + (monthOfYear + 1)
								+ "��" + dayOfMonth + "��");
					}
				}
				// ���ó�ʼ����
						, rightNow.get(Calendar.YEAR), rightNow
								.get(Calendar.MONTH), rightNow
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	void hideIM(View edt) {
		InputMethodManager im = (InputMethodManager) getActivity()
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		IBinder windowToken = edt.getWindowToken();
		if (windowToken != null) {
			im.hideSoftInputFromWindow(windowToken, 0);
		}
	}
}
