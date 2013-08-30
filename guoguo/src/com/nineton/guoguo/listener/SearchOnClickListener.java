package com.nineton.guoguo.listener;

import com.nineton.guoguo.R;
import com.nineton.guoguo.adapter.ClassifyListAdapter;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.utils.TitleManeger;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SearchOnClickListener implements OnClickListener {


	public static final Integer DYNAMIC = 0;
	public static final Integer STATIC = 1;
	private RelativeLayout parentView;
	private Integer mode;
	private MainActivity activity;

	public SearchOnClickListener(MainActivity activity,Integer mode) {
		this.parentView = (RelativeLayout) activity.findViewById(R.id.search_view);
		this.activity = activity;
		this.mode = mode;
	}

	@Override
	public void onClick(View arg0) {
		Long cid = null;
		String keyword = null;
		if (mode == DYNAMIC) {
			keyword = ((EditText)parentView.findViewById(R.id.search_keyword_edit_text)).getText().toString();
		}else {
			String tag = (String)arg0.getTag();
			String[] searchInformation = tag.split(ClassifyListAdapter.DECOLLATOR);
			cid = Long.valueOf(searchInformation[0]);
			keyword = searchInformation[1];
		}
		if (keyword == null || keyword.length() == 0) {
			Toast.makeText(activity, "请输入关键字", Toast.LENGTH_SHORT).show();
			return; 
		}
		activity.goSearchItemListView(cid,keyword);
		activity.setState(2,MainActivity.SEARCH_RESULT);
		TitleManeger.setTitleSearch(keyword);
	}

}
