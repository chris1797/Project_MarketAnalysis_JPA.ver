package com.mac.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.mappers.UserMapper;
import com.mac.demo.model.Board;
import com.mac.demo.model.User;

@Service
public class HomeService {
	
	@Autowired
	private UserMapper uao;
	@Autowired
	private BoardMapper bao;
	

	public User getMyPageInUser(String nickNameMac) {
		
	 
	    return uao.getOne(nickNameMac);
	    
		
	}
	
	public List<Board> getMyPageInFreeBoard(String idMac) {
		System.out.println(bao.getMypageInFreeBoard(idMac).toString());
		 
	    return bao.getMypageInFreeBoard(idMac);
	    
		
	}
	public List<Board> getMyPageInAdsBoard(String idMac) {
		
	    return bao.getMypageInAdsBoard(idMac);
	    
		
	}

}