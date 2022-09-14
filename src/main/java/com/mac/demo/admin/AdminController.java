package com.mac.demo.admin;


import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;

@Controller
public class AdminController {
	

	@Autowired
	private AdminService svc;
	
	
	
	//관리자 페이지 메인
		@GetMapping("/admin")
		public String adminMain() {
			return "thymeleaf/mac/admin/adminMain";
		}
		
     //관리자 로그인
		@GetMapping("/admin/loginForm")
		public String adminLogin() {
		
			
			return "thymeleaf/mac/admin/adminLoginForm";
		}
	
	//에러
	@GetMapping("/err")
	public String adminLogin(@RequestParam(value="error",required=false) String err,Model model) {
		
		model.addAttribute("msg", err);
		
		return "thymeleaf/mac/admin/adminLoginForm";
	}
	
	
	
    //모든 유저 정보
	@GetMapping("/admin/allUser")
	public String allUser(Model model,@RequestParam(name="page", required = false,defaultValue ="1") int page) {
		
	     PageHelper.startPage(page, 5);
			PageInfo<User> pageInfo = new PageInfo<>(svc.findAllUser());
			
			 model.addAttribute("pageInfo", pageInfo);
             return "thymeleaf/mac/admin/allUser";
	}
	
	//모든 자유게시판
	@GetMapping("/admin/allFreeBoard")
	public String allFreeBord(Model model,@RequestParam(name="page", required = false,defaultValue ="1") int page) {
	     PageHelper.startPage(page, 5);
			PageInfo<Board> pageInfo = new PageInfo<>(svc.findAllFreeBord());
		System.out.println(svc.findAllFreeBord());
			 model.addAttribute("pageInfo", pageInfo);
		return "thymeleaf/mac/admin/allFreeBoard";
	}
	
	//모든 광고게시판
	@GetMapping("/admin/allAdsBoard")
	public String allAdsBoard(Model model,@RequestParam(name="page", required = false,defaultValue ="1") int page) {
	     PageHelper.startPage(page, 5);
			PageInfo<Board> pageInfo = new PageInfo<>(svc.findAllAdsBoard());
			
			 model.addAttribute("pageInfo", pageInfo);
		return "thymeleaf/mac/admin/allAdsBoard";
	}
	
	
	//유저 지우기
	@GetMapping("/admin/userDeleted/{numMac}")
	@ResponseBody
	public Map<String,Object> UserDeleted(@PathVariable("numMac")int numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.userDeleted(numMac);
		map.put("result", result);
		return map;
	}
	
//	자유게시물 지우기
	@GetMapping("/admin/freeBoardDeleted/{numMac}")
	@ResponseBody
	public Map<String,Object> freeBoardDeleted(@PathVariable("numMac")int numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("1111");
		
		boolean result = svc.freeBoardDeleted(numMac);
		map.put("result", result);
		return map;
	}
//	광고게시물 지우기
	@GetMapping("/admin/adsBoardDeleted/{numMac}")
	@ResponseBody
	public Map<String,Object> adsBoardDeleted(@PathVariable("numMac")int numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		boolean result = svc.adsBoardDeleted(numMac);
		map.put("result", result);
		return map;
	}
	
	//공지사항 폼 띄우기
	@GetMapping("/admin/writeNotice")
	public String writeNotice() {
	
		
		return "thymeleaf/mac/admin/writeNotice";
	}
	
	//공지사항 저장
	@PostMapping("/admin/save")
	@ResponseBody
	public Map<String, Object> save(Board board) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saved",svc.save(board)>0);
		return map;
	}
	
	//모든 공지사항
	@GetMapping("/admin/allNoticeBoard")
	public String allNoticeBoard(Model model,@RequestParam(name="page", required = false,defaultValue ="1") int page) {
	     PageHelper.startPage(page, 5);
			PageInfo<Board> pageInfo = new PageInfo<>(svc.findAllNoticeBoard());
		
			 model.addAttribute("pageInfo", pageInfo);
		return "thymeleaf/mac/admin/allNoticeBoard";
	}
	
