package com.zhy.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;

public class DetailedBean {
	private String uid;
	private String age;
	private String usename;
	private String datetime;
	private String subject;
	private String content;
	private List<PicsBean> picsBean;
	private List<CommentBean> commentBean;
	private String avatar_url;
	private String name;
	private static final long ONE_MINUTE = 60000L;  
    private static final long ONE_HOUR = 3600000L;  
    
  
    private static final String ONE_SECOND_AGO = "秒前";  
    private static final String ONE_MINUTE_AGO = "分钟前";  
    private static final String ONE_HOUR_AGO = "小时前";  
    
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getUsename() {
		return usename;
	}
	public void setUsename(String usename) {
		this.usename = usename;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = TimeStamp2Date(datetime);
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<PicsBean> getPics() {
		return picsBean;
	}
	public void setPics(List<PicsBean> list) {
		this.picsBean = list;
	}
	public List<CommentBean> getComment() {
		return commentBean;
	}
	public void setComment(List<CommentBean> list) {
		this.commentBean = list;
	}
	
	public DetailedBean() {
		// TODO 自动生成的构造函数存根
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public DetailedBean(String uid, String age, String usename, String datetime,
			String subject, String content, List<PicsBean> picsBean,
			List<CommentBean> commentBean, String avatar_url, String name) {
		super();
		this.uid = uid;
		this.age = age;
		this.usename = usename;
		this.datetime = datetime;
		this.subject = subject;
		this.content = content;
		this.picsBean = picsBean;
		this.commentBean = commentBean;
		this.avatar_url = avatar_url;
		this.name = name;
	}
	@Override
	public String toString() {
		return "DetailedBean [uid=" + uid + ", age=" + age + ", usename=" + usename
				+ ", datetime=" + datetime + ", subject=" + subject
				+ ", content=" + content + ", picsBean=" + picsBean + ", commentBean="
				+ commentBean + ", avatar_url=" + avatar_url + ", name=" + name
				+ "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
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
		  else if (delta < 45L * ONE_MINUTE) {  
	          long minutes = toMinutes(delta);  
	          return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;  
	      }  
		  else if (delta < 24L * ONE_HOUR) {  
	          long hours = toHours(delta);  
	          return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;  
	      } else {  
	    	 
	          return  new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date(timestamp));  
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
	
}
