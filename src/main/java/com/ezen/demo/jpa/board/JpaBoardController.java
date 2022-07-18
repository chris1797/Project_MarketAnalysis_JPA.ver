package com.ezen.demo.jpa.board;

import java.sql.Date;
import java.util.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/jpaboard")
public class JpaBoardController {
	
//	test에 활용
//	log.trace("list={}", list.toString());

	long miliseconds = System.currentTimeMillis();
    Date date = new Date(miliseconds);
	
	@Autowired
	private JpaBoardService svc;
	
	
	@GetMapping("")
	public String jpaTest() {
		return "JPA Test";
	}
	
	@GetMapping("/inputform")
	public String inputform(Model model) {
		return "/jpa_board/board_inputform";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Board board) {
		Map<String, Object> map = new HashMap<>();
		
		boolean added = svc.add(board); // 레코드 추가 (한 행 추가)
		map.put("added", added);
		
		return map;
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Board> list = svc.getList();
		
		model.addAttribute("list", list);
		return "/jpa_board/board_list";
	}
	
	@PostMapping("/remove/{num}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("num") int num) {
		Map<String, Object> map = new HashMap<>();
		
		boolean removed = svc.remove(num);
		map.put("removed", removed);
		
		return map;
	}
	
	@GetMapping("/detail/{num}")
	public String detail(@PathVariable("num") int num, Model model) {
		Board board = svc.findByNum(num);
		
		model.addAttribute("board", board);
		return "/jpa_board/board_detail";
	}
	
	@GetMapping("/editform/{num}")
	public String editform(@PathVariable("num") int num, Model model) {
		Board board = svc.findByNum(num);
		
		model.addAttribute("board", board);
		return "/jpa_board/board_edit";
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Map<String, Object> update(Board board) {
		log.trace(board.toString());
		Map<String, Object> map = new HashMap<>();
		
		boolean updated = svc.update(board);
		map.put("updated", updated);
		
		return map;
	}
	
	/*
	
	@GetMapping("/findAll")
	public ResponseEntity<List<Board>> findAllBoard() {
		
		List<Board> Boardlist = BoardRepository.findAll();
		
		return new ResponseEntity<>(Boardlist, HttpStatus.OK); 
	}
	
	@GetMapping("/findbyid/{num}")
	public ResponseEntity<Board> findbyid(@PathVariable("num") int num) {
		
		Optional<Board> op = BoardRepository.findById(num);
		Board Board = op.get();
		if(op.isEmpty()) {
			//검색 실패
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<>(Board, HttpStatus.OK); 
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Board>> findByUname(@PathVariable("name") String uname){
		List<Board> Boardlist = BoardRepository.findByUname(uname);
		
		return new ResponseEntity<List<Board>>(Boardlist, HttpStatus.OK);
	}
	
	@GetMapping("/name_email/{name}/{email}")
	public ResponseEntity<List<Board>> findByUnameAndEmail(@PathVariable("name") String uname, 
														@PathVariable("email") String email){
		List<Board> Boardlist = BoardRepository.findByUnameAndEmail(uname, email);
		
		return new ResponseEntity<List<Board>>(Boardlist, HttpStatus.OK);
	}
	
	@GetMapping("/update/{num}/{name}/{email}")
	public ResponseEntity<Board> update(@PathVariable("num") int num,
										@PathVariable("name") String uname,
										@PathVariable("email") String email) {
		Board Board = new Board();
		Board.setNum(num);
		Board.setUname(uname);
		Board.setEmail(email);
		
		Board savedBoard = BoardRepository.save(Board); // 레코드 추가 (한 행 추가)
		
		return new ResponseEntity<>(savedBoard, HttpStatus.OK); 
	}
	*/
	
	/*
	@GetMapping("/jpql/{start}/{end}")
	public ResponseEntity<List<Board>> getAllBet(@PathVariable("start") int start,
												@PathVariable("start") int end) {
		
		return new ResponseEntity<>(BoardRepository.findAllNumBet(start, end), HttpStatus.OK);
	}
	 */
	
}