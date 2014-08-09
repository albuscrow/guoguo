package com.nineton.guoguo.listener;

import com.nineton.guoguo.R;
import com.nineton.guoguo.adapter.RandomItemListAdapter;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.utils.MySensorManager;
import com.nineton.guoguo.utils.TitleManeger;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class MyOnPageChangeListener implements OnPageChangeListener {

	private MainActivity activity;
	private View slideView;
	private int space;

	@SuppressLint("NewApi")
	public MyOnPageChangeListener(MainActivity activity, RandomItemListAdapter randomItemListAdapter) {
		this.activity = activity;
		slideView = activity.findViewById(R.id.slide_bar);
		final View basebar = activity.findViewById(R.id.base_bar);
		basebar.post(new Runnable() {
			@Override
			public void run() {
				space = basebar.getWidth()/3;
				slideView.setX((int) (basebar.getX()+basebar.getWidth()/6-slideView.getWidth()/2));
				slideView.setTag(0);
			}
		});
		MySensorManager.init(activity,randomItemListAdapter);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0:
			TitleManeger.setTitleHome(activity.getResources().getString(R.string.taobao_hot));
			MySensorManager.unregisterListener();
			slide((Integer) slideView.getTag(),0);
			break;
		case 1:
			TitleManeger.setTitleHome(activity.getResources().getString(R.string.tmall_special));
			MySensorManager.unregisterListener();
			slide((Integer) slideView.getTag(),space);
			break;
		case 2:
			TitleManeger.setTitleHome(activity.getResources().getString(R.string.random));
			MySensorManager.registerListener();
			slide((Integer) slideView.getTag(),2*space);
			break;
		default:
			break;
		}
	}

	@SuppressLint("NewApi")
	private void slide(int begain,int end) {
		Animation animation = new TranslateAnimation(begain, end, 0, 0);
		animation.setFillAfter(true);
		animation.setDuration(200);
		slideView.setTag(end);
		slideView.startAnimation(animation);
	}
	
}
