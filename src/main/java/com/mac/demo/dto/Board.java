package com.mac.demo.dto;

import lombok.*;
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
 * 기본키를 설정하는 것으로, strategy = GenerationType.SEQUENCE or IDENTITY로 DB의 Sequence Object를 사용하겠다는 뜻
 * 
 * @SequenceGenerator
 * DB에 생성한 시퀀스를 바탕으로 식별자를 생성하는 시퀀스 생성기를 설정
 * allocationSize 옵션은 DB의 시퀀스 증가값이 1인 경우 반드시 1로 설정
 * IDENTITY는 자동으로 증가
 */

@Entity
@Builder
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name="BOARD_TB")
public class Board {
	
//	Entity 클래스로 등록한 클래스지만, DB 테이블과는 별도로 기능이(추가 필드나 메소드) 필요한 경우
//	@Transient
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long board_num; // 번호
	
	private String user_id; // 유저 아이디
	private String nickname;
	private String title; // 제목
	
	@Column(length=500)
	private String contents; // 내용
	
	@Temporal(TemporalType.DATE)
	private Date wdate; // 작성일
	
	@ColumnDefault("0")
	private int comment_cnt;
	
	private String category;

	// Fetch 타입 LAZY 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_num")
	private User user;

	@Builder
	public Board(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public Board toEntity() {
		return Board.builder()
				.title(title)
				.contents(contents)
				.build();
	}
}