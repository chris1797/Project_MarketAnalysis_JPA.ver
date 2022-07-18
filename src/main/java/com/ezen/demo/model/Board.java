package com.ezen.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Board {
	private int num;
	private String title;
	private String contents;
	private String author = "Chris";
	private java.sql.Date wdate;
	private int pcode;
	
	
	
	@Override
	public String toString() {
		return String.format("%s|%s", title, contents);
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public java.sql.Date getWdate() {
		return wdate;
	}
	public void setWdate(java.sql.Date wdate) {
		this.wdate = wdate;
	}
	public int getPcode() {
		return pcode;
	}
	public void setPcode(int pcode) {
		this.pcode = pcode;
	}
}