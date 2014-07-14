package com.aihunqin.fragment;

import com.aihunqin.model.BookContent;
import com.example.aihunqin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

public class BookDetailFragment extends Fragment {
	public static final String ITEM_ID = "item_id";
	// 保存该Fragment显示的book对象
	BookContent.Book book;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 如果启动该Fragment时包含了ITEM_ID参数
		if (getArguments().containsKey(ITEM_ID)) {
			book = BookContent.ITEM_MAP.get(getArguments().getInt(ITEM_ID));
		}
	}

	// 重写该方法，该方法返回的View将作为Fragment显示的组件
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 加载/res/layout/目录下的fragment_book_detail.xml布局文件
		View rootView = inflater.inflate(R.layout.selectbook,
				container, false);
		if (book != null) {
			// 让book_title文本框显示book对象的title属性
			((TextView) rootView.findViewById(R.id.book_title))
					.setText(book.title);
			// 让book_desc文本框显示book对象的desc属性
			((TextView) rootView.findViewById(R.id.book_desc))
					.setText(R.id.book_desc);
		}
		return rootView;
	}
}
