package com.ihunqin.fragment;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
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
import com.ihunqin.activity.AlarmActivity;
import com.ihunqin.model.Task;
import com.ihunqin.test.AlarmTest;
import com.ihunqin.util.XMLUtil;

public class FragmentTask extends BaseFragment {
	TextView taskjindu;
	TextView taskmind;
	TextView taskjihuashijian;
	EditText taskname;
	TextView backbtn;
	EditText beizhu;
	TextView rightmenu;
	SharedPreferences preferences;
	int yearnew = -1, monthnew, daynew;
	AlarmManager alarmManager;
	Calendar minddate;
	PendingIntent pi;
	DatePicker datePicker;
	TimePicker timePicker;

	private float calPert(Element root) {
		float done = 0.f;
		NodeList nodelist = root.getElementsByTagName("dict");
		int totalnode = nodelist.getLength();
		for (int i = 0; i < totalnode; i++) {
			Element node = (Element) nodelist.item(i);
			if (node.getAttribute("status").equals("已完成")) {
				done++;
			}
		}
		return done / totalnode;
	}

	private void deleteTask() {
		String filepath = Environment.getExternalStorageDirectory().getPath()
				+ "/ihunqin/" + "weddingjihua.xml";
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/ihunqin/" + "weddingjihua.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			Element root = doc.getDocumentElement();
			NodeList nodelist = root.getElementsByTagName("dict");
			int totalnode = nodelist.getLength();
			float done = 0.f;
			for (int i = 0; i < totalnode; i++) {
				Element node = (Element) nodelist.item(i);
				if (node.getAttribute("title").equals(
						((Task) child.get(groupp).get(childp)).title)) {
					node.getParentNode().removeChild(node);
				}
				if (node.getAttribute("status").equals("已完成")) {
					done++;
				}
			}
			preferences
					.edit()
					.putString("jindu",
							(int) Math.floor((done / totalnode) * 100) + "%")
					.commit();
			XMLUtil.filepath = filepath;
			XMLUtil.saveXML(doc);

			getActivity().getSupportFragmentManager().popBackStack();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		preferences = getActivity().getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		final String[] jindus = new String[] { "未完成", "已完成" };

		View view = inflater.inflate(R.layout.fragment_task, container, false);

		TextView title = (TextView) view.findViewById(R.id.titleTv);

		rightmenu = (TextView) view.findViewById(R.id.rightmenu);
		rightmenu.setText("删除任务");
		rightmenu.setVisibility(View.VISIBLE);
		rightmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!newTask) {
					AlertDialog.Builder builder = new Builder(getActivity());
					builder.setMessage("确定要删除?")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											deleteTask();
										}
									}).setNegativeButton("取消", null).create()
							.show();
				} else {
					getActivity().getSupportFragmentManager().popBackStack();
				}
			}
		});

		taskname = (EditText) view.findViewById(R.id.taskname);
		if (!newTask)
			taskname.setText(((Task) child.get(groupp).get(childp)).title);

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
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		taskjihuashijian = (TextView) view.findViewById(R.id.taskjihuashijian);
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		if (!newTask)
			taskjihuashijian.setText(format.format(group.get(groupp)));
		else
			taskjihuashijian.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Calendar c = Calendar.getInstance();
					new DatePickerDialog(getActivity(),
							new OnDateSetListener() {

								@Override
								public void onDateSet(DatePicker view,
										int year, int monthOfYear,
										int dayOfMonth) {
									taskjihuashijian.setText(year + "年"
											+ (monthOfYear + 1) + "月"
											+ dayOfMonth + "日");
									yearnew = year;
									monthnew = monthOfYear + 1;
									daynew = dayOfMonth;
								}
							}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
									.get(Calendar.DAY_OF_MONTH)).show();
				}
			});
		taskmind = (TextView) view.findViewById(R.id.taskmind);
		if (!newTask) {
			if (((Task) child.get(groupp).get(childp)).mind.equals("")) {
				taskmind.setText("无需提醒");
			} else
				taskmind.setText(((Task) child.get(groupp).get(childp)).mind);
		}

		taskmind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = View.inflate(getActivity(),
						R.layout.date_time_picker, null);

				datePicker = (DatePicker) view
						.findViewById(R.id.act_date_picker);
				timePicker = (TimePicker) view
						.findViewById(R.id.act_time_picker);

				timePicker.setIs24HourView(true);
				timePicker.setCurrentHour(Calendar.getInstance().get(
						Calendar.HOUR_OF_DAY));
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("选择日期与时间");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								int yeart = datePicker.getYear();
								int montht = datePicker.getMonth() + 1;
								int days = datePicker.getDayOfMonth();

								int hour = timePicker.getCurrentHour();
								int min = timePicker.getCurrentMinute();
								String minstr = min < 10 ? "0" + min : min + "";
								taskmind.setText(yeart + "年" + montht + "月"
										+ days + "日 " + hour + ":" + minstr);
								alarmManager = (AlarmManager) getActivity()
										.getSystemService(Context.ALARM_SERVICE);
								// Intent intent = new Intent(getActivity(),
								// AlarmActivity.class);
								// pi = PendingIntent.getActivity(getActivity(),
								// 0, intent, 0);
								Intent intent = new Intent("MYALARM");
								intent.putExtra("msg", taskname.getText()
										.toString());
								pi = PendingIntent.getBroadcast(getActivity(),
										0, intent, 0);
								minddate = Calendar.getInstance();
								minddate.setTimeInMillis(0);
								SimpleDateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd");
								Date d = null;
								try {
									d = format.parse(yeart + "-" + montht + "-"
											+ days);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								minddate.setTime(d);
								minddate.set(Calendar.HOUR_OF_DAY, hour);
								minddate.set(Calendar.MINUTE, min);
								String sys = (new Date(System
										.currentTimeMillis())).getMonth()
										+ "/"
										+ (new Date(System.currentTimeMillis()))
												.getDay()
										+ " "
										+ (new Date(System.currentTimeMillis()))
												.getHours()
										+ ":"
										+ (new Date(System.currentTimeMillis()))
												.getMinutes()
										+ " "
										+ (new Date(System.currentTimeMillis()))
												.getSeconds();
								String mind = (new Date(minddate
										.getTimeInMillis())).getMonth()
										+ "/"
										+ (new Date(minddate.getTimeInMillis()))
												.getDay()
										+ " "
										+ (new Date(minddate.getTimeInMillis()))
												.getHours()
										+ ":"
										+ (new Date(minddate.getTimeInMillis()))
												.getMinutes()
										+ " "
										+ (new Date(minddate.getTimeInMillis()))
												.getSeconds();
								minddate.getTimeInMillis();
								System.currentTimeMillis();
							}
						});

				builder.setView(view).create().show();
			}
		});
		taskjindu = (TextView) view.findViewById(R.id.taskjindu);
		if (!newTask)
			taskjindu.setText(((Task) child.get(groupp).get(childp)).status);
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
		beizhu = (EditText) view.findViewById(R.id.beizhu);
		if (!newTask)
			beizhu.setText(((Task) child.get(groupp).get(childp)).describe);
		TextView btndone = (TextView) view.findViewById(R.id.btndone);
		btndone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String filepath = Environment.getExternalStorageDirectory()
						.getPath() + "/ihunqin/" + "weddingjihua.xml";
				File file = new File(Environment.getExternalStorageDirectory()
						.getPath() + "/ihunqin/" + "weddingjihua.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();

				DocumentBuilder db;
				try {
					db = dbf.newDocumentBuilder();
					Document doc = db.parse(file);
					Element root = doc.getDocumentElement();
					float done = 0.f;
					if (!newTask) {
						NodeList nodelist = root.getElementsByTagName("dict");
						int totalnode = nodelist.getLength();
						for (int i = 0; i < totalnode; i++) {
							Element node = (Element) nodelist.item(i);
							if (node.getAttribute("title")
									.equals(((Task) child.get(groupp).get(
											childp)).title)) {
								node.setAttribute("mind", taskmind.getText()
										.toString());
								node.setAttribute("describe", beizhu.getText()
										.toString());
								node.setAttribute("status", taskjindu.getText()
										.toString());
							}
							if (node.getAttribute("status").equals("已完成")) {
								done++;
							}
						}
						preferences
								.edit()
								.putString(
										"jindu",
										(int) Math
												.floor((done / totalnode) * 100)
												+ "%").commit();
					}
					if (newTask) {
						String taskname_new = taskname.getText().toString();
						if (taskname_new.equals("")) {
							Toast.makeText(getActivity(), "任务名称不能为空",
									Toast.LENGTH_SHORT).show();
							return;
						}
						if (yearnew == -1) {
							Toast.makeText(getActivity(), "必须输入计划的时间",
									Toast.LENGTH_SHORT).show();
							return;
						}
						SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
						Date datenew = f2.parse(yearnew + "-" + monthnew + "-"
								+ daynew);

						int flag = 0;
						for (int i = 0; i < group.size(); i++, flag++) {
							if (datenew.compareTo(group.get(i)) == 0) {
								NodeList nodelist = root
										.getElementsByTagName("countdown");
								for (int j = 0; j < nodelist.getLength(); j++) {
									Element ele = (Element) nodelist.item(j);
									if (ele.getAttribute("key").equals(
											tags.get(i))) {
										Node countdown = nodelist.item(j);
										Element dict = doc
												.createElement("dict");
										dict.setAttribute("title", taskname_new);
										dict.setAttribute("mind", taskmind
												.getText().toString());
										dict.setAttribute("describe", beizhu
												.getText().toString());
										dict.setAttribute("status", taskjindu
												.getText().toString());
										countdown.appendChild(dict);
										break;
									}
								}
								break;
							}
						}

						if (flag == group.size()) {
							long weddingdate = Long.parseLong(preferences
									.getString("setweddingdate", ""
											+ Calendar.getInstance().getTime()
													.getTime()));
							long diff = weddingdate - datenew.getTime();
							long days = diff / (24 * 60 * 60 * 1000);
							Element countdown = doc.createElement("countdown");
							countdown.setAttribute("key", days + "");
							Element dict = doc.createElement("dict");
							dict.setAttribute("title", taskname_new);
							dict.setAttribute("mind", taskmind.getText()
									.toString());
							dict.setAttribute("describe", beizhu.getText()
									.toString());
							dict.setAttribute("status", taskjindu.getText()
									.toString());
							countdown.appendChild(dict);
							root.appendChild(countdown);
						}
						preferences
								.edit()
								.putString(
										"jindu",
										(int) Math.floor(calPert(root) * 100)
												+ "%").commit();
					}

					XMLUtil.filepath = filepath;
					XMLUtil.saveXML(doc);

					if (alarmManager != null) {
						minddate.getTimeInMillis();
						System.currentTimeMillis();
						alarmManager.set(AlarmManager.RTC_WAKEUP,
								minddate.getTimeInMillis(), pi);
					}
					getActivity().getSupportFragmentManager().popBackStack();

				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
		});
		return view;
	}
}
