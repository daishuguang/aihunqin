package com.android.training;

import com.example.commoncomponent.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

public class immersivemode extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_immersive);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = MotionEventCompat.getActionMasked(event);
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d("roboce", "Action was DOWN");
			return true;
		case MotionEvent.ACTION_MOVE:
			Log.d("roboce", "Action was MOVE");
			return true;
		case MotionEvent.ACTION_UP:
			Log.d("roboce", "Action was UP");
			return true;
		case MotionEvent.ACTION_CANCEL:
			Log.d("roboce", "Action was CANCEL");
			return true;
		case MotionEvent.ACTION_OUTSIDE:
			Log.d("roboce", "Action was outside");
			return true;
		default:
			return super.onTouchEvent(event);
		}
	}

}
