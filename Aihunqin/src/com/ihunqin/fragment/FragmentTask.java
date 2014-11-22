package com.ihunqin.fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.aihunqin.R;
import com.ihunqin.model.Task;
import com.ihunqin.util.XMLUtil;

public class FragmentTask extends BaseFragment {
	TextView taskjindu;
	TextView taskmind;
	TextView taskjihuashijian;
	EditText taskname;
	TextView backbtn;
	EditText beizhu;
	SharedPreferences preferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		preferences = getActivity().getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		final String[] jindus = new String[] { "未完成", "已完成" };

		View view = inflater.inflate(R.layout.fragment_task, container, false);

		TextView title = (TextView) view.findViewById(R.id.titleTv);

		taskname = (EditText) view.findViewById(R.id.taskname);
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
		taskjihuashijian.setText(format.format(group.get(groupp)));

		taskmind = (TextView) view.findViewById(R.id.taskmind);
		if (((Task) child.get(groupp).get(childp)).mind.equals("")) {
			taskmind.setText("无需提醒");
		} else
			taskmind.setText(((Task) child.get(groupp).get(childp)).mind);

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
								int yeart = datePicker.getYear();
								int montht = datePicker.getMonth() + 1;
								int days = datePicker.getDayOfMonth();

								int hour = timePicker.getCurrentHour();
								int min = timePicker.getCurrentMinute();
								String minstr = min < 10 ? "0" + min : min + "";
								taskmind.setText(yeart + "年" + montht + "月"
										+ days + "日 " + hour + ":" + minstr);
							}
						});

				builder.setView(view).create().show();
			}
		});
		taskjindu = (TextView) view.findViewById(R.id.taskjindu);
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
					NodeList nodelist = root.getElementsByTagName("dict");
					int totalnode = nodelist.getLength();
					float done = 0.f;
					for (int i = 0; i < totalnode; i++) {
						Element node = (Element) nodelist.item(i);
						if (node.getAttribute("title").equals(
								((Task) child.get(groupp).get(childp)).title)) {
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
							.putString("jindu",
									(int) Math.ceil(done / totalnode) + "%")
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
		});
		return view;
	}
}
