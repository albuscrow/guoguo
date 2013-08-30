package com.nineton.guoguo.controller;

import com.nineton.guoguo.R;
import com.nineton.guoguo.common.PublicContainers;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ItemWebviewActivity extends Activity {

	private WebView taobaoWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUi();
	}
	
	@Override
	protected void onPause() {
		super.onStart();
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPause(this);
	}

	private void initUi() {
		setContentView(R.layout.activity_item_webview);
		taobaoWebView = (WebView)findViewById(R.id.item_detail_webView);
		taobaoWebView.loadUrl((String) PublicContainers.getInstance().getData("item_url"));
		taobaoWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				findViewById(R.id.loading).setVisibility(View.VISIBLE);
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				findViewById(R.id.loading).setVisibility(View.GONE);
			}
		});
	}

}
