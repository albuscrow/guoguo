package com.nineton.guoguo.listener;

import com.nineton.guoguo.R;
import com.nineton.guoguo.controller.ItemDetailActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;
import com.nineton.guoguo.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.nineton.guoguo.entity.Item;
import com.nineton.guoguo.utils.FavouriteItemManager;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class FavouriteOnClickListener implements OnClickListener {
	
	private Item item;
	private ItemDetailActivity activity;
	public FavouriteOnClickListener(Item item,ItemDetailActivity activity) {
		this.item = item;
		this.activity =activity;
	}
	
	@Override
	public void onClick(View view) {
		final ImageButton imageButton = (ImageButton) view;
		switchIcon(imageButton);
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {
			
			@Override
			public void onPreExecute() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPostExecute(Object result) {
				if (imageButton.getTag() != null) {
					Toast.makeText(activity, activity.getResources().getString(R.string.favourite_complete), Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.unfavourite_complete), Toast.LENGTH_SHORT).show();
				}
				
			}
		}).setItemsFavourite(item.getNumIid().toString());
	}

	private void switchIcon(ImageButton imageButton) {
		if (imageButton.getTag() != null) {
			imageButton.setTag(null);
			imageButton.setImageResource(R.drawable.unfavourite);
			FavouriteItemManager.removeById(item.getNumIid());
		}else {
			imageButton.setTag("tag");
			imageButton.setImageResource(R.drawable.favourite);
			FavouriteItemManager.addItem(item);
		}
	}

}
