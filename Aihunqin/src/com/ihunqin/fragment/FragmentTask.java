package com.ihunqin.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aihunqin.R;

public class FragmentTask extends Fragment {
	TextView taskjindu;
	TextView taskmind;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final String[] jindus = new String[] { "未完成", "已完成" };
		
		View view = inflater.inflate(R.layout.fragment_task, container, false);
		taskmind = (TextView) view.findViewById(R.id.taskmind);
		taskmind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setItems(jindus, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						taskmind.setText(jindus[which]);
					}
				}).create().show();
			}
		});
		taskjindu = (TextView) view.findViewById(R.id.taskjindu);
		taskjindu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setItems(jindus, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						taskjindu.setText(jindus[which]);
					}
				}).create().show();

			}
		});
		return view;
	}
}
