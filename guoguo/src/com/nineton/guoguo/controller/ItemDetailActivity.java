package com.nineton.guoguo.controller;

import com.nineton.guoguo.R;
import com.nineton.guoguo.adapter.ItemDetailViewPagerAdapter;
import com.nineton.guoguo.adapter.MyBaseAdapter;
import com.nineton.guoguo.common.PublicContainers;
import com.nineton.guoguo.utils.FavouriteItemManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ItemDetailActivity extends Activity {

	private MyBaseAdapter adapter;
	private Integer position;
	public UMSocialService socializeController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		socializeController = UMServiceFactory.getUMSocialService(
				ItemDetailActivity.class.getName(), RequestType.SOCIAL);
		SocializeConfig socializeConfig = socializeController.getConfig();
		socializeConfig.setPlatforms(SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA,
				SHARE_MEDIA.TENCENT);
		super.onCreate(savedInstanceState);
		initObject();
		initUI();
	}

	private void initObject() {
		PublicContainers instance = PublicContainers.getInstance();
		adapter = (MyBaseAdapter) instance.getData("adapter");
		position = (Integer) instance.getData("position");

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	

	public void setDetailTitle(String keyword) {
		((TextView)findViewById(R.id.header_title_text_view)).setText(this.getResources().getString(R.string.item_detail));
	}

	private void initUI(){
		setContentView(R.layout.activity_item_detail);
		this.setDetailTitle(getResources().getString(R.string.item_detail));
		ItemDetailViewPagerAdapter itemDetailViewPagerAdapter = new ItemDetailViewPagerAdapter(this,adapter);
		ViewPager itemDetailView = (ViewPager) findViewById(R.id.item_detail_display_view_page);
		itemDetailView.setAdapter(itemDetailViewPagerAdapter);
		itemDetailView.setCurrentItem(position);
		((ImageButton)findViewById(R.id.back_image_button)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			ItemDetailActivity.this.finish();
			}
		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		FavouriteItemManager.save();
		MobclickAgent.onPause(this);
	}
	
	

}
