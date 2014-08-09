package com.nineton.guoguo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends WebView {

	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public static String TAOBAO_URL = "http://h5.m.taobao.com/my/index.htm";
	public static String BUY_CAR_URL = "http://cart.m.taobao.com/myCart.htm";
	public static String COLLOCT_URL = "http://fav.m.taobao.com/my_collect_list.htm";
	public static String ORDER_URL = "http://tm.m.taobao.com/order_list.htm";

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		this.getSettings().setJavaScriptEnabled(true);
		this.setWebViewClient(new WebViewClient());
	}
	
	public void setUrl(String url){
		this.loadUrl(url);
	}
}
