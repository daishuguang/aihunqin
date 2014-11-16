package com.ihunqin.fragment;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aihunqin.R;

public class FragmentTask extends Fragment {
	TextView taskjindu;
	TextView taskmind;
	TextView taskjihuashijian;
	EditText taskname;
	TextView backbtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final String[] jindus = new String[] { "未完成", "已完成" };

		View view = inflater.inflate(R.layout.fragment_task, container, false);

		TextView title = (TextView) view.findViewById(R.id.titleTv);

		taskname = (EditText) view.findViewById(R.id.taskname);
		if (taskname.getText().toString().equals("")) {
			title.setText("创建新任务");
		} else {
			title.setText("编辑任务");
		}
		backbtn = (TextView) view.findViewById(R.id.back);
		backbtn.setVisibility(View.VISIBLE);
		backbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, new FragmentMain())
						.commit();
			}
		});
		taskjihuashijian = (TextView) view.findViewById(R.id.taskjihuashijian);
		taskjihuashijian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar c = Calendar.getInstance();
				new DatePickerDialog(getActivity(), new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						taskjihuashijian.setText(year + "年" + (monthOfYear + 1)
								+ "月" + dayOfMonth + "日");
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
						.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		taskmind = (TextView) view.findViewById(R.id.taskmind);
		taskmind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = View.inflate(getActivity(),
						R.layout.date_time_picker, null);

				final DatePicker datePicker = (DatePicker) view
						.findViewById(R.id.act_date_picker);
				final TimePicker timePicker = (TimePicker) view
						.findViewById(R.id.act_time_picker);

				timePicker.setIs24HourView(true);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("选择日期与时间");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(getActivity(), "hahah",
										Toast.LENGTH_SHORT).show();
							}
						});

				builder.setView(view).create().show();
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
