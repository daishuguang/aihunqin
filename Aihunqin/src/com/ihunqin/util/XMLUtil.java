package com.ihunqin.util;

import java.io.File;

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

import com.ihunqin.model.LiJing;

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
		} else {
			return null;
		}
	}

	public static boolean addXML(String tagName, LiJing liJing) {
		try {
			Document document = loadInit(filePath);
			Element eltItem = document.createElement("item");
			eltItem.setAttribute("id", liJing.getId());

			Element eltName = document.createElement("name");
			Element eltDolar = document.createElement("dolar");
			Element eltDate = document.createElement("date");
			Element eltComment = document.createElement("comment");
			Text eltNameValue = document.createTextNode(liJing.getName());
			eltName.appendChild(eltNameValue);

			Text eltDolarValue = document
					.createTextNode(liJing.getDolar() + "");
			eltDolar.appendChild(eltDolarValue);

			Text eltDateValue = document.createTextNode(liJing.getDate());
			eltDate.appendChild(eltDate);

			Text eltCommentValue = document.createTextNode(liJing.getComment());
			eltComment.appendChild(eltCommentValue);

			Node tagRoot = document.getElementsByTagName(tagName).item(0);
			tagRoot.appendChild(eltName);
			tagRoot.appendChild(eltDolar);
			tagRoot.appendChild(eltDate);
			tagRoot.appendChild(eltComment);

			Element eltRoot = document.getDocumentElement();
			eltRoot.appendChild(tagRoot);

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

	public static boolean updateXML(String tagName, LiJing lJing) {
		String id = lJing.getId();
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
							itemdetail.setNodeValue(lJing.getName());
						}
						if (itemdetail.getNodeName().equals("dolar")) {
							itemdetail.setNodeValue(lJing.getDolar() + "");
						}
						if (itemdetail.getNodeName().equals("date")) {
							itemdetail.setNodeValue(lJing.getDate());
						}
						if (itemdetail.getNodeName().equals("comment")) {
							itemdetail.setNodeValue(lJing.getComment());
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
