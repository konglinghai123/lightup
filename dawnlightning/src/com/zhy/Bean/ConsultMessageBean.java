package com.zhy.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class ConsultMessageBean {
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
private String bwztid;
private String message;
private String uid;
private String usename;
private String subject;
private String bwztclassid;
private String bwztdivisionid;
private String sex;
private String age;
private String viewnum;
private String replynum;
public String dateline;
private String avatar_url;
public String messagetype;
private String status;
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getUsename() {
	return usename;
}
public void setUsename(String usename) {
	this.usename = usename;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getBwztclassid() {
	return bwztclassid;
}
public void setBwztclassid(String bwztclassid) {
	this.bwztclassid = bwztclassid;
}
public String getBwztdivisionid() {
	return bwztdivisionid;
}
public void setBwztdivisionid(String bwztdivisionid) {
	this.bwztdivisionid = bwztdivisionid;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public String getViewnum() {
	return viewnum;
}
public void setViewnum(String viewnum) {
	this.viewnum = viewnum;
}
public String getReplynum() {
	return replynum;
}
public void setReplynum(String replynum) {
	this.replynum = replynum;
}
public String getDateline() {
	return dateline;
}
public void setDateline(String dateline) {
	this.dateline =  TimeStamp2Date(dateline);
}
public String getAvatar_url() {
	return avatar_url;
}
public void setAvatar_url(String pic) {
	this.avatar_url = pic;
}

public ConsultMessageBean(){

}
@SuppressLint("SimpleDateFormat")
public String TimeStamp2Date(String timestampString){  
	  Long timestamp = Long.parseLong(timestampString)*1000;  
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

public String getBwztid() {
	return bwztid;
}
public void setBwztid(String bwztid) {
	this.bwztid = bwztid;
}


public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public ConsultMessageBean(String bwztid, String message, String uid,
		String usename, String subject, String bwztclassid,
		String bwztdivisionid, String sex, String age, String viewnum,
		String replynum, String dateline, String avatar_url,
		String messagetype, String status) {
	super();
	this.bwztid = bwztid;
	this.message = message;
	this.uid = uid;
	this.usename = usename;
	this.subject = subject;
	this.bwztclassid = bwztclassid;
	this.bwztdivisionid = bwztdivisionid;
	this.sex = sex;
	this.age = age;
	this.viewnum = viewnum;
	this.replynum = replynum;
	this.dateline = dateline;
	this.avatar_url = avatar_url;
	this.messagetype = messagetype;
	this.status = status;
}
@Override
public String toString() {
	return "ConsultMessageBean [bwztid=" + bwztid + ", message=" + message
			+ ", uid=" + uid + ", usename=" + usename + ", subject=" + subject
			+ ", bwztclassid=" + bwztclassid + ", bwztdivisionid="
			+ bwztdivisionid + ", sex=" + sex + ", age=" + age + ", viewnum="
			+ viewnum + ", replynum=" + replynum + ", dateline=" + dateline
			+ ", avatar_url=" + avatar_url + ", messagetype=" + messagetype
			+ ", status=" + status + "]";
}

}
