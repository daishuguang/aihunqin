package com.aihunqin.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aihunqin.crazy.PopupMain;
import com.aihunqin.crazy.SelectPopupWindow;
import com.example.aihunqin.R;

public class FragmentSettingName extends Fragment {
	EditText bridegroom;
	EditText bride;
	SelectPopupWindow menuWindow;
	String uristr = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.fragment_settingname, container, false);
	}

	void readFromXML() {
		String str = "";
		DocumentBuilderFactory documentBuilderFactory;
		DocumentBuilder documentBuilder;
		Document document;
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			// xml文件在默认目录
			document = documentBuilder.parse(getActivity().openFileInput(
					"namesetting.xml"));
			Element root = document.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("bridename");
			Node nd = nodeList.item(0);
			str = nd.getFirstChild().getNodeValue();
			bride.setText(str);
			nodeList = root.getElementsByTagName("bridegroomname");
			nd = nodeList.item(0);
			str = nd.getFirstChild().getNodeValue();
			bridegroom.setText(str);
		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			document = null;
			documentBuilder = null;
			documentBuilderFactory = null;
		}
	}

	private OnClickListener itemsOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			Intent intent = null;
			switch (v.getId()) {
			case R.id.btn_take_photo:
				// 拍照我们用 Action为MediaStore.ACTION_IMAGE_CAPTURE,
				// 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
				break;
			case R.id.btn_pick_photo:
				// 选择照片的时候也一样，我们用Intent.ACTION_GET_CONTENT
				intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, 2);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case 1:

			break;
		case 2:
			Uri uri = data.getData();
			uristr = uri.toString();
			ContentResolver resolver = getActivity().getContentResolver();
			Bitmap bm = null;
			try {
				bm = MediaStore.Images.Media.getBitmap(resolver,
						Uri.parse(uristr));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			ImageView image = (ImageView) getView().findViewById(
					R.id.weddingpic);
			image.setImageBitmap(bm);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bride = (EditText) getView().findViewById(R.id.bridename);
		bridegroom = (EditText) getView().findViewById(R.id.bridegroomname);
		readFromXML();

		ImageView weddingpic = (ImageView) getView().findViewById(
				R.id.weddingpic);
		weddingpic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				menuWindow = new SelectPopupWindow(getActivity(), itemsOnClick);
				// 显示窗口
				menuWindow.showAtLocation(
						getActivity().findViewById(R.id.mainpop),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});
		((TextView) getView().findViewById(R.id.titleTv)).setText("照片和名字");
		((TextView) getView().findViewById(R.id.back))
				.setVisibility(View.VISIBLE);
		((TextView) getView().findViewById(R.id.back))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						FragmentManager fragmentManager = getActivity()
								.getSupportFragmentManager();

						FragmentTransaction fragmentTransaction = fragmentManager
								.beginTransaction();
						Fragment fragment_settingname = new FragmentMain();
						fragmentTransaction.replace(R.id.fragment_container,
								fragment_settingname);
						fragmentTransaction.commit();
					}
				});
		((TextView) getView().findViewById(R.id.rightmenu)).setText("保存");
		((TextView) getView().findViewById(R.id.rightmenu))
				.setVisibility(View.VISIBLE);

		((TextView) getView().findViewById(R.id.rightmenu))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String bridename = bride.getText().toString();
						String bridegroomname = bridegroom.getText().toString();

						String FILENAME = "namesetting.xml";

						try {
							XmlSerializer serializer = Xml.newSerializer();
							StringWriter writer = new StringWriter();
							serializer.setOutput(writer);
							serializer.startDocument("utf-8", true);
							serializer.startTag("", "user");
							serializer.startTag("", "bridename");
							serializer.text(bridename);
							serializer.endTag("", "bridename");

							serializer.startTag("", "bridegroomname");
							serializer.text(bridegroomname);
							serializer.endTag("", "bridegroomname");

							serializer.startTag("", "pic");
							serializer.text(uristr);
							serializer.endTag("", "pic");

							serializer.endTag("", "user");
							serializer.endDocument();
							OutputStream out = getActivity().openFileOutput(
									FILENAME, Context.MODE_PRIVATE);
							OutputStreamWriter outWriter = new OutputStreamWriter(
									out);
							outWriter.write(writer.toString());
							outWriter.close();
							out.close();
						} catch (FileNotFoundException e) {

							e.printStackTrace();
						} catch (IOException e) {

							e.printStackTrace();
						}

						FragmentManager fragmentmanager = getActivity()
								.getSupportFragmentManager();
						Fragment fragment = new FragmentMain();
						fragmentmanager.beginTransaction()
								.replace(R.id.fragment_container, fragment)
								.commit();
					}
				});
	}
}
