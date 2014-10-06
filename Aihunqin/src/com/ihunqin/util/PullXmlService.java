package com.ihunqin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.content.pm.FeatureInfo;
import android.os.IInterface;
import android.util.Xml;
import android.view.Window;

import com.ihunqin.model.InvitationItem;

public class PullXmlService {
	// We don't use namespaces
	private static final String ns = null;
	public static InvitationItem currentItem;

	public static List<InvitationItem> readXml(InputStream is) {

		// try {
		// InputStreamReader reader = new InputStreamReader(is, "UTF-8");
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

		List<InvitationItem> items = null;
		XmlPullParser xmlPullParser = Xml.newPullParser();

		try {
			xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
					false);
			// 解析文件
			xmlPullParser.setInput(is, "UTF-8");
			int eventType = xmlPullParser.getEventType();

			int flagpic = 10;
			// 判断文件解析的是否完毕
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					items = new ArrayList<InvitationItem>();
					break;
				case XmlPullParser.START_TAG:
					String tagName = xmlPullParser.getName();
					if (tagName.length() > 11
							&& "invitation_".equals(tagName.substring(0, 11))) {
						flagpic = 10;
						// 创建item对象
						currentItem = new InvitationItem();
						currentItem.setItemid(tagName.substring(11));
					} else if ("pic_".equals(tagName.substring(0, 4))) {
						if (flagpic > Integer.parseInt(tagName.substring(4))) {
							xmlPullParser.next();
							currentItem.setImguri(xmlPullParser.getText());
							flagpic = Integer.parseInt(tagName.substring(4));
						}
					} else if ("weddingdate".equals(tagName)) {
						xmlPullParser.next();
						currentItem.setItemdate(xmlPullParser.getText());
					} else if ("bridegroomname".equals(tagName)) {
						xmlPullParser.next();
						currentItem.setIteminvitor(xmlPullParser.getText());
					} else if ("bridename".equals(tagName)) {
						xmlPullParser.next();
						currentItem.setItemdrink(xmlPullParser.getText());
					}

					break;
				case XmlPullParser.END_TAG:
					if (xmlPullParser.getName().length() > 11
							&& "invitation_".equals(xmlPullParser.getName()
									.substring(0, 11)) && currentItem != null) {
						// 把currentItem对象放到集合中去
						items.add(currentItem);
						currentItem = null;
					}
					break;
				}
				eventType = xmlPullParser.next();
			}
			is.close();
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return items;
	}
}
