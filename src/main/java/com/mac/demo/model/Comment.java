package com.mac.demo.model;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_NUM_SEQ")
	@SequenceGenerator(sequenceName = "COMMENT_NUM_SEQ", allocationSize = 1, name = "COMMENT_NUM_SEQ")
	private long nummac;
	
	private long pcodemac;
	private String idmac;
	private String nicknamemac;
	private String commentmac;
	
	@Temporal(TemporalType.DATE)
	private Date wdatemac;
	
	
}