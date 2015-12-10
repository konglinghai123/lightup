package com.zhy.Bean;

import java.io.Serializable;
import java.util.List;


public class ConsultBean  implements Serializable {
private	String messages;
private int count;
public ConsultBean(String messages, int count) {
	super();
	this.messages = messages;
	this.count = count;
}
public ConsultBean(){

}

public String getMessages() {
	return messages;
}
public void setMessages(String messages) {
	this.messages = messages;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}

@Override
public String toString() {
	return "ConsultBean [messages=" + messages + ", count=" + count + "]";
}
}
