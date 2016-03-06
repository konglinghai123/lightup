package com.zhy.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 评论对象
 * @author Administrator
 *
 */
public class CommentBean {
	private static final long ONE_MINUTE = 60000L;  
    private static final long ONE_HOUR = 3600000L;  
    private static final long ONE_DAY = 86400000L;  
    private static final long ONE_WEEK = 604800000L;  
  
    private static final String ONE_SECOND_AGO = "秒前";  
    private static final String ONE_MINUTE_AGO = "分钟前";  
    private static final String ONE_HOUR_AGO = "小时前";  
    private static final String ONE_DAY_AGO = "天前";  
    private static final String ONE_MONTH_AGO = "月前";  
    private static final String ONE_YEAR_AGO = "年前";  
	private String author;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String message;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public CommentBean(){
		
	}
	
	@Override
	public String toString() {
		return "CommentBean [author=" + author + ", name=" + name + ", message="
				+ message + "]";
	}

	public CommentBean(String author, String name, String message, String dateline) {
		super();
		this.author = author;
		this.name = name;
		this.message = message;
		this.dateline = dateline;
	}
	public String dateline;
	@SuppressLint("SimpleDateFormat")
	public String TimeStamp2Date(String timestampString){  
		  Long timestamp = Long.parseLong(timestampString)*1000;  
		  @SuppressWarnings("unused")
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");  
		  
		  Date date = new java.util.Date(timestamp);
		  long delta = new Date().getTime() - date.getTime();  
		  if (delta < 1L * ONE_MINUTE) {  
	          long seconds = toSeconds(delta);  
	          return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;  
	      }  
	      if (delta < 45L * ONE_MINUTE) {  
	          long minutes = toMinutes(delta);  
	          return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;  
	      }  
	      if (delta < 24L * ONE_HOUR) {  
	          long hours = toHours(delta);  
	          return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;  
	      }  
	      if (delta < 48L * ONE_HOUR) {  
	          return "昨天";  
	      }  
	      if (delta < 30L * ONE_DAY) {  
	          long days = toDays(delta);  
	          return (days <= 0 ? 1 : days) + ONE_DAY_AGO;  
	      }  
	      if (delta < 12L * 4L * ONE_WEEK) {  
	          long months = toMonths(delta);  
	          return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;  
	      } else {  
	          long years = toYears(delta);  
	          return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;  
	      }  
		
		}
	private static long toSeconds(long date) {  
	    return date / 1000L;  
	}  

	private static long toMinutes(long date) {  
	    return toSeconds(date) / 60L;  
	}  

	private static long toHours(long date) {  
	    return toMinutes(date) / 60L;  
	}  

	private static long toDays(long date) {  
	    return toHours(date) / 24L;  
	}  

	private static long toMonths(long date) {  
	    return toDays(date) / 30L;  
	}  

	private static long toYears(long date) {  
	    return toMonths(date) / 365L;  
	}  
	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline =  TimeStamp2Date(dateline);
	}
}
