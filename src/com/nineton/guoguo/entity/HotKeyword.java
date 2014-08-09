package com.nineton.guoguo.entity;

import com.nineton.guoguo.data.DataProvider;

public class HotKeyword {
	private Long id;
	private String name;
	private String picurl;
	private Integer displayorder;
	public String getPicurl() {
		return DataProvider.BASE_URL + picurl;
	}
	public String getName() {
		return name;
	}
	
}
