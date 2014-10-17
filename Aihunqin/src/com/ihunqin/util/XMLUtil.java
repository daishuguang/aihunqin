package com.ihunqin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class XMLUtil {
	private static String deleteNumber;
	private static String updateNumber;

	// 读取传入的路径，返回一个document对象
	public static Document loadInit(String filePath) {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder;

			builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filePath));
			document.normalize();
			return document;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	public static boolean deleteXML(String filePath) {
		deleteNumber = "421f481e-790c-41be-91e3-27d215b73ce2";
		Document document = loadInit(filePath);
		try {
			NodeList nodeList = document.getElementsByTagName("color");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	private String readFromFile(Context context, String filestr) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Environment.getExternalStorageDirectory()
					.getPath() + "/ihunqin";
			File folder = new File(foldername);

			if (folder == null || folder.exists()) {
				folder.mkdir();
			}
			String fileName = "/" + filestr;
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
			Toast.makeText(context, "未发现SD卡!", Toast.LENGTH_SHORT).show();
			return "-1";
		}
	}

	private void SavedToText(Context context, String stringToWrite,
			boolean append, String filestr) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Environment.getExternalStorageDirectory()
					.getPath() + "/ihunqin/";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			String fileName = "/" + filestr;
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
			Toast.makeText(context, "未发现SD卡!", Toast.LENGTH_SHORT).show();
		}
	}
}
