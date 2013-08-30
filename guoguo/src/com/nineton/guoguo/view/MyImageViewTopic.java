package com.nineton.guoguo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class MyImageViewTopic extends ImageView {

	public MyImageViewTopic(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

@Override
protected void onFinishInflate() {
	// TODO Auto-generated method stub
	super.onFinishInflate();
	this.post(new Runnable() {
		
		@Override
		public void run() {
			int temp = MyImageViewTopic.this.getWidth();
			LayoutParams LayoutParams = (android.widget.RelativeLayout.LayoutParams) MyImageViewTopic.this.getLayoutParams();
			LayoutParams.height = (int) (temp*0.7);
			MyImageViewTopic.this.setLayoutParams(LayoutParams);
			MyImageViewTopic.this.invalidate();
		}
	});
}
}
