package com.mac.demo.mappers;

import com.mac.demo.dto.Board;
import com.mac.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {

//	계정CRUD

	//계정리스트
	List<User> getList();
	//계정추가
	int add(User user);
	//계정정보
	User getOne(String idMac);
	//계정삭제
	boolean deleted(String idMac);
	//계정정보 수정
	boolean updated(User user);
	
	//nickname 중복체크
	User getOneNick(String nickNameMac);
	//마이 페이지에서 글
	List<Board> findWrite(String idMac);
	

	
	
	
	
}
