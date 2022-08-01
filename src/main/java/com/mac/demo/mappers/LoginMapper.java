package com.mac.demo.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.mac.demo.model.User;

@Mapper
public interface LoginMapper {
//	String loginUser(User user);

	String loginUser(String idMac, String pwMac);

	String findId(String nameMac, String phoneNumMac);

	String findPassword(String idMac, String nameMac, String phoneNumMac);
}
