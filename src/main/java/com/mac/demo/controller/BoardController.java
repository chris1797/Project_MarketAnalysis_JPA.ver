package com.mac.demo.controller;

import java.awt.print.Pageable;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.model.Attach;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.service.BoardService;


@RequestMapping("/board")
@Controller
public class BoardController {
	
	@Autowired
	private BoardService svc;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	
	
	
	
//	커뮤니티메인화면
	@GetMapping("/main")
	public String main(Model model, HttpSession session) {
		
//		model.addAttribute((String)session.getAttribute("idMac"));
		return "thymeleaf/mac/board/board_main";
	}
	
//======================================== 자유게시판 ========================================
//	게시글작성폼
	@GetMapping("/{categoryMac}/input")
	public String input(Model model,
						HttpSession session,
						@PathVariable("categoryMac") String categoryMac) {
		
		System.out.println("현재 접속한 ID : " + (String)session.getAttribute("idMac"));
		
		// login check
		if((String)session.getAttribute("idMac") == null){
			model.addAttribute("msg", "로그인 후 사용 가능합니다.");
			model.addAttribute("board", new Board());
			
		} else {
			String id = (String)session.getAttribute("idMac");
			
			//닉네임 가져오기
			Board board = new Board();
			board.setNickNameMac(svc.getOne(id).getNickNameMac());
			board.setCategoryMac(categoryMac);
			model.addAttribute("board", board);
			
			// 현재 세션의 ID를 넘겨주고 inputform에서는 hidden으로 다시 넘겨받아서 save	 
			model.addAttribute("idMac", id);
		}
		
		return String.format("thymeleaf/mac/board/%s_board_input", categoryMac);
	}
	

//	게시글 저장
	@PostMapping("/{categoryMac}/save")
	@ResponseBody
	public Map<String, Object> save(Board board,
									@PathVariable("categoryMac") String categoryMac,
									@RequestParam("files") MultipartFile[] mfiles,
									@SessionAttribute(name = "idMac", required = false) String idMac,
									HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// categoryMac 컬럼을 넣어줌
		svc.save(board);
		svc.fileinsert(board, mfiles, request);
		
		map.put("saved", board.getNumMac());
		//insert 후 시퀸스의 값을 가져와 map에 넣은뒤 다시 폼으로
		//그후 그 번호를 가지고 detail로 넘어가독
		//자세한건 form에 ajax 확인
		
		return map;
	}
	
//	리스트
	@GetMapping("/{categoryMac}/list")
	public String getListByPage(@RequestParam(name="page", required = false,defaultValue = "1") int page,
								@PathVariable("categoryMac") String categoryMac,
								Model model,
								HttpSession session) {
		PageHelper.startPage(page, 10);
		model.addAttribute("page", page);
		model.addAttribute("pageInfo", svc.getPageInfo(categoryMac));
		model.addAttribute("idMac",(String)session.getAttribute("idMac"));
		
		return String.format("thymeleaf/mac/board/%s_board_list", categoryMac);
	}
	
	
//  게시글 보기
	@GetMapping("/{categoryMac}/detail/{num}")
	public String getDetail(@PathVariable("num") int num,
							@PathVariable("categoryMac") String categoryMac,
							@RequestParam(name="page", required = false,defaultValue = "1") int page, 
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
			model.addAttribute("msg", "로그인 후 작성 가능합니다.");
		}
		
		// 글 번호
		model.addAttribute("num", num);
		
		// 게시판 분기
		String linkpath = null;
		if(categoryMac.contentEquals("free")) {
			model.addAttribute("board", svc.getFreeDetail(num));
			linkpath = "thymeleaf/mac/board/free_board_detail_copy";
		} else if(categoryMac.contentEquals("ads")) {
			model.addAttribute("board", svc.getAdsDetail(num));
			linkpath = "thymeleaf/mac/board/ads_board_detail_copy";
		} else if(categoryMac.contentEquals("notice")) {
			model.addAttribute("board", svc.getNoticeDetail(num));
			linkpath = "thymeleaf/mac/board/notice_board_detail_copy";
		}
		
