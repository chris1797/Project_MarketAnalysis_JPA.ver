package com.mac.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/*
 * @Entity : JPA에서 관리할 객체를 선언
 * 
 * @Data : Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor 종합세트
 * 
 * @GeneratedValue
 * 기본키를 설정하는 것으로, strategy = GenerationType.SEQUENCE는 DB의 Sequence Object를 사용하겠다는 뜻
 * 
 * @SequenceGenerator
 * DB에 생성한 시퀀스를 바탕으로 식별자를 생성하는 시퀀스 생성기를 설정
 * allocationSize 옵션은 DB의 시퀀스 증가값이 1인 경우 반드시 1로 설정
 * 
 */

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name="BOARD_TB")
public class Board {
	
//	Entity 클래스로 등록한 클래스지만, DB 테이블과는 별도로 기능이(추가 필드나 메소드) 필요한 경우
//	@Transient
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_NUM_SEQ")
	@SequenceGenerator(sequenceName = "BOARD_NUM_SEQ", allocationSize = 1, name = "BOARD_NUM_SEQ")
	@Column(name="NUMMAC")
	private long nummac; // 번호
	
	private String idmac; // 유저 아이디
	private String nicknamemac;
	private String titlemac; // 제목
	
	@Column(length=500)
	private String contentsmac; // 내용
	
	@Temporal(TemporalType.DATE)
	private Date wdatemac; // 작성일
	
	@ColumnDefault("0")
	private int comment_cntmac;
	
	private String categorymac;

	// Fetch 타입 LAZY 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_idmac")
	private User user;
}