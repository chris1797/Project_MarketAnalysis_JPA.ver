package com.mac.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mac.demo.dto.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

	/*
	 * Generic type은 관리하는 Entity와 그 Entity의 primary type (idmac)
	 * 
	 * JPA는 메소드 이름을 이용하여 SQL을 파생한다. (Query Method)
	 *
	 * @Transaction : 오류가 발생했을 때 모든 작업들을 원상태로 되돌림
	 * @Modifying : @Query를 통해 작성된 INSERT, UPDATE, DELETE (SELECT 제외) 쿼리에서 사용
	 *			 	주로 단건이 아닌 Bulk연산과 함께 사용
	 *
	 *
	 */
	
//	게시판 글번호를 받아 해당 번호에 달린 댓글리스트 얻어오기
	List<Comment> findByPcodemac(int pcodemac);
	
	int deleteByNummac(int nummac);
	

}
