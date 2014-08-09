package com.nineton.guoguo.adapter;

import java.util.ArrayList;
import java.util.Collection;

import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.entity.Item;
import com.nineton.guoguo.utils.FavouriteItemManager;

import android.widget.ListAdapter;
import android.widget.Toast;

public class myFavouriteListAdapter extends MyBaseAdapter implements
		ListAdapter {

	public myFavouriteListAdapter(MainActivity activity) {
		super(activity);
		items = new ArrayList<Item>();
	}

	public void initData(){
		Collection<Item> items = FavouriteItemManager.getFavouriteItems();
		if (items.size() == 0) {
			Toast.makeText(activity, "您还没有收藏宝贝哦！", Toast.LENGTH_LONG).show();
		}else {
			this.items.addAll(items);
		}
	}
}
