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

	boolean freeBordDeleted(int numMac);

	boolean adsBordDeleted(int numMac);

	boolean userDeleted(int numMac);

	int saveNotice(Board board);

	boolean noticeBoardDeleted(int numMac);
	
	boolean commentBoardDeleted(int numMac);
	
	List<Board> getAdsListByKeyword(String keyword);
	List<Board> getAdsListByNickName(String nickNameMac);

}