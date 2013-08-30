package com.nineton.guoguo.entity;

import java.util.List;

public class Classify {
	private Long cid;
	private String name;
	private Integer displayorder;
	private Object items;
	private List<HotKeyword> hotKeywords;
	public List<HotKeyword> getHotKeywords() {
		return hotKeywords;
	}
	
	public String getName() {
		return name;
	}

	public Long getCid() {
		return cid;
	}
	
	
}
