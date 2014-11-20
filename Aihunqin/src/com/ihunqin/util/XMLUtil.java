package com.ihunqin.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import android.os.Environment;

import com.ihunqin.model.LiJin;

public class XMLUtil {
	private static String deleteNumber;
	private static String updateNumber;
	private static String fileName;
	public static String filepath;

	public static void setFileName(String filename) {
		fileName = filename;
	}

	// 读取传入的路径，返回一个document对象
	public static Document loadInit() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Environment.getExternalStorageDirectory()
					.getPath() + "/ihunqin/";
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			filepath = foldername + fileName;
			File file = new File(filepath);

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
			Document document = loadInit();
			Element eltItem = document.createElement("item");
			eltItem.setAttribute("id", liJin.getId());

			Element eltName = document.createElement("name");
			Element eltDolar = document.createElement("dolar");
			Element eltDate = document.createElement("date");
			Element eltComment = document.createElement("comment");

			Text eltNameValue = document.createTextNode(liJin.getName());
			eltName.appendChild(eltNameValue);

			Text eltDolarValue = document.createTextNode(liJin.getDolar() + "");
			eltDolar.appendChild(eltDolarValue);

			Text eltDateValue = document.createTextNode(liJin.getDate());
			eltDate.appendChild(eltDateValue);

			Text eltCommentValue = document.createTextNode(liJin.getComment());
			eltComment.appendChild(eltCommentValue);

			eltItem.appendChild(eltName);
			eltItem.appendChild(eltDolar);
			eltItem.appendChild(eltDate);
			eltItem.appendChild(eltComment);

			Element eltRoot = document.getDocumentElement();
			if (tagName.equals("income")) {
				eltRoot.getFirstChild().appendChild(eltItem);
			} else {
				eltRoot.getLastChild().appendChild(eltItem);
			}
			saveXML(document);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteXML(String tagName, String id) {
		Document document = loadInit();

		Node tagRoot = document.getElementsByTagName(tagName).item(0);

		try {
			NodeList tagChilds = tagRoot.getChildNodes();
			for (int i = 0; i < tagChilds.getLength(); i++) {
				Node item = tagChilds.item(i);
				if (item.getAttributes().getNamedItem("id").getNodeValue()
						.equals(id)) {
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
		Document document = loadInit();
		try {
			Node tagRoot = document.getElementsByTagName(tagName).item(0);
			NodeList tagChilds = tagRoot.getChildNodes();
			int length = tagChilds.getLength();
			for (int i = 0; i < length; i++) {
				if (tagChilds.item(i).getAttributes().getNamedItem("id")
						.getNodeValue().equals(id)) {
					Node item = tagChilds.item(i);
					NodeList itemchild = item.getChildNodes();
					for (int k = 0; k < itemchild.getLength(); k++) {
						Node itemdetail = itemchild.item(k);
						if (itemdetail.getNodeName().equals("name")) {
							document.getElementsByTagName(tagName).item(0)
									.getChildNodes().item(i).getChildNodes()
									.item(k).getFirstChild()
									.setNodeValue(liJin.getName());
							continue;
						}
						if (itemdetail.getNodeName().equals("dolar")) {
							document.getElementsByTagName(tagName).item(0)
									.getChildNodes().item(i).getChildNodes()
									.item(k).getFirstChild()
									.setNodeValue(liJin.getDolar() + "");
							continue;
						}
						if (itemdetail.getNodeName().equals("date")) {
							document.getElementsByTagName(tagName).item(0)
									.getChildNodes().item(i).getChildNodes()
									.item(k).getFirstChild()
									.setNodeValue(liJin.getDate());
							continue;
						}
						if (itemdetail.getNodeName().equals("comment")) {
							document.getElementsByTagName(tagName).item(0)
									.getChildNodes().item(i).getChildNodes()
									.item(k).getFirstChild()
									.setNodeValue(liJin.getComment());
							continue;
						}
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
		Document document = loadInit();

		Node tagRoot = document.getElementsByTagName(tagName).item(0);

		try {
			NodeList tagChilds = tagRoot.getChildNodes();
			for (int i = 0; i < tagChilds.getLength(); i++) {
				Node item = tagChilds.item(i);
				if (item.getAttributes().getNamedItem("id").getNodeValue()
						.equals(id)) {
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
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int selectDolar(String tagName) {
		int lijinlist = 0;
		try {
			Document document = loadInit();
			Node root = document.getElementsByTagName(tagName).item(0);
			NodeList itemlist = root.getChildNodes();
			for (int i = 0; i < itemlist.getLength(); i++) {
				NodeList ljitem = itemlist.item(i).getChildNodes();
				for (int k = 0; k < ljitem.getLength(); k++) {
					Node node = ljitem.item(k);

					if (node.getNodeName().equals("dolar")) {
						lijinlist += Integer.parseInt(node.getFirstChild()
								.getNodeValue());
						continue;
					}
				}
			}
			return lijinlist;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static ArrayList<LiJin> selectXML(String tagName) {
		ArrayList<LiJin> lijinlist = new ArrayList<LiJin>();
		try {
			Document document = loadInit();
			Node root = document.getElementsByTagName(tagName).item(0);
			NodeList itemlist = root.getChildNodes();
			for (int i = 0; i < itemlist.getLength(); i++) {
				NodeList ljitem = itemlist.item(i).getChildNodes();
				String id = itemlist.item(i).getAttributes().getNamedItem("id")
						.getNodeValue();
				LiJin lijinvalue = new LiJin();
				lijinvalue.setId(id);
				for (int k = 0; k < ljitem.getLength(); k++) {
					Node node = ljitem.item(k);
					if (node.getNodeName().equals("date")) {
						lijinvalue.setDate(node.getFirstChild().getNodeValue());
						continue;
					}
					if (node.getNodeName().equals("name")) {
						lijinvalue.setName(node.getFirstChild().getNodeValue());
						continue;
					}
					if (node.getNodeName().equals("dolar")) {
						lijinvalue.setDolar(Integer.parseInt(node
								.getFirstChild().getNodeValue()));
						continue;
					}
					if (node.getNodeName().equals("comment")) {
						if (node.getFirstChild() != null) {
							lijinvalue.setComment(node.getFirstChild()
									.getNodeValue());

						} else {
							lijinvalue.setComment("");
						}
						continue;
					}
				}
				lijinlist.add(lijinvalue);
			}
			return lijinlist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static LiJin selectItem(String tagName, String id) {
		LiJin lijinvalue = new LiJin();
		try {
			Document document = loadInit();
			Node root = document.getElementsByTagName(tagName).item(0);
			NodeList itemlist = root.getChildNodes();
			for (int i = 0; i < itemlist.getLength(); i++) {
				NodeList ljitem = itemlist.item(i).getChildNodes();
				if (id.equals(itemlist.item(i).getAttributes()
						.getNamedItem("id").getNodeValue())) {
					lijinvalue.setId(id);
					for (int k = 0; k < ljitem.getLength(); k++) {
						Node node = ljitem.item(k);
						if (node.getNodeName().equals("date")) {
							lijinvalue.setDate(node.getFirstChild()
									.getNodeValue());
							continue;
						}
						if (node.getNodeName().equals("name")) {
							lijinvalue.setName(node.getFirstChild()
									.getNodeValue());
							continue;
						}
						if (node.getNodeName().equals("dolar")) {
							lijinvalue.setDolar(Integer.parseInt(node
									.getFirstChild().getNodeValue()));
							continue;
						}
						if (node.getNodeName().equals("comment")) {
							if (node.getFirstChild() != null) {
								lijinvalue.setComment(node.getFirstChild()
										.getNodeValue());

							} else {
								lijinvalue.setComment("");
							}
							continue;
						}
					}
					break;
				}
			}
			return lijinvalue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
