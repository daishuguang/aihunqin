package com.aihunqin.fragment;

import com.aihunqin.model.BookContent;
import com.example.aihunqin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

public class BookDetailFragment extends Fragment {
	public static final String ITEM_ID = "item_id";
	// �����Fragment��ʾ��book����
	BookContent.Book book;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���������Fragmentʱ������ITEM_ID����
		if (getArguments().containsKey(ITEM_ID)) {
			book = BookContent.ITEM_MAP.get(getArguments().getInt(ITEM_ID));
		}
	}

	// ��д�÷������÷������ص�View����ΪFragment��ʾ�����
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// ����/res/layout/Ŀ¼�µ�fragment_book_detail.xml�����ļ�
		View rootView = inflater.inflate(R.layout.selectbook,
				container, false);
		if (book != null) {
			// ��book_title�ı�����ʾbook�����title����
			((TextView) rootView.findViewById(R.id.book_title))
					.setText(book.title);
			// ��book_desc�ı�����ʾbook�����desc����
			((TextView) rootView.findViewById(R.id.book_desc))
					.setText(R.id.book_desc);
		}
		return rootView;
	}
}
