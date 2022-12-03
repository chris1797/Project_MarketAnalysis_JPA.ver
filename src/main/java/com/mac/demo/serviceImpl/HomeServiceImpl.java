package com.mac.demo.serviceImpl;

import com.mac.demo.dto.Board;
import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.mappers.UserMapper;
import com.mac.demo.model.User;
import com.mac.demo.repository.BoardRepository;
import com.mac.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl {

	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	
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