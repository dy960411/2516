package com.yc.chatroom.entity;

public class YCCP {
	public static final int LOGIN = 1 ;
	public static final int SEND_MSG = 2 ;
	public static final int USER_LIST = 3 ;
	public static final int LOGIN_FAIL = 4 ;
	private int code;
	private String content;
	
	public YCCP() {
	}
	
	public YCCP(int code, String content) {
		super();
		this.code = code;
		this.content = content;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
