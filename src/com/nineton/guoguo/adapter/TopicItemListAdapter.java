package com.nineton.guoguo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nineton.guoguo.R;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;
import com.nineton.guoguo.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.nineton.guoguo.entity.TopicItem;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

public class TopicItemListAdapter extends MyBaseAdapter {


	private Long topicId;



	public TopicItemListAdapter(MainActivity activity) {
		super(activity);
		items = new ArrayList<TopicItem>();
	}

	public TopicItemListAdapter(MainActivity activity, Long topicId) {
		super(activity);
		items = new ArrayList<TopicItem>();
		this.topicId = topicId;
	}


	private void addItem(List<TopicItem> TopicItems){
		this.items.addAll(TopicItems);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
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
					addItem((List<TopicItem>)result);
				}
				refreshView.onRefreshComplete();
			}
		}).getTopicItem(null, null,topicId);
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
					addItem((List<TopicItem>)result);
				}
			}
		}).getTopicItem(++nextIndex,null,topicId);
	}

	@Override
	public void initData(final PullToRefreshBase<ListView> topicItemListView, final PullToRefreshListView topicListView) {
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
					addItem((List<TopicItem>)result);
					Animation animation = AnimationUtils.loadAnimation(activity, R.anim.view_switch_in);
					topicItemListView.setVisibility(View.VISIBLE);
					topicListView.setVisibility(View.GONE);
					topicItemListView.startAnimation(animation);
				}
			}
		}).getTopicItem(null, null,topicId);
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
					addItem((List<TopicItem>)result);
				}
				itemDetailViewPagerAdapter.notifyDataSetChanged();
			}
		}).getTopicItem(++nextIndex, null,topicId);
	}
}
