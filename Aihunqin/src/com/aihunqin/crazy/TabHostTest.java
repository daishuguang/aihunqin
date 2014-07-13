package com.aihunqin.crazy;

import com.example.aihunqin.R;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabHostTest extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhosttest);
		// 获取该Activity里面的TabHost组件
		TabHost tabHost = getTabHost();
		// 创建第一个Tab页
		TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator("已接电话")
				.setContent(R.id.tab01);
		// 添加第一个标签页
		tabHost.addTab(tab1);
		// 创建第二个Tab页
		TabSpec tab2 = tabHost
				.newTabSpec("tab2")
				.setIndicator("呼出电话",
						getResources().getDrawable(R.drawable.hunqin))
				.setContent(R.id.tab02);
		// 添加第二个标签页
		tabHost.addTab(tab2);
		TabSpec tab3 = tabHost.newTabSpec("tab3").setIndicator("未接电话")
				.setContent(R.id.tab03);

		// 添加第三个标签页
		tabHost.addTab(tab3);
	}
}
