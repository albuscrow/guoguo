package com.nineton.guoguo.listener;

import com.nineton.guoguo.R;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.entity.SpecialTopic;
import com.nineton.guoguo.utils.TitleManeger;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class MyOnTopicClickListener implements OnItemClickListener {
	private MainActivity activity;

	public MyOnTopicClickListener(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TextView textView = (TextView)arg1.findViewById(R.id.topic_name);
		TitleManeger.setTitleTopic(textView.getText().toString());
		activity.setState(1,MainActivity.TOPIC_LIST);
		SpecialTopic topic = (SpecialTopic) arg0.getAdapter().getItem(arg2);
		activity.goTopicItemListView(topic.getZtid());
	}

}
