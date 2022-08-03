package com.mac.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mac.demo.mappers.LoginMapper;

@Service
public class LoginService {

	@Autowired
	private LoginMapper dao;
	
	
//	public String loginUser(User user){
//		
//		return dao.loginUser(user);
//	}


	public String loginUser(String idMac, String pwMac) {
		
		return dao.loginUser(idMac,pwMac);
	}


	public String findId(String nameMac, String phoneNumMac) {
		
		return dao.findId(nameMac,phoneNumMac);
	}


	public String findPassword(String idMac, String nameMac, String phoneNumMac) {
		
		return dao.findPassword(idMac,nameMac,phoneNumMac);
	}
}