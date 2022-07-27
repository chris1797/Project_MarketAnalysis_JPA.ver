package com.mac.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.thymeleaf.standard.expression.AdditionSubtractionExpression;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.model.Board;
import com.mac.demo.service.BoardService;

@RequestMapping("/board")
@Controller
public class BoardController {

	@Autowired
	private BoardMapper dao;
	
	@Autowired
	private BoardService svc;
	
//	커뮤니티메인화면
	@GetMapping("/main")
	public String main() {
		
		return "board/boardMain";
	}
	
	
//	게시글작성(게시글 인풋폼을 자유게시판과 공지, 광고게시판과 나눠야되나 생각중 )
	@GetMapping("/input")
	public String input(Model model) {
		Board board = new Board();
		board.setPcodeMac(0);
		board.setNickNameMac("재훈");
		model.addAttribute("board", board);
		return "thymeleaf/board/board_inputform";
	}
	

//	게시글 저장
//	추후 로그인 체크 구현 완료 후 수정 예정
	@PostMapping("/save")
	@ResponseBody
	public Map<String, Object> save(Board board, @SessionAttribute(name = "uid", required = false) String uid) {
		Map<String, Object> map = new HashMap<String, Object>();
/*
		board.setPcode(0);
		if (uid == null) {
			map.put("saved", false);
			map.put("msg", "로그인 후에 사용할 수 있습니다");
			return map;
		}
*/
		board.setPcodeMac(0);
		board.setNickNameMac("재훈");
		boolean saved = dao.save(board)>0;

		map.put("saved", saved);
		return map;
	}
	
//	자유게시판
//	page 구현 필요
	@GetMapping("/free/list")
	public String getList(Model model) {

//		PageHelper.startPage(i, dao.getList().size()/3);
//		PageInfo<Board> pageInfo = new PageInfo<>(dao.getList());
		List<Board> list = dao.getList();

//		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("list", list);
		
		return "thymeleaf/board/free_boardList";
	}
	
//  게시글 보기
	@GetMapping("/detail/{num}")
	public String getDetail(@PathVariable("num") int num, Model model) {
		model.addAttribute("num", num);
		model.addAttribute("board", dao.getDetail(num));
		return "thymeleaf/board/free_board_detail";
	}
	
	
//  게시글 삭제
//	PostMapping 방식으로 form 밖에 있는 데이터를 넘기지 못해 get으로 우선 구현
	@GetMapping("/delete/{num}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("num") int num, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean deleted = 0<dao.delete(num);
		System.out.println(deleted);
		model.addAttribute("deleted", deleted);
		return map;
	}
//============================================================================================================//
	
	
//  게시글 업데이트폼
	@GetMapping("/update/{num}")
	public String update(@PathVariable("num") int num, Model model) {

		Board board = new Board();
		board.setNumMac(num);
		model.addAttribute("board", dao.edit(board));
		
		return "board/boardEdit";
	}
	
//  게시글 수정
	@GetMapping("/edit/{num}")
	@ResponseBody
	public Map<String, Object> edit(@PathVariable("num") int num, Board newBoard, Model model) {

		newBoard.setNumMac(num);
		newBoard.setContentsMac(newBoard.getContentsMac());
		newBoard.setTitleMac(newBoard.getTitleMac());

		Map<String, Object> map = new HashMap<String, Object>();
		boolean updated = dao.edit(newBoard)>0;
		map.put("updated", updated);
		return map;
	}
	
	
	
	
//	게시글 타이틀 검색
	@GetMapping("/listByTitle")
	public String getListByTitle(Model model) {

		PageHelper.startPage(5,30);
		PageInfo<Board> pageinfo=new PageInfo<Board>();
		List<Board> pagelist=pageinfo.getList();
		
		model.addAttribute("pageInfo",pageinfo);
		
		List<Board> list = dao.getList();
		model.addAttribute("list", list);
		return "board/boardList";
	}
//	게시글 닉네임 검색
	@GetMapping("/listByNickName")
	public String getListByNickName(Model model) {

		PageHelper.startPage(5,30);
		PageInfo<Board> pageinfo=new PageInfo<Board>();
		List<Board> pagelist=pageinfo.getList();
		
		model.addAttribute("pageInfo",pageinfo);
		
		List<Board> list = dao.getList();
		model.addAttribute("list", list);
		return "board/boardList";
	}
	
	
//	공지게시판(미완성 type속성을 notice로 주면될듯) X
//	type 없이 get으로 구분하여 mapper로 해당 테이블의 list 받아오기
	@GetMapping("/notice/list")
	public String getNoticeList(Model model) {
		
		PageHelper.startPage(5,30);
		PageInfo<Board> pageinfo=new PageInfo<Board>();
		List<Board> pagelist=pageinfo.getList();
		
		model.addAttribute("pageInfo",pageinfo);
		
		List<Board> list = dao.getList();
		model.addAttribute("list", list);
		return "board/noticeList";
	}
	
//	광고게시판(미완성 type속성을 ads로 주면될듯)
	@GetMapping("/ads/list")
	public String getAdsList(Model model) {
		
		PageHelper.startPage(5,30);
		PageInfo<Board> pageinfo=new PageInfo<Board>();
		List<Board> pagelist=pageinfo.getList();
		
		model.addAttribute("pageInfo",pageinfo);
		
		List<Board> list = dao.getList();
		model.addAttribute("list", list);
		return "board/adsList";
	}
	
/*	게시글 타입속성(업종 등) 은 일단 안하기로 함
	@GetMapping("/listByType")
	public String getListByNickNameType(Model model) {
		PageHelper.startPage(5,30);
		PageInfo<Board> pageinfo=new PageInfo<Board>();
		List<Board> pagelist=pageinfo.getList();
		
		model.addAttribute("pageInfo",pageinfo);
		
		List<Board> list = dao.getList();
		model.addAttribute("list", list);
		return "board/boardList";
	}
*/
}
