package com.aihunqin.crazy;

import com.example.aihunqin.R;

import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SinaMain extends FragmentActivity {
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	Fragment[] mFragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sina);
		fragmentManager = getSupportFragmentManager();
		mFragments = new Fragment[3];
		mFragments[0] = fragmentManager.findFragmentById(R.id.fragment_main);
		mFragments[1] = fragmentManager.findFragmentById(R.id.fragment_search);
		mFragments[2] = fragmentManager.findFragmentById(R.id.fragment_setting);
		fragmentTransaction = fragmentManager.beginTransaction()
				.hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
		fragmentTransaction.show(mFragments[0]).commit();
		setFragmentIndicator();
	}

	void setFragmentIndicator() {
		RadioGroup bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
		RadioButton rbOne = (RadioButton) findViewById(R.id.rbOne);
		RadioButton rbTwo = (RadioButton) findViewById(R.id.rbTwo);
		RadioButton rbThree = (RadioButton) findViewById(R.id.rbThree);

		bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0])
						.hide(mFragments[1]).hide(mFragments[2]);
				Log.v("roboce", checkedId + "");
				switch (checkedId) {
				case R.id.rbOne:
					fragmentTransaction.show(mFragments[0]).commit();
					break;
				case R.id.rbTwo:
					fragmentTransaction.show(mFragments[1]).commit();
					break;
				case R.id.rbThree:
					fragmentTransaction.show(mFragments[2]).commit();
					break;
				default:
					break;
				}
			}
		});
	}
}
