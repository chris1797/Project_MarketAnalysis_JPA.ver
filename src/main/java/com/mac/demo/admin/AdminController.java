package com.mac.demo.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.Board;
import com.mac.demo.dto.Comment;
import com.mac.demo.dto.User;
import com.mac.demo.service.CommentService;
import com.mac.demo.serviceImpl.AttachServiceImpl;
import com.mac.demo.serviceImpl.BoardServiceImpl;
import com.mac.demo.serviceImpl.UserServiceImpl;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminSvc;
	private final BoardServiceImpl boardSvc;
	private final AttachServiceImpl attachSvc;
	private final UserServiceImpl userSvc;
	private final CommentService commentSvc;

	ResourceLoader resourceLoader;
	

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
	public String adminLogin(@RequestParam(value = "error", required = false) String err, Model model) {
		model.addAttribute("msg", err);
		return "thymeleaf/mac/admin/adminLoginForm";
	}
	
	
	
    //모든 유저 정보
	@GetMapping("/admin/allUser")
	public String allUser(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
		PageHelper.startPage(page, 5);
		PageInfo<User> pageInfo = userSvc.getList();

		model.addAttribute("pageInfo", pageInfo);
		return "thymeleaf/mac/admin/allUser";
	}

	/**
	 * 자유게시판 리스트
	 */
	@GetMapping("/admin/allFreeBoard")
	public String allFreeBord(Model model, String categorymac, @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
		PageHelper.startPage(page, 5);
		PageInfo<Board> pageInfo = new PageInfo<>(boardSvc.findBoardByCategory(categorymac));
		model.addAttribute("pageInfo", pageInfo);
		return "thymeleaf/mac/admin/allFreeBoard";
	}
	
	
	//유저 정보 삭제
	@DeleteMapping("/admin/userDeleted/{numMac}")
	@ResponseBody
	public Map<String,Object> UserDeleted(@PathVariable("numMac")Long user_num, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		boolean result = userSvc.delete(user_num);
		map.put("result", result);
		return map;
	}
	
//	자유게시물 지우기
	@DeleteMapping("/admin/freeBoardDeleted/{numMac}")
	@ResponseBody
	public Map<String,Object> freeBoardDeleted(@PathVariable("numMac")Long numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		boolean result = boardSvc.delete(numMac);
		map.put("result", result);
		return map;
	}
