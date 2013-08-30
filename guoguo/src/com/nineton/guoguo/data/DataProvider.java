package com.nineton.guoguo.data;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.R.integer;
import android.R.string;

import com.nineton.guoguo.entity.Classify;
import com.nineton.guoguo.entity.HotItem;
import com.nineton.guoguo.entity.HotKeyword;
import com.nineton.guoguo.entity.ItemDetail;
import com.nineton.guoguo.entity.RandomItem;
import com.nineton.guoguo.entity.Searcheditem;
import com.nineton.guoguo.entity.SpecialTopic;
import com.nineton.guoguo.entity.TaobaoGood;
import com.nineton.guoguo.entity.TmallSpecialOffer;
import com.nineton.guoguo.entity.TopicItem;
import com.nineton.guoguo.utils.JsonTarget;
import com.nineton.guoguo.utils.JsonUtils;
import com.nineton.guoguo.utils.NetUtils;

public class DataProvider {

	private static final String TAG = "DataProvider";
	public static final String BASE_URL = "http://365smz.com:9080/TaoShiKe/";
	private static final String HOTITEM_URL = BASE_URL+"tsk/taobaoke/hotitem";
	private static final String TmallSpecialOffer_URL = BASE_URL+"tsk/tmall/temaiitem";
	private static final String RANDOM_GOODS_URL = BASE_URL+"tsk/randomitem";
	private static final String SPECIAL_TOPIC_URL = BASE_URL+"tsk/zhuanti/list";
	private static final String TOPIC_ITEMS_URL = BASE_URL+"tsk/zhuanti/item";
	private static final String ITEMS_DETAIL_URL = BASE_URL+"tsk/item/detail";
	private static final String ALL_CLASSIFY_URL = BASE_URL+"tsk/category";
	private static final String KEYWORDS_URL = BASE_URL+"tsk/hotkeyword";
	private static final String SEARCH_GOODS_URL = BASE_URL+"tsk/keyword/item";
	private static final String GOODS_OPERATION_URL = BASE_URL+"tsk/item/operation";




	private static final String PAGE_INDEX = "pageindex";
	private static final String PAGE_SIZE = "pagesize";
	private static final String COUNT = "count";
	private static final String FLAG = "flag";
	private static final String FLAG_STATIC = "tsk_static";
	private static final String FLAG_OPERATION = "item_operation";
	private static final String TOPIC_ID = "zhuantiid";
	private static final String ITEM_ID = "numiid";
	private static final String ITEM_IDS = "numiids";
	private static final String OPERATION = "operation";
	public static final String FAVOURITE_OPERATION = "like";
	public static final String SHOW_OPERATION = "collect";
	public static final String DETAIL_OPERATION = "viewdetail";
	private static final Long All_CLASSIFY = 50002766L;
	private static final String CID = "cid";
	private static final String KEYWORD = "keyword";
	private static final String RANDOM = "random";

	/**
	 * 失败返回null
	 * @param type
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */

	static List<HotItem> getHotItem(Type type,Integer pageIndex,Integer pageSize){
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageIndex != null) {
			params.put(PAGE_INDEX , pageIndex);
		}
		if (pageSize != null) {
			params.put(PAGE_SIZE , pageSize);
		}
		String json = NetUtils.getPostResult(params,HOTITEM_URL);
		List<HotItem> result = null;
		if (json != null) {
			result = (List<HotItem>) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}

