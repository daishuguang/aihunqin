package com.aihunqin.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

public class HttpUtil {
	// ����HttpClient����
	// public static HttpClient httpClient = new DefaultHttpClient();
	public static DefaultHttpClient httpClient = new DefaultHttpClient();

	public static final String BASE_URL = "http://www.ryapp.cn:3083";

	public static String getRequest(final String url) throws Exception {
		// ����HttpGet����.
		HttpGet get = new HttpGet(url);
		// ����GET����
		HttpResponse httpResponse = httpClient.execute(get);
		// ����������ɹ�������Ӧ
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// ��ȡ��������Ӧ�ַ���
			String result = EntityUtils.toString(httpResponse.getEntity(),
					"utf-8");
			return result;
		}
		return null;
	}

	public static String postRequst(final String url,
			final Map<String, String> rawParams) throws Exception {
		// ����HttpPost����
		HttpPost post = new HttpPost(url);
		// ������ݲ��������Ƚ϶�Ļ����ԶԴ��ݵĲ������з�װ
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawParams.keySet()) {
			// ��װ�������
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// �����������
		post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
		// ����POST����
		HttpResponse httpResponse = httpClient.execute(post);
		// ����������ɹ��طŻ���Ӧ
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// ��ȡ��������Ӧ�ַ���
			// if (edit != null) {
			// // List<Cookie> cookie = ((AbstractHttpClient) httpClient)
			// // .getCookieStore().getCookies();
			// List<Cookie> cookie = httpClient.getCookieStore().getCookies();
			// for (int i = 0; i < cookie.size(); i++) {
			// if (cookie.get(i).getName().equals(".ASPXAUTH")) {
			// Log.v("roboce", "login cookie: ----"
			// + cookie.get(i).getValue());
			// edit.putString("cookie", cookie.get(i).getValue());
			// edit.commit();
			// break;
			// }
			// }
			// }
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, "utf-8");
			return result;
		}
		return null;
	}
}