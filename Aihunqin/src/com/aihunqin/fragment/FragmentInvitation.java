package com.aihunqin.fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aihunqin.crazy.WebActivity;
import com.aihunqin.model.InvitationItem;
import com.aihunqin.util.HttpUtil;
import com.aihunqin.util.PullXmlService;
import com.example.aihunqin.R;

public class FragmentInvitation extends Fragment {
	TextView invitationid;
	ListView invitationlist;
	TransferIDListener mCallback;
	/** 保存图片 */
	String FILENAME = "invitationinfo.xml";

	File file = null;
	/** data */
	List<InvitationItem> invitationDatas = null;

	public interface TransferIDListener {
		public void onItemClicked(String id, String fragment);
	}

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
		return inflater.inflate(R.layout.fragment_invitation, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		invitationlist = (ListView) getView().findViewById(R.id.invitationlist);
		TextView textView = (TextView) getView().findViewById(R.id.titleTv);
		textView.setText("请帖管理");
		TextView backbtn = (TextView) getView().findViewById(R.id.back);
		backbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, new FragmentMain())
						.commit();
			}
		});
		backbtn.setVisibility(View.VISIBLE);
		RelativeLayout layout = (RelativeLayout) getView().findViewById(
				R.id.demolink);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", "http://ruiqinsoft.com:3083/wh/t/66");
				startActivity(intent);
			}
		});

		// Create Invitation
		TextView createInvitationbtn = (TextView) getView().findViewById(
				R.id.rightmenu);
		createInvitationbtn.setVisibility(View.VISIBLE);
		createInvitationbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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

				// register or login
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						String url = "http://www.ruiqinsoft.com:3083/wh/c";
						Map<String, String> rawparams = new HashMap<String, String>();
						rawparams.put("KehuName", "wenhong");
						rawparams.put("BackGroundImageUrl", "beijing");
						rawparams.put("YouKuVideoId", "youkuid");
						rawparams.put("XinlangName", "xl");
						rawparams.put("XinlangMobile", "1333");
						rawparams.put("XinniangName", "xn");
						rawparams.put("XinniangMobile", "133333");
						rawparams.put("WeddingDateNongli", "nongliriqi");
						rawparams.put("WeddingDateTime", "hunlishij");
						rawparams.put("textFieldWeddingTime", "jidianshijian");
						rawparams.put("WeddingLoacation", "123");
						rawparams.put("WeddingMenu", "hunlicaidan");
						rawparams.put("WeddingTables", "hunzuo");
						rawparams.put("LoveWord", "12");
						rawparams.put("LoveWord2", "12");
						rawparams.put("LoveWord3", "12");
						rawparams.put("LoveWord4", "12");
						rawparams.put("HotelName", "jiudianmingzi");
						String result;
						try {
							result = HttpUtil.postRequst(url, rawparams);

							Log.v("roboce", result);
							JSONObject json;
							json = new JSONObject(result);
							String m = json.getString("id");
							writeToXml(m);
							// Save to XML
							mCallback.onItemClicked(m, "createnew");
							Log.v("roboce", m);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();

			}
		});

		// writeToXml("101");
		// writeToXml("160");
		// try {
		// FileInputStream fs = getActivity().openFileInput(FILENAME);
		// try {
		// InputStreamReader reader = new InputStreamReader(fs, "UTF-8");
		// BufferedReader in = new BufferedReader(reader);
		// StringBuffer buffer = new StringBuffer();
		//
		// buffer.append(in.readLine());
		// buffer.toString();
		// } catch (UnsupportedEncodingException e) {
		//
		// e.printStackTrace();
		// } catch (IOException e) {
		//
		// e.printStackTrace();
		// }
		//
		// } catch (FileNotFoundException e1) {
		//
		// e1.printStackTrace();
		// }

		try {
			invitationDatas = PullXmlService.readXml(getActivity()
					.openFileInput(FILENAME));
			InvitationAdapter adapter = new InvitationAdapter();
			invitationlist.setAdapter(adapter);

			invitationlist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mCallback.onItemClicked(invitationDatas.get(position)
							.getItemid(), "createnew");
				}
			});
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	void writeToXml(String returnid) {

		file = new File(getActivity().getFilesDir().getPath() + File.separator
				+ FILENAME);
		try {
			FileInputStream fis = getActivity().openFileInput(FILENAME);

			fis.close();
			DocumentBuilderFactory dbf = null;
			DocumentBuilder db = null;
			Document doc = null;
			try {
				dbf = DocumentBuilderFactory.newInstance();
				// 通过实例构建DocumentBuilder
				db = dbf.newDocumentBuilder();
				// 创建Document 解析给定的文件
				doc = db.parse(file);
				Element root = doc.getDocumentElement();

				root.getElementsByTagName("invitations").getLength();
				Element root2 = doc.createElement("invitation_" + returnid);
				root2.setTextContent("");
				root.appendChild(root2);

				TransformerFactory tfs = TransformerFactory.newInstance();
				Transformer tf = tfs.newTransformer();
				tf.transform(new DOMSource(doc), new StreamResult(
						new FileOutputStream(file)));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				doc = null;
				db = null;
				dbf = null;
			}
		} catch (FileNotFoundException e1) {
			try {
				XmlSerializer serializer = Xml.newSerializer();
				StringWriter writer = new StringWriter();
				serializer.setOutput(writer);
				serializer.startDocument("utf-8", true);
				serializer.startTag("", "invitations");

				serializer.startTag("", "invitation_" + returnid);
				serializer.text("");
				serializer.endTag("", "invitation_" + returnid);

				serializer.endTag("", "invitations");
				serializer.endDocument();
				OutputStream out = getActivity().openFileOutput(FILENAME,
						Context.MODE_PRIVATE);
				getActivity().getFileStreamPath(FILENAME).exists();

				OutputStreamWriter outWriter = new OutputStreamWriter(out);
				outWriter.write(writer.toString());
				writer.close();
				outWriter.close();
				out.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}

	class InvitationAdapter extends BaseAdapter {
		TextView listid, listdate, listtitle, listinvitor, listdrink;
		ImageView listimg;

		@Override
		public int getCount() {
			return invitationDatas.size();
		}

		@Override
		public Object getItem(int position) {

			return null;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.simple_invitation_item, null);

				listid = (TextView) convertView.findViewById(R.id.listid);
				listid.setText("第" + position + "张");
				listimg = (ImageView) convertView.findViewById(R.id.listimg);
				if (invitationDatas.get(position).getImguri() != null) {
					listimg.setImageURI(Uri.parse(invitationDatas.get(position)
							.getImguri()));
				}
				listdate = (TextView) convertView.findViewById(R.id.listdate);
				listdate.setText(invitationDatas.get(position).getItemdate());
				listtitle = (TextView) convertView.findViewById(R.id.listtitle);
				listtitle.setText("婚贴标题");
				listinvitor = (TextView) convertView
						.findViewById(R.id.listinvitor);
				listinvitor.setText(invitationDatas.get(position)
						.getIteminvitor());
				listdrink = (TextView) convertView.findViewById(R.id.listdrink);
				listdrink.setText(invitationDatas.get(position).getItemdrink());
			}
			return convertView;
		}
	}
}