		// 페이지네이션
		PageHelper.startPage(page, 7);
		PageInfo<Comment> pageInfo = new PageInfo<>(svc.getCommentList(num));
		
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("page", page);
		// 댓글
		model.addAttribute("comment", comment);
		
		List<Attach> filelist = svc.getFileList(num);
		model.addAttribute("filelist", filelist);
		model.addAttribute("fileindex", filelist.size());
		
		
		// 댓글 삭제를 위한 idMac체크
		
		return String.format("thymeleaf/mac/board/%s_board_detail", categoryMac);
	}
	
//  게시글 삭제
//	PostMapping 방식으로 form 밖에 있는 데이터를 넘기지 못해 get으로 우선 구현
	@GetMapping("/{categoryMac}/delete/{num}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("num") int num,
									  @PathVariable("categoryMac") String categoryMac) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (categoryMac.equals("free")) {
			map.put("deleted", svc.Freedelete(num));
			map.put("commetdeleted", svc.freeCommentAllDelete(num));
		} else if (categoryMac.equals("ads")) {
			map.put("deleted", svc.Adsdelete(num));
			map.put("commetdeleted", svc.adsCommentAllDelete(num));
		}
		return map;
	}
	
//  게시글 업데이트폼
	@GetMapping("/{categoryMac}/update/{num}")
	public String update(@PathVariable("num") int num, 
						 Model model,
						 @PathVariable("categoryMac") String categoryMac) {
		
//		{board_kind}에 따른 html경로 변수 초기화
		String linkpath = null;
		
		if (categoryMac.equals("free")) {
			model.addAttribute("board", svc.getFreeDetail(num));
		} else if (categoryMac.equals("ads")) {
			model.addAttribute("board", svc.getAdsDetail(num));
		}
		
		List<Attach> filelist = svc.getFileList(num);;
		
		model.addAttribute("filelist", filelist);
		model.addAttribute("fileindex", filelist.size());
		
		return String.format("thymeleaf/mac/board/%s_board_edit", categoryMac);
	}
	
//  게시글 수정
	@PostMapping("/{categoryMac}/edit")
	@ResponseBody
	public Map<String, Object> edit(Board newBoard,
									@RequestParam("files") MultipartFile[] mfiles,
									@PathVariable("categoryMac") String categoryMac,
									HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		newBoard.setCategoryMac(categoryMac);	
		map.put("updated", svc.update(newBoard));
		svc.fileupdate(newBoard, mfiles, request);
		
		return map;
	}
	
	
//	게시글 타이틀 검색
	@GetMapping("/{categoryMac}/search")
	public String getListByTitle(@RequestParam(name="page", required = false,defaultValue = "1") int page,
								 @RequestParam(name="category", required = false) String category,
								 @RequestParam(name="keyword", required = false) String keyword,
								 @PathVariable("categoryMac") String categoryMac,
								 Model model) {
		
		PageHelper.startPage(page, 10);
		
		PageInfo<Board> pageInfo = null;
		if(categoryMac.contentEquals("free")) {
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getFreeListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getFreeListByNickName(keyword));
			}
		} else if(categoryMac.contentEquals("ads")) {
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getAdsListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getAdsListByNickName(keyword));
			}
		} else if(categoryMac.contentEquals("notice")) {
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getAdsListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getAdsListByNickName(keyword));
			}
		}
		
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("page", page);
		
		return String.format("thymeleaf/mac/board/%s_board_list", categoryMac);
	}
	
//======================================== 댓글 ========================================
	@PostMapping("/comment")
	@ResponseBody
	public Map<String, Object> comment(Comment comment, Model model, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if((String)session.getAttribute("idMac") == null){ //세션을 가져옴
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
//======================================== 파일 ========================================
	
	@GetMapping("/file/delete/{numMac}")
	@ResponseBody
	public Map<String, Object> file_delte(@PathVariable("numMac") int numMac, 
										  Model model, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("삭제할 파일 No. : " + numMac);
		map.put("filedeleted", svc.filedelete(numMac));
		return map;
	}
	
	@GetMapping("/file/download/{filenum}")
	@ResponseBody
	public ResponseEntity<Resource> download(HttpServletRequest request,
											 @PathVariable(name="filenum", required = false) int FileNum) throws Exception {
		return svc.download(request, FileNum);
	}
}