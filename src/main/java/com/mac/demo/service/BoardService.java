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

import com.mac.demo.jpa.board.BoardRepository;
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
	
	
	
	public Page<Board> getList(Pageable pageable) {
        //List<Board> list = boardRepository.findAll();
        Page<Board> pageInfo = boardDao.getList(pageable);

        return pageInfo;
    }
	
	public List<Board> getList(){
		return boardDao.getList();
	}

//	------------------id로 유저정보 가져오기-------------------    
	public User getOne(String idMac) {
		return userDao.getOne(idMac);
	}
	
	public Page<Board> getListByPage(Pageable pageable) {
		return null;
	}
	
	public boolean save(Board board){
		return 0 < boardDao.save(board);
	}
	
	
	
	public Board getDetail(int num) {
		return boardDao.getDetail(num);
	}
	
	public boolean delete(int num) {
		return 0 < boardDao.delete(num);
	}
	
	public boolean edit(Board board) {
		return 0 < boardDao.edit(board);
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

//	-----------------------검색-----------------------	
	public List<Board> getFreeListByKeyword(String titleMac){
		return boardDao.getFreeListByKeyword(titleMac);
	}

	public List<Board> getFreeListByNickName(String nickNameMac) {
		return boardDao.getFreeListByNickName(nickNameMac);
	}

	
	
//	------------------------------------------------
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
