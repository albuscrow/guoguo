package com.nineton.guoguo.common;

import java.util.HashMap;
import java.util.Map;

public class PublicContainers {
	static public PublicContainers instance = new PublicContainers();
	public Map<String, Object> map = new HashMap<String, Object>();
	public static PublicContainers getInstance() {
		return instance;
	}
	public void putData(String key, Object value){
		map.put(key, value);
	}
	public Object getData(String key){
		Object temp =  map.get(key);
		map.remove(key);
		return temp;
	}
	public void removeData(String key){
		map.remove(key);
	}
	private PublicContainers(){};
}
