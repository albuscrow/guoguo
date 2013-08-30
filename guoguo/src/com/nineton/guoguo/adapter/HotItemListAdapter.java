package com.nineton.guoguo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;
import com.nineton.guoguo.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.nineton.guoguo.entity.HotItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HotItemListAdapter extends MyBaseAdapter {

	public HotItemListAdapter(MainActivity activity) {
		super(activity);
		items = new ArrayList<HotItem>();
	}

	private void addItem(List<HotItem> hotItems){
		this.items.addAll(hotItems);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}
	
	public void loadMoreItem(){
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
				showLoadingView();
			}

			@Override
			public void onPostExecute(Object result) {
				hideLoadingVIew();
				if (result != null) {
					addItem((List<HotItem>)result);
				}
			}
		}).getHotItem(++nextIndex, null);
	}
	
	@Override
	public void loadMoreItemFromOutside(
			final ItemDetailViewPagerAdapter itemDetailViewPagerAdapter) {
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
				showLoadingView();
			}

			@Override
			public void onPostExecute(Object result) {
				hideLoadingVIew();
				if (result != null) {
					addItem((List<HotItem>)result);
				}
				itemDetailViewPagerAdapter.notifyDataSetChanged();
			}
		}).getHotItem( ++nextIndex, null);
	}

	@Override
	public void initData() {
		nextIndex = 0;
		items.clear();
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
				showLoadingView();
			}

			@Override
			public void onPostExecute(Object result) {
				hideLoadingVIew();
				if (result != null) {
					addItem((List<HotItem>)result);
				}
			}
		}).getHotItem( null, null);
	}
	
	public void initData(final PullToRefreshBase<ListView> refreshView) {
		nextIndex = 0;
		items.clear();
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
			}

			@Override
			public void onPostExecute(Object result) {
				if (result != null) {
					items.clear();
					addItem((List<HotItem>)result);
				}
				refreshView.onRefreshComplete();
			}
		}).getHotItem(null, null);
	}

}
