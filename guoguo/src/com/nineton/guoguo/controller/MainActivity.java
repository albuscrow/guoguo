package com.nineton.guoguo.controller;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nineton.guoguo.R;
import com.nineton.guoguo.adapter.ClassifyListAdapter;
import com.nineton.guoguo.adapter.MyBaseAdapter;
import com.nineton.guoguo.adapter.MyViewPageAdapter;
import com.nineton.guoguo.adapter.SearchedItemListAdapter;
import com.nineton.guoguo.adapter.TopicItemListAdapter;
import com.nineton.guoguo.adapter.TopicListAdapter;
import com.nineton.guoguo.adapter.myFavouriteListAdapter;
import com.nineton.guoguo.common.AppConfig;
import com.nineton.guoguo.listener.MyOnItemClickListener;
import com.nineton.guoguo.listener.SearchOnClickListener;
import com.nineton.guoguo.listener.MyOnLastItemVisibleListener;
import com.nineton.guoguo.listener.MyOnPageChangeListener;
import com.nineton.guoguo.listener.MyOnRefreshListener;
import com.nineton.guoguo.listener.MyOnTabChangeListener;
import com.nineton.guoguo.listener.MyOnTopicClickListener;
import com.nineton.guoguo.listener.MineOnClickListener;
import com.nineton.guoguo.utils.MySensorManager;
import com.nineton.guoguo.utils.TitleManeger;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class MainActivity extends Activity{

	public static final String[] TAB_TAGS = {"item","topic","search","mytaobao"};
	private static final int[] TAB_INDICATOR_DRAWABLE_IDS = {R.layout.tag_indicator,R.layout.tag_indicator,R.layout.tag_indicator,R.layout.tag_indicator};
	private static final int[] TAB_CONTENT_VIEW_IDS = {R.id.item_display_layout,R.id.topic_view,R.id.search_view,R.id.mine};

	private LayoutInflater layoutInflater;
	private WebView taobaoWebView;
	private ViewPager pageViews;
	private TabHost tabHost;


	private View searchLayout;

	private PullToRefreshListView topicListView;
	private ListView myFavouriteView;
	private LinearLayout mineView;
	private PullToRefreshListView topicItemListView;
	private PullToRefreshListView searchItemListView;
	private ImageView vernier;
	private ImageButton backImageBotton;
	private View welcomeImageView;

	private Integer[] state = new Integer[4];
	private int position = 0;
	private boolean shutDownFlag;
	private RelativeLayout mineRoot;
	final static public Integer NORMAL = 0;
	final static public Integer SEARCH_RESULT = 1;
	final static public Integer TOPIC_LIST = 2;
	public static final Integer MINE_NEXT = 3;

	public void setState(int position,Integer state) {
		this.state[position] = state;
		backImageBotton.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FeedbackAgent agent = new FeedbackAgent(this);
		agent.sync();
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			
			@Override
			public void onUpdateReturned(int arg0, UpdateResponse arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		UmengUpdateAgent.update(this);
		super.onCreate(savedInstanceState);
		AppConfig.initConfig(this);
		AppConfig.isWifiConnect();
		initUI();
		new Handler().postDelayed(new Runnable() {

			public void run() {
				welcomeImageView = findViewById(R.id.welcome_image);
				welcomeImageView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.welcome_page_fade_out));
				welcomeImageView.setVisibility(View.GONE);
			}
		}, 2000);
	}

	private void initUI(){
		setContentView(R.layout.activity_main);
		layoutInflater = getLayoutInflater();
		TitleManeger.init(this);
		vernier = (ImageView)findViewById(R.id.vernier_image_view);
		if (!AppConfig.HAS_WIFI) {
			Toast.makeText(this, "网络不给力哦，尝试连接WiFi吧", Toast.LENGTH_SHORT).show();
		}
		initTagHost();
		initVernier();
		backImageBotton = (ImageButton)findViewById(R.id.back_image_button);
		backImageBotton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (state[position] == TOPIC_LIST) {
					state[position] = NORMAL;
					backImageBotton.setVisibility(View.GONE);
					Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_switch_out);
					topicListView.setVisibility(View.VISIBLE);
					topicItemListView.setVisibility(View.GONE);
					topicItemListView.setAnimation(animation);
					TitleManeger.restoreTitleTopic();
				}else if(state[position] == SEARCH_RESULT){
					state[position] = NORMAL;
					backImageBotton.setVisibility(View.GONE);
					Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_switch_out);
					searchItemListView.setVisibility(View.GONE);
					searchLayout.setVisibility(View.VISIBLE);
					searchItemListView.startAnimation(animation);
					TitleManeger.restoreTitleSearch();
				}else if (state[position] == MINE_NEXT) {
					if (taobaoWebView.canGoBack()) { 
						taobaoWebView.goBack();
					}else {
						backToMine();
						state[position] = NORMAL;
						backImageBotton.setVisibility(View.GONE);
						TitleManeger.restoreTitleMine();
					}
				}
			}
		});
	}

	@SuppressLint("NewApi")
	private void initVernier() {
		final View view = tabHost.getTabWidget().getChildTabViewAt(0).findViewById(R.id.tab_label);
		view.post(new Runnable() {
			@Override
			public void run() {
				float x = view.getX()+(view.getWidth()-vernier.getWidth())/2;
				vernier.setX(x);
				vernier.setTag(x);
			}
		});
	}

	@SuppressLint("NewApi")
	public void moveNervier(int index){
		final float newX = getNewX(index);
		float x = (Float) vernier.getTag();
		vernier.setTag(newX);
		TranslateAnimation animation = new TranslateAnimation(x, newX, 0,0 );
		animation.setDuration(100);
		animation.setFillAfter(true);
		vernier.startAnimation(animation);
	}

	@SuppressLint("NewApi")
	private float getNewX(int index) {
		View view = tabHost.getTabWidget().getChildTabViewAt(index).findViewById(R.id.tab_label);
		return view.getX();
	}
	
	private void initTagHost() {
		tabHost = (TabHost) findViewById(R.id.my_tabhost);
		tabHost.setup();
		tabHost.setOnTabChangedListener(new MyOnTabChangeListener(this));
		initItemDisplayViewPager();
		initTopicDisplayList();
		initSearchView();
		initTaobaoWebView();
	}

	private void initSearchView() {
		TabSpec tabSpec = tabHost.newTabSpec(TAB_TAGS[2]);
		ImageView tagIndicator = (ImageView) getTagIndicator(TAB_INDICATOR_DRAWABLE_IDS[2]);
		tagIndicator.setImageResource(R.drawable.tab_search);
		tabSpec.setIndicator(tagIndicator).setContent(TAB_CONTENT_VIEW_IDS[2]);
		tabHost.addTab(tabSpec);
		searchLayout = findViewById(R.id.search_layout);
		searchItemListView = (PullToRefreshListView)findViewById(R.id.brife_item_list);
		ListView classifyListView = (ListView) findViewById(R.id.classify_list);
		ClassifyListAdapter classifyListAdapter = new ClassifyListAdapter(this);
		classifyListAdapter.initData();
		classifyListView.setAdapter(classifyListAdapter);
		classifyListView.requestFocus();
		findViewById(R.id.dosearch_button).setOnClickListener(new SearchOnClickListener(this,SearchOnClickListener.DYNAMIC));
	}

	private void initTaobaoWebView() {
		TabSpec tabSpec = tabHost.newTabSpec(TAB_TAGS[3]);
		ImageView tagIndicator = (ImageView) getTagIndicator(TAB_INDICATOR_DRAWABLE_IDS[3]);
		tagIndicator.setImageResource(R.drawable.tab_user);
		tabSpec.setIndicator(tagIndicator)
		.setContent(TAB_CONTENT_VIEW_IDS[3]);
		tabHost.addTab(tabSpec);
		taobaoWebView = (WebView) findViewById(R.id.taobao_web_view);
		myFavouriteView = (ListView) findViewById(R.id.my_favourite_list);
		mineView = (LinearLayout) findViewById(R.id.mine_choose);
		mineRoot = (RelativeLayout) findViewById(R.id.mine);
		MineOnClickListener mineOnClickListener = new MineOnClickListener(this);
		((RelativeLayout)findViewById(R.id.my_favourite)).setOnClickListener(mineOnClickListener);
		((RelativeLayout)findViewById(R.id.clear_cach)).setOnClickListener(mineOnClickListener);
		((RelativeLayout)findViewById(R.id.my_order)).setOnClickListener(mineOnClickListener);
		((RelativeLayout)findViewById(R.id.colloction)).setOnClickListener(mineOnClickListener);
		((RelativeLayout)findViewById(R.id.shop_car)).setOnClickListener(mineOnClickListener);
		((RelativeLayout)findViewById(R.id.connect_us)).setOnClickListener(mineOnClickListener);
		((RelativeLayout)findViewById(R.id.update)).setOnClickListener(mineOnClickListener);
	}

	private void initTopicDisplayList() {
		TabSpec tabSpec = tabHost.newTabSpec(TAB_TAGS[1]);
		ImageView tagIndicator = (ImageView) getTagIndicator(TAB_INDICATOR_DRAWABLE_IDS[1]);
		tagIndicator.setImageResource(R.drawable.tab_topic);
		tabSpec.setIndicator(tagIndicator).setContent(TAB_CONTENT_VIEW_IDS[1]);
		tabHost.addTab(tabSpec);
		topicListView = (PullToRefreshListView) findViewById(R.id.topic_list);
		TopicListAdapter topicListAdapter = new TopicListAdapter(this);
		topicListAdapter.initData();
		topicListView.setAdapter(topicListAdapter);
		topicListView.setOnRefreshListener(new MyOnRefreshListener(topicListAdapter));
		topicListView.setOnItemClickListener(new MyOnTopicClickListener(this));
		topicItemListView = (PullToRefreshListView) findViewById(R.id.topic_item_list);
	}

	private void initItemDisplayViewPager() {
		TabSpec tabSpec = tabHost.newTabSpec(TAB_TAGS[0]);
		ImageView tagIndicator = (ImageView) getTagIndicator(TAB_INDICATOR_DRAWABLE_IDS[0]);
		tagIndicator.setImageResource(R.drawable.tab_home);
		tabSpec.setIndicator(tagIndicator)
		.setContent(TAB_CONTENT_VIEW_IDS[0]);
		tabHost.addTab(tabSpec);
		pageViews = (ViewPager) findViewById(R.id.item_display_view_page);
		MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(this);
		pageViews.setAdapter(myViewPageAdapter);
		pageViews.setOnPageChangeListener(new MyOnPageChangeListener(this,myViewPageAdapter.getRandomItemListAdapter()));
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (state[position] == TOPIC_LIST) {
				state[position] = NORMAL;
				backImageBotton.setVisibility(View.GONE);
				Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_switch_out);
				topicListView.setVisibility(View.VISIBLE);
				topicItemListView.setVisibility(View.GONE);
				topicItemListView.setAnimation(animation);
				TitleManeger.restoreTitleTopic();
			}else if(state[position] == SEARCH_RESULT){
				state[position] = NORMAL;
				backImageBotton.setVisibility(View.GONE);
				Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_switch_out);
				searchItemListView.setVisibility(View.GONE);
				searchLayout.setVisibility(View.VISIBLE);
				searchItemListView.startAnimation(animation);
				TitleManeger.restoreTitleSearch();
			}else if (state[position] == MINE_NEXT) {
				if (taobaoWebView.canGoBack()) { 
					taobaoWebView.goBack();
				}else {
					state[position] = NORMAL;
					backImageBotton.setVisibility(View.GONE);
					backToMine();
					TitleManeger.restoreTitleMine();
				}
			}else {
				if (shutDownFlag) {
					super.onKeyDown(keyCode, event);
				}
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				shutDownFlag = true;
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						shutDownFlag = false;
					}
				}).start();
			}
		}
		return true;
	}

	private void backToMine() {
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_switch_out);
		if (myFavouriteView.getVisibility() == View.VISIBLE) {
			myFavouriteView.setVisibility(View.GONE);
			myFavouriteView.startAnimation(animation);
		}else {
			taobaoWebView.setVisibility(View.GONE);
			taobaoWebView.startAnimation(animation);
		}
		mineView.setVisibility(View.VISIBLE);
	}

	private View getTagIndicator(int drawableId) {
		return layoutInflater.inflate(drawableId, null);
	}

	public void goMyFavourite() {
		TitleManeger.setTitleMine("我的喜欢");
		Animation animation = AnimationUtils.loadAnimation(this,R.anim.view_switch_in);
		myFavouriteListAdapter myFavOuriteListAdapter = new myFavouriteListAdapter(MainActivity.this);
		myFavouriteView.setAdapter(myFavOuriteListAdapter);
		myFavouriteView.setOnItemClickListener(new MyOnItemClickListener(myFavOuriteListAdapter,MainActivity.this, false));
		myFavOuriteListAdapter.initData();
		mineView.setVisibility(View.GONE);
		myFavouriteView.setVisibility(View.VISIBLE);
		myFavouriteView.startAnimation(animation);
	}

	public void goMyTaobao(String Url,String title) {
		TitleManeger.setTitleMine(title);
		mineView.removeView(taobaoWebView);
		taobaoWebView = (WebView) getLayoutInflater().inflate(R.layout.taobao_web_view, null);
		taobaoWebView.loadUrl(Url);
		mineRoot.addView(taobaoWebView);
		final Animation animation = AnimationUtils.loadAnimation(this,R.anim.view_switch_in);
		taobaoWebView.setWebViewClient(new WebViewClient(){
			private boolean flag = true;
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				findViewById(R.id.loading).setVisibility(View.VISIBLE);
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if (flag) {
					taobaoWebView.startAnimation(animation);
					flag = false;
				}
				findViewById(R.id.loading).setVisibility(View.GONE);
			}
		});
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				mineView.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
	}

	public void goTopicItemListView(Long ztid) {
		
		MyBaseAdapter adapter = new TopicItemListAdapter(this,ztid);
		topicItemListView.setAdapter(adapter);
		topicItemListView.setOnRefreshListener(new MyOnRefreshListener(adapter));
		topicItemListView.setOnLastItemVisibleListener(new MyOnLastItemVisibleListener(adapter));
		topicItemListView.setOnItemClickListener(new MyOnItemClickListener(adapter, this, true));
		adapter.initData(topicItemListView,topicListView);
	}

	public void goSearchItemListView(Long cid, String keyword){

		SearchedItemListAdapter adapter = new SearchedItemListAdapter(this,cid,keyword);
		searchItemListView.setAdapter(adapter);
		searchItemListView.setOnRefreshListener(new MyOnRefreshListener(adapter));
		searchItemListView.setOnLastItemVisibleListener(new MyOnLastItemVisibleListener(adapter));
		searchItemListView.setOnItemClickListener(new MyOnItemClickListener(adapter,this,true));
		adapter.initData(searchItemListView,searchLayout);
		
	}

	public void setPosition(int temp) {
		position = temp;
	}

	@Override
	protected void onStart() {
		super.onStart();
		MySensorManager.arriveViewPager();
		if (!AppConfig.HAS_WIFI) {
			if (AppConfig.isWifiConnect()) {
				initUI();
				welcomeImageView = findViewById(R.id.welcome_image);
				welcomeImageView.setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	

	@Override
	protected void onPause() {
		super.onPause();
		MySensorManager.leaveViewPager();
		MobclickAgent.onPause(this);
	}
}
