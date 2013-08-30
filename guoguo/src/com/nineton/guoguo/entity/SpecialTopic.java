package com.nineton.guoguo.entity;

import com.nineton.guoguo.data.DataProvider;

public class SpecialTopic {

	private Long ztid;
	private String title;
	private String picUrl;
	private String zhuantidesc;
	private String createddate;
	private Integer displayorder;
	private Object items;
	public String getPicUrl() {
		if (picUrl != null) {
			return DataProvider.BASE_URL + picUrl;
		}else {
			return null;
		}
	}
	
	public Long getZtid() {
		return ztid;
	}
	
	public String getTitle() {
		return title;
	}

	public String getZhuantidesc() {
		return zhuantidesc;
	}

	public Object getItems() {
		return items;
	}

	public String getCreateddate() {
		return createddate;
	}
	
	
}
