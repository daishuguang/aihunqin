package com.aihunqin.crazy;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class FirstProviders extends ContentProvider {

	// 第一次创建该ContentProvider时调用该方法
	@Override
	public boolean onCreate() {
		Log.v("roboce", "=========onCreate方法被调用=========");
		return true;
	}

	// 实现查询方法，该方法返回查询得到的Cursor
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.v("roboce", "==========query方法被调用=======");
		return null;
	}

	// 该方法的返回值代表了该ContentProvider所提供数据的MIME类型
	@Override
	public String getType(Uri uri) {

		return null;
	}

	// 实现插入的方法，该方法应该返回新插入的记录的Uri
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.v("roboce", "===============insert方法被调用============");
		return null;
	}

	// 实现删除方法，该方法返回被删除的记录条数
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.v("roboce", "==========delete方法被调用");
		return 0;
	}

	// 实现更新方法，该方法返回被更新的记录条数
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Log.v("roboce", "=========update方法被调用");
		return 0;
	}

}
