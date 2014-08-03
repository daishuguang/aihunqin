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
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aihunqin.crazy.WebActivity;
import com.aihunqin.util.HttpUtil;
import com.example.aihunqin.R;

public class FragmentInvitation extends Fragment {
	TextView invitationid;
	TransferIDListener mCallback;
	/** ����ͼƬ */
	String FILENAME = "invitationinfo.xml";

	File file = null;

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

		Button button2 = (Button) getView().findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked("101", "createnew");
			}
		});

		Button button3 = (Button) getView().findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked("160", "createnew");
			}
		});
		TextView textView = (TextView) getView().findViewById(R.id.titleTv);
		textView.setText("��������");
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
						getActivity()
								.getSupportFragmentManager()
								.beginTransaction()
								.replace(R.id.fragment_container,
										new FragmentInvitationCreateNew())
								.addToBackStack(null).commit();
					}
				}).start();

			}
		});

		// writeToXml("101");
		// writeToXml("160");

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
				// ͨ��ʵ������DocumentBuilder
				db = dbf.newDocumentBuilder();
				// ����Document �����������ļ�
				doc = db.parse(file);
				Element root = doc.getDocumentElement();

				root.getElementsByTagName("invitations").getLength();
				Element root2 = doc.createElement("invitation_" + returnid);
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

	// xml��������
	String WriteToString() {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("utf-8", true);
			serializer.startTag("", "invitations");
			serializer.startTag("", "invitation");

			serializer.startTag("", "username");
			serializer.text("roboce");
			serializer.endTag("", "username");

			serializer.startTag("", "useremail");
			serializer.text("daishuguang@126.com");
			serializer.endTag("", "usermail");

			serializer.endTag("", "invitation");
			serializer.endTag("", "invitations");
			serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	void saveToXML() {
		// �ĵ�����������
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// ʵ�����ĵ�������
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			File f = new File("test.xml");
			if (!f.exists()) {
				f.createNewFile();

				// ����һ���ĵ�
				Document document = builder.newDocument();

				// �������ڵ�
				Element configs = document.createElement("configs");
				// ����XML�ļ�����ĸ��ֶ������л�(Ԫ��)
				Element config = document.createElement("config");// ����Ԫ�ؽڵ�

				Element ip = document.createElement("ip");
				Element socket = document.createElement("socket");

				Text ip_text = document.createTextNode("255.255.0.1");// ����text�ı�
				Text socket_text = document.createTextNode("8888");

				ip.appendChild(ip_text);
				socket.appendChild(socket_text);

				config.appendChild(ip);
				config.appendChild(socket);

				configs.appendChild(config);// ��ӵ��ĵ���
				// ���÷��������ĵ�д��xml�ļ���

			} else {
				// �����ĵ�
				Document document = builder.parse(f);
				Element configs = document.getDocumentElement();// �õ����ڵ㣬�Ѻ��洴�����ӽڵ����������ڵ���

			}
		} catch (Exception e) {

		}
	}

	void deletefile() {

		getActivity().deleteFile("users.xml");
		String[] filelist = getActivity().fileList();
		String str = null;
		for (int i = 0; i < filelist.length; i++) {
			str += filelist[i] + "\n";
		}
		invitationid.setText(str);
	}
}
