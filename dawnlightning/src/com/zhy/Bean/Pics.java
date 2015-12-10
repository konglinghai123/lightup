package com.zhy.Bean;

public class Pics {
	public Pics(){
		
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
		return "Pics [url=" + picurl + ", title=" + title + "]";
	}
	public Pics(String url, String title) {
		super();
		this.picurl = url;
		this.title = title;
	}
	
}
