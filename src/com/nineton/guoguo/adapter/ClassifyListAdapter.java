package com.nineton.guoguo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nineton.guoguo.R;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;
import com.nineton.guoguo.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.nineton.guoguo.entity.Classify;
import com.nineton.guoguo.entity.HotKeyword;
import com.nineton.guoguo.listener.SearchOnClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ClassifyListAdapter extends MyBaseAdapter {



	public static final String DECOLLATOR = ":;:;";
	private static final Integer COLUMN_NUM = 4;
	List<View> viewStore;
	private SearchOnClickListener searchOnClickListener;

	public ClassifyListAdapter(MainActivity activity) {
		super(activity);
		items = new ArrayList<Classify>();
		viewStore = new ArrayList<View>();
		searchOnClickListener = new SearchOnClickListener(activity,SearchOnClickListener.STATIC);
	}

	private void addItem(List<Classify> classify){
		this.items.addAll(classify);
		for (Classify item : classify) {
			View view = layoutInflater.inflate(R.layout.classify_cell, null);
			((TextView)view.findViewById(R.id.classify_name)).setText(item.getName());
			LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.hot_keywords);
			List<HotKeyword> hotKeywords = item.getHotKeywords();
			Integer sizeOfHotkeyword = hotKeywords.size();
			Integer rowNum = sizeOfHotkeyword/COLUMN_NUM;
			for (int i = 0; i < rowNum; i++) {
				LinearLayout subLinearLayout = new LinearLayout(activity);
				subLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				subLinearLayout.setLayoutParams(layoutParams);
				subLinearLayout.setWeightSum(4.0f);
				for (int j = 0; j < COLUMN_NUM; j++) {
					HotKeyword hotKeyword = hotKeywords.get(i*4+j);
					View hotkeyWordView = layoutInflater.inflate(R.layout.hot_word_cell, null);
					ImageView imageView = (ImageView)hotkeyWordView.findViewById(R.id.hot_word_picture);
					ImageLoader.getInstance().displayImage(hotKeyword.getPicurl(), imageView);
					String hotKeywordName = hotKeyword.getName();
					((TextView)hotkeyWordView.findViewById(R.id.hot_word_name)).setText(hotKeywordName);
					String tag = item.getCid().toString()+DECOLLATOR+hotKeywordName;
					hotkeyWordView.setTag(tag);
					hotkeyWordView.setOnClickListener(searchOnClickListener);
					subLinearLayout.addView(hotkeyWordView);
					LinearLayout.LayoutParams hotkeyWordViewLayoutParams = (android.widget.LinearLayout.LayoutParams) hotkeyWordView.getLayoutParams();
					hotkeyWordViewLayoutParams.weight = 1;
				}
				linearLayout.addView(subLinearLayout);
			}
			viewStore.add(view);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return viewStore.get(position);
	}


	@Override
	public void initData() {
		nextIndex = 0;
		items.clear();
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
				showLoadingView();
			}

			@Override
			public void onPostExecute(Object result) {
				hideLoadingVIew();
				if (result != null) {
					addItem((List<Classify>)result);
				}
			}
		}).getClassify();
	}

}
