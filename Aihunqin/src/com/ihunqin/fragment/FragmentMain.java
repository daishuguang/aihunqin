package com.ihunqin.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihunqin.R;
import com.ihunqin.crazy.SinaMain;

public class FragmentMain extends Fragment {
	private TextView jindu;
	private ImageButton qindan;
	private ImageButton zuowei;
	LinearLayout settingwedding = null;
	OnClickListener settingnameListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			FragmentManager fragmentManager = getActivity()
					.getSupportFragmentManager();

			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			Fragment fragment_settingname = new FragmentSettingName();
			fragmentTransaction.replace(R.id.fragment_container,
					fragment_settingname);
			fragmentTransaction.commit();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// jindu
		jindu = (TextView) getView().findViewById(R.id.jindu);

		qindan = (ImageButton) getView().findViewById(R.id.qindan);

		zuowei = (ImageButton) getView().findViewById(R.id.zuowei);

		jindu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT)
						.show();
			}
		});

		qindan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT)
						.show();
			}
		});

		ImageButton xizhi = (ImageButton) getView().findViewById(R.id.xizhi);
		xizhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();

				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				Fragment fragment_invitation = new FragmentInvitation();
				fragmentTransaction.replace(R.id.fragment_container,
						fragment_invitation);
				fragmentTransaction.commit();
			}
		});

		ImageButton zuowei = (ImageButton) getView().findViewById(R.id.zuowei);
		zuowei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT)
						.show();

				//
				// FragmentManager fragmentManager = getActivity()
				// .getSupportFragmentManager();
				//
				// FragmentTransaction fragmentTransaction = fragmentManager
				// .beginTransaction();
				// Fragment fragment_settingname = new FragmentSettingName();
				// fragmentTransaction.replace(R.id.fragment_container,
				// fragment_settingname);
				// fragmentTransaction.commit();
			}
		});

		// Setting bridegroom name
		TextView bridegroom = (TextView) getView().findViewById(
				R.id.bridegroomname);
		bridegroom.setOnClickListener(settingnameListener);
		// Setting bride name
		TextView bride = (TextView) getView().findViewById(R.id.bridename);
		bride.setOnClickListener(settingnameListener);
		ImageView bannerhead = (ImageView) getView().findViewById(
				R.id.bannerhead);

		settingwedding = (LinearLayout) getView().findViewById(
				R.id.settingwedding);
		settingwedding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		/** Read data from xml */
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
			if (nd.getFirstChild() != null) {
				str = nd.getFirstChild().getNodeValue();
				bride.setText(str);
			}
			nodeList = root.getElementsByTagName("bridegroomname");

			nd = nodeList.item(0);
			if (nd.getFirstChild() != null) {
				str = nd.getFirstChild().getNodeValue();
				bridegroom.setText(str);
			}

			nodeList = root.getElementsByTagName("pic");
			nd = nodeList.item(0);
			Node k = nd.getFirstChild();

			if (k != null) {
				str = nd.getFirstChild().getNodeValue();
				if (str != "") {
					Uri uri = Uri.parse(str);
					ContentResolver contentresolver = getActivity()
							.getContentResolver();
					Bitmap bitmap = null;
					bitmap = MediaStore.Images.Media.getBitmap(contentresolver,
							uri);
					bannerhead.setImageBitmap(bitmap);
				}
			}

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
}
