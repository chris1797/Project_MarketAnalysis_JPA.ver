package com.mac.demo.model;

import java.util.List;
import lombok.*;

import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Attach
{
	private int numMac;  // index
	private int pcodeMac; // 부모코드(게시판 numMac)
	private String idMac; // 유저 ID
	private String nickNameMac; // 유저 닉네임
	private String fileNameMac; // 파일 이름
	private String filepathMac; // 파일 저장된 경로
	private java.sql.Date wdateMac; // 파일 저장 날짜
	private List<Attach> attListMac; // 첨부파일명 리스트
	
}