package com.aihunqin.crazy;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class FirstProviders extends ContentProvider {

	// ��һ�δ�����ContentProviderʱ���ø÷���
	@Override
	public boolean onCreate() {
		Log.v("roboce", "=========onCreate����������=========");
		return true;
	}

	// ʵ�ֲ�ѯ�������÷������ز�ѯ�õ���Cursor
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.v("roboce", "==========query����������=======");
		return null;
	}

	// �÷����ķ���ֵ�����˸�ContentProvider���ṩ���ݵ�MIME����
	@Override
	public String getType(Uri uri) {

		return null;
	}

	// ʵ�ֲ���ķ������÷���Ӧ�÷����²���ļ�¼��Uri
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.v("roboce", "===============insert����������============");
		return null;
	}

	// ʵ��ɾ���������÷������ر�ɾ���ļ�¼����
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.v("roboce", "==========delete����������");
		return 0;
	}

	// ʵ�ָ��·������÷������ر����µļ�¼����
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Log.v("roboce", "=========update����������");
		return 0;
	}

}
