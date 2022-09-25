package com.mac.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mac.demo.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>{

	// JPA는 메소드 이름을 이용하여 SQL을 파생한다. (Query Method)
	
//	@Transaction : 오류가 발생했을 때 모든 작업들을 원상태로 되돌림
//	@Modifying : @Query를 통해 작성된 INSERT, UPDATE, DELETE (SELECT 제외) 쿼리에서 사용
//				 주로 단건이 아닌 Bulk연산과 함께 사용
	
	@Query("SELECT NUMMAC, IDMAC, NICKNAMEMAC, TITLEMAC, CONTENTSMAC, WDATEMAC," + 
			"(SELECT COUNT(*) " +
			"FROM comment_tb c " +
			"WHERE c.pcodemac = b.nummac) countmac " +
			"FROM board_tb b " +
			"WHERE CATEGORYMAC = ?1 " +
			"ORDER BY NUMMAC " +
			"DESC")
	List<Board> getBoardList(String categorymac); //User테이블을 대상을 이름을 검색
//	SELECT * FROM user_tb WHERE CATEGORYMAC = categorymac;
	
//	SELECT COUNT(*) FROM comment_board c WHERE c.pcodemac = b.nummac
	
//	List<Board> findByUnameAndEmail(String uname, String email);
//	
//	//num값이 5~10 사이에 있는 행을 추출하려고 한다. 내가 만든 메소드에 내가 만든 쿼리문을 넣은 것
//	@Query("SELECT b FROM Board b WHERE b.num BETWEEN ?1 AND ?2") // JPQL, 이름은 아무렇게나 써도 상관없음
//	List<Board> findAllNumBet(@Param("start") int start, @Param("end")int end); // named parameter
//	
//
//	Board findByNum(int num);
//	
//	@Transactional
//	@Modifying
//	@Query("UPDATE Board b SET b.title=?2, b.contents=?3 WHERE b.num = ?1")
//	int update(int num, String title, String contents);
//	// UPDATE "Board" 는 DB의 테이블명이 아닌 클래스명
}
