package com.ezen.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Fileupload 
{
	private int num;
	private String writer;
	private java.sql.Date udate;
	private String comments;
	private String fpath;
	private List<AttachVO> attach = new ArrayList<>(); //첨부파일명
	
	@Override
	public boolean equals(Object obj) {
		Fileupload other = (Fileupload) obj;
		return this.num == other.num;
	}
	
	@Override
	public String toString() {
		return String.format("%d\t%s\t%s", num, writer, udate);
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public java.sql.Date getUdate() {
		return udate;
	}
	public void setUdate(java.sql.Date udate) {
		this.udate = udate;
	}
	
	public List<AttachVO> getAttach() {
		return attach;
	}
	public void setAttach(List<AttachVO> fnames) {
		this.attach = fnames;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String description) {
		this.comments = description;
	}
	public String getFpath() {
		return fpath;
	}
	public void setFpath(String fpath) {
		this.fpath = fpath;
	}
	
}