package com.zhy.Bean;

import java.io.Serializable;


public class BaseBean implements Serializable {

	private String code;
	
	private String data;
	
	private String msg;
	
	private String action;

	public String  getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	public BaseBean(){
		
	}
	public BaseBean(String code, String data, String msg, String action) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
		this.action = action;
	}

	@Override
	public String toString() {
		return "BaseBean [code=" + code + ", data=" + data + ", msg=" + msg
				+ ", action=" + action + "]";
	}

	
	
}
