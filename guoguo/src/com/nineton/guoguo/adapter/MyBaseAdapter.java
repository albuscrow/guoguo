package com.nineton.guoguo.adapter;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nineton.guoguo.R;
import com.nineton.guoguo.common.AppConfig;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.entity.Item;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public abstract class MyBaseAdapter extends BaseAdapter {


	protected MainActivity activity;
	protected List items;
	protected LayoutInflater layoutInflater;
	protected View loadingView;
	protected Integer nextIndex = 0;

	private class ViewHolder{
		public ImageView itemPicture;
		public TextView itemTitle;
		public TextView itemPrice;
	}

	public MyBaseAdapter(MainActivity activity) {
		this.activity = activity;
		layoutInflater = ((Activity)activity).getLayoutInflater();
		loadingView = ((Activity)activity).findViewById(R.id.loading);
	}


	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		if (items.size() != 0) {
			return items.get(position);
		}else {
			return null;
		}
	}

	public List<Item> getItems() {
		return (List<Item>) items;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder viewHolder = null;
		if (v == null) {
			v = layoutInflater.inflate(R.layout.brife_item_cell, null);
			viewHolder = new ViewHolder();
			viewHolder.itemPicture = (ImageView) v.findViewById(R.id.brife_item_picture);
			viewHolder.itemTitle = (TextView) v.findViewById(R.id.brife_item_title);
			viewHolder.itemPrice = (TextView) v.findViewById(R.id.brife_item_price);

			v.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) v.getTag();
		}

		Item item = (Item) getItem(position);
		if (item != null) {
			viewHolder.itemPicture.setImageResource(R.drawable.default_item_image);
			ImageLoader.getInstance().displayImage(item.getFirstPicUrl(), viewHolder.itemPicture);
			viewHolder.itemTitle.setText(item.getTitle());
			viewHolder.itemPrice.setText("￥ "+item.getPrice().toString());
		}

		return v;
	}


	protected void showLoadingView() {
		if (AppConfig.HAS_WIFI) {
			loadingView.setVisibility(View.VISIBLE);
		}
	}

	protected void hideLoadingVIew(){
		loadingView.setVisibility(View.GONE);
	};

	public void loadMoreItem(){}

	public void initData(PullToRefreshBase<ListView> refreshView) {}

	public void initData(){}


	public void loadMoreItemFromOutside(
			ItemDetailViewPagerAdapter itemDetailViewPagerAdapter) {
	}

	public void initData(PullToRefreshBase<ListView> refreshView,
			PullToRefreshListView topicListView) {

	}
}
