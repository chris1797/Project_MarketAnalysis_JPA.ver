package com.mac.demo.model;



import org.springframework.stereotype.Component;


@Component
public class Board {
	
	private int numMac;
	private String nicknameMac;
	private String titleMac;
	private String contentsMac;
	private java.sql.Date wdateMac;
	private int pcodeMac;
	
	
	public int getNumMac() {
		return numMac;
	}
	public void setNumMac(int numMac) {
		this.numMac = numMac;
	}
	public String getNicknameMac() {
		return nicknameMac;
	}
	public void setNicknameMac(String nicknameMac) {
		this.nicknameMac = nicknameMac;
	}
	public String getTitleMac() {
		return titleMac;
	}
	public void setTitleMac(String titleMac) {
		this.titleMac = titleMac;
	}
	public String getContentsMac() {
		return contentsMac;
	}
	public void setContentsMac(String contentsMac) {
		this.contentsMac = contentsMac;
	}
	public java.sql.Date getWdateMac() {
		return wdateMac;
	}
	public void setWdateMac(java.sql.Date wdateMac) {
		this.wdateMac = wdateMac;
	}
	public int getPcodeMac() {
		return pcodeMac;
	}
	public void setPcodeMac(int pcodeMac) {
		this.pcodeMac = pcodeMac;
	}
	
}
