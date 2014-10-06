package com.ihunqin.crazy;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.aihunqin.R;

public class SelectPopupWindow extends PopupWindow {

	private Button btn_take_photo, btn_pick_photo, btn_cancel;
	private View mMenuView;

	public SelectPopupWindow(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);

		mMenuView = inflater.inflate(R.layout.popupwindow, null);
		btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
		btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
		// ȡ����ť
		btn_cancel.setOnClickListener(itemsOnClick);

		// ���ð�ť����
		btn_pick_photo.setOnClickListener(itemsOnClick);
		btn_take_photo.setOnClickListener(itemsOnClick);
		// ����SelectPopupWindow��View
		this.setContentView(mMenuView);
		// ����SelectPopupWindow��������Ŀ�
		this.setWidth(LayoutParams.MATCH_PARENT);
		// ����SelectPopupWindow��������ĸ�
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// ����SelectPopupWindow��������ɵ��
		this.setFocusable(true);
		// ����SelectPopupWindow�������嶯��Ч��
		this.setAnimationStyle(R.style.Animation);
		// ʵ����һ��ColorDrawable��ɫΪ͸��
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// ����SelectPopupWindow��������ı���
		this.setBackgroundDrawable(dw);
		// mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����
		mMenuView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}
}
