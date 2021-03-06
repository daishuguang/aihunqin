package com.ihunqin.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihunqin.R;
import com.ihunqin.crazy.WebActivity;
import com.ihunqin.model.InvitationItem;
import com.ihunqin.util.HttpUtil;
import com.ihunqin.util.PullXmlService;
import com.ihunqin.util.XMLUtil;

public class FragmentInvitation extends Fragment {
	TextView invitationid;
	ListView invitationlist;
	TransferIDListener mCallback;
	/** 保存图片 */
	String FILENAME = "invitationinfo.xml";

	File file = null;
	/** data */
	List<InvitationItem> invitationDatas = null;
	InvitationAdapter adapter = null;
	int pos;

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
				try {
					if ((new File(FILENAME)).exists()) {
						invitationDatas = PullXmlService.readXml(getActivity()
								.openFileInput(FILENAME));
					} else {
						invitationDatas = null;
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				if (invitationDatas == null || invitationDatas.size() < 3) {

					// register or login

					new Thread(new Runnable() {

						@Override
						public void run() {
							// String url =
							// "http://www.ruiqinsoft.com:3083/wh/c";
							String url = "http://wedding.ihunqin.com/api/ecard";
							Map<String, String> rawparams = new HashMap<String, String>();
							rawparams.put(
									"UserID",
									getActivity().getSharedPreferences(
											"userinfo", Activity.MODE_PRIVATE)
											.getString("userid", ""));
							rawparams.put("StoreID", "0");
							rawparams.put("KehuName", "wenhong");
							rawparams.put("BackGroundImageUrl", "beijing");
							rawparams.put("YouKuVideoId", "youkuid");
							rawparams.put("XinlangName", "xl");
							rawparams.put("XinlangMobile", "1333");
							rawparams.put("XinniangName", "xn");
							rawparams.put("XinniangMobile", "133333");
							rawparams.put("WeddingDateNongli", "nongliriqi");
							rawparams.put("WeddingDateTime",
									(new SimpleDateFormat("yyyy-MM-dd"))
											.format(new Date()));
							rawparams.put("textFieldWeddingTime",
									"jidianshijian");
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
								if (json.getString("Status").equals("0")) {
									String m = json.getString("Data");
									writeToXml(m);
									// Save to XML
									mCallback.onItemClicked(m, "createnew");
									Log.v("roboce", m);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				} else {
					Toast.makeText(getActivity(), "最多创建3张", Toast.LENGTH_SHORT)
							.show();
				}

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
			adapter = new InvitationAdapter();
			invitationlist.setAdapter(adapter);

			invitationlist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mCallback.onItemClicked(invitationDatas.get(position)
							.getItemid(), "createnew");
				}
			});
			invitationlist
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							pos = position;
							AlertDialog.Builder builder = new Builder(
									getActivity());
							builder.setMessage("确定要删除?")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO
													deleteToXml(pos);
													invitationDatas.remove(pos);
													adapter.notifyDataSetChanged();

												}
											}).setNegativeButton("取消", null)
									.create().show();
							return true;
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

	void deleteToXml(int position) {
		String id = invitationDatas.get(position).getItemid();
		DocumentBuilderFactory facotry = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = facotry.newDocumentBuilder();
			Document document = builder.parse(new File(getActivity()
					.getFilesDir().getPath() + File.separator + FILENAME));
			document.normalize();
			NodeList nodeList = document.getElementsByTagName("invitation_"
					+ id);

			Node node = nodeList.item(0);
			node.getParentNode().removeChild(node);
			XMLUtil.filepath = getActivity().getFilesDir().getPath()
					+ File.separator + FILENAME;
			XMLUtil.saveXML(document);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class InvitationAdapter extends BaseAdapter {
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
			ViewHolder viewholder = null;
			if (convertView == null) {
				viewholder = new ViewHolder();
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.simple_invitation_item, null);

				viewholder.listid = (TextView) convertView
						.findViewById(R.id.listid);
				viewholder.listimg = (ImageView) convertView
						.findViewById(R.id.listimg);
				viewholder.listdate = (TextView) convertView
						.findViewById(R.id.listdate);
				viewholder.listtitle = (TextView) convertView
						.findViewById(R.id.listtitle);
				viewholder.listinvitor = (TextView) convertView
						.findViewById(R.id.listinvitor);
				viewholder.listdrink = (TextView) convertView
						.findViewById(R.id.listdrink);
				convertView.setTag(viewholder);

			} else {
				viewholder = (ViewHolder) convertView.getTag();
			}
			viewholder.listid.setText("第" + position + "张");
			if (invitationDatas.get(position).getImguri() != null) {
				viewholder.listimg.setImageURI(Uri.parse(invitationDatas.get(
						position).getImguri()));
			}
			viewholder.listdate.setText(invitationDatas.get(position)
					.getItemdate());
			viewholder.listtitle.setText("婚贴标题");
			viewholder.listinvitor.setText(invitationDatas.get(position)
					.getIteminvitor());
			viewholder.listdrink.setText(invitationDatas.get(position)
					.getItemdrink());

			return convertView;
		}
	}

	private static class ViewHolder {
		TextView listid;
		TextView listdate;
		TextView listtitle;
		TextView listinvitor;
		TextView listdrink;
		ImageView listimg;
	}
}
