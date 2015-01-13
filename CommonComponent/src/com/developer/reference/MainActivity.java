package com.developer.reference;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.commoncomponent.R;

public class MainActivity extends Activity {

	private Button mHandle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidelayout);

	}

	public void setupViews() {
		mHandle = (Button) findViewById(R.id.handle);
		mHandle.setTranslationX(100);
	}
}
