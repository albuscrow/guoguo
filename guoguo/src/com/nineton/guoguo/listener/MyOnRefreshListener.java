package com.nineton.guoguo.listener;

import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nineton.guoguo.adapter.MyBaseAdapter;

public class MyOnRefreshListener implements OnRefreshListener<ListView> {

	private MyBaseAdapter adapter;

	public MyOnRefreshListener(MyBaseAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		adapter.initData(refreshView);
	}

}
