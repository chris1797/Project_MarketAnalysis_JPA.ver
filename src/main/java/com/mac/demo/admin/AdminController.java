package com.mac.demo.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.Attach;
import com.mac.demo.dto.Board;
import com.mac.demo.dto.Comment;
import com.mac.demo.dto.User;
import com.mac.demo.service.AttachService;
import com.mac.demo.service.BoardService;
import com.mac.demo.service.CommentService;
import com.mac.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminSvc;
	private final BoardService boardSvc;
	private final AttachService attachSvc;
	private final UserService userSvc;
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
	public ModelAndView adminLogin(@RequestParam(value = "error", required = false) String err) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/adminLoginForm");
		mav.addObject("msg", err);

		return mav;
	}
	
	
	
    //모든 유저 정보
	@GetMapping("/admin/allUser")
	public ModelAndView allUser(@RequestParam(name = "page", required = false, defaultValue = "1") int page) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allUser");

		try {
			PageHelper.startPage(page, 5);
			PageInfo<User> pageInfo = userSvc.getList();
			mav.addObject("pageInfo", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	/**
	 * 자유게시판 리스트
	 */
	@GetMapping("/admin/allFreeBoard")
	public ModelAndView allFreeBord(String categorymac, @RequestParam(name = "page", required = false, defaultValue = "1") int page) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allFreeBoard");

		try {
			PageHelper.startPage(page, 5);
			PageInfo<Board> pageInfo = new PageInfo<>(boardSvc.findBoardByCategory(categorymac));
			mav.addObject("pageInfo", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}
	
	
	//유저 정보 삭제
	@DeleteMapping("/admin/userDeleted/{numMac}")
	public Map<String,Object> UserDeleted(@PathVariable("numMac")Long user_num, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		boolean result = userSvc.delete(user_num);
		map.put("result", result);
		return map;
	}
	
//	자유게시물 지우기
	@DeleteMapping("/admin/freeBoardDeleted/{numMac}")
	public Map<String,Object> freeBoardDeleted(@PathVariable("numMac")Long numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		boolean result = boardSvc.delete(numMac);
		map.put("result", result);

		return map;
	}

//	광고게시물 지우기
	@GetMapping("/admin/adsBoardDeleted/{numMac}")
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
	public Map<String, Object> save(Board board, @RequestParam("files") MultipartFile[] mfiles, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		map.put("saved",boardSvc.save(board, mfiles, savePath)>0);

		return map;
	}

	
	//모든 공지사항
	@GetMapping("/admin/allNoticeBoard")
	public ModelAndView allNoticeBoard(@RequestParam(name="page", required = false,defaultValue ="1") int page) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allNoticeBoard");

		try {
			PageHelper.startPage(page, 5);
			PageInfo<Board> pageInfo = new PageInfo<>(boardSvc.findBoardByCategory("notice"));

			mav.addObject("pageInfo", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}
	
//	공지사항 삭제
	@GetMapping("/admin/noticeBoardDeleted/{numMac}")
	public Map<String,Object> noticeBoardDeleted(@PathVariable("numMac")Long numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		boolean result = boardSvc.delete(numMac);
		map.put("result", result);
		return map;
	}


	//모든 댓글
	@GetMapping("/admin/allComment")
	public ModelAndView allCommentBoard(@RequestParam(name = "page", required = false, defaultValue = "1") int page) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allComment");

		try {
			PageHelper.startPage(page, 5);
			PageInfo<Comment> pageInfo = new PageInfo<>(commentSvc.getCommentList());
			mav.addObject("pageInfo", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	//댓글 지우기
	@GetMapping("/admin/commentDeleted/{numMac}")
	public Map<String, Object> commentBoardDeleted(@PathVariable("numMac") long numMac, HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		boolean result = commentSvc.commentDelete(numMac);
		map.put("result", result);
		return map;
	}

	//자유게시판 검색
	@PostMapping("/admin/freeSearch")
	public ModelAndView searchFree(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
								   @RequestParam(name = "category", required = false) String category,
								   @RequestParam(name = "keyword", required = false) String keyword) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allFreeBoard");

		PageInfo<Board> pageInfo = null;

		try {
			PageHelper.startPage(page, 5);
			if (category.equals("contents")) {
				pageInfo = new PageInfo<>(boardSvc.getListByKeyword(keyword, category));
			} else {
				pageInfo = new PageInfo<>(boardSvc.getListByNickName(keyword, category));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("pageInfo", pageInfo);

		return mav;
	}

	//광고게시판 검색
	@PostMapping("/admin/adsSearch")
	public ModelAndView searchAds(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
								  @RequestParam(name = "category", required = false) String category,
								  @RequestParam(name = "keyword", required = false) String keyword) {

		PageHelper.startPage(page, 5);

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allAdsBoard");

		PageInfo<Board> pageInfo = null;
		try {
			if (category.equals("contents")) {
				pageInfo = new PageInfo<>(boardSvc.getListByKeyword(keyword, category));
			} else {
				pageInfo = new PageInfo<>(boardSvc.getListByNickName(keyword, category));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("pageinfo", pageInfo);

		return mav;
	}


	//공지사항 검색
	@PostMapping("/admin/NoticeSearch")
	public ModelAndView searchNotice(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
									 @RequestParam(name = "category", required = false) String category,
									 @RequestParam(name = "keyword", required = false) String keyword) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allNoticeBoard");
		PageInfo<Board> pageInfo = null;

		try {
			PageHelper.startPage(page, 5);
			pageInfo = new PageInfo<>(boardSvc.getListByKeyword(keyword, category));
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("pageInfo", pageInfo);

		return mav;
	}


	//댓글 검색
	@PostMapping("/admin/commentSearch")
	public ModelAndView searchComment(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
									  @RequestParam(name = "category", required = false) String category,
									  @RequestParam(name = "keyword", required = false) String keyword) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allComment");

		PageInfo<Comment> pageInfo = null;

		try {
			PageHelper.startPage(page, 5);
			if (category.equals("contents")) {
				pageInfo = new PageInfo<>(commentSvc.getCommentListByComment(keyword));
			} else {
				pageInfo = new PageInfo<>(commentSvc.getCommentListByNickName(keyword));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("pageInfo", pageInfo);

		return mav;
	}

	//유저 검색
	@PostMapping("/admin/userSearch")
	public ModelAndView searchUser(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
								   @RequestParam(name = "keyword", required = false) String keyword) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/admin/allUser");

		try {
			PageHelper.startPage(page, 5);
			PageInfo<User> pageInfo = new PageInfo<>(userSvc.findByUsernameContaining(keyword));
			mav.addObject("pageInfo", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}


	@GetMapping("/board/notice/list")
	public ModelAndView getListByPage_notice(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
											 @RequestParam(name = "category", required = false) String category,
											 HttpSession session) {
		ModelAndView mav = new ModelAndView("thymeleaf/mac/board/notice_boardList");

		try {
			PageHelper.startPage(page, 10);
			PageInfo<Board> pageInfo = new PageInfo<>(boardSvc.findBoardByCategory(category));

			mav.addObject("pageInfo", pageInfo);
			mav.addObject("page", page);
			mav.addObject("idMac", (String) session.getAttribute("idMac"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}


	//  게시글 보기
	@GetMapping("/board/notice/detail/{num}")
	public ModelAndView getDetail(@PathVariable("num") Long num,
							@RequestParam(name = "category", required = false) String category,
							@RequestParam(name = "page", required = false, defaultValue = "1") int page,
							HttpSession session) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/board/notice_board_detail");

		String idMac = (String) session.getAttribute("idMac");
		mav.addObject("idMac", idMac);

		Board board = boardSvc.getDetail(num, category);
		List<Attach> fileList = attachSvc.getFileList(num);

		mav.addObject("board", boardSvc.getDetail(num, category));
		mav.addObject("filelist", attachSvc.getFileList(num));
		mav.addObject("fileindex", fileList.size());

		return mav;
	}

	@GetMapping("/board/noticeFile/download/{filenum}")
	public ResponseEntity<Resource> Download(HttpServletRequest request,
											 @PathVariable(name = "filenum", required = false) Long fileNum) throws Exception {
		String fileName = attachSvc.getFileName(fileNum);
		String originFilename = URLDecoder.decode(fileName, "UTF-8");

		Resource resource = resourceLoader.getResource("WEB-INF/files/" + originFilename);
		String contextType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		return attachSvc.download(contextType, fileNum, resource);

	}

	@GetMapping("/board/notice/search")
	public ModelAndView getListByTitle_Notice(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
											  @RequestParam(name = "keyword", required = false) String keyword,
											  @RequestParam(name = "category", required = false) String category) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/board/notice_boardList");

		PageHelper.startPage(page, 10);
		PageInfo<Board> pageInfo = null;

		pageInfo = new PageInfo<>(boardSvc.getListByKeyword(category, keyword));

		mav.addObject("pageInfo", pageInfo);
		mav.addObject("page", page);

		return mav;
	}
}