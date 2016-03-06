package com.zhy.Bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDataBean implements Serializable {
	String uid;
	String credit;
	String experience;
	String username;
	String name;
	String avatar_url;
	String sex;
	String age;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public UserDataBean(){
		
	}
	public UserDataBean(String uid, String credit, String experience,
			String username, String name, String avatar_url, String sex,
			String age) {
		super();
		this.uid = uid;
		this.credit = credit;
		this.experience = experience;
		this.username = username;
		this.name = name;
		this.avatar_url = avatar_url;
		this.sex = sex;
		this.age = age;
	}
	@Override
	public String toString() {
		return "UserDataBean [uid=" + uid + ", credit=" + credit + ", experience="
				+ experience + ", username=" + username + ", name=" + name
				+ ", avatar_url=" + avatar_url + ", sex=" + sex + ", age="
				+ age + "]";
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
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
	
	
	
	
}
