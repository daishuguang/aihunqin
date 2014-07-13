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
		// 加载/res/layout目录下的activity_book_twopane.xml布局文件
		setContentView(R.layout.selectbook);
	}

	// 实现Callbacks接口必须实现的方法
	@Override
	public void onItemSelected(Integer id) {
		// 创建Bundle,准备向Fragment传入参数
		Bundle arguments = new Bundle();
		arguments.putInt(BookDetailFragment.ITEM_ID, id);
		// 创建BookDetailFragment对象
		BookDetailFragment fragment = new BookDetailFragment();
		// 向Fragment传入参数
		fragment.setArguments(arguments);
		// 使用fragment替换book_detail_container容器当前显示的Fragment
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.book_detail_container, fragment).commit();
	}
}
