package com.nineton.guoguo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.nineton.guoguo.R;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;
import com.nineton.guoguo.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.nineton.guoguo.entity.HotItem;
import com.nineton.guoguo.entity.RandomItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RandomItemListAdapter extends MyBaseAdapter {
	
	private boolean flag = true;

	public RandomItemListAdapter(MainActivity activity) {
		super(activity);
		items = new ArrayList<HotItem>();
	}
	
	private void addItem(List<RandomItem> randomItems){
		this.items.addAll(randomItems);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}

	public void refresh() {
		initData();
	}
	
	@Override
	synchronized public void initData() {
		if (flag) {
			flag = false;
			activity.findViewById(R.id.random_textview).setVisibility(View.GONE);
		}
		nextIndex = 0;
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
				showLoadingView();
			}

			@Override
			public void onPostExecute(Object result) {
				hideLoadingVIew();
				List<RandomItem> result2 = (List<RandomItem>)result;
				if (result2.size() != 0) {
					items.clear();
				}
				if (result != null) {
					addItem(result2);
				}
			}
		}).getRandomItem(null);
	}
	
	@Override
	public void initData(final PullToRefreshBase<ListView> refreshView) {
		if (flag) {
			flag = false;
			activity.findViewById(R.id.random_textview).setVisibility(View.GONE);
		}
		nextIndex = 0;
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
			}

			@Override
			public void onPostExecute(Object result) {
				hideLoadingVIew();
				List<RandomItem> result2 = (List<RandomItem>)result;
				if (result2.size() != 0) {
					items.clear();
				}
				if (result != null) {
					addItem(result2);
				}
				refreshView.onRefreshComplete();
			}
		}).getRandomItem(null);
	}

}
