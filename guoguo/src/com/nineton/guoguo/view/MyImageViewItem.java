package com.nineton.guoguo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class MyImageViewItem extends ImageView {

	public MyImageViewItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

@Override
protected void onFinishInflate() {
	super.onFinishInflate();
	this.post(new Runnable() {
		
		@Override
		public void run() {
			int temp = MyImageViewItem.this.getWidth();
			LayoutParams LayoutParams = (android.widget.RelativeLayout.LayoutParams) MyImageViewItem.this.getLayoutParams();
			LayoutParams.height = temp;
			MyImageViewItem.this.setLayoutParams(LayoutParams);
			MyImageViewItem.this.invalidate();
		}
	});
}
}