	/**
	 * 失败返回null
	 * @param type
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	static List<TmallSpecialOffer> getTmallSpecialOffer(Type type , Integer pageIndex,Integer pageSize){
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageIndex != null) {
			params.put(PAGE_INDEX , pageIndex);
		}
		if (pageSize != null) {
			params.put(PAGE_SIZE , pageSize);
		}
		params.put(FLAG, FLAG_STATIC);
		String json = NetUtils.getPostResult(params,TmallSpecialOffer_URL);
		List<TmallSpecialOffer> result = null;
		if (json != null) {
			result = (List<TmallSpecialOffer>) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}

	/**
	 * 失败返回null
	 * @param type
	 * @param count
	 * @return
	 */
	static List<RandomItem> getRandomItems(Type type , Integer count){
		Map<String, Object> params = new HashMap<String, Object>();
		if (count == null) {
			count = 20;
		}
		params.put(COUNT , count);
		params.put(RANDOM, getRandom());
		String json = NetUtils.getPostResult(params,RANDOM_GOODS_URL);
		List<RandomItem> result = null;
		if (json != null) {
			result = (List<RandomItem>) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}

	/**
	 * 返回1——99的整数
	 * @return
	 */
	private static int getRandom() {
		return new Random().nextInt(99)+1;
	}

	/**
	 * 失败返回null
	 * @param type
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	static List<SpecialTopic> getSpecialTopics(Type type,Integer pageIndex,Integer pageSize){
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageIndex != null) {
			params.put(PAGE_INDEX , pageIndex);
		}
		if (pageSize != null) {
			params.put(PAGE_SIZE , pageSize);
		}
		params.put(FLAG, FLAG_STATIC);
		String json = NetUtils.getPostResult(params,SPECIAL_TOPIC_URL);
		List<SpecialTopic> result = null;;
		if (json != null) {
			result = (List<SpecialTopic>) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}

	/**
	 * 失败返回null
	 * @param type
	 * @param pageIndex
	 * @param pageSize
	 * @param topicId
	 * @return
	 */

	static List<TopicItem> getTopicItems(Type type,Integer pageIndex,Integer pageSize,Long topicId){
		Map<String,Object> params = new HashMap<String,Object>();
		if (pageIndex != null) {
			params.put(PAGE_INDEX,pageIndex);
		}
		if(pageSize != null){
			params.put(PAGE_SIZE, pageSize);
		}
		params.put(TOPIC_ID,topicId);
		String json = NetUtils.getPostResult(params, TOPIC_ITEMS_URL);
		List<TopicItem> result = null;
		if(json != null){
			result = (List<TopicItem>) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}
	/**
	 * 失败返回null
	 * @param type
	 * @param itemId
	 * @return
	 */

	static ItemDetail getItemDetail(Type type,Long itemId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(ITEM_ID, itemId);
		String json = NetUtils.getPostResult(params, ITEMS_DETAIL_URL);
		ItemDetail result = null;		
		if (json != null) {
			result = (ItemDetail) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}
	/**
	 * 失败返回null
	 * @param type
	 * @return
	 */
	static List<Classify> getAllClassify(Type type){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(FLAG, FLAG_STATIC);
		String json = NetUtils.getPostResult(params, ALL_CLASSIFY_URL);
		List<Classify> result = null;
		if (json != null) {
			result = (List<Classify>) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}

	/**
	 * 失败返回null
	 * @param type
	 * @return
	 */
	static List<HotKeyword> getHotKeywords(Type type){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(FLAG, FLAG_STATIC);
		String json = NetUtils.getPostResult(params, KEYWORDS_URL);
		List<HotKeyword> result = null;
		if (json != null) {
			result = (List<HotKeyword>) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}

	/**
	 * 失败或者未搜到结果返回null
	 * @param type
	 * @param pageIndex
	 * @param pageSize
	 * @param cid 若为null，则为根据动态搜索，反之为静态搜索
	 * @param keyWord 动态搜索关键字任意，静态搜索关键字由服务器提供
	 * @return
	 */

	static List<Searcheditem> getSearchedItems(Type type,Integer pageIndex,Integer pageSize,Long cid,String keyWord){
		Map<String, Object> params = new HashMap<String, Object>();
		if (pageIndex != null) {
			params.put(PAGE_INDEX , pageIndex);
		}
		if (pageSize != null) {
			params.put(PAGE_SIZE , pageSize);
		}
		if (cid == null) {
			cid = All_CLASSIFY;
		}
		params.put(CID, cid);
		params.put(KEYWORD, keyWord);
		String json = NetUtils.getPostResult(params, SEARCH_GOODS_URL);
		List<Searcheditem> result = null;
		if (json != null) {
			result = (List<Searcheditem>) JsonUtils.parseJsonResult(type, json);
		}
		return result;
	}

	/**
	 * 对item进行操作
	 * @param itemIds
	 * @param operation 可选的operation为LIKE_OPERATION = "like";COLLECT_OPERATION = "collect";String DETAIL_OPERATION
	 */
	static void operateitems(String itemIds , String operation){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FLAG, FLAG_OPERATION);
		params.put(ITEM_IDS,itemIds);
		params.put(OPERATION, operation);
		NetUtils.getPostResult(params, GOODS_OPERATION_URL);
	}


}
