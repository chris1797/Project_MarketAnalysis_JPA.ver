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
@SessionAttributes("idMac")
public class BoardController {
	
	@Autowired
	private BoardService svc;
	
//	커뮤니티메인화면
	@GetMapping("/main")
	public String main() {
		
		return "thymeleaf/board/boardMain";
	}
	
	
//	게시글작성(게시글 인풋폼을 자유게시판과 공지, 광고게시판과 나눠야되나 생각중 )
	@GetMapping("/free/input")
	public String input(Model model,HttpSession session) {
		
		String id = null;
		String msg = null;
		Board board = new Board();
		
		System.out.println((String)session.getAttribute("idMac"));
		
		if(session.getAttribute("idMac") == null){ //세션을 가져옴
			id = "null";
			msg = "로그인 후 사용 가능합니다.";
		} else {
			id = (String)session.getAttribute("idMac");
			
			//닉네임 가져오기
//			board.setNickNameMac(svc.getOne(id).getNickNameMac());
		}
		
		
		model.addAttribute("msg", msg);
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
		
//		if (idMac == null) {
//			map.put("saved", false);
//			map.put("msg", "로그인 후에 사용할 수 있습니다");
//			return map;
//		}

		
		svc.save(board);
		map.put("saved",board.getNumMac());
		//insert 후 시퀸스의 값을 가져와 map에 넣은뒤 다시 폼으로
		//그후 그 번호를 가지고 detail로 넘어가독
		//자세한건 form에 ajax 확인
		
		return map;
	}
/*	
	자유게시판
	page 구현 필요
	@GetMapping("/free/list")
	public String getList(Model model) {

		PageHelper.startPage(1, dao.getList().size()/3);
		PageInfo<Board> pageInfo = new PageInfo<>(dao.getList());
		model.addAttribute("pageInfo", pageInfo);

		model.addAttribute("list", svc.getList());
		return "thymeleaf/board/free_boardList";
	}
	
//	자유게시판
//	page 구현 필요
	@GetMapping("/free/list/{i}")
	public String getListByPage(@PathVariable("i") int i, Model model) {

		PageHelper.startPage(i, svc.getList().size()/3);
		PageInfo<Board> pageInfo = new PageInfo<>(svc.getList());
		model.addAttribute("pageInfo", pageInfo);

		model.addAttribute("list", svc.getList());
		return "thymeleaf/mac/board/free_boardList";
	}
*/
	
//	자유게시판
//	page 구현 필요
	@GetMapping("/free/list")
	public String getListByPage2(@RequestParam(name="page", required = false,defaultValue = "1") int page, 
								Model model) {

		PageHelper.startPage(page, 3);
		PageInfo<Board> pageInfo = new PageInfo<>(svc.getList());
		List<Board> list = pageInfo.getList();
		
		model.addAttribute("pageInfo", pageInfo);
		
		return "thymeleaf/mac/board/free_boardList";
	}
	
	
//  게시글 보기
	@GetMapping("/detail/{num}")
	public String getDetail(@PathVariable("num") int num, 
							Model model,
							HttpSession session) {
		String idMac = (String) session.getAttribute("idMac");
		
		
		//test용
		Comment comment = new Comment();
		comment.setIdMac("chris");
		comment.setPcodeMac(num);
		
		// 글상세
		model.addAttribute("num", num);
		model.addAttribute("board", svc.getDetail(num));
		
		// 댓글
		model.addAttribute("commentlist", svc.getCommentList(num));
		model.addAttribute("comment", comment);
		
		// 댓글 삭제를 위한 idMac체크
		model.addAttribute("idMac", "chris");
		
		return "thymeleaf/mac/board/free_board_detail";
	}
	
	
//  게시글 삭제
//	PostMapping 방식으로 form 밖에 있는 데이터를 넘기지 못해 get으로 우선 구현
	@GetMapping("/delete/{num}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("num") int num) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("deleted", svc.delete(num));
		return map;
	}
	
//  게시글 업데이트폼
	@GetMapping("/update/{num}")
	public String update(@PathVariable("num") int num, Model model) {
		model.addAttribute("board", svc.getDetail(num));
		return "thymeleaf/mac/board/board_updateform";
	}
	
//  게시글 수정
	@PostMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(Board newBoard) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("updated", svc.edit(newBoard));
		return map;
	}
	
	
	@PostMapping("/comment")
	@ResponseBody
	public Map<String, Object> comment(Comment comment, Model model, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("commented", svc.commentsave(comment));
		return map;
	}
	
	@GetMapping("/comment/delete/{numMac}")
	@ResponseBody
	public Map<String, Object> comment_delte(@PathVariable int numMac, Model model, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println(numMac);
		map.put("deleted", svc.commentdelete(numMac));
		return map;
	}
	
//============================================================================================================//
	
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
		
		return "thymeleaf/mac/board/free_boardList";
	}
	
//	공지게시판(미완성 type속성을 notice로 주면될듯) X
//	type 없이 get으로 구분하여 mapper로 해당 테이블의 list 받아오기
	@GetMapping("/notice/list")
	public String getNoticeList(Model model) {
		
		PageHelper.startPage(5,30);
		PageInfo<Board> pageinfo=new PageInfo<Board>();
		List<Board> pagelist=pageinfo.getList();
		
		model.addAttribute("pageInfo",pageinfo);
		
		List<Board> list = svc.getList();
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
		
		List<Board> list = svc.getList();
		model.addAttribute("list", list);
		return "board/adsList";
	}
}