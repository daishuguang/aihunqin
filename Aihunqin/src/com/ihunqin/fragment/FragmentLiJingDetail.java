package com.ihunqin.fragment;

import java.util.Calendar;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihunqin.R;
import com.ihunqin.fragment.FragmentInvitation.TransferIDListener;
import com.ihunqin.model.LiJin;
import com.ihunqin.util.XMLUtil;

public class FragmentLiJingDetail extends Fragment {
	private TransferIDListener mCallback;
	private TextView textView;
	private TextView back;
	private TextView rightmenu;
	private Button saveitem;
	private String itemid = null;
	private TextView lijintype;
	private TextView lijindate;
	private EditText lijindolar;
	private EditText lijinname;
	private EditText lijincomment;

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

		return inflater
				.inflate(R.layout.fragment_lijindetail, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		final String id = getArguments().getString("id");
		textView = (TextView) getView().findViewById(R.id.titleTv);
		lijintype = (TextView) getView().findViewById(R.id.lijintype);
		final int index = id.indexOf("income");
		if (index != -1) {
			textView.setText("礼金收入");
			lijintype.setText("礼金收入");
			itemid = id.substring(6);
		} else {
			textView.setText("礼金支出");
			lijintype.setText("礼金支出");
			itemid = id.substring(7);
		}
		String tagName1 = null;
		if (index != -1) {
			tagName1 = "income";
		} else {
			tagName1 = "outcome";
		}
		textView.setVisibility(View.VISIBLE);
		back = (TextView) getView().findViewById(R.id.back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		rightmenu = (TextView) getView().findViewById(R.id.rightmenu);
		rightmenu.setVisibility(View.VISIBLE);
		rightmenu.setText("删除");
		rightmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tagName = null;
				if (index != -1) {
					tagName = "income";
				} else {
					tagName = "outcome";
				}
				if (XMLUtil.IsExist(tagName, itemid)) {
					XMLUtil.deleteXML(tagName, itemid);
				}
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		saveitem = (Button) getView().findViewById(R.id.saveitem);
		saveitem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (lijindolar.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "礼金不能为空", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (lijinname.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "宾客不能为空", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				String tagName = null;
				if (index != -1) {
					tagName = "income";
				} else {
					tagName = "outcome";
				}
				XMLUtil.setFileName("lj.xml");
				LiJin liJing = new LiJin();
				liJing.setId(itemid);
				liJing.setName(lijinname.getText().toString());
				liJing.setDate(lijindate.getText().toString());
				liJing.setDolar(Integer.parseInt(lijindolar.getText()
						.toString()));
				liJing.setComment(lijincomment.getText().toString());
				if (!XMLUtil.IsExist(tagName, itemid)) {
					XMLUtil.addXML(tagName, liJing);
				} else {
					XMLUtil.updateXML(tagName, liJing);
				}
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});

		lijindate = (TextView) getView().findViewById(R.id.lijindate);
		Calendar now = Calendar.getInstance();
		String nowdate = now.get(Calendar.YEAR) + "年"
				+ (now.get(Calendar.MONTH) + 1) + "月"
				+ now.get(Calendar.DAY_OF_MONTH) + "日";
		lijindate.setText(nowdate);
		lijindate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar rightNow = Calendar.getInstance();
				new DatePickerDialog(getActivity(), new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						lijindate.setText(year + "年" + (monthOfYear + 1) + "月"
								+ dayOfMonth + "日");
					}
				}
				// 设置初始日期
						, rightNow.get(Calendar.YEAR), rightNow
								.get(Calendar.MONTH), rightNow
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		lijincomment = (EditText) getView().findViewById(R.id.lijincomment);
		lijinname = (EditText) getView().findViewById(R.id.lijinname);
		lijindolar = (EditText) getView().findViewById(R.id.lijindolar);
		XMLUtil.setFileName("lj.xml");
		LiJin lj = new LiJin();
		if (XMLUtil.IsExist(tagName1, itemid)) {
			lj = XMLUtil.selectItem(tagName1, itemid);
			lijincomment.setText(lj.getComment());
			lijindate.setText(lj.getDate());
			lijindolar.setText(lj.getDolar() + "");
			lijinname.setText(lj.getName());
		}
	}
}
