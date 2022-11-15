package com.mac.demo.repository;

import com.mac.demo.dto.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{

	/*
	 * Generic type은 관리하는 Entity와 그 Entity의 primary type (idmac)
	 * 
	 * JPA는 메소드 이름을 이용하여 SQL을 파생한다. (Query Method)
	
		@Transaction : 오류가 발생했을 때 모든 작업들을 원상태로 되돌림
		@Modifying : @Query를 통해 작성된 INSERT, UPDATE, DELETE (SELECT 제외) 쿼리에서 사용
				     주로 단건이 아닌 Bulk연산과 함께 사용
	 *
	*/
	
	List<Board> findByCategory(String categorymac); //User테이블을 대상을 이름을 검색
	List<Board> findAllByTitlemacAndCategorymac(String titlemac, String categorymac);
	
	Board findByNummacAndCategorymac(Long nummac, String categorymac);
	Board findByNummac(int nummac);
	
	int deleteByboard_num(Long nummac);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Board b SET b.title=?1, b.contents=?2 WHERE b.board_num=?3", nativeQuery = true)
	int update(String title, String contents, long board_num);
	
	
	@Query(value = "SELECT b FROM Board b WHERE b.titlemac LIKE CONCAT('%', :keyword, '%') OR b.contentsmac LIKE CONCAT('%', :keyword, '%') AND b.categorymac = :categorymac", nativeQuery = true)
	List<Board> getListByKeyword(@Param("keyword")String keyword, @Param("categorymac")String categorymac);
	
	
	@Query(value = "SELECT b FROM Board b WHERE b.nicknamemac LIKE CONCAT('%', :nickname, '%') AND b.categorymac = :categorymac", nativeQuery = true)
	List<Board> getListByNickname(@Param("nickname") String nickname, @Param("categorymac") String categorymac);
	
	/*
	num값이 5~10 사이에 있는 행을 추출
	@Query("SELECT b FROM Board b WHERE b.num BETWEEN ?1 AND ?2")
	List<Board> findAllNumBet(@Param("start") int start, @Param("end")int end); // named parameter
	*/	

    
	/*
	 * @Transactional
	 * @Modifying
	 * @Query("UPDATE Board b SET b.title=?2, b.contents=?3 WHERE b.num = ?1")
	 * int update(int num, String title, String contents);
	 * UPDATE "Board" 는 DB의 테이블명이 아닌 클래스명
	*/
}
