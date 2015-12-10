package com.zhy.Bean;
/**
 * 评论对象
 * @author Administrator
 *
 */
public class Comment {

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
	
	public Comment(){
		
	}
	
	@Override
	public String toString() {
		return "Comment [author=" + author + ", name=" + name + ", message="
				+ message + "]";
	}
	public Comment(String author, String name, String message) {
		super();
		this.author = author;
		this.name = name;
		this.message = message;
	}
	
	
}
