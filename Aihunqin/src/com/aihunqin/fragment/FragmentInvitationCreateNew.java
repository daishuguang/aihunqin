package com.aihunqin.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aihunqin.crazy.SelectPopupWindow;
import com.aihunqin.crazy.WebActivity;
import com.aihunqin.fragment.FragmentInvitation.TransferIDListener;
import com.aihunqin.util.FtpUtil;
import com.example.aihunqin.R;

public class FragmentInvitationCreateNew extends Fragment {
	String id = "";
	TransferIDListener mCallback;
	ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9;
	SelectPopupWindow menuWindow;
	Uri fileUri;
	static final int MEDIA_TYPE_IMAGE = 1;
	static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	static int viewid;
	int current = 0;
	static String img_path = null;
	static int picid = 0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (TransferIDListener) activity;
			Log.v("roboce", "onattach");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Create a file Uri for saving an image or video */
	private Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */

	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"Aihunqin/pics/");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}
		// Create a media file name

		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			switch (viewid) {
			case R.id.img1:
				picid = 1;
				break;
			case R.id.img2:
				picid = 2;
				break;
			case R.id.img3:
				picid = 3;
				break;
			case R.id.img4:
				picid = 4;
				break;
			case R.id.img5:
				picid = 5;
				break;
			case R.id.img6:
				picid = 6;
				break;
			case R.id.img7:
				picid = 7;
				break;
			case R.id.img8:
				picid = 8;
				break;
			case R.id.img9:
				picid = 9;
				break;
			}
			img_path = mediaStorageDir.getPath() + File.separator + picid
					+ ".jpg";
			mediaFile = new File(img_path);
		} else {
			return null;
		}
		return mediaFile;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_invitation_createnew,
				container, false);
	}

	String[] items = new String[] { "拍照", "从相册选择" };

	OnClickListener clickListiener = new OnClickListener() {

		@Override
		public void onClick(final View v) {
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
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					viewid = v.getId();
					Intent intent = null;
					switch (which) {
					case 0:
						// 拍照我们用 Action为MediaStore.ACTION_IMAGE_CAPTURE,
						// 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
						intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

						fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
						startActivityForResult(intent,
								CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
						break;
					case 1:
						intent = new Intent();
						intent.setAction(Intent.ACTION_PICK);
						intent.setType("image/*");
						intent.putExtra("view", viewid);
						startActivityForResult(intent, 2);
						break;
					}
				}
			}).create().show();

		}
	};

	void loadImg() {
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
				for (int i = 1; i < 10; i++) {
					if (root2.getElementsByTagName("pic_" + i).item(0) != null) {
						String uri = root2.getElementsByTagName("pic_" + i)
								.item(0).getFirstChild().getNodeValue();
						int imgid = 0;
						switch (i) {
						case 1:
							imgid = R.id.img1;
							break;
						case 2:
							imgid = R.id.img2;
							break;
						case 3:
							imgid = R.id.img3;
							break;
						case 4:
							imgid = R.id.img4;
							break;
						case 5:
							imgid = R.id.img5;
							break;
						case 6:
							imgid = R.id.img6;
							break;
						case 7:
							imgid = R.id.img7;
							break;
						case 8:
							imgid = R.id.img8;
							break;
						case 9:
							imgid = R.id.img9;
							break;
						}
						((ImageView) getView().findViewById(imgid))
								.setImageURI(Uri.parse(uri));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
			// ((ImageView)
			// getView().findViewById(viewid)).setImageURI(fileUri);
			break;
		case 2:
			fileUri = data.getData();
			// ((ImageView)
			// getView().findViewById(viewid)).setImageURI(fileUri);

			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor actualimagecursor = getActivity().getContentResolver()
					.query(fileUri, projection, null, null, null);
			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			if (actualimagecursor.moveToFirst()) {
				img_path = actualimagecursor
						.getString(actual_image_column_index);
			}
			actualimagecursor.close();
			// Bitmap bitmap = null;
			// try {
			// bitmap = BitmapFactory.decodeStream(new
			// FileInputStream(img_path));
			// } catch (FileNotFoundException e) {
			//
			// e.printStackTrace();
			// }
			// ((ImageView)
			// getView().findViewById(viewid)).setImageBitmap(bitmap);

			break;
		}
		((ImageView) getView().findViewById(viewid)).setImageURI(fileUri);
		switch (viewid) {
		case R.id.img1:
			current = 1;
			break;
		case R.id.img2:
			current = 2;
			break;
		case R.id.img3:
			current = 3;
			break;
		case R.id.img4:
			current = 4;
			break;
		case R.id.img5:
			current = 5;
			break;
		case R.id.img6:
			current = 6;
			break;
		case R.id.img7:
			current = 7;
			break;
		case R.id.img8:
			current = 8;
			break;
		case R.id.img9:
			current = 9;
			break;
		}
		new uploadThread().start();

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

				if (root2.getElementsByTagName("pic_" + current).item(0) != null) {
					String uri = root2.getElementsByTagName("pic_" + current)
							.item(0).getFirstChild().getNodeValue();

					root2.getElementsByTagName("pic_" + current).item(0)
							.getFirstChild().setNodeValue(fileUri.toString());
				} else {
					Element ele = doc.createElement("pic_" + current);
					Text eletext = doc.createTextNode(fileUri.toString());
					ele.appendChild(eletext);
					root2.appendChild(ele);
				}
				TransformerFactory tfs = TransformerFactory.newInstance();
				Transformer tf = tfs.newTransformer();
				tf.transform(new DOMSource(doc), new StreamResult(
						new FileOutputStream(file)));
				// 添加一个元素
				// Element eModel = doc.createElement("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			try {
				XmlSerializer serializer = Xml.newSerializer();
				StringWriter writer = new StringWriter();
				serializer.setOutput(writer);
				serializer.startDocument("utf-8", true);
				serializer.startTag("", "invitations");
				serializer.startTag("", "invitation_" + id);

				serializer.startTag("", "pic_" + current);
				serializer.text(fileUri.toString());
				serializer.endTag("", "pic_" + current);

				serializer.endTag("", "invitation_" + id);
				serializer.endTag("", "invitations");
				serializer.endDocument();
				OutputStream out = getActivity().openFileOutput(FILENAME,
						Context.MODE_PRIVATE);
				OutputStreamWriter outWriter = new OutputStreamWriter(out);
				outWriter.write(writer.toString());
				outWriter.close();
				out.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	};

	/***
	 * 上传图片到FTP
	 * 
	 * @author Alex
	 * 
	 */
	class uploadThread extends Thread {
		@Override
		public void run() {

			String returnmsg = FtpUtil.ftpUpload("www.ihunqin.com", "3083",
					"tanqci", "tanqci123", null, img_path, id + "-"
							+ current + ".jpg");
			Log.v("roboce", returnmsg);
		}
	}

	/***
	 * 
	 * 第一步要执行的Callback
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		id = getArguments().getString("id");
		Log.v("roboce", "ID:" + id);
		TextView back = (TextView) getView().findViewById(R.id.back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.fragment_container,
								new FragmentInvitation()).commit();
			}
		});
		TextView title = (TextView) getView().findViewById(R.id.titleTv);
		title.setText("请帖管理");
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});

		// 底部菜单
		Button editcontent = (Button) getView().findViewById(R.id.editcontent);
		editcontent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked(id, "content");
			}
		});
		Button preview = (Button) getView().findViewById(R.id.preview);
		preview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", "http://www.ruiqinsoft.com:3083/wh/t/"
						+ id);
				startActivity(intent);
			}
		});

		Button gest = (Button) getView().findViewById(R.id.gest);
		gest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link",
						"http://www.ruiqinsoft.com:3083/guest/v/" + id);
				startActivity(intent);
			}
		});

		Button blessing = (Button) getView().findViewById(R.id.blessing);
		blessing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link",
						"http://www.ruiqinsoft.com:3083/follower/v/" + id);
				startActivity(intent);
			}
		});

		Button qrcode = (Button) getView().findViewById(R.id.qrcode);
		qrcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallback.onItemClicked(id, "qrcode");
			}
		});
		img1 = (ImageView) getView().findViewById(R.id.img1);
		img2 = (ImageView) getView().findViewById(R.id.img2);
		img3 = (ImageView) getView().findViewById(R.id.img3);
		img4 = (ImageView) getView().findViewById(R.id.img4);
		img5 = (ImageView) getView().findViewById(R.id.img5);
		img6 = (ImageView) getView().findViewById(R.id.img6);
		img7 = (ImageView) getView().findViewById(R.id.img7);
		img8 = (ImageView) getView().findViewById(R.id.img8);
		img9 = (ImageView) getView().findViewById(R.id.img9);

		img1.setOnClickListener(clickListiener);
		img2.setOnClickListener(clickListiener);
		img3.setOnClickListener(clickListiener);
		img4.setOnClickListener(clickListiener);
		img5.setOnClickListener(clickListiener);
		img6.setOnClickListener(clickListiener);
		img7.setOnClickListener(clickListiener);
		img8.setOnClickListener(clickListiener);
		img9.setOnClickListener(clickListiener);
		loadImg();
	}
}
