package com.mac.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Comment {
	
	private int pcodeMac;
	private String idMac;
	private String commentMac;
	private java.sql.Date wdateMac;
	
	
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
