package com.nineton.guoguo.common;

import com.nineton.guoguo.utils.FavouriteItemManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class GuoguoApplication extends Application {

	private String TAG = "GougouApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader();
		FavouriteItemManager.init(this);
	}

	

	private void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheInMemory()
		.cacheOnDisc()
		.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.defaultDisplayImageOptions(defaultOptions)
		.build();
		ImageLoader.getInstance().init(config);
	}
}
