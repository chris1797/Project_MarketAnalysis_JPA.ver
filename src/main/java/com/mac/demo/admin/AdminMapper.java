package com.mac.demo.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;

@Mapper
public interface AdminMapper {

	List<User> findAllUser();

	List<Board> findAllFreeBoard();

	List<Board> findAllAdsBoard();
	
	List<Board> findAllNoticeBoard();
	
	List<Comment> findAllCommentBoard();

	boolean freeBoardDeleted(int numMac);

	boolean adsBoardDeleted(int numMac);

	boolean userDeleted(int numMac);

	int saveNotice(Board board);

	boolean noticeBoardDeleted(int numMac);
	
	boolean commentBoardDeleted(int numMac);
	//광고 검색
	List<Board> getAdsListByKeyword(String keyword);
	List<Board> getAdsListByNickName(String nickNameMac);
	
    //공지사항 검색
	List<Board> getNoticeListByKeyword(String keyword);
	List<Board> getNoticeListByNickName(String keyword);

	//댓글 검색
	List<Comment> getCommentListByKeyword(String keyword);
	List<Comment> getCommentListByNickName(String keyword);

	//유저 검색
	List<User> getUserListByKeyword(String keyword);



}