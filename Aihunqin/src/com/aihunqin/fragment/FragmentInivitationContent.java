package com.aihunqin.fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aihunqin.util.HttpUtil;
import com.example.aihunqin.R;

public class FragmentInivitationContent extends Fragment {
	String id = "";
	EditText bridegroomname, bridename, bridegroommobile, bridemobile, love1,
			love2, love3, love4, weddingdate, weddingdatechinese,
			weddinglocation, hotelname, weddingtime, seat, youkuid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_invitation_content,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((TextView) getView().findViewById(R.id.titleTv)).setText("编辑请帖内容");

		id = getArguments().getString("id");
		TextView back = (TextView) getView().findViewById(R.id.back);
		back.setText("返回");
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
				// getActivity()
				// .getSupportFragmentManager()
				// .beginTransaction()
				// .replace(R.id.fragment_container,
				// new FragmentInvitationCreateNew()).commit();
			}
		});
		bridegroomname = (EditText) getView().findViewById(R.id.bridegroomname);
		bridename = (EditText) getView().findViewById(R.id.bridename);
		bridegroommobile = (EditText) getView().findViewById(
				R.id.bridegroommobile);
		bridemobile = (EditText) getView().findViewById(R.id.bridemobile);
		love1 = (EditText) getView().findViewById(R.id.love1);
		love2 = (EditText) getView().findViewById(R.id.love2);
		love3 = (EditText) getView().findViewById(R.id.love3);
		love4 = (EditText) getView().findViewById(R.id.love4);

		weddingdate = (EditText) getView().findViewById(R.id.weddingdate);
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
						weddingdate.setText(year + "年" + (monthOfYear + 1)
								+ "月" + dayOfMonth + "日");
					}
				}
				// 设置初始日期
						, rightNow.get(Calendar.YEAR), rightNow
								.get(Calendar.MONTH), rightNow
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		weddingdatechinese = (EditText) getView().findViewById(
				R.id.weddingdatechinese);
		weddingdatechinese.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar rightNow = Calendar.getInstance();

				new DatePickerDialog(getActivity(), new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						weddingdatechinese.setText(year + "年"
								+ (monthOfYear + 1) + "月" + dayOfMonth + "日");
					}
				}
				// 设置初始日期
						, rightNow.get(Calendar.YEAR), rightNow
								.get(Calendar.MONTH), rightNow
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		weddinglocation = (EditText) getView().findViewById(
				R.id.weddinglocation);
		hotelname = (EditText) getView().findViewById(R.id.hotelname);

		weddingtime = (EditText) getView().findViewById(R.id.weddingtime);
		weddingtime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar rightNow = Calendar.getInstance();

				new TimePickerDialog(getActivity(), new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						weddingtime.setText(hourOfDay + ":" + minute);
					}
				}, rightNow.get(Calendar.HOUR_OF_DAY), rightNow
						.get(Calendar.MINUTE), true).show();

			}
		});
		seat = (EditText) getView().findViewById(R.id.seat);
		youkuid = (EditText) getView().findViewById(R.id.youkuid);

		TextView rightmenu = ((TextView) getView().findViewById(R.id.rightmenu));
		rightmenu.setText("提交");
		rightmenu.setVisibility(View.VISIBLE);
		rightmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (bridegroomname.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "新郎名不能为空", Toast.LENGTH_LONG)
							.show();
					return;
				}
				if (bridename.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "新娘名不能为空", Toast.LENGTH_LONG)
							.show();
					return;
				}

				if (weddingdate.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "婚礼日期不能为空", Toast.LENGTH_LONG)
							.show();
					return;
				}

				if (weddinglocation.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "婚礼地址不能为空", Toast.LENGTH_LONG)
							.show();
					return;
				}
				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

				if (networkInfo != null && networkInfo.isConnected()) {
					//
				} else {
					Toast.makeText(getActivity(), "网络未连接", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				// send to server
				sendToServer();
			}
		});
		loadXML();
	}

	void writeToXml() {
		/** 保存图片 */
		String FILENAME = "invitationinfo.xml";

		File file = new File(getActivity().getFilesDir().getPath() + "/"
				+ FILENAME);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// 通过实例构建DocumentBuilder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 创建Document 解析给定的文件
			Document doc = db.parse(file);
			Element root = doc.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("invitation_" + id);
			Element root2 = (Element) nodeList.item(0);

			if (root2.getElementsByTagName("bridegroomname").item(0) != null) {
				root2.getElementsByTagName("bridegroomname").item(0)
						.getFirstChild()
						.setNodeValue(bridegroomname.getText().toString());

				root2.getElementsByTagName("bridename").item(0).getFirstChild()
						.setNodeValue(bridename.getText().toString());
				Text eletext = null;
				if (root2.getElementsByTagName("bridegroommobile").item(0)
						.getFirstChild() == null) {
					eletext = doc.createTextNode(bridegroomname.getText()
							.toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("bridegroommobile")
							.item(0)
							.getFirstChild()
							.setNodeValue(bridegroommobile.getText().toString());
				}

				if (root2.getElementsByTagName("bridemobile").item(0)
						.getFirstChild() == null) {
					eletext = doc.createTextNode(bridemobile.getText()
							.toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("bridemobile").item(0)
							.getFirstChild()
							.setNodeValue(bridemobile.getText().toString());
				}

				if (root2.getElementsByTagName("love1").item(0).getFirstChild() == null) {
					eletext = doc.createTextNode(love1.getText().toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("love1").item(0).getFirstChild()
							.setNodeValue(love1.getText().toString());
				}
				if (root2.getElementsByTagName("love2").item(0).getFirstChild() == null) {
					eletext = doc.createTextNode(love2.getText().toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("love2").item(0).getFirstChild()
							.setNodeValue(love2.getText().toString());
				}
				if (root2.getElementsByTagName("love3").item(0).getFirstChild() == null) {
					eletext = doc.createTextNode(love3.getText().toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("love3").item(0).getFirstChild()
							.setNodeValue(love3.getText().toString());
				}
				if (root2.getElementsByTagName("love4").item(0).getFirstChild() == null) {
					eletext = doc.createTextNode(love4.getText().toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("love4").item(0).getFirstChild()
							.setNodeValue(love4.getText().toString());
				}
				if (root2.getElementsByTagName("weddingdate").item(0)
						.getFirstChild() == null) {
					eletext = doc.createTextNode(weddingdate.getText()
							.toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("weddingdate").item(0)
							.getFirstChild()
							.setNodeValue(weddingdate.getText().toString());
				}
				if (root2.getElementsByTagName("weddingdatechinese").item(0)
						.getFirstChild() == null) {
					eletext = doc.createTextNode(weddingdatechinese.getText()
							.toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("weddingdatechinese")
							.item(0)
							.getFirstChild()
							.setNodeValue(
									weddingdatechinese.getText().toString());
				}

				if (root2.getElementsByTagName("weddinglocation").item(0)
						.getFirstChild() == null) {
					eletext = doc.createTextNode(weddinglocation.getText()
							.toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("weddinglocation").item(0)
							.getFirstChild()
							.setNodeValue(weddinglocation.getText().toString());
				}

				if (root2.getElementsByTagName("weddingtime").item(0)
						.getFirstChild() == null) {
					eletext = doc.createTextNode(weddingtime.getText()
							.toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("weddingtime").item(0)
							.getFirstChild()
							.setNodeValue(weddingtime.getText().toString());
				}
				if (root2.getElementsByTagName("seat").item(0).getFirstChild() == null) {
					eletext = doc.createTextNode(seat.getText().toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("seat").item(0).getFirstChild()
							.setNodeValue(seat.getText().toString());
				}
				if (root2.getElementsByTagName("youkuid").item(0)
						.getFirstChild() == null) {
					eletext = doc.createTextNode(youkuid.getText().toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("youkuid").item(0)
							.getFirstChild()
							.setNodeValue(youkuid.getText().toString());
				}

				if (root2.getElementsByTagName("hotelname").item(0)
						.getFirstChild() == null) {
					eletext = doc
							.createTextNode(hotelname.getText().toString());
					root2.appendChild(eletext);
				} else {
					root2.getElementsByTagName("hotelname").item(0)
							.getFirstChild()
							.setNodeValue(hotelname.getText().toString());
				}

			} else {
				Element ele = doc.createElement("bridegroomname");
				Text eletext = doc.createTextNode(bridegroomname.getText()
						.toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("bridename");
				eletext = doc.createTextNode(bridename.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("bridegroommobile");
				eletext = doc.createTextNode(bridegroommobile.getText()
						.toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("bridemobile");
				eletext = doc.createTextNode(bridemobile.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("love1");
				eletext = doc.createTextNode(love1.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("love2");
				eletext = doc.createTextNode(love2.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("love3");
				eletext = doc.createTextNode(love3.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("love4");
				eletext = doc.createTextNode(love4.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("weddingdate");
				eletext = doc.createTextNode(weddingdate.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("weddingdatechinese");
				eletext = doc.createTextNode(weddingdatechinese.getText()
						.toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("weddinglocation");
				eletext = doc.createTextNode(weddinglocation.getText()
						.toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("hotelname");
				eletext = doc.createTextNode(hotelname.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("weddingtime");
				eletext = doc.createTextNode(weddingtime.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("seat");
				eletext = doc.createTextNode(seat.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

				ele = doc.createElement("youkuid");
				eletext = doc.createTextNode(youkuid.getText().toString());
				ele.appendChild(eletext);
				root2.appendChild(ele);

			}

			TransformerFactory tfs = TransformerFactory.newInstance();
			Transformer tf = tfs.newTransformer();
			tf.transform(new DOMSource(doc), new StreamResult(
					new FileOutputStream(file)));

			try {
				FileInputStream fs = getActivity().openFileInput(FILENAME);
				try {
					InputStreamReader reader = new InputStreamReader(fs,
							"UTF-8");
					BufferedReader in = new BufferedReader(reader);
					StringBuffer buffer = new StringBuffer();

					buffer.append(in.readLine());
					buffer.toString();
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}

			} catch (FileNotFoundException e1) {

				e1.printStackTrace();
			}
			doc = null;
			db = null;
			dbf = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void loadXML() {

		/** 保存图片 */
		String FILENAME = "invitationinfo.xml";

		File file = new File(getActivity().getFilesDir().getPath() + "/"
				+ FILENAME);

		if (file.exists()) {
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				// 通过实例构建DocumentBuilder
				DocumentBuilder db = dbf.newDocumentBuilder();
				// 创建Document 解析给定的文件
				Document doc = db.parse(file);
				Element root = doc.getDocumentElement();
				NodeList nodeList = root.getElementsByTagName("invitation_"
						+ id);
				Element root2 = (Element) nodeList.item(0);
				if (root2.getElementsByTagName("bridegroomname").item(0) != null) {
					bridegroomname.setText(root2
							.getElementsByTagName("bridegroomname").item(0)
							.getFirstChild().getNodeValue());
					bridename.setText(root2.getElementsByTagName("bridename")
							.item(0).getFirstChild().getNodeValue());

					bridegroommobile.setText(root2
							.getElementsByTagName("bridegroommobile").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("bridegroommobile").item(0)
							.getFirstChild().getNodeValue());
					bridemobile.setText(root2
							.getElementsByTagName("bridemobile").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("bridemobile").item(0)
							.getFirstChild().getNodeValue());

					love1.setText(root2.getElementsByTagName("love1").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("love1").item(0)
							.getFirstChild().getNodeValue());
					love2.setText(root2.getElementsByTagName("love2").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("love2").item(0)
							.getFirstChild().getNodeValue());

					love3.setText(root2.getElementsByTagName("love3").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("love3").item(0)
							.getFirstChild().getNodeValue());
					love4.setText(root2.getElementsByTagName("love4").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("love4").item(0)
							.getFirstChild().getNodeValue());

					weddingdate.setText(root2
							.getElementsByTagName("weddingdate").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("weddingdate").item(0)
							.getFirstChild().getNodeValue());
					weddingdatechinese.setText(root2
							.getElementsByTagName("weddingdatechinese").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("weddingdatechinese").item(0)
							.getFirstChild().getNodeValue());

					weddinglocation.setText(root2
							.getElementsByTagName("weddinglocation").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("weddinglocation").item(0)
							.getFirstChild().getNodeValue());
					hotelname.setText(root2.getElementsByTagName("hotelname")
							.item(0).getFirstChild() == null ? "" : root2
							.getElementsByTagName("hotelname").item(0)
							.getFirstChild().getNodeValue());

					weddingtime.setText(root2
							.getElementsByTagName("weddingtime").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("weddingtime").item(0)
							.getFirstChild().getNodeValue());
					seat.setText(root2.getElementsByTagName("seat").item(0)
							.getFirstChild() == null ? "" : root2
							.getElementsByTagName("seat").item(0)
							.getFirstChild().getNodeValue());
					youkuid.setText(root2.getElementsByTagName("youkuid")
							.item(0).getFirstChild() == null ? "" : root2
							.getElementsByTagName("youkuid").item(0)
							.getFirstChild().getNodeValue());
				}
				doc = null;
				db = null;
				dbf = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	void sendToServer() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// String url = "http://www.ruiqinsoft.com:3083/wh/edit/" + id;
				String url = "http://wedding.ihunqin.com/api/ecard/" + id
						+ "/edit";
				Map<String, String> rawparams = new HashMap<String, String>();
				// rawparams.put("EcardType1Id", id);
				rawparams.put(
						"UserID",
						getActivity().getSharedPreferences("userinfo",
								Activity.MODE_PRIVATE).getString("userid", ""));
				rawparams.put("StoreID", "0");
				rawparams.put("KehuName", "wenhong");
				rawparams.put("BackGroundImageUrl", "beijing");
				rawparams.put("YouKuVideoId", youkuid.getText().toString());
				rawparams.put("XinlangName", bridegroomname.getText()
						.toString());
				rawparams.put("XinlangMobile", bridegroommobile.getText()
						.toString());
				rawparams.put("XinniangName", bridename.getText().toString());
				rawparams.put("XinniangMobile", bridemobile.getText()
						.toString());
				rawparams.put("WeddingDateNongli", weddingdatechinese.getText()
						.toString());
				rawparams.put("WeddingDateTime", weddingdate.getText()
						.toString());
				rawparams.put("textFieldWeddingTime", weddingtime.getText()
						.toString());
				rawparams.put("WeddingLoacation", weddinglocation.getText()
						.toString());
				rawparams.put("WeddingMenu", "");
				rawparams.put("WeddingTables", seat.getText().toString());
				rawparams.put("LoveWord", love1.getText().toString());
				rawparams.put("LoveWord2", love2.getText().toString());
				rawparams.put("LoveWord3", love3.getText().toString());
				rawparams.put("LoveWord4", love4.getText().toString());
				rawparams.put("HotelName", hotelname.getText().toString());
				String result;
				try {
					result = HttpUtil.postRequst(url, rawparams);

					Log.v("roboce", result);
					JSONObject json;
					json = new JSONObject(result);
					if (json.getString("Status").equals("0")) {
						String m = json.getString("Data");
						if (m.equals(id)) {
							writeToXml();
							getActivity().getSupportFragmentManager()
									.popBackStack();
						}
						Log.v("roboce", m);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
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
