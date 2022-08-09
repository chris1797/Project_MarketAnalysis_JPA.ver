package com.mac.demo.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;

@Service
public class AdminService {
	
	@Autowired 
	private AdminMapper dao;
	
	@Autowired 
	private BoardMapper bao;
	


	public List<User> findAllUser() {
		return dao.findAllUser();
		
	}

	public List<Board> findAllFreeBord() {
		
		return dao.findAllFreeBoard();
	}

	public List<Board>findAllAdsBoard() {
	
		return dao.findAllAdsBoard();
	}

	public boolean freeBordDeleted(int numMac) {
		return dao.freeBordDeleted(numMac);
	}

	public boolean adsBoardDeleted(int numMac) {
	
		return  dao.adsBordDeleted(numMac);
	}

	public boolean userDeleted(int numMac) {
		
		return dao.userDeleted(numMac);
	}

	public int save(Board board) {
		
		return dao.saveNotice(board);
	}

	public List<Board> findAllNoticeBoard() {

		return dao.findAllNoticeBoard();
	}

	public boolean noticeBordDeleted(int numMac) {
	
		return dao.noticeBoardDeleted(numMac);
	}

	public List<Comment> findAllCommentBoard() {
		
		return dao.findAllCommentBoard();
	}

	public boolean commentBordDeleted(int numMac) {
		
		return dao.commentBoardDeleted(numMac);
	}

	public List<Board> getFreeListByKeyword(String titleMac){
		return bao.getFreeListByKeyword(titleMac);
	}

	public List<Board> getFreeListByNickName(String nickNameMac) {
		return bao.getFreeListByNickName(nickNameMac);
	}

	public List<Board> getAdsListByKeyword(String keyword) {

		return dao.getAdsListByKeyword(keyword);
	}

	public List<Board> getAdsListByNickName(String keyword) {
		
		return dao.getAdsListByNickName(keyword);
	}

}