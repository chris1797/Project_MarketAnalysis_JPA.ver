package com.mac.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name="USER_TB")
public class User {

	@SequenceGenerator(sequenceName = "USER_NUM_SEQ", allocationSize = 1, name = "USER_NUM_SEQ")
	private long nummac;
	
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
