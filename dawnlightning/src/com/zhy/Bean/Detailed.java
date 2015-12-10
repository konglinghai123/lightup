package com.zhy.Bean;

import java.util.ArrayList;
import java.util.List;

public class Detailed {
	private String uid;
	private String age;
	private String usename;
	private String datetime;
	private String subject;
	private String content;
	private List<Pics> pics;
	private List<Comment> comment;
	private String avatar_url;
	private String name;
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
		this.datetime = datetime;
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
	public List<Pics> getPics() {
		return pics;
	}
	public void setPics(List<Pics> list) {
		this.pics = list;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> list) {
		this.comment = list;
	}
	
	public Detailed() {
		// TODO 自动生成的构造函数存根
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public Detailed(String uid, String age, String usename, String datetime,
			String subject, String content, List<Pics> pics,
			List<Comment> comment, String avatar_url, String name) {
		super();
		this.uid = uid;
		this.age = age;
		this.usename = usename;
		this.datetime = datetime;
		this.subject = subject;
		this.content = content;
		this.pics = pics;
		this.comment = comment;
		this.avatar_url = avatar_url;
		this.name = name;
	}
	@Override
	public String toString() {
		return "Detailed [uid=" + uid + ", age=" + age + ", usename=" + usename
				+ ", datetime=" + datetime + ", subject=" + subject
				+ ", content=" + content + ", pics=" + pics + ", comment="
				+ comment + ", avatar_url=" + avatar_url + ", name=" + name
				+ "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
