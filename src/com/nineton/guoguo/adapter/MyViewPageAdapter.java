package com.nineton.guoguo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nineton.guoguo.R;
import com.nineton.guoguo.common.AppConfig;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.listener.MyOnItemClickListener;
import com.nineton.guoguo.listener.MyOnLastItemVisibleListener;
import com.nineton.guoguo.listener.MyOnRefreshListener;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MyViewPageAdapter extends PagerAdapter {

	private List<View> viewList;
	private MainActivity activity;
	private LayoutInflater layoutInflater;
	private RandomItemListAdapter randomItemListAdapter;


	public RandomItemListAdapter getRandomItemListAdapter() {
		return randomItemListAdapter;
	}

	public MyViewPageAdapter(MainActivity activity) {
		this.activity = activity;
		this.viewList = new ArrayList<View>();
		initViewList();
	}

	private void initViewList() {
		layoutInflater = activity.getLayoutInflater();
		initHotItemList();
		initTmallSpecailList();
		initRandomItemList();
	}

	private void initHotItemList() {
		PullToRefreshListView view = (PullToRefreshListView)layoutInflater.inflate(R.layout.pull_refresh_item_list, null);
		HotItemListAdapter adapter = new HotItemListAdapter(activity);
		adapter.initData();
		view.setAdapter(adapter);
		view.setOnRefreshListener(new MyOnRefreshListener(adapter));
		view.setOnLastItemVisibleListener(new MyOnLastItemVisibleListener(adapter));
		view.setOnItemClickListener(new MyOnItemClickListener(adapter, activity,true));
		viewList.add(view);
	}

	private void initTmallSpecailList() {
		PullToRefreshListView view = (PullToRefreshListView)layoutInflater.inflate(R.layout.pull_refresh_item_list, null);
		TmallSpecialListAdapter adapter = new TmallSpecialListAdapter(activity);
		adapter.initData();
		view.setAdapter(adapter);
		view.setOnRefreshListener(new MyOnRefreshListener(adapter));
		view.setOnLastItemVisibleListener(new MyOnLastItemVisibleListener(adapter));
		view.setOnItemClickListener(new MyOnItemClickListener(adapter, activity,true));
		viewList.add(view);
	}

	private void initRandomItemList() {
		RelativeLayout view = (RelativeLayout)layoutInflater.inflate(R.layout.random_item_list, null);
		randomItemListAdapter = new RandomItemListAdapter(activity);
		PullToRefreshListView listView = (PullToRefreshListView) view.findViewById(R.id.random_items);
		if (!AppConfig.HAS_WIFI) {
			view.findViewById(R.id.random_textview).setVisibility(View.GONE);
		}else {
			view.findViewById(R.id.random_textview).setVisibility(View.VISIBLE);
		}
		listView.setAdapter(randomItemListAdapter);
		listView.setOnItemClickListener(new MyOnItemClickListener(randomItemListAdapter, activity ,true));
		listView.setOnRefreshListener(new MyOnRefreshListener(randomItemListAdapter));
		viewList.add(view);
	}

	@Override 
	public int getCount() {
		return viewList.size(); 
	} 

	@Override 
	public boolean isViewFromObject(View arg0, Object arg1) { 
		return arg0 == arg1; 
	} 

	@Override 
	public int getItemPosition(Object object) { 
		return super.getItemPosition(object); 
	} 

	@Override 
	public void destroyItem(View arg0, int arg1, Object arg2) { 
		((ViewPager) arg0).removeView(viewList.get(arg1)); 
	} 

	@Override 
	public Object instantiateItem(View arg0, int arg1) { 
		((ViewGroup)arg0).addView(viewList.get(arg1));
		return viewList.get(arg1);
	} 


}
