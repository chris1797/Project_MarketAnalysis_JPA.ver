package com.mac.demo.model;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Entity
@Builder
@Getter
@RequiredArgsConstructor
@Table(name="USER_TB")
public class User {
	/*
	 * User의 VO(Value Object), read-Only 특징
	 * 사용하는 도중에 변경 불가능하며 오직 읽기만 가짐
	 * DTO와 비슷하지만 DTO는 setter를 가지고 있어 값이 변할 수 있음
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_NUM_SEQ")
	@SequenceGenerator(sequenceName = "USER_NUM_SEQ", allocationSize = 1, name = "USER_NUM_SEQ")
	@Column(name = "user_idmac")
	private long nummac;
	
	@NotBlank
	private String idmac;

	private String pwmac;

	private String nicknamemac;

	@NotBlank(message = "이메일 주소를 입력해주세요.")
	@Email(message = "올바른 이메일 주소를 입력해주세요.")
	private String emailmac;
	private String gendermac;
	private Date birthmac;

	@NotBlank(message = "휴대폰 번호를 입력해주세요.")
	@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
	private String phonenummac;
	private String citymac;
	private String townmac; 
	private String villagemac;
	private int managermac;
	private String namemac;

	@Temporal(TemporalType.DATE)
	private java.util.Date datemac; // 회원가입일

}
