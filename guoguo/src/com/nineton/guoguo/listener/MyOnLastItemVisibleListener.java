package com.nineton.guoguo.listener;

import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.nineton.guoguo.adapter.MyBaseAdapter;

public class MyOnLastItemVisibleListener implements OnLastItemVisibleListener {

	private MyBaseAdapter adapter;

	public MyOnLastItemVisibleListener(MyBaseAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void onLastItemVisible() {
		this.adapter.loadMoreItem();
	}

}
