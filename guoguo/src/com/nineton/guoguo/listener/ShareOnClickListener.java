package com.nineton.guoguo.listener;

import com.nineton.guoguo.controller.ItemDetailActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

import android.view.View;
import android.view.View.OnClickListener;

public class ShareOnClickListener implements OnClickListener {

	private String ids;
	private ItemDetailActivity activity;
	private View imageView;
	private String title;
	
	public ShareOnClickListener(String ids,ItemDetailActivity activity, View view, String title) {
		this.ids = ids;
		this.activity =activity;
		this.imageView = view;
		this.title = title;
	}

	@Override
	public void onClick(View arg0) {
		com.umeng.socom.Log.LOG = true;
		UMSocialService socializeController = activity.socializeController;
		socializeController.setShareContent(title);
		imageView.setDrawingCacheEnabled(true);
		socializeController.setShareImage(new UMImage(activity,imageView.getDrawingCache()));
		socializeController.openShare(activity, false);
		new GetDataAnsycTask().shareItems(ids);
	}

}
