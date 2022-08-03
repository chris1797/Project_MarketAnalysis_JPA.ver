package com.mac.demo.mappers;

import java.awt.print.Pageable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;


@Mapper
public interface BoardMapper {

//	게시글CRUD
	int saveToFree(Board board);
	int saveToAds(Board board);
	int edit(Board board); //게시글 수정
	int delete(int num); //게시글 삭제
	Board getDetail(int num); //게시글 상세보기
	
//	댓글
	int commentsave(Comment comment);
	List<Comment> getCommentList(int num); //자유게시판
	int commentdelete(int numMac);

//	유저
	User getUserById(String idMac);

	
//	게시글 목록
	List<Board> getFreeList(); // 자유게시판
	List<Board> getAdsList(); // 광고게시판
	List<Board> getNoticeList(); // 공지게시판
	
//	닉네임, 글내용+제목 검색
	List<Board> getFreeListByKeyword(String keyword);
	List<Board> getFreeListByNickName(String nickNameMac);
	
	
	
//	myPage
	List<Board> getMypageInFreeBoard(String idMac);
	List<Board> getMypageInAdsBoard(String idMac);
	
	Page<Board> getList(Pageable pageable);

}