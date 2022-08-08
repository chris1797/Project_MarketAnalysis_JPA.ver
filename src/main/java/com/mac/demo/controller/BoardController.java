package com.mac.demo.controller;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.SetAttribute;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class BoardController {
	
	@Autowired
	private BoardService svc;
	
//	커뮤니티메인화면
	@GetMapping("/main")
	public String main(Model model, HttpSession session) {
		
//		model.addAttribute((String)session.getAttribute("idMac"));
		return "thymeleaf/mac/board/boardMain_copy";
	}
	
//======================================== 자유게시판 ========================================
//	게시글작성폼
	@GetMapping("/free/input")
	public String input(Model model,HttpSession session) {
		
		Board board = new Board();
		System.out.println("현재 접속한 ID : " + (String)session.getAttribute("idMac"));
		
		// login check
		if((String)session.getAttribute("idMac") == null){
			model.addAttribute("msg", "로그인 후 사용 가능합니다.");
			model.addAttribute("board", board);
			
		} else {
			String id = (String)session.getAttribute("idMac");
			
			//닉네임 가져오기
			board.setNickNameMac(svc.getOne(id).getNickNameMac());
			model.addAttribute("board", board);
			
			// 현재 세션의 ID를 넘겨주고 inputform에서는 hidden으로 다시 넘겨받아서 save	 
			model.addAttribute("idMac", id);
		}
		
		return "thymeleaf/mac/board/free_inputform";
	}
	

//	게시글 저장
	@PostMapping("/free/save")
	@ResponseBody
	public Map<String, Object> save(Board board, @SessionAttribute(name = "idMac", required = false) String idMac) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		svc.saveToFree(board);
		map.put("saved",board.getNumMac());
		//insert 후 시퀸스의 값을 가져와 map에 넣은뒤 다시 폼으로
		//그후 그 번호를 가지고 detail로 넘어가독
		//자세한건 form에 ajax 확인
		
		return map;
	}
	
//	자유게시판 리스트
	@GetMapping("/free/list")
	public String getListByPage_free(@RequestParam(name="page", required = false,defaultValue = "1") int page, 
								Model model) {

		PageHelper.startPage(page, 3);
		PageInfo<Board> pageInfo = new PageInfo<>(svc.getFreeList());
		List<Board> list = pageInfo.getList();
		
		model.addAttribute("pageInfo", pageInfo);
		
		return "thymeleaf/mac/board/free_boardList_copy";
	}
	
	
//  게시글 보기
	@GetMapping("/free/detail/{num}")
	public String getDetail(@PathVariable("num") int num, 
							Model model,
							HttpSession session) {
		
		//test용
		String idMac = null;
		Comment comment = new Comment();
		if(session.getAttribute("idMac") != null) {
			idMac = (String)session.getAttribute("idMac");
			comment.setIdMac((String) session.getAttribute("idMac"));
			comment.setNickNameMac(svc.getOne(idMac).getNickNameMac());	
			comment.setPcodeMac(num);
			model.addAttribute("idMac", idMac);
		} else {
			model.addAttribute("msg", "로그인 후 작성가능합니다.");
		}
		
		// 글상세
		model.addAttribute("num", num);
		model.addAttribute("board", svc.getDetail(num));
		
		// 댓글
		model.addAttribute("commentlist", svc.getCommentList(num));
		model.addAttribute("comment", comment);
		
		// 댓글 삭제를 위한 idMac체크
		
		return "thymeleaf/mac/board/free_board_detail";
	}
	
	
//  게시글 삭제
//	PostMapping 방식으로 form 밖에 있는 데이터를 넘기지 못해 get으로 우선 구현
	@GetMapping("/free/delete/{num}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("num") int num) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("deleted", svc.delete(num));
		return map;
	}
	
//  게시글 업데이트폼
	@GetMapping("/free/update/{num}")
	public String update(@PathVariable("num") int num, Model model) {
		model.addAttribute("board", svc.getDetail(num));
		return "thymeleaf/mac/board/free_updateform";
	}
	
