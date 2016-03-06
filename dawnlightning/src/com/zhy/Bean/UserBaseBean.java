package com.zhy.Bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserBaseBean implements Serializable   {
	String space;
	String m_auth;
	String formhash;
	public UserBaseBean(String space, String m_auth, String formhash) {
		super();
		this.space = space;
		this.m_auth = m_auth;
		this.formhash = formhash;
	}
	public UserBaseBean(){
		
	}
	public String getFormhash() {
		return formhash;
	}
	public void setFormhash(String formhash) {
		this.formhash = formhash;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getM_auth() {
		return m_auth;
	}
	public void setM_auth(String m_auth) {
		this.m_auth = m_auth;
	}
}
