package com.nineton.guoguo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nineton.guoguo.R;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;
import com.nineton.guoguo.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.nineton.guoguo.entity.Searcheditem;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;

public class SearchedItemListAdapter extends MyBaseAdapter {

	private String keyword;
	private Long cid;

	public SearchedItemListAdapter(MainActivity activity, Long cid,String keyword) {
		super(activity);
		items = new ArrayList<Searcheditem>();
		this.keyword = keyword;
		this.cid = cid;
	}
	
	private void addItem(List<Searcheditem> items){
		this.items.addAll(items);
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
					addItem((List<Searcheditem>)result);
				}else {
					Toast.makeText(activity, "搜不到，换个关键字试试吧!", Toast.LENGTH_SHORT).show();
				}
			}
		}).getSearchedItems(null, null,cid,keyword);
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
					addItem((List<Searcheditem>)result);
				}
				refreshView.onRefreshComplete();
			}
		}).getSearchedItems(null, null,cid,keyword);
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
					addItem((List<Searcheditem>)result);
				}
			}
		}).getSearchedItems(++nextIndex, null,cid,keyword);
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
					addItem((List<Searcheditem>)result);
				}
				itemDetailViewPagerAdapter.notifyDataSetChanged();
			}
		}).getSearchedItems(++nextIndex, null,cid,keyword);
	}

	public void initData(final PullToRefreshListView searchItemListView,
			final View searchLayout) {
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
					addItem((List<Searcheditem>)result);
					Animation animation = AnimationUtils.loadAnimation(activity, R.anim.view_switch_in);
					searchItemListView.startAnimation(animation);
					searchItemListView.setVisibility(View.VISIBLE);
					searchLayout.setVisibility(View.GONE);
				}else {
					Toast.makeText(activity, "搜不到，换个关键字试试吧!", Toast.LENGTH_SHORT).show();
				}
			}
		}).getSearchedItems(null, null,cid,keyword);
	}

}
