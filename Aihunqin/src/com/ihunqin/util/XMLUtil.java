package com.ihunqin.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.ihunqin.model.LiJin;

public class XMLUtil {
	private static String deleteNumber;
	private static String updateNumber;
	private static String filePath;

	// 读取传入的路径，返回一个document对象
	public static Document loadInit(String fileName) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Environment.getExternalStorageDirectory()
					.getPath() + "/ihunqin/";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			filePath = "/" + fileName;
			File file = new File(filePath);

			Document document = null;
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder;

				builder = factory.newDocumentBuilder();
				if (!file.exists()) {
					try {
						file.createNewFile();
						document = builder.newDocument();
						Element root = document.createElement("lijin");
						document.appendChild(root);

						Element income = document.createElement("income");
						root.appendChild(income);

						Element outcome = document.createElement("outcome");
						root.appendChild(outcome);
						saveXML(document);
					} catch (IOException e) {

						e.printStackTrace();
					}
				} else {
					document = builder.parse(file);
				}
				document.normalize();
				return document;
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public static boolean addXML(String tagName, LiJin liJin) {
		try {
			Document document = loadInit(filePath);
			Element eltItem = document.createElement("item");
			eltItem.setAttribute("id", liJin.getId());

			Element eltName = document.createElement("name");
			Element eltDolar = document.createElement("dolar");
			Element eltDate = document.createElement("date");
			Element eltComment = document.createElement("comment");
			Text eltNameValue = document.createTextNode(liJin.getName());
			eltName.appendChild(eltNameValue);

			Text eltDolarValue = document
					.createTextNode(liJin.getDolar() + "");
			eltDolar.appendChild(eltDolarValue);

			Text eltDateValue = document.createTextNode(liJin.getDate());
			eltDate.appendChild(eltDate);

			Text eltCommentValue = document.createTextNode(liJin.getComment());
			eltComment.appendChild(eltCommentValue);

			Node tagRoot = document.getElementsByTagName(tagName).item(0);
			tagRoot.appendChild(eltName);
			tagRoot.appendChild(eltDolar);
			tagRoot.appendChild(eltDate);
			tagRoot.appendChild(eltComment);

			Element eltRoot = document.getDocumentElement();
			eltRoot.appendChild(tagRoot);
			saveXML(document);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteXML(String tagName, String id) {
		Document document = loadInit(filePath);

		Node tagRoot = document.getElementsByTagName(tagName).item(0);

		try {
			NodeList tagChilds = tagRoot.getChildNodes();
			for (int i = 0; i < tagChilds.getLength(); i++) {
				Node item = tagChilds.item(i);
				if (item.getAttributes().getNamedItem("id").equals(id)) {
					item.getParentNode().removeChild(item);
					saveXML(document);
					break;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean updateXML(String tagName, LiJin liJin) {
		String id = liJin.getId();
		Document document = loadInit(filePath);
		try {
			Node tagRoot = document.getElementsByTagName(tagName).item(0);
			NodeList tagChilds = tagRoot.getChildNodes();
			int length = tagChilds.getLength();
			for (int i = 0; i < length; i++) {
				if (tagChilds.item(i).getAttributes().getNamedItem("id")
						.equals(id)) {
					Node item = tagChilds.item(i);
					NodeList itemchild = item.getChildNodes();
					for (int k = 0; k < itemchild.getLength(); k++) {
						Node itemdetail = itemchild.item(k);
						if (itemdetail.getNodeName().equals("name")) {
							itemdetail.setNodeValue(liJin.getName());
						}
						if (itemdetail.getNodeName().equals("dolar")) {
							itemdetail.setNodeValue(liJin.getDolar() + "");
						}
						if (itemdetail.getNodeName().equals("date")) {
							itemdetail.setNodeValue(liJin.getDate());
						}
						if (itemdetail.getNodeName().equals("comment")) {
							itemdetail.setNodeValue(liJin.getComment());
						}
						break;
					}
					break;
				}
			}
			saveXML(document);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean IsExist(String tagName, String id) {
		Document document = loadInit(filePath);

		Node tagRoot = document.getElementsByTagName(tagName).item(0);

		try {
			NodeList tagChilds = tagRoot.getChildNodes();
			for (int i = 0; i < tagChilds.getLength(); i++) {
				Node item = tagChilds.item(i);
				if (item.getAttributes().getNamedItem("id").equals(id)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean saveXML(Document document) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