//  게시글 수정
	@PostMapping("/free/edit")
	@ResponseBody
	public Map<String, Object> edit(Board newBoard) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("updated", svc.edit(newBoard));
		return map;
	}
	
	
//	게시글 타이틀 검색
	@GetMapping("/free/search")
	public String getListByTitle(@RequestParam(name="page", required = false,defaultValue = "1") int page,
								@RequestParam(name="category", required = false) String category,
								@RequestParam(name="keyword", required = false) String keyword,
								Model model) {
		
		PageHelper.startPage(page, 3);
		
		PageInfo<Board> pageInfo = null;
		if(category.equals("contents")) {
			pageInfo = new PageInfo<>(svc.getFreeListByKeyword(keyword));
		} else {
			pageInfo = new PageInfo<>(svc.getFreeListByNickName(keyword));
		}
		
		model.addAttribute("pageInfo",pageInfo);
		
		return "thymeleaf/mac/board/free_boardList_copy";
	}
	
	
	
//======================================== 광고게시판 ========================================
//	광고게시판
	@GetMapping("/ads/list")
	public String getListByPage_ads(@RequestParam(name="page", required = false,defaultValue = "1") int page, 
			Model model) {
		
		PageHelper.startPage(page, 3);
		PageInfo<Board> pageInfo = new PageInfo<>(svc.getAdsList());
		
		model.addAttribute("pageInfo", pageInfo);
		
		return "thymeleaf/mac/board/ads_boardList";
	}
	
//	광고게시글 작성폼
	@GetMapping("/ads/input")
	public String input_ads(Model model,HttpSession session) {
		
		String id = null;
		String msg = null;
		Board board = new Board();
		
		System.out.println((String)session.getAttribute("idMac"));
		
		if((String)session.getAttribute("idMac") == null){ //세션을 가져옴
			model.addAttribute("msg", msg);
			msg = "로그인 후 사용 가능합니다.";
		} else {
			id = (String)session.getAttribute("idMac");
			
			//닉네임 가져오기
			board.setNickNameMac(svc.getOne(id).getNickNameMac());
			model.addAttribute("board", board);
			
			// 현재 세션의 ID를 넘겨주고 inputform에서는 hidden으로 다시 넘겨받아서 save	 
			model.addAttribute("idMac", id);
		}
		
		return "thymeleaf/mac/board/ads_inputform";
	}
	
//	광고게시글 저장
	@PostMapping("/ads/save")
	@ResponseBody
	public Map<String, Object> save_ads(Board board, @SessionAttribute(name = "idMac", required = false) String idMac) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		svc.saveToAds(board);
		map.put("saved",board.getNumMac());
		//insert 후 시퀸스의 값을 가져와 map에 넣은뒤 다시 폼으로
		//그후 그 번호를 가지고 detail로 넘어가독
		//자세한건 form에 ajax 확인
		
		return map;
	}

//======================================== 공지게시판 ========================================
//	자유게시판 리스트
	@GetMapping("/notice/list")
	public String getListByPage_notice(@RequestParam(name="page", required = false,defaultValue = "1") int page, 
			Model model) {
		
		PageHelper.startPage(page, 3);
		PageInfo<Board> pageInfo = new PageInfo<>(svc.getNoticeList());
		
		model.addAttribute("pageInfo", pageInfo);
		
		return "thymeleaf/mac/board/notice_boardList";
	}

//======================================== 댓글 ========================================
	@PostMapping("/comment")
	@ResponseBody
	public Map<String, Object> comment(Comment comment, Model model, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if((String)session.getAttribute("idMac") == null){ //세션을 가져옴
//			model.addAttribute("msg", "로그인 후 사용 가능합니다.");
			map.put("msg", "로그인 후 사용 가능합니다.");
		} else {
			map.put("commented", svc.commentsave(comment));
		}
		
		return map;
	}
	
	@GetMapping("/comment/delete/{numMac}")
	@ResponseBody
	public Map<String, Object> comment_delte(@PathVariable int numMac, Model model, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("삭제할 댓글 No. : " + numMac);
		map.put("deleted", svc.commentdelete(numMac));
		return map;
	}
//=====================================================================================
}