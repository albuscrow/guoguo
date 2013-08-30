package com.nineton.guoguo.listener;

import com.nineton.guoguo.adapter.MyBaseAdapter;
import com.nineton.guoguo.common.PublicContainers;
import com.nineton.guoguo.controller.ItemDetailActivity;
import com.nineton.guoguo.controller.MainActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MyOnItemClickListener implements OnItemClickListener {
	
	private MyBaseAdapter adapter;
	private MainActivity activity;
	private Boolean isPullToRefreshView;
	
	public MyOnItemClickListener(MyBaseAdapter adapter,MainActivity activity,Boolean isPullToRefreshView) {
		this.adapter = adapter;
		this.activity = activity;
		this.isPullToRefreshView = isPullToRefreshView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(activity, ItemDetailActivity.class);
		PublicContainers instance = PublicContainers.getInstance();
		instance.putData("adapter", adapter);
		if (isPullToRefreshView) {
			arg2 -= 1;
		}
		instance.putData("position", arg2);
		activity.startActivity(intent);
	}
}
