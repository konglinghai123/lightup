package com.zhy.Bean;

public class PicsBean {
	public PicsBean(){
		
	}
	private String picurl;
	private String title;
	public String getUrl() {
		return picurl;
	}
	public void setUrl(String url) {
		this.picurl =url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "PicsBean [url=" + picurl + ", title=" + title + "]";
	}
	public PicsBean(String url, String title) {
		super();
		this.picurl = url;
		this.title = title;
	}
	
}
