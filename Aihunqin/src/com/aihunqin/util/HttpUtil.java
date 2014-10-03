package com.aihunqin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	// 创建HttpClient对象
	// public static HttpClient httpClient = new DefaultHttpClient();
	public static DefaultHttpClient httpClient = new DefaultHttpClient();

	// public static final String BASE_URL = "http://www.ryapp.cn:3083";
	public static final String BASE_URL = "http://home.ihunqin.com";

	public static String getRequest(final String url) throws Exception {
		// 创建HttpGet对象.
		HttpGet get = new HttpGet(url);
		// 发送GET请求
		HttpResponse httpResponse = httpClient.execute(get);
		// 如果服务器成功返回响应
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 获取服务器响应字符串
			String result = EntityUtils.toString(httpResponse.getEntity(),
					"utf-8");
			return result;
		}
		return null;
	}

	public static String postRequst(final String url,
			final Map<String, String> rawParams) throws Exception {
		// 创建HttpPost对象
		HttpPost post = new HttpPost(url);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawParams.keySet()) {
			// 封装请求参数
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// 设置请求参数
		post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
		// 发送POST请求
		HttpResponse httpResponse = httpClient.execute(post);
		// 如果服务器成功地放回响应
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 获取服务器响应字符串
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