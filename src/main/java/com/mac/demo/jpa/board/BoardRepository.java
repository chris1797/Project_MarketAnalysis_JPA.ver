package com.mac.demo.jpa.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface BoardRepository extends JpaRepository<Board, Integer>{

	// 이게 DAO 역할을 하게 됨
	// JPA는 메소드 이름을 이용하여 SQL을 파생한다. (Query Method)
	// User 클래스의 uname 속성을 이용하여 이름으로 검색되도록 메소드 작성
	
	/*
	List<Board> findByUname(String uname); //User테이블을 대상을 이름을 검색
	//select * from user_tb where name=uname;
	
	List<Board> findByUnameAndEmail(String uname, String email);
	
	//num값이 5~10 사이에 있는 행을 추출하려고 한다. 우리가 만든 메소드에 우리가 만든 쿼리문을 넣은 것
	@Query("SELECT b FROM Board b WHERE b.num BETWEEN ?1 AND ?2") // JPQL, 이름은 아무렇게나 써도 상관없음
	List<Board> findAllNumBet(@Param("start") int start, @Param("end")int end); // named parameter
	*/

	Board findByNum(int num);
	
	@Transactional
	@Modifying
	@Query("UPDATE Board b SET b.title=?2, b.contents=?3 WHERE b.num = ?1")
	int update(int num, String title, String contents);
	// UPDATE "Board" 는 DB의 테이블명이 아닌 클래스명
}