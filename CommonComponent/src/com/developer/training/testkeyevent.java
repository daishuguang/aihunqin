package com.developer.training;

import com.commoncomponent.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class testkeyevent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_key);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.v("roboce", "keydown");
		return true;
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		Log.v("roboce", "keyLongPress");
		return super.onKeyLongPress(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.v("roboce", "keyUp");
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		Log.v("roboce", "keyMultiple");
		return super.onKeyMultiple(keyCode, repeatCount, event);
	}

}
