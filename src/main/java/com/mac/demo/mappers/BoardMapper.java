package com.mac.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;


@Mapper
public interface BoardMapper {

//	게시글CRUD
	int save(Board board); //게시글 저장
	int edit(Board board); //게시글 수정
	int delete(int num); //게시글 삭제
	Board getDetail(int num); //게시글 상세보기
	
	int commentsave(Comment comment);
	List<Comment> getCommentList(int num); //자유게시판

//게시글 목록
	List<Board> getList(); //자유게시판
	List<Board> getAdsList(); //광고게시판
	List<Board> getNoticeList(); //공지게시판
	
	
	//myPage
	List<Board> getMypageInFreeBoard(String idMac);
	List<Board> getMypageInAdsBoard(String idMac);
	

}