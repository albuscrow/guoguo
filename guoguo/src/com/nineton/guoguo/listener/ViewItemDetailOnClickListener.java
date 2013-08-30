package com.nineton.guoguo.listener;

import com.nineton.guoguo.common.PublicContainers;
import com.nineton.guoguo.controller.ItemDetailActivity;
import com.nineton.guoguo.controller.ItemWebviewActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewItemDetailOnClickListener implements OnClickListener {
	
	private String ids;
	private ItemDetailActivity activity;
	private String url;
	public ViewItemDetailOnClickListener(String ids,ItemDetailActivity activity,String url) {
		this.ids = ids;
		this.activity =activity;
		this.url = url;
	}
	
	@Override
	public void onClick(View arg0) {
		new GetDataAnsycTask().viewItemsdetail(ids);
		PublicContainers.getInstance().putData("item_url", url);
		Intent intent = new Intent(activity, ItemWebviewActivity.class);
		activity.startActivity(intent);
	}

}
