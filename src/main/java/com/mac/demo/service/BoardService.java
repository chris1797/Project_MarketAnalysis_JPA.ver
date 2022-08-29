package com.mac.demo.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.SimplePropertyValueConversions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.mappers.UserMapper;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;

@Service
public class BoardService {

	@Autowired
	private BoardMapper boardDao;
	
	@Autowired
	private UserMapper userDao;
	
	
	public List<Board> getFreeList(){
		return boardDao.getFreeList();
	}
	
	public List<Board> getNoticeList() {
		return boardDao.getNoticeList();
	}
	
	public List<Board> getAdsList() {
		return boardDao.getAdsList();
	}

//	------------------id로 유저정보 가져오기-------------------    
	public User getOne(String idMac) {
		return userDao.getOne(idMac);
		
	}
//	------------------ SAVE -------------------    
	public boolean saveToFree(Board board){
		return 0 < boardDao.saveToFree(board);
	}
	public boolean saveToAds(Board board){
		return 0 < boardDao.saveToAds(board);
	}
	
//	------------------상세보기-------------------    
	public Board getFreeDetail(int num) {
		return boardDao.getFreeDetail(num);
	}
	
	public Board getAdsDetail(int num) {
		return boardDao.getAdsDetail(num);
	}
	public Board getNoticeDetail(int num) {
		return boardDao.getNoticeDetail(num);
	}
//	------------------DELETE-------------------    
	public boolean Freedelete(int num) {
		return 0 > boardDao.Freedelete(num);
	}
	public boolean Adsdelete(int num) {
		return 0 > boardDao.Adsdelete(num);
	}
	public boolean Noticedelete(int num) {
		return 0 > boardDao.Noticedelete(num);
	}
	
	public boolean Freeedit(Board board) {
		return 0 < boardDao.Freeedit(board);
	}
	public boolean Adsedit(Board board) {
		return 0 < boardDao.Adsedit(board);
	}
	public boolean Noticeedit(Board board) {
		return 0 < boardDao.Noticeedit(board);
		
	}

	public boolean freeCommentAllDelete(int num) {
		return 0<boardDao.freeCommentAllDelete(num);
		
	}
	public boolean adsCommentAllDelete(int num) {
		return 0<boardDao.adsCommentAllDelete(num);
		
	}
//	-----------------------댓글-----------------------
	public List<Comment> getCommentList(int num){
		return boardDao.getCommentList(num);		
	}
	
	public boolean commentsave(Comment comment) {
		return 0 < boardDao.commentsave(comment);	
	}
	
	public boolean commentdelete(int numMac) {
		return 0 < boardDao.commentdelete(numMac);
	}

//	-----------------------SEARCH-----------------------	
	public List<Board> getFreeListByKeyword(String titleMac){
		return boardDao.getFreeListByKeyword(titleMac);
	}

	public List<Board> getFreeListByNickName(String nickNameMac) {
		return boardDao.getFreeListByNickName(nickNameMac);
	}
	
	public List<Board> getAdsListByKeyword(String titleMac){
		return boardDao.getAdsListByKeyword(titleMac);
	}

	public List<Board> getAdsListByNickName(String nickNameMac) {
		return boardDao.getAdsListByNickName(nickNameMac);
	}
	
	public List<Board> getNoticeListByKeyword(String titleMac) {
		
		return boardDao.getNoticeListByKeyword(titleMac);
	}

	public List<Board> getNoticeListByNickName(String nickNameMac) {
		return boardDao.getNoticeListByNickName(nickNameMac);
	}
	
	

	
	
//	------------------------PAGE------------------------
	public int[] getLinkRange(Page<Board> pageInfo) {
		int start = 0;
		int end = 0;
		
		if (pageInfo.getNumber() - 2 < 0) {
			start = 0;
		} else {
			start = pageInfo.getNumber() - 2;
		}
		
		if (pageInfo.getTotalPages() < (start + 4)) {
			end = pageInfo.getTotalPages();
			start = (end - 4) < 0 ? 0 : (end - 4);
		} else {
			end = start + 4;
		}
		return new int[] { start, end };
	}


	


}
