package com.nineton.guoguo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.nineton.guoguo.R;
import com.nineton.guoguo.controller.MainActivity;
import com.nineton.guoguo.data.GetDataAnsycTask;
import com.nineton.guoguo.data.GetDataAnsycTask.OnDataAnsyTaskListener;
import com.nineton.guoguo.entity.SpecialTopic;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TopicListAdapter extends MyBaseAdapter {

	private class ViewHolder{
		public TextView topicName;
		public ImageView topicPicture;
		public TextView time;
		public TextView content;
	}
	
	private Integer getNextIndex(){
		return nextIndex;
	}
	
	public TopicListAdapter(MainActivity activity) {
		super(activity);
		items = new ArrayList<SpecialTopic>();
	}
	
	private void addItem(List<SpecialTopic> specialTopic){
		this.items.addAll(specialTopic);
		notifyDataSetChanged();
		nextIndex++;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder viewHolder = null;
		if (v == null) {
			v = layoutInflater.inflate(R.layout.topic_cell, null);
			viewHolder = new ViewHolder();
			viewHolder.topicPicture = (ImageView) v.findViewById(R.id.topic_picture);
			viewHolder.topicName = (TextView) v.findViewById(R.id.topic_name);
			viewHolder.time = (TextView) v.findViewById(R.id.topic_time);
			viewHolder.content = (TextView) v.findViewById(R.id.topic_content);
			v.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) v.getTag();
		}
		
		SpecialTopic specialTopic = (SpecialTopic) getItem(position);
		if (specialTopic != null) {
			ImageLoader.getInstance().displayImage(specialTopic.getPicUrl(), viewHolder.topicPicture);
			viewHolder.topicName.setText(specialTopic.getTitle());
			viewHolder.time.setText(specialTopic.getCreateddate());
			viewHolder.content.setText(specialTopic.getZhuantidesc());
		}
		return v;
	}
	
	public void initData(final PullToRefreshBase<ListView> refreshView) {
		nextIndex = 0;
		items.clear();
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
			}

			@Override
			public void onPostExecute(Object result) {
				if (result != null) {
					items.clear();
					addItem((List<SpecialTopic>)result);
				}
				refreshView.onRefreshComplete();
			}
		}).getTopics(null, null);
	}
	
	
	public void loadMoreItem(){
		new GetDataAnsycTask().setOnDataAnsyTaskListener(new OnDataAnsyTaskListener() {

			@Override
			public void onPreExecute() {
				showLoadingView();
			}

			@Override
			public void onPostExecute(Object result) {
				hideLoadingVIew();
				if (result != null) {
					addItem((List<SpecialTopic>)result);
				}
			}
		}).getTopics(getNextIndex(), null);
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
					addItem((List<SpecialTopic>)result);
				}
			}
		}).getTopics(null, null);
	}

}
