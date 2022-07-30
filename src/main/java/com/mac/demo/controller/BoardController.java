package com.mac.demo.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.apache.ibatis.javassist.expr.NewArray;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.thymeleaf.standard.expression.AdditionSubtractionExpression;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.service.BoardService;

@RequestMapping("/board")
@Controller
@SessionAttributes("idMac")
public class BoardController {
	HttpSession session;
	
	@Autowired
	private BoardMapper dao;
	
	@Autowired
	private BoardService svc;
	
//	커뮤니티메인화면
	@GetMapping("/main")
	public String main() {
		
		return "thymeleaf/board/boardMain";
	}
	
	
//	게시글작성(게시글 인풋폼을 자유게시판과 공지, 광고게시판과 나눠야되나 생각중 )
	@GetMapping("/input")
	public String input(Model model,HttpSession session) {
		Board board = new Board();
		board.setPcodeMac(0);
		board.setNickNameMac("재훈");
		String id =session.getAttribute("idMac").toString();//세션을 가져옴
		model.addAttribute("board", board);
		model.addAttribute("idMac", id);//세션을 넣어준후 form에서는 hidden으로!!!
		return "thymeleaf/board/board_inputform";
	}
	

//	게시글 저장
//	추후 로그인 체크 구현 완료 후 수정 예정
	@PostMapping("/save")
	@ResponseBody
	public Map<String, Object> save(Board board, @SessionAttribute(name = "idMac", required = false) String idMac) {
		Map<String, Object> map = new HashMap<String, Object>();
/*
		board.setPcode(0);
		if (uid == null) {
			map.put("saved", false);
			map.put("msg", "로그인 후에 사용할 수 있습니다");
			return map;
		}
*/
		//board.setPcodeMac(0);
		//board.setNickNameMac("재훈");
		//boolean saved = dao.save(board)>0;

		//map.put("saved", saved);
		
		 dao.save(board);
		 System.out.println(board.getNumMac());
		map.put("saved",board.getNumMac());
		//insert 후 시퀸스의 값을 가져와 map에 넣은뒤 다시 폼으로
		//그후 그 번호를 가지고 detail로 넘어가독
		//자세한건 form에 ajax 확인
		
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
		
		Comment comment = new Comment();
		comment.setIdMac("test");
		comment.setPcodeMac(num);
		
		List<Comment> list = dao.getCommentList(num);
		
		model.addAttribute("commentlist", list);
		model.addAttribute("comment", comment);
		return "thymeleaf/board/free_board_detail";
	}
	
	
//  게시글 삭제
//	PostMapping 방식으로 form 밖에 있는 데이터를 넘기지 못해 get으로 우선 구현
	@GetMapping("/delete/{num}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("num") int num, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean deleted = 0<dao.delete(num);
		model.addAttribute("deleted", deleted);
		return map;
	}
	
//  게시글 업데이트폼
	@GetMapping("/update/{num}")
	public String update(@PathVariable("num") int num, Model model) {
		model.addAttribute("board", dao.getDetail(num));
		return "thymeleaf/board/board_updateform";
	}
	
//  게시글 수정
	@PostMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(Board newBoard, Model model) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean updated = dao.edit(newBoard)>0;
		System.out.println(dao.edit(newBoard));
		System.out.println(newBoard.getNumMac());
		map.put("updated", updated);
		return map;
	}
	
	
	@PostMapping("/comment")
	@ResponseBody
	public Map<String, Object> comment(Comment comment, Model model, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println(comment.getPcodeMac());
		System.out.println(comment.getCommentMac());
		
		boolean commented = 0<dao.commentsave(comment);
		map.put("commented", commented);
		
		return map;
	}
	
//============================================================================================================//
	
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