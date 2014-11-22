package com.ihunqin.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.example.aihunqin.R;
import com.ihunqin.fragment.FragmentInvitation.TransferIDListener;
import com.ihunqin.model.Task;

public class FragmentWeddingList extends BaseFragment {
	TextView backbtn;
	TextView title;
	TextView rightmenu;
	ExpandableListView qindanlist;

	ArrayList<String> years = null;
	SharedPreferences preferences;
	SimpleDateFormat format = null;
	TransferIDListener mCallback;

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
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	private XmlPullParser readFromSD() {
		String filepath = null;
		String filename = "weddingjihua.xml";
		XmlPullParser xmlpullparser = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Environment.getExternalStorageDirectory()
					.getPath() + "/ihunqin/";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			filepath = foldername + filename;
			File file = new File(filepath);

			if (file.exists()) {
				xmlpullparser = Xml.newPullParser();
				try {
					xmlpullparser.setFeature(
							XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
					FileInputStream fs = new FileInputStream(filepath);
					xmlpullparser.setInput(fs, "UTF-8");
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				try {
					file.createNewFile();

					InputStream is = getResources().getAssets()
							.open("task.xml");
					OutputStream os = new FileOutputStream(file);
					int len = 0;
					byte[] bytes = new byte[1024];
					while ((len = is.read(bytes)) != -1) {
						os.write(bytes, 0, len);

					}
					is.close();
					os.close();
					xmlpullparser.setInput(is, "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
			}
		}

		return xmlpullparser;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_weddinglist, container,
				false);

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
		title = (TextView) view.findViewById(R.id.titleTv);
		title.setText("婚礼清单");
		// rightmenu = (TextView) view.findViewById(R.id.rightmenu);
		// rightmenu.setVisibility(View.VISIBLE);
		// rightmenu.setText("新建任务");

		preferences = getActivity().getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);

		long weddingdate = Long.parseLong(preferences.getString(
				"setweddingdate", ""
						+ Calendar.getInstance().getTime().getTime()));
		Date date = new Date(weddingdate);
		Calendar calendar = new GregorianCalendar();

		format = new SimpleDateFormat("MM月dd日");
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
		// String a1 = format.format(date);
		// String a = f2.format(date);
		// String a2 = format.format(new Date(Calendar.getInstance().getTime()
		// .getTime()));
		// String a3 = f2.format(new Date(Calendar.getInstance().getTime()
		// .getTime()));
		ArrayList<Task> childitem = null;
		Task task = null;

		XmlPullParser xml = readFromSD();
		try {
			int eventType = xml.getEventType();// 产生第一个事件
			String key = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 判断当前事件是否是文档开始事件
					child = new ArrayList<ArrayList<Task>>();
					group = new ArrayList<Date>();
					years = new ArrayList<String>();
					break;
				case XmlPullParser.START_TAG:
					if ("countdown".equals(xml.getName())) {
						childitem = new ArrayList<Task>();
						key = xml.getAttributeValue(null, "key");
						calendar.setTime(date);
						if (key.equals("nineMonthPlan")) {
							calendar.add(Calendar.MONTH, -9);
						} else if (key.equals("sixMonthPlan")) {
							calendar.add(Calendar.MONTH, -6);
						} else if (key.equals("threeMonthPlan")) {
							calendar.add(Calendar.MONTH, -3);
						} else if (key.equals("twoMonthPlan")) {
							calendar.add(Calendar.MONTH, -2);
						} else if (key.equals("oneandhalfMonth")) {
							calendar.add(Calendar.MONTH, -1);
							calendar.add(calendar.DAY_OF_MONTH, -15);
						} else if (key.equals("oneMonth")) {
							calendar.add(Calendar.MONTH, -1);
						} else if (key.equals("twoWeek")) {
							calendar.add(Calendar.WEEK_OF_MONTH, -2);
						} else if (key.equals("sevenDay")) {
							calendar.add(Calendar.WEEK_OF_MONTH, -1);
						} else if (key.equals("twoDay")) {
							calendar.add(Calendar.DAY_OF_WEEK, -2);
						} else if (key.equals("oneDay")) {
							calendar.add(Calendar.DAY_OF_WEEK, -1);
						} else if (key.equals("theDay")) {

						}
						Date d = calendar.getTime();
						// String datestr = format.format(calendar.getTime());
						group.add(d);
						years.add(calendar.get(Calendar.YEAR) + "");
						break;
					}
					if (childitem != null) {
						if ("dict".equals(xml.getName())) {
							task = new Task();
							task.title = xml.getAttributeValue(null, "title");
							task.mind = xml.getAttributeValue(null, "mind");
							task.prompt = xml.getAttributeValue(null, "prompt");
							task.describe = xml.getAttributeValue(null,
									"describe");
							task.status = xml.getAttributeValue(null, "status");
						}
					}
					break;
				case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
					if ("countdown".equals(xml.getName())) {
						child.add(childitem);// 将task添加到tasks集合中
					}
					if ("dict".equals(xml.getName())) {
						childitem.add(task);
						task = null;
					}
					break;
				}// end switch
				eventType = xml.next();// 进入下一个元素并触发相应事件
			}// end while
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		qindanlist = (ExpandableListView) view.findViewById(R.id.qindanlist);
		ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

			class GroupHolder {
				TextView father_title_tv;
				TextView year;
				View leftview;
			}

			class ChildHolder {
				TextView child_title_tv;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				return true;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				GroupHolder groupHolder = null;
				if (convertView == null) {
					groupHolder = new GroupHolder();
					convertView = LayoutInflater.from(getActivity()).inflate(
							R.layout.expandlistview_indicator, null);
					convertView.setLayoutParams(new LayoutParams(
							android.view.ViewGroup.LayoutParams.MATCH_PARENT,
							80));
					groupHolder.father_title_tv = (TextView) convertView
							.findViewById(R.id.father_title_tv);
					groupHolder.year = (TextView) convertView
							.findViewById(R.id.taskyear);
					groupHolder.leftview = (View) convertView
							.findViewById(R.id.leftview);
					convertView.setTag(groupHolder);
				} else {
					groupHolder = (GroupHolder) convertView.getTag();
				}

				groupHolder.father_title_tv.setText(format.format(
						(Date) getGroup(groupPosition)).toString());
				groupHolder.year.setText(years.get(groupPosition));
				Date d = (Date) getGroup(groupPosition);
				Calendar catemp = new GregorianCalendar();
				catemp.setTime(d);
				catemp.add(Calendar.DATE, 1);
				d = catemp.getTime();
				if (Calendar.getInstance().getTime().after(d)) {
					groupHolder.father_title_tv.setTextColor(getResources()
							.getColor(R.color.group_gray));
					groupHolder.leftview.setBackgroundColor(getResources()
							.getColor(R.color.indicatorview_gray));
				} else {
					groupHolder.father_title_tv.setTextColor(getResources()
							.getColor(R.color.indicatorfont));
					groupHolder.leftview.setBackgroundColor(getResources()
							.getColor(R.color.indicatorview_red));
				}
				return convertView;
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public int getGroupCount() {
				return group.size();
			}

			@Override
			public Object getGroup(int groupPosition) {
				return group.get(groupPosition);
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				return child.get(groupPosition).size();
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				ChildHolder childHolder = null;
				if (convertView == null) {
					childHolder = new ChildHolder();
					convertView = LayoutInflater.from(getActivity()).inflate(
							R.layout.expandlistview_task, null);
					convertView.setLayoutParams(new LayoutParams(
							android.view.ViewGroup.LayoutParams.MATCH_PARENT,
							80));
					childHolder.child_title_tv = (TextView) convertView
							.findViewById(R.id.child_title_tv);
					convertView.setTag(childHolder);
				} else {
					childHolder = (ChildHolder) convertView.getTag();
				}
				childHolder.child_title_tv.setText(((Task) getChild(
						groupPosition, childPosition)).title);
				return convertView;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				return child.get(groupPosition).get(childPosition);
			}
		};

		qindanlist.setAdapter(adapter);
		qindanlist.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});
		qindanlist.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				childp = childPosition;
				groupp = groupPosition;
				mCallback.onItemClicked("", "task");
				return true;
			}
		});
		int qindanlen = qindanlist.getCount();
		for (int i = 0; i < qindanlen; i++) {
			qindanlist.expandGroup(i);
		}
		return view;
	}
}
