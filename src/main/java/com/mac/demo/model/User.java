package com.mac.demo.model;


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="USER_TB")
public class User {

	@SequenceGenerator(sequenceName = "USER_NUM_SEQ", allocationSize = 1, name = "USER_NUM_SEQ")
	private int nummac;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idmac;
	
	private String pwmac; 
	private String nicknamemac; 
	private String emailmac;
	private String gendermac;
	private Date birthmac;
	private String phonenummac;
	private String citymac;
	private String townmac; 
	private String villagemac;
	private int managermac;
	private String namemac;
	
}
