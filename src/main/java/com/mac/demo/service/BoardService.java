package com.mac.demo.service;

import java.awt.print.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mac.demo.jpa.board.BoardRepository;
import com.mac.demo.model.Board;

@Service
public class BoardService {
	

	public Page<Board> getList(Pageable pageable) {
		return null;
	}

}
