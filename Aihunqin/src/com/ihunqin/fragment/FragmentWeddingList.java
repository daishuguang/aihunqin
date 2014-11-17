package com.ihunqin.fragment;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aihunqin.R;

public class FragmentWeddingList extends Fragment {
	TextView backbtn;
	TextView title;
	TextView rightmenu;
	ExpandableListView qindanlist;

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

		XmlResourceParser xml = getResources().getXml(R.xml.task);
		try {
			int eventType = xml.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {

				} else if (eventType == XmlPullParser.START_TAG) {
					if (xml.getName().equals("title")) {
					}
				}
				eventType = xml.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		qindanlist = (ExpandableListView) view.findViewById(R.id.qindanlist);
		ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

			String[] armTypes = new String[] { "神族兵种", "虫族兵种", "人族兵种" };
			String[][] arms = new String[][] { { "狂战士", "龙骑士", "黑暗圣堂", "电兵" },
					{ "小狗", "刺蛇", "飞龙", "自曝飞机" }, { "机枪兵", "护士MM", "幽灵" }

			};

			class GroupHolder {
				TextView father_title_tv;
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

			TextView getTextView() {
				AbsListView.LayoutParams lp = new LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT, 64);
				TextView textView = new TextView(getActivity());
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER);
				textView.setTextSize(20);
				return textView;
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public int getGroupCount() {
				return armTypes.length;
			}

			@Override
			public Object getGroup(int groupPosition) {
				return armTypes[groupPosition];
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				return arms[groupPosition].length;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				TextView textView = getTextView();
				textView.setText(getChild(groupPosition, childPosition)
						.toString());
				return textView;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				return arms[groupPosition][childPosition];
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
