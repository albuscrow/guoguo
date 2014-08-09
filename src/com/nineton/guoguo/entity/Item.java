package com.nineton.guoguo.entity;

import java.io.Serializable;
import java.util.Random;

public abstract class Item  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 580565229535401211L;
	
	private static final String TTID = "&ttid=400000_21482379@wslsk_Android_1.0";
	
	Long numIid;
	String title;
	String nick;
	Float price;
	String picUrls;
	Integer picWidth;
	Integer picHeight;
	String shopClickUrl;
	Integer sellerCreditScore;
	Integer volume;
	Integer favoriteCount;
	String datailUrl;
	String clickUrl;
	public Long getNumIid() {
		return numIid;
	}
	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String[] getPicUrls() {
		String[] result = null;
		if (picUrls != null) {
			result = picUrls.split(",");
		}
		return result;
	}
	
	public String getFirstPicUrl(){
		String result = null;
		String[] urls = getPicUrls();
		if (urls != null) {
			result = urls[0]+"_570x570";
		}
		return result;
	}
	public void setPicUrls(String picUrls) {
		this.picUrls = picUrls;
	}
	public Integer getPicWidth() {
		return picWidth;
	}
	public void setPicWidth(Integer picWidth) {
		this.picWidth = picWidth;
	}
	public Integer getPicHeight() {
		return picHeight;
	}
	public void setPicHeight(Integer picHeight) {
		this.picHeight = picHeight;
	}
	public String getShopClickUrl() {
		return shopClickUrl;
	}
	public void setShopClickUrl(String shopClickUrl) {
		this.shopClickUrl = shopClickUrl;
	}
	public Integer getSellerCreditScore() {
		return sellerCreditScore;
	}
	public void setSellerCreditScore(Integer sellerCreditScore) {
		this.sellerCreditScore = sellerCreditScore;
	}
	public Integer getVolume() {
		if (volume == null) {
			return new Random().nextInt(100);
		}
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public Integer getFavoriteCount() {
		if (favoriteCount == null) {
			return new Random().nextInt(100);
		}
		return favoriteCount;
	}
	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	public String getDatailUrl() {
		return datailUrl;
	}
	public void setDatailUrl(String datailUrl) {
		this.datailUrl = datailUrl;
	}
	public String getClickUrl() {
		return clickUrl+TTID;
	}
	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl+TTID;
	}
}
