package com.ihunqin.fragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihunqin.R;

public class FragmentZuowei extends Fragment {

	private TextView back;
	private TextView rightmenu;
	private RadioButton radio_zuowei;
	private RadioButton radio_caidan;
	private RadioGroup radio_zuoweiandcaidan;
	private TextView showzuowei;
	private LinearLayout showcaidan;
	private ImageButton addcaidan;
	private EditText caiming;
	private ListView listcontent;
	public MyAdapter adapter;
	public MyZuoAdapter zuoweiadapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_zuoandcai, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		adapter = new MyAdapter(getActivity());
		zuoweiadapter = new MyZuoAdapter(getActivity());

		listcontent = (ListView) getView().findViewById(R.id.listcontent);

		listcontent.setAdapter(zuoweiadapter);
		back = (TextView) getView().findViewById(R.id.back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//
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

		rightmenu = (TextView) getView().findViewById(R.id.rightmenu);
		rightmenu.setText("���Ӿ�ϯ");
		rightmenu.setVisibility(View.VISIBLE);

		radio_zuowei = (RadioButton) getView().findViewById(R.id.radio0);
		radio_caidan = (RadioButton) getView().findViewById(R.id.radio1);

		showzuowei = (TextView) getView().findViewById(R.id.showzuowei);
		showcaidan = (LinearLayout) getView().findViewById(R.id.showcaidan);

		caiming = (EditText) getView().findViewById(R.id.caiming);

		addcaidan = (ImageButton) getView().findViewById(R.id.addcaidan);
		addcaidan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String caimingstr = caiming.getText().toString();
				if (caimingstr.equals("")) {
					Toast.makeText(getActivity(), "��������Ϊ��", Toast.LENGTH_SHORT)
							.show();
				} else {
					adapter.arr.add(caimingstr);
					adapter.notifyDataSetChanged();
					caiming.setText("");
					String caistr = adapter.arr.toString();
					SavedToText(getActivity(), caistr, false);
				}
			}
		});

		radio_zuoweiandcaidan = (RadioGroup) getView().findViewById(
				R.id.radio_zuoweiandcaidan);
		radio_zuoweiandcaidan
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.radio0:
							rightmenu.setVisibility(View.VISIBLE);
							showzuowei.setVisibility(View.VISIBLE);
							showcaidan.setVisibility(View.GONE);
							listcontent.setAdapter(zuoweiadapter);
							break;
						case R.id.radio1:
							rightmenu.setVisibility(View.INVISIBLE);
							showzuowei.setVisibility(View.GONE);
							showcaidan.setVisibility(View.VISIBLE);
							listcontent.setAdapter(adapter);
							break;
						}
					}
				});

		String caidantxt = readFromFile(getActivity());
		if (!caidantxt.equals("-1")) {
			String caiitemwithcomma = caidantxt.substring(1,
					caidantxt.length() - 2);
			String[] caiitem = caiitemwithcomma.split(",");
			for (String i : caiitem) {
				adapter.arr.add(i);
			}
		}

	}

	public boolean isExternalStorageAvailable() {
		boolean state = false;
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
			state = true;
		}
		return state;
	}

	public boolean isExternalStorageReadOnly() {
		boolean state = false;
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
			state = true;
		}
		return state;
	}

	public void writeToExternalStoragePublic(String filename, byte[] content) {
		String packagename = getActivity().getPackageName();
		String path = "/Android/data/" + packagename + "/files/";

		if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
			File file = new File(path, filename);
			file.mkdirs();
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
				fos.write(content);
				fos.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public byte[] readExternalStoragePublic(String filename) {
		int len = 1024;
		byte[] buffer = new byte[len];
		String packageName = getActivity().getPackageName();

		String path = "/Android/data/" + packageName + "/files/";

		if (!isExternalStorageReadOnly()) {
			try {
				File file = new File(path, filename);
				FileInputStream fis = new FileInputStream(file);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int nrb = fis.read(buffer, 0, len);
				while (nrb != -1) {
					baos.write(buffer, 0, nrb);
					nrb = fis.read(buffer, 0, len);
				}
				buffer = baos.toByteArray();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return buffer;
	}

	private String readFromFile(Context context) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Environment.getExternalStorageDirectory()
					.getPath() + "/ihunqin";
			File folder = new File(foldername);

			if (folder == null || folder.exists()) {
				folder.mkdir();
			}
			String fileName = "/caidan.txt";
			File targetFile = new File(foldername + fileName);
			String readedStr = "";
			try {
				if (!targetFile.exists()) {
					targetFile.createNewFile();
					return "-1";
				} else {
					InputStream in = new BufferedInputStream(
							new FileInputStream(targetFile));
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, "utf-8"));
					String tmp;

					while ((tmp = br.readLine()) != null) {
						readedStr += tmp;
					}
					br.close();
					in.close();
					return readedStr;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "-1";
			}
		} else {
			Toast.makeText(context, "δ����SD��!", Toast.LENGTH_SHORT).show();
			return "-1";
		}
	}

	private void SavedToText(Context context, String stringToWrite,
			boolean append) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Environment.getExternalStorageDirectory()
					.getPath() + "/ihunqin/";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			String fileName = "/caidan.txt";
			File targetFile = new File(foldername + fileName);
			OutputStreamWriter osw;
			try {
				if (!targetFile.exists()) {
					targetFile.createNewFile();
					osw = new OutputStreamWriter(new FileOutputStream(
							targetFile), "utf-8");
					osw.write(stringToWrite);
					osw.close();
				} else {
					osw = new OutputStreamWriter(new FileOutputStream(
							targetFile, append), "utf-8");
					osw.write(stringToWrite);
					osw.flush();
					osw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(context, "δ����SD��!", Toast.LENGTH_SHORT).show();
		}
	}

	private class MyZuoAdapter extends BaseAdapter {
		private Context context;
		private EditText zuoweianpai;
		private ImageButton del;
		private TextView xuhao;
		public ArrayList<String> arr;

		public MyZuoAdapter(Context context) {
			this.context = context;
			arr = new ArrayList<String>();
		}

		@Override
		public int getCount() {

			return arr.size();
		}

		@Override
		public Object getItem(int position) {

			return arr.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_zuowei, null);
			}
			del = (ImageButton) convertView.findViewById(R.id.delzuoxi);
			xuhao = (TextView) convertView.findViewById(R.id.xuhao);
			zuoweianpai = (EditText) convertView.findViewById(R.id.zuoweianpai);
			xuhao.setText("��" + (position + 1) + "����");

			del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					arr.remove(position);
					zuoweiadapter.notifyDataSetChanged();
					// String caistr = arr.toString();
					// SavedToText(getActivity(), caistr, false);
				}
			});
			return convertView;
		}

	}

	private class MyAdapter extends BaseAdapter {
		private Context context;
		TextView caidanming;
		ImageButton del;
		TextView xuhao;
		public ArrayList<String> arr;

		public MyAdapter(Context context) {
			this.context = context;
			arr = new ArrayList<String>();
		}

		@Override
		public int getCount() {

			return arr.size();
		}

		@Override
		public Object getItem(int position) {

			return arr.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_caidan, null);
			}
			caidanming = (TextView) convertView.findViewById(R.id.caidanitem);
			caidanming.setText(arr.get(position));
			xuhao = (TextView) convertView.findViewById(R.id.xuhao);
			xuhao.setText((position + 1) + "");
			del = (ImageButton) convertView.findViewById(R.id.delimage);
			del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					arr.remove(position);
					adapter.notifyDataSetChanged();
					String caistr = arr.toString();
					SavedToText(getActivity(), caistr, false);
				}
			});

			return convertView;
		}
	}
}