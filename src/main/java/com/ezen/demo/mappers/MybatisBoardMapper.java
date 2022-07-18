package com.ezen.demo.mappers;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.demo.model.Board;


@Mapper
public interface MybatisBoardMapper {

	int write(Board board);
	int update(Board board);
	int remove(int num);

	Board getBoardByNum(int num);
	List<Board> getListAll();

}