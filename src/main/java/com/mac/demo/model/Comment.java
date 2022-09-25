package com.mac.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name="COMMENT_TB")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_NUM_SEQ")
	@SequenceGenerator(sequenceName = "COMMENT_NUM_SEQ", allocationSize = 1, name = "COMMENT_NUM_SEQ")
	private int nummac;
	
	private int pcodemac;
	private String idmac;
	private String nicknamemac;
	private String commentmac;
	private java.sql.Date wdatemac;
	
	
}