package com.mac.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="COMMENT_TB")
public class CommentDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long comment_num;
	
	private long pcode;
	private String user_id;
	private String nickname;
	private String comment;
	
	@Temporal(TemporalType.DATE)
	private Date wdate;


	@Builder
	public CommentDTO(String user_id, Long pcode, String nickname, String comment) {
		this.user_id = user_id;
		this.pcode = pcode;
		this.nickname = nickname;
		this.comment = comment;
	}

	public CommentDTO toEntity() {
		return CommentDTO.builder()
				.user_id(user_id)
				.pcode(pcode)
				.nickname(nickname)
				.comment(comment)
				.build();
	}
}