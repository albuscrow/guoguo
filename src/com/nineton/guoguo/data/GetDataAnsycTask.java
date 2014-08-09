package com.nineton.guoguo.data;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.nineton.guoguo.common.AppConfig;
import com.nineton.guoguo.entity.Classify;
import com.nineton.guoguo.entity.HotItem;
import com.nineton.guoguo.entity.HotKeyword;
import com.nineton.guoguo.entity.ItemDetail;
import com.nineton.guoguo.entity.RandomItem;
import com.nineton.guoguo.entity.Searcheditem;
import com.nineton.guoguo.entity.SpecialTopic;
import com.nineton.guoguo.entity.TmallSpecialOffer;
import com.nineton.guoguo.entity.TopicItem;
import com.nineton.guoguo.utils.JsonTarget;

import android.os.AsyncTask;
import android.util.Log;

public class GetDataAnsycTask extends AsyncTask<Object, Void, Object> {

	private static final String TYPE_NOT_SUPPORTED = "type is not supported";
	private static final String TAG = "GetDataAnsycTask";

	public interface OnDataAnsyTaskListener{
		/**
		 * 失败传入null
		 * @param result
		 */
		void onPostExecute(Object result);
		void onPreExecute();
	}

	public GetDataAnsycTask setOnDataAnsyTaskListener(OnDataAnsyTaskListener onDataAnsyTaskListener){
		this.onDataAnsyTaskListener = onDataAnsyTaskListener;
		return this;
	}

	private OnDataAnsyTaskListener onDataAnsyTaskListener;
	private JsonTarget jsonTarget;


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (onDataAnsyTaskListener != null) {
			onDataAnsyTaskListener.onPreExecute();
		}
	}

	/**
	 * 第一个参数为请求数据的类型，后面为请求的url参数
	 */
	@Override
	protected Object doInBackground(Object... params) {
		jsonTarget = (JsonTarget) params[0];
		Type type;
		Object result = null;
		if (AppConfig.isWifiConnect()) {
			switch (jsonTarget) {
			case HOT_ITEM_LIST:
				type = new TypeToken<List<HotItem>>(){}.getType();
				result = DataProvider.getHotItem(type,(Integer)params[1], (Integer)params[2]);
				break;
			case TMALL_SOECIAL_LIST:
				type = new TypeToken<List<TmallSpecialOffer>>(){}.getType();
				result = DataProvider.getTmallSpecialOffer(type , (Integer)params[1] , (Integer)params[2]);
				break;
			case RANDOM_ITEM_LIST:
				type = new TypeToken<List<RandomItem>>(){}.getType();
				result = DataProvider.getRandomItems(type, (Integer)params[1]);
				break;
			case SPECIAL_TOPIC_LIST:
				type = new TypeToken<List<SpecialTopic>>(){}.getType();
				result = DataProvider.getSpecialTopics(type, (Integer)params[1], (Integer)params[2]);
				break;
			case TOPIC_ITEMS_LIST:
				type = new TypeToken<List<TopicItem>>(){}.getType();
				result = DataProvider.getTopicItems(type, (Integer)params[1], (Integer)params[2], (Long)params[3]);
				break;
			case ITEM_DETAIL:
				type = ItemDetail.class;
				result = DataProvider.getItemDetail(type, (Long)params[1]);
				break;
			case CLASSIFY_LIST:
				type = new TypeToken<List<Classify>>(){}.getType();
				result = DataProvider.getAllClassify(type);
				break;
			case HOT_KEYWORD_LIST:
				type = new TypeToken<List<HotKeyword>>(){}.getType();
				result = DataProvider.getHotKeywords(type);
				break;
			case SEARCHED_ITEM_LIST:
				type = new TypeToken<List<Searcheditem>>(){}.getType();
				result = DataProvider.getSearchedItems(type, (Integer)params[1], (Integer)params[2],(Long)params[3],(String) params[4]);
				break;
			case VOID_OPERATION:
				DataProvider.operateitems((String) params[1], (String) params[2]);
				break;
			default:
				Log.e(TAG, TYPE_NOT_SUPPORTED);
				break;
			}
		}
		return result;
	}

	protected void onPostExecute(Object result) {
		if (onDataAnsyTaskListener != null) {
			onDataAnsyTaskListener.onPostExecute(result);
		}
	};


	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 */
	public void getHotItem(Integer pageIndex,Integer pageSize){
		this.execute(JsonTarget.HOT_ITEM_LIST,pageIndex,pageSize);
	}


	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 */
	public void getTopics(Integer pageIndex,Integer pageSize){
		this.execute(JsonTarget.SPECIAL_TOPIC_LIST,pageIndex,pageSize);
	}

	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 */
	public void getHotKeys(){
		this.execute(JsonTarget.HOT_KEYWORD_LIST);
	}

	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 */
	public void getTmallSpecial(Integer pageIndex,Integer pageSize){
		this.execute(JsonTarget.TMALL_SOECIAL_LIST,pageIndex,pageSize);
	}


	/**
	 * 
	 * @param count
	 */
	public void getRandomItem(Integer count) {
		this.execute(JsonTarget.RANDOM_ITEM_LIST,count);
	}


	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param TopicId
	 */
	public void getTopicItem(Integer pageIndex,Integer pageSize,Long TopicId){
		this.execute(JsonTarget.TOPIC_ITEMS_LIST,pageIndex,pageSize,TopicId);
	}

	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param cid
	 * @param keyword
	 */
	public void getSearchedItems(Integer pageIndex,
			Integer pageSize,Long cid,String keyword) {
		this.execute(JsonTarget.SEARCHED_ITEM_LIST,pageIndex,pageSize,cid,keyword);
	};

	/**
	 * 
	 * @param ids
	 */
	public void setItemsFavourite(String ids){
		this.execute(JsonTarget.VOID_OPERATION,ids,DataProvider.FAVOURITE_OPERATION);
	}

	/**
	 * 
	 * @param ids
	 */
	public void viewItemsdetail(String ids){
		this.execute(JsonTarget.VOID_OPERATION,ids,DataProvider.DETAIL_OPERATION);
	}

	/**
	 * 
	 * @param ids
	 */
	public void shareItems(String ids){
		this.execute(JsonTarget.VOID_OPERATION,ids,DataProvider.SHOW_OPERATION);
	}


	/**
	 * 
	 */
	public void getClassify() {
		this.execute(JsonTarget.CLASSIFY_LIST);
	};

}
