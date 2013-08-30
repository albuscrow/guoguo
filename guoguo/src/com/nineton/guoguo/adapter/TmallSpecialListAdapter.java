package com.nineton.guoguo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;
import com.nineton.guoguo.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.nineton.guoguo.entity.TmallSpecialOffer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TmallSpecialListAdapter extends MyBaseAdapter {
	
	
	public TmallSpecialListAdapter(MainActivity activity) {
		super(activity);
		items = new ArrayList<TmallSpecialOffer>();
	}
	
	private void addItem(List<TmallSpecialOffer> tmallSpecialOffers){
		this.items.addAll(tmallSpecialOffers);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
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
					addItem((List<TmallSpecialOffer>)result);
				}
			}
		}).getTmallSpecial(null, null);
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
					addItem((List<TmallSpecialOffer>)result);
				}
				refreshView.onRefreshComplete();
			}
		}).getTmallSpecial(null, null);
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
					addItem((List<TmallSpecialOffer>)result);
				}
			}
		}).getTmallSpecial(++nextIndex, null);
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
					addItem((List<TmallSpecialOffer>)result);
				}
				itemDetailViewPagerAdapter.notifyDataSetChanged();
			}
		}).getTmallSpecial(++nextIndex, null);
	}
}
