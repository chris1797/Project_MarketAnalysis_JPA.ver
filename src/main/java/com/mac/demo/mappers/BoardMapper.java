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
	int save(Board board);
//	int saveToAds(Board board);
	
//	게시글 수정
	int update(Board board);
	int Noticeedit(Board board);
	
	int Freedelete(int num); //게시글 삭제
	int Adsdelete(int num); 
	int Noticedelete(int num); 
	
	int freeCommentAllDelete(int num);//게시글 삭제하면 댓글도 삭제
	int adsCommentAllDelete(int num);
	
	Board getDetail(int numMac, String categoryMac); //게시글 상세보기
	Board getNoticeDetail(int num);
	
//	댓글
	int commentsave(Comment comment);
	List<Comment> getCommentList(int num); //자유게시판
	int commentdelete(int numMac);

//	유저
	User getUserById(String idMac);

	
//	게시글 목록
	List<Board> getBoardList(String categoryMac); // 자유게시판
	List<Board> getNoticeList(); // 공지게시판
	
//	닉네임, 글내용+제목 검색
	List<Board> getFreeListByKeyword(String keyword);
	List<Board> getFreeListByNickName(String nickNameMac);
	
	List<Board> getAdsListByKeyword(String keyword);
	List<Board> getAdsListByNickName(String nickNameMac);
	
	List<Board> getNoticeListByKeyword(String keyword);
	List<Board> getNoticeListByNickName(String nickNameMac);
	

	
//	myPage
	List<Board> getMypageInFreeBoard(String idMac);
	List<Board> getMypageInAdsBoard(String idMac);
	
	Page<Board> getList(Pageable pageable);
	

}