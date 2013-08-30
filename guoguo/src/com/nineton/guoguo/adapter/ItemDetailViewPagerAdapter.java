package com.nineton.guoguo.adapter;


import com.nineton.guoguo.R;
import com.nineton.guoguo.controller.ItemDetailActivity;
import com.nineton.guoguo.entity.Item;
import com.nineton.guoguo.listener.FavouriteOnClickListener;
import com.nineton.guoguo.listener.ShareOnClickListener;
import com.nineton.guoguo.listener.ViewItemDetailOnClickListener;
import com.nineton.guoguo.utils.FavouriteItemManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetailViewPagerAdapter extends PagerAdapter {

	private static final Integer PRESTRAIN_THRESHOLD = 5;
	private ItemDetailActivity activity;
	private LayoutInflater layoutInflater;
	private MyBaseAdapter dataProviderAdapter;

	
	public ItemDetailViewPagerAdapter(ItemDetailActivity activity, MyBaseAdapter adapter) {
		this.activity = activity;
		layoutInflater = ((Activity)activity).getLayoutInflater();
		this.dataProviderAdapter = adapter;
	}
	
	@Override
	public int getCount() {
		return dataProviderAdapter.getItems().size();
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		if (position + PRESTRAIN_THRESHOLD > dataProviderAdapter.getItems().size()) {
			dataProviderAdapter.loadMoreItemFromOutside(this);
		}
		Item item = dataProviderAdapter.getItems().get(position);
		View view = layoutInflater.inflate(R.layout.item_detail_cell, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.item_picture);
		ImageLoader.getInstance().displayImage(item.getFirstPicUrl(), imageView);
		ImageButton favourite_botton = (ImageButton) view.findViewById(R.id.favourite_button);
		if (FavouriteItemManager.isFavouriteItem(item.getNumIid())) {
			favourite_botton.setImageResource(R.drawable.favourite);
			favourite_botton.setTag("tag");
		}
		TextView title = (TextView) view.findViewById(R.id.item_title);
		title.setText(item.getTitle());
		TextView sale = (TextView) view.findViewById(R.id.item_sales_volume);
		sale.setText("月销:"+item.getVolume().toString());
		TextView favouritr = (TextView) view.findViewById(R.id.item_favourite_volume);
		favouritr.setText("喜欢:"+item.getFavoriteCount());
		TextView price = (TextView) view.findViewById(R.id.item_price);
		price.setText("￥"+item.getPrice().toString());
		
		favourite_botton.setOnClickListener(new FavouriteOnClickListener(item,activity));
		view.findViewById(R.id.share_button).setOnClickListener(new ShareOnClickListener(item.getNumIid().toString(),activity,imageView,item.getTitle()));
		view.findViewById(R.id.view_detail_button).setOnClickListener(new ViewItemDetailOnClickListener(item.getNumIid().toString(),activity,item.getClickUrl()));
		container.addView(view);
		return view;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
		Bitmap bitmap = ((View)object).findViewById(R.id.item_picture).getDrawingCache();
		if (bitmap != null) {
			bitmap.recycle();
			System.gc();
			bitmap = null;
		}
	}

}