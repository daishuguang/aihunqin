package com.ihunqin.fragment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihunqin.R;

public class FragmentMain extends Fragment {
	private TextView jindu;
	private ImageButton qindan;
	private ImageButton zuowei;
	LinearLayout settingwedding = null;
	TextView weddingdate;
	ImageView shangjialogo;
	SharedPreferences preferences;
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
		preferences = getActivity().getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		shangjialogo = (ImageView) getView().findViewById(R.id.shangjialogo);
		try {
			FileInputStream fis = getActivity().openFileInput("logourl.png");
			Bitmap bmp = BitmapFactory.decodeStream(fis);
			shangjialogo.setImageBitmap(bmp);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		weddingdate = (TextView) getView().findViewById(R.id.weddingdate);
		try {
			if (!preferences.getString("setweddingdate", "").equals("")) {
				Calendar c = Calendar.getInstance();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				long diff;
				try {
					diff = Long.parseLong(preferences.getString(
							"setweddingdate", ""))
							- format.parse(
									c.get(Calendar.YEAR) + "-"
											+ (c.get(Calendar.MONTH) + 1) + "-"
											+ c.get(Calendar.DAY_OF_MONTH))
									.getTime();
					long days = diff / (24 * 60 * 60 * 1000);
					weddingdate.setText(days + "");
				} catch (ParseException e1) {

					e1.printStackTrace();
				}
			}

		} catch (Exception e) {
			Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

		// jindu
		jindu = (TextView) getView().findViewById(R.id.jindu);

		jindu.setText(preferences.getString("jindu", "0%"));
		qindan = (ImageButton) getView().findViewById(R.id.qindan);

		zuowei = (ImageButton) getView().findViewById(R.id.zuowei);

		jindu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();

				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				Fragment fragment_invitation = new FragmentWeddingList();
				fragmentTransaction.replace(R.id.fragment_container,
						fragment_invitation);
				fragmentTransaction.commit();
			}
		});

		qindan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();

				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				Fragment fragment_invitation = new FragmentJiZhang();
				fragmentTransaction.replace(R.id.fragment_container,
						fragment_invitation);
				fragmentTransaction.commit();
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

				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();

				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				Fragment fragment_zuowei = new FragmentZuowei();
				fragmentTransaction.replace(R.id.fragment_container,
						fragment_zuowei);
				fragmentTransaction.commit();
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
				final Calendar c = Calendar.getInstance();
				new DatePickerDialog(getActivity(), new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date date1 = null, date2 = null;
						try {
							date1 = format.parse(year + "-" + (monthOfYear + 1)
									+ "-" + dayOfMonth);
							date2 = format.parse(c.get(Calendar.YEAR) + "-"
									+ (1 + c.get(Calendar.MONTH)) + "-"
									+ c.get(Calendar.DAY_OF_MONTH));

						} catch (ParseException e) {

							e.printStackTrace();
						}
						long diff = date1.getTime() - date2.getTime();
						long days = diff / (24 * 60 * 60 * 1000);
						weddingdate.setText(days + "");
						preferences
								.edit()
								.putString("setweddingdate",
										date1.getTime() + "").commit();

					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
						.get(Calendar.DAY_OF_MONTH)).show();
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
