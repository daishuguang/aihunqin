package com.android.training;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.commoncomponent.R;

public class Speech extends Activity {

	TextToSpeech tts;
	EditText editText;
	Button speech;
	Button record;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_immersive);

		// 初始化
		tts = new TextToSpeech(this, new OnInitListener() {

			@Override
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					int result = tts.setLanguage(Locale.US);
					if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
							&& result != TextToSpeech.LANG_AVAILABLE) {
						Toast.makeText(Speech.this, "暂时不支持", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
		});

		editText = (EditText) findViewById(R.id.txt);
		speech = (Button) findViewById(R.id.speech);
		record = (Button) findViewById(R.id.record);

		speech.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tts.speak(editText.getText().toString(),
						TextToSpeech.QUEUE_ADD, null);
			}
		});

		record.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tts.synthesizeToFile(editText.getText().toString(), null,
						"/mnt/sdcard/roboce.wav");
				Toast.makeText(Speech.this, "录音成功", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onDestroy() {
		if (tts != null) {
			tts.shutdown();
		}
		super.onDestroy();
	}

}
