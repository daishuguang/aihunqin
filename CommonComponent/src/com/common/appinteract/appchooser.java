package com.common.appinteract;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.commoncomponent.R;

public class appchooser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appchooser);

		Button b1 = (Button)findViewById(R.id.appchooser);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Build the intent
				Intent intent = new Intent(Intent.ACTION_SEND);

				// Always use string resources for UI text.
				// This says something like "Share this photo with"
				String title = getResources().getString(R.string.app_name);
				// Create intent to show chooser
				Intent chooser = Intent.createChooser(intent, title);

				// Verify the intent will resolve to at least one activity
				if (intent.resolveActivity(getPackageManager()) != null) {
					startActivity(chooser);
				}
			}
		});
		
	}

}