//	공지사항 삭제
	@GetMapping("/admin/noticeBoardDeleted/{numMac}")
	@ResponseBody
	public Map<String,Object> noticeBoardDeleted(@PathVariable("numMac")int numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		boolean result = svc.noticeBoardDeleted(numMac);
		map.put("result", result);
		return map;
	}
	
	
	//모든 댓글
		@GetMapping("/admin/allComment")
		public String allCommentBoard(Model model,@RequestParam(name="page", required = false,defaultValue ="1") int page) {
			//페이지를 설정하면 처음으로 뜰 화면을 기본1로 설정하여 startPage에 넣어준다
		     PageHelper.startPage(page, 5);
				//startPage시작하는 페이지 넘버와 그 페이지에 얼마의 글이 들어갈지를 정한다.
				PageInfo<Comment> pageInfo = new PageInfo<>(svc.findAllCommentBoard());
				 model.addAttribute("pageInfo", pageInfo);
			return "thymeleaf/mac/admin/allComment";
		}
		
//		댓글 지우기
		@GetMapping("/admin/commentDeleted/{numMac}")
		@ResponseBody
		public Map<String,Object> commentBoardDeleted(@PathVariable("numMac")int numMac, HttpSession session) {
			Map<String, Object> map = new HashMap<>();
			
			boolean result = svc.commentBordDeleted(numMac);
			map.put("result", result);
			return map;
		}
		
		//자유게시판 검색
		@PostMapping("/admin/freeSearch")
		public String searchFree(@RequestParam(name="page", required = false,defaultValue = "1") int page,
									@RequestParam(name="category", required = false) String category,
									@RequestParam(name="keyword", required = false) String keyword,
									Model model) {
			
			PageHelper.startPage(page, 5);
			
			PageInfo<Board> pageInfo = null;
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getFreeListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getFreeListByNickName(keyword));
			}
			
			model.addAttribute("pageInfo",pageInfo);
			
			return "thymeleaf/mac/admin/allFreeBoard";
		}
		
		//광고게시판 검색
		@PostMapping("/admin/adsSearch")
		public String searchAds(@RequestParam(name="page", required = false,defaultValue = "1") int page,
									@RequestParam(name="category", required = false) String category,
									@RequestParam(name="keyword", required = false) String keyword,
									Model model) {
			
			PageHelper.startPage(page, 5);
			
			PageInfo<Board> pageInfo = null;
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getAdsListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getAdsListByNickName(keyword));
			}
			
			model.addAttribute("pageInfo",pageInfo);
			
			return "thymeleaf/mac/admin/allAdsBoard";
		}
		
		//공지사항 검색
		@PostMapping("/admin/NoticeSearch")
		public String searchNotice(@RequestParam(name="page", required = false,defaultValue = "1") int page,
									@RequestParam(name="category", required = false) String category,
									@RequestParam(name="keyword", required = false) String keyword,
									Model model) {
			
			PageHelper.startPage(page, 5);
			
			PageInfo<Board> pageInfo = null;
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getNoticeListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getNoticeListByNickName(keyword));
			}
			
			model.addAttribute("pageInfo",pageInfo);
			
			return "thymeleaf/mac/admin/allNoticeBoard";
		}
		
		//댓글 검색
		@PostMapping("/admin/commentSearch")
		public String searchComment(@RequestParam(name="page", required = false,defaultValue = "1") int page,
									@RequestParam(name="category", required = false) String category,
									@RequestParam(name="keyword", required = false) String keyword,
									Model model) {
			
			PageHelper.startPage(page, 5);
			
			PageInfo<Comment> pageInfo = null;
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getCommentListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getCommentListByNickName(keyword));
			}
			
			model.addAttribute("pageInfo",pageInfo);
			
			return "thymeleaf/mac/admin/allComment";
		}
		
		//유저 검색
				@PostMapping("/admin/userSearch")
				public String searchUser(@RequestParam(name="page", required = false,defaultValue = "1") int page,
											@RequestParam(name="category", required = false) String category,
											@RequestParam(name="keyword", required = false) String keyword,
											Model model) {
					
					PageHelper.startPage(page, 5);
					
					PageInfo<User> pageInfo = null;
					if(category.equals("contents")) {
						pageInfo = new PageInfo<>(svc.getUserListByKeyword(keyword));
					};
					
					model.addAttribute("pageInfo",pageInfo);
					
					return "thymeleaf/mac/admin/allUser";
				}

}