package com.mac.demo.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.mac.demo.model.User;

@Mapper
public interface LoginMapper {

//	로그인
	String loginUser(String idMac, String pwMac);

//	아이디찾기
	String findId(String nameMac, String emailMac);

//	비밀번호찾기
	String findPassword(String idMac, String nameMac, String emailMac);
}
