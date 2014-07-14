package com.aihunqin.fragment;

import com.aihunqin.model.BookContent;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BookListFragment extends ListFragment {
	private Callbacks mCallbacks;

	// ����һ���ص��ӿڣ���Fragment����Activity��Ҫʵ�ָýӿ�
	// ��Fragment��ͨ���ýӿ��������ڵ�Activity����
	public interface Callbacks {
		public void onItemSelected(Integer id);
	}

	@Override
	public void onCreate(Bundle savedInstaceState) {
		super.onCreate(savedInstaceState);
		// Ϊ��ListFragment����Adapter
		setListAdapter(new ArrayAdapter<BookContent.Book>(getActivity(),
				R.layout.simple_list_item_activated_1, R.id.text1,
				BookContent.ITEMS));
	}

	// ����Fragment����ӡ���ʾ��Activityʱ���ص��÷���
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// ���Activityû��ʵ��Callbacks�ӿڣ��׳��쳣
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"BookListFragment���ڵ�Activity����ʵ��Callbacks�ӿ�!");
		}
		// �Ѹ�Activity����Callbacks����
		mCallbacks = (Callbacks) activity;
	}

	// ����Fragment����������Activity�б�ɾ��ʱ�ص��÷���
	@Override
	public void onDetach() {
		super.onDetach();
		// ��mCallbacks��Ϊnull
		mCallbacks = null;
	}

	// ���û����ĳ�б���ʱ�����ûص�����
	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		// ����mCallbacks��onItemSelected����
		mCallbacks.onItemSelected(BookContent.ITEMS.get(position).id);
	}

	public void setActivateOnItemClick(boolean activateOnItemClick) {
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}
}