//	광고게시물 지우기
	@GetMapping("/admin/adsBoardDeleted/{numMac}")
	@ResponseBody
	public Map<String,Object> adsBoardDeleted(@PathVariable("numMac")Long numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		boolean result = boardSvc.delete(numMac);
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
	public Map<String, Object> save(Board board, @RequestParam("files") MultipartFile[] mfiles, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		map.put("saved",boardSvc.save(board, mfiles, savePath)>0);

		return map;
	}

	
	//모든 공지사항
	@GetMapping("/admin/allNoticeBoard")
	public String allNoticeBoard(Model model,@RequestParam(name="page", required = false,defaultValue ="1") int page) {
	     PageHelper.startPage(page, 5);
			PageInfo<Board> pageInfo = new PageInfo<>(boardSvc.findBoardByCategory("notice"));
		
			 model.addAttribute("pageInfo", pageInfo);
		return "thymeleaf/mac/admin/allNoticeBoard";
	}
	
//	공지사항 삭제
	@GetMapping("/admin/noticeBoardDeleted/{numMac}")
	@ResponseBody
	public Map<String,Object> noticeBoardDeleted(@PathVariable("numMac")Long numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		boolean result = boardSvc.delete(numMac);
		map.put("result", result);
		return map;
	}
	
	
	//모든 댓글
		@GetMapping("/admin/allComment")
		public String allCommentBoard(Model model,
									  @RequestParam(name="page", required = false,defaultValue ="1") int page) {
			// 페이지를 설정하면 처음으로 뜰 화면을 기본1로 설정하여 startPage에 넣어준다
				PageHelper.startPage(page, 5);
				// startPage시작하는 페이지 넘버와 그 페이지에 얼마의 글이 들어갈지를 정한다.
				PageInfo<Comment> pageInfo = new PageInfo<>(commentSvc.getCommentList());
				 model.addAttribute("pageInfo", pageInfo);
			return "thymeleaf/mac/admin/allComment";
		}
		
//		댓글 지우기
		@GetMapping("/admin/commentDeleted/{numMac}")
		@ResponseBody
		public Map<String,Object> commentBoardDeleted(@PathVariable("numMac")long numMac, HttpSession session) {
			Map<String, Object> map = new HashMap<>();
			
			boolean result = commentSvc.commentDelete(numMac);
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
				pageInfo = new PageInfo<>(boardSvc.getListByKeyword(keyword, category));
			} else {
				pageInfo = new PageInfo<>(boardSvc.getListByNickName(keyword, category));
			}
			
			model.addAttribute("pageInfo",pageInfo);
			
			return "thymeleaf/mac/admin/allFreeBoard";
		}
		
		//광고게시판 검색
		@PostMapping("/admin/adsSearch")
		public ModelAndView searchAds(@RequestParam(name="page", required = false,defaultValue = "1") int page,
									  @RequestParam(name="category", required = false) String category,
									  @RequestParam(name="keyword", required = false) String keyword) {
			
			PageHelper.startPage(page, 5);

			ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allAdsBoard");
			
			PageInfo<Board> pageInfo = null;
			try {
				if(category.equals("contents")) {
					pageInfo = new PageInfo<>(boardSvc.getListByKeyword(keyword, category));
				} else {
					pageInfo = new PageInfo<>(boardSvc.getListByNickName(keyword, category));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage().toString());
			}

			mav.addObject("pageinfo", pageInfo);
			
			return mav;
		}
		
		//공지사항 검색
		@PostMapping("/admin/NoticeSearch")
		public String searchNotice(@RequestParam(name="page", required = false,defaultValue = "1") int page,
								   @RequestParam(name="category", required = false) String category,
								   @RequestParam(name="keyword", required = false) String keyword,
								   Model model) {
			
			PageHelper.startPage(page, 5);
			
			PageInfo<Board> pageInfo = null;
			
			pageInfo = new PageInfo<>(boardSvc.getListByKeyword(keyword, category));
			
			
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
				pageInfo = new PageInfo<>(commentSvc.getCommentListByComment(keyword));
			} else {
				pageInfo = new PageInfo<>(commentSvc.getCommentListByNickName(keyword));
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
//						pageInfo = new PageInfo<>(svc.getUserListByKeyword(keyword));
					};
					
					model.addAttribute("pageInfo",pageInfo);
					
					return "thymeleaf/mac/admin/allUser";
				}
				
				
				
				//공지사항 보드쪽 서비스
				
				@GetMapping("/board/notice/list")
				public String getListByPage_notice(@RequestParam(name="page", required = false,defaultValue = "1") int page, 
													Model model,
													HttpSession session) {
					
					PageHelper.startPage(page, 10);
					PageInfo<Board> pageInfo = new PageInfo<>(svc.findAllNoticeBoard());
					
					model.addAttribute("pageInfo", pageInfo);
					model.addAttribute("page", page);
					model.addAttribute("idMac",(String)session.getAttribute("idMac"));
					
					return "thymeleaf/mac/board/notice_boardList";
				}
				
				
			//  게시글 보기
				@GetMapping("/board/notice/detail/{num}")
				public String getDetail(@PathVariable("num") int num,
										
										@RequestParam(name="page", required = false,defaultValue = "1") int page, 
										Model model,
										HttpSession session) {
					
					String idMac = null;
					idMac = (String)session.getAttribute("idMac");
					model.addAttribute("idMac", idMac);
					model.addAttribute("board", svc.getNoticeDetail(num));
					model.addAttribute("filelist", svc.getNoticeFileList(num));
					model.addAttribute("fileindex", svc.getNoticeFileList(num).size());
					
					
					// 댓글 삭제를 위한 idMac체크
					
					return "thymeleaf/mac/board/notice_board_detail";
				}
				
				@GetMapping("board/noticeFile/download/{filenum}")
				@ResponseBody
				public ResponseEntity<Resource> noticeDownload(HttpServletRequest request,
														 @PathVariable(name="filenum", required = false) int FileNum) throws Exception {
					
					return svc.noticeDownload(request, FileNum);
				}
				
				@GetMapping("/board/notice/search")
				public String getListByTitle_Notice(@RequestParam(name="page", required = false,defaultValue = "1") int page,
											@RequestParam(name="keyword", required = false) String keyword,
											Model model) {
					
					PageHelper.startPage(page, 10);
				
					
					PageInfo<Board> pageInfo = null;
					
						pageInfo = new PageInfo<>(svc.getNoticeListByKeyword(keyword));
				
					
					model.addAttribute("pageInfo",pageInfo);
					model.addAttribute("page", page);
					
					return "thymeleaf/mac/board/notice_boardList";
				}

				
				
				
				

}