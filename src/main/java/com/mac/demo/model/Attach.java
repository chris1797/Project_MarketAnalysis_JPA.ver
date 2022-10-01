package com.mac.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.*;


@Entity
@Data
@Table(name="ATTACH_TB")
public class Attach
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTACH_NUM_SEQ")
	@SequenceGenerator(sequenceName="ATTACH_NUM_SEQ", allocationSize = 1, name = "ATTACH_NUM_SEQ")
	private long nummac;  // index
	private long pcodemac; // 부모코드(게시판 numMac)
	private String idmac; // 유저 ID
	private String nicknamemac; // 유저 닉네임
	private String filenamemac; // 파일 이름
	
	@Column(length=500)
	private String filepathmac; // 파일 저장된 경로
	
	@Temporal(TemporalType.DATE)
	private Date wdatemac; // 파일 저장 날짜
	
	@Transient
	private List<Attach> attListmac; // 첨부파일명 리스트
	
}