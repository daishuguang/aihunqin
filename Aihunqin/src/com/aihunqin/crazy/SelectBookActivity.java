package com.aihunqin.crazy;

import com.example.aihunqin.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class SelectBookActivity extends FragmentActivity implements
		BookListFragment.Callbacks {
	@Override
	public void onCreate(Bundle savedInstaceState) {
		super.onCreate(savedInstaceState);
		// ����/res/layoutĿ¼�µ�activity_book_twopane.xml�����ļ�
		setContentView(R.layout.selectbook);
	}

	// ʵ��Callbacks�ӿڱ���ʵ�ֵķ���
	@Override
	public void onItemSelected(Integer id) {
		// ����Bundle,׼����Fragment�������
		Bundle arguments = new Bundle();
		arguments.putInt(BookDetailFragment.ITEM_ID, id);
		// ����BookDetailFragment����
		BookDetailFragment fragment = new BookDetailFragment();
		// ��Fragment�������
		fragment.setArguments(arguments);
		// ʹ��fragment�滻book_detail_container������ǰ��ʾ��Fragment
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.book_detail_container, fragment).commit();
	}
}
