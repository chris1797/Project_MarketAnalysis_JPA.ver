package com.mac.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mac.demo.mappers.UserMapper;
import com.mac.demo.model.User;

@Service
public class UserService {

	//유저 맵퍼
	@Autowired
	private UserMapper dao;

	public boolean add(User user) {
		return dao.add(user) > 0;
	}

	public List<User> getList() {
		
		return dao.getList();
	}

	public User getOne(String idMac) {
		User user = dao.getOne(idMac);
		return user;
	}

	public boolean deleted(String idMac) {
		boolean result = dao.deleted(idMac);
		return result;
	}

	public boolean updated(User user) {
		boolean result = dao.updated(user);
		return result;
	}

	public boolean idcheck(String idMac) {
		User user = dao.getOne(idMac);
		return user != null;
	}
	
	
	
}
