package com.mac.demo.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
	

    //모든 유저
	public List<User> findAllUser() {
		return dao.findAllUser();	
	}
	
	//모든 자유게시물
	public List<Board> findAllFreeBord() {
		return dao.findAllFreeBoard();
	}

	//모든 광고게시물
	public List<Board>findAllAdsBoard() {
		return dao.findAllAdsBoard();
	}

	//자유게시물 삭제
	public boolean freeBoardDeleted(int numMac) {
		return dao.freeBoardDeleted(numMac);
	}

	//광고게시물 삭제
	public boolean adsBoardDeleted(int numMac) {
		return  dao.adsBoardDeleted(numMac);
	}

	//계정 삭제
	public boolean userDeleted(int numMac) {
		return dao.userDeleted(numMac);
	}

	//공지사항 저장
	public int save(Board board) {
		return dao.saveNotice(board);
	}

	//공지사항 리스트
	public List<Board> findAllNoticeBoard() {
		return dao.findAllNoticeBoard();
	}

	//공지사항 삭제
	public boolean noticeBoardDeleted(int numMac) {
		return dao.noticeBoardDeleted(numMac);
	}

	//모든 댓글
	public List<Comment> findAllCommentBoard() {
		return dao.findAllCommentBoard();
	}

	//댓글 삭제
	public boolean commentBordDeleted(int numMac) {
		return dao.commentBoardDeleted(numMac);
	}
	

	//자유게시물 검색
	public List<Board> getFreeListByKeyword(String titleMac){
		return bao.getFreeListByKeyword(titleMac);
	}
	public List<Board> getFreeListByNickName(String nickNameMac) {
		return bao.getFreeListByNickName(nickNameMac);
	}

	//광고게시물 검색
	public List<Board> getAdsListByKeyword(String keyword) {

		return dao.getAdsListByKeyword(keyword);
	}
	public List<Board> getAdsListByNickName(String keyword) {
		
		return dao.getAdsListByNickName(keyword);
	}
	
	//공지사항 검색
	public List<Board> getNoticeListByKeyword(String keyword) {
		
		return dao.getNoticeListByKeyword(keyword);
	}
	public List<Board> getNoticeListByNickName(String keyword) {
		
		return dao.getNoticeListByNickName(keyword);
	}

	//댓글 검색
	public List<Comment> getCommentListByKeyword(String keyword) {
		
		return dao.getCommentListByKeyword(keyword);
	}
	public List<Comment> getCommentListByNickName(String keyword) {
		
		return dao.getCommentListByNickName(keyword);
	}

	//계정 검색
	public List<User> getUserListByKeyword(String keyword) {
		return dao.getUserListByKeyword(keyword);
	}

}