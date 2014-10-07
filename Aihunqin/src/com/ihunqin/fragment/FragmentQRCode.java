package com.ihunqin.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.ihunqin.crazy.WebActivity;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.example.aihunqin.R;

public class FragmentQRCode extends Fragment {
	TextView back, visitweb, saveqrcode, friend;
	String url, id;
	Bitmap bm;
	private static final String APP_ID = "wx7160a43122ae9274";

	private IWXAPI api;

	private void regToWx() {
		// ͨ��WXAPIFactory��������ȡIWXAPI��ʵ��
		api = WXAPIFactory.createWXAPI(getActivity(), APP_ID, true);

		// ��Ӧ�õ�appIdע�ᵽ΢��
		api.registerApp(APP_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		regToWx();
		return inflater.inflate(R.layout.fragment_qrcode, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		back = (TextView) getView().findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		try {
			id = getArguments().getString("id");
			url = "http://wedding.ihunqin.com/ecard/" + id;
			bm = create2DCode(url);
			((ImageView) getView().findViewById(R.id.qrimg)).setImageBitmap(bm);
		} catch (WriterException e) {

			e.printStackTrace();
		}
		TextView linkadd = (TextView) getView().findViewById(R.id.linkadd);
		linkadd.setText(url);
		visitweb = (TextView) getView().findViewById(R.id.visitweb);
		visitweb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("link", url);
				startActivity(intent);
			}
		});
		visitweb.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		saveqrcode = (TextView) getView().findViewById(R.id.saveqrcode);
		saveqrcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ProgressDialog pd = new ProgressDialog(getActivity());
				pd.setMessage("���ڱ���...");
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.show();
				ContentResolver cr = getActivity().getContentResolver();
				String uri = MediaStore.Images.Media.insertImage(cr, bm, "pic_"
						+ id, "");
				if (uri != null) {
					pd.dismiss();
					Toast.makeText(getActivity(), "�ѱ��浽���", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		saveqrcode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		friend = (TextView) getView().findViewById(R.id.friend);
		friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ʼ��һ��WXTextObject����
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = url;

				// WXTextObject
				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title = "�����μ��ҵĻ����";
				msg.description = "��������";
				Bitmap bwx = Bitmap.createScaledBitmap(bm, 150, 150, true);
				msg.setThumbImage(bwx);

				// ����һ��Req
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				req.transaction = String.valueOf(System.currentTimeMillis());
				req.message = msg;

				api.sendReq(req);
			}
		});
	}

	public Bitmap create2DCode(String str) throws WriterException {
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 400, 400);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// ��ά����תΪһά�������飬Ҳ����һֱ������
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				} else {
					pixels[y * width + x] = 0xffffffff;
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// ͨ��������������bitmap������ο�api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
}
