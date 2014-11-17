package com.ihunqin.fragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.example.aihunqin.R;
import com.ihunqin.model.Task;

public class FragmentWeddingList extends Fragment {
	TextView backbtn;
	TextView title;
	TextView rightmenu;
	ExpandableListView qindanlist;

	ArrayList<String> group = null;
	ArrayList<ArrayList<Task>> child = null;

	SharedPreferences preferences;

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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long diff = Long.parseLong(preferences.getString("setweddingdate", ""));

		ArrayList<Task> childitem = null;
		Task task = null;
		XmlResourceParser xml = getResources().getXml(R.xml.task);
		try {
			int eventType = xml.getEventType();// 产生第一个事件
			String key = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 判断当前事件是否是文档开始事件
					child = new ArrayList<ArrayList<Task>>();
					group = new ArrayList<String>();
					break;
				case XmlPullParser.START_TAG:
					if ("countdown".equals(xml.getName())) {
						childitem = new ArrayList<Task>();
						group.add(xml.getAttributeValue(null, "key"));
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
					convertView.setTag(groupHolder);
				} else {
					groupHolder = (GroupHolder) convertView.getTag();
				}
				groupHolder.father_title_tv.setText(getGroup(groupPosition)
						.toString());
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
