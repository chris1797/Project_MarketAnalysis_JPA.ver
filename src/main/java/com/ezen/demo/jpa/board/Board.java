package com.ezen.demo.jpa.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="board_tb")
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_NUM_SEQ")
	@SequenceGenerator(sequenceName = "BOARD_NUM_SEQ", allocationSize = 1, name = "BOARD_NUM_SEQ")
	private int num;
	
	@Column(name="title")
	private String title;
	private String author = "Chris";
	private String contents;
	private java.sql.Date wdate;
	

}