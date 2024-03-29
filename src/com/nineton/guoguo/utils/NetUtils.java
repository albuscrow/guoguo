package com.nineton.guoguo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;


public class NetUtils {
	private static final String TAG = "NetUtils";
	private static final String HTTPPOST_HEADER_NAME = "User-Agent";
	private static final String HTTPPOST_HEADER_CONTENT = "Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:1.8.1.6) Gecko/20070914 Firefox/2.0.0.7";
	private static final int WAIT_TIME = 20*1000;
	private static final int GET_RESPONSE_SUCCESSCODE = 200;
	private static final String GET_DATA_SUCCESS = "get data success";
	private static final String GET_DATA_FAILED = "get data failed";
	
	/**
	 * 失败返回null
	 * @param params
	 * @param targetURL
	 * @return
	 */
	public static String getPostResult(Map<String, Object> params,String targetURL){
		String result = null;
		try {
			HttpPost post = new HttpPost(targetURL);
			post.setHeader(HTTPPOST_HEADER_NAME, HTTPPOST_HEADER_CONTENT);
			List<NameValuePair> httpParams = generateHttpParams(params);
			post.setEntity(new UrlEncodedFormEntity(httpParams,HTTP.UTF_8));
			HttpConnectionParams.setConnectionTimeout(new BasicHttpParams(), WAIT_TIME);
			HttpResponse response = MySSLSocketFactory.getDefaultHttpClient().execute(post);
			if (response.getStatusLine().getStatusCode() == GET_RESPONSE_SUCCESSCODE) {
				result = EntityUtils.toString(response.getEntity());
			}
			Log.i(TAG, GET_DATA_SUCCESS);
		} catch (Exception e) {
			Log.e(TAG, GET_DATA_FAILED);
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	private static List<NameValuePair> generateHttpParams(
			Map<String, Object> params) {
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			httpParams.add(new BasicNameValuePair(key, params.get(key).toString()));
		}
		return httpParams;
	}
}
