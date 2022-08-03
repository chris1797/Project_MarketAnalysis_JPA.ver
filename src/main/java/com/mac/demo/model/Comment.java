package com.mac.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Comment {
	
	private int numMac;
	private int pcodeMac;
	private String idMac;
	private String nickNameMac;
	private String commentMac;
	private java.sql.Date wdateMac;
	
	
	
	
	public String getNickNameMac() {
		return nickNameMac;
	}
	public void setNickNameMac(String nickNameMac) {
		this.nickNameMac = nickNameMac;
	}
	public int getNumMac() {
		return numMac;
	}
	public void setNumMac(int numMac) {
		this.numMac = numMac;
	}
	public int getPcodeMac() {
		return pcodeMac;
	}
	public void setPcodeMac(int pcodeMac) {
		this.pcodeMac = pcodeMac;
	}
	public String getIdMac() {
		return idMac;
	}
	public void setIdMac(String idMac) {
		this.idMac = idMac;
	}
	public String getCommentMac() {
		return commentMac;
	}
	public void setCommentMac(String commentMac) {
		this.commentMac = commentMac;
	}
	public java.sql.Date getWdateMac() {
		return wdateMac;
	}
	public void setWdateMac(java.sql.Date wdateMac) {
		this.wdateMac = wdateMac;
	}
	
}
