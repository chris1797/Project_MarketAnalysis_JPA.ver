package com.mac.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="COMMENT_TB")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long num;
	
	private long pcode;
	private String user_id;
	private String nickname;
	private String comment;
	
	@Temporal(TemporalType.DATE)
	private Date wdate;
	
	
}