package com.aihunqin.model;

import java.util.*;

public class BookContent {
	// ����һ���ڲ��࣬��Ϊϵͳ��ҵ�����
	public static class Book {
		public Integer id;
		public String title;
		public String desc;

		public Book(Integer id, String title, String desc) {
			this.id = id;
			this.title = title;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.title;
		}
	}

	// ʹ��List���ϼ�¼ϵͳ��������Book����
	public static List<Book> ITEMS = new ArrayList<Book>();
	// ʹ��Map���ϼ�¼ϵͳ��������Book����
	public static Map<Integer, Book> ITEM_MAP = new HashMap<Integer, BookContent.Book>();
	static {
		// ʹ�þ�̬��ʼ�����룬��Book������ӵ�List���ϡ�Map������
		addItem(new Book(1, "���Java����", "һ��ȫ�桢�����Javaѧϰͼ�飬�ѱ���Ҹ�Уѡ���̲ġ�"));
		addItem(new Book(2, "���Android����", "Androidѧϰ�ߵ���ѡͼ�飬����ռ�ݾ�����������"
				+ "����ѷ3����վAndroid�������а�İ���"));
		addItem(new Book(3, "������Java EE��ҵӦ��ʵս",
				"ȫ�����Java EE������Struts 2��Sping 3��Hibernate 4���"));
	}

	private static void addItem(Book book) {
		ITEMS.add(book);
		ITEM_MAP.put(book.id, book);
	}
}
