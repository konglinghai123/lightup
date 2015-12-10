package com.zhy.Bean;

public class SendConsult {
	private String subject;
	private String age;
	private String sex;
	private String  bwztclassid;
	private String bwztdivisionid;
	private String message;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public SendConsult(String subject, String age, String sex,
			String bwztclassid, String bwztdivisionid, String message) {
		super();
		this.subject = subject;
		this.age = age;
		this.sex = sex;
		this.bwztclassid = bwztclassid;
		this.bwztdivisionid = bwztdivisionid;
		this.message = message;
	}
	public SendConsult() {
		// TODO 自动生成的构造函数存根
	}
	@Override
	public String toString() {
		return "SendConsult [subject=" + subject + ", age=" + age + ", sex="
				+ sex + ", bwztclassid=" + bwztclassid + ", bwztdivisionid="
				+ bwztdivisionid + ", message=" + message + "]";
	}
	
}
