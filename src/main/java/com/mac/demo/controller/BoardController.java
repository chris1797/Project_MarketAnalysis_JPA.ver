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
	public String save(Board board,
									@PathVariable("categoryMac") String categoryMac,
									@RequestParam("files") MultipartFile[] mfiles,
									@SessionAttribute(name = "idMac", required = false) String idMac,
									HttpServletRequest request) {
		
		svc.save(board);
		
		// file 객체 배열의 첫번째 값이 비었는지 검사, 파일이 있을 경우에만 실행되도록 조건문 부여
		if(mfiles[0].isEmpty() != true) {
			svc.fileinsert(board, mfiles, request);
		}
		
		//insert 후 해당 글의 num을 다시 폼으로 보내서, 글쓰기 완료 후 해당 글의 상세페이지로 이동되도록 구현
		return String.format("{\"savednum\":\"%d\"}", board.getNumMac());
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
		Comment comment = new Comment();
		if(session.getAttribute("idMac") != null) {
			comment.setIdMac((String) session.getAttribute("idMac"));
			comment.setNickNameMac(svc.getOne((String)session.getAttribute("idMac")).getNickNameMac());	
			comment.setPcodeMac(num);
			model.addAttribute("idMac", (String)session.getAttribute("idMac"));
		} else {
			model.addAttribute("msg", "로그인 후 작성 가능합니다.");
		}
		
//		게시판 분기
		model.addAttribute("board", svc.getDetail(num, categoryMac));
		
//		Pagenation
		PageHelper.startPage(page, 7);
		model.addAttribute("pageInfo", new PageInfo<>(svc.getCommentList(num)));
		model.addAttribute("page", page);
		
//		Comment
		model.addAttribute("comment", comment);
		
//		File
		model.addAttribute("filelist", svc.getFileList(num));
		model.addAttribute("fileindex", svc.getFileList(num).size());
		
		
		// 댓글 삭제를 위한 idMac체크
		
		return String.format("thymeleaf/mac/board/%s_board_detail", categoryMac);
	}
	
//  게시글 삭제
//	PostMapping 방식으로 form 밖에 있는 데이터를 넘기지 못해 get으로 우선 구현
	@GetMapping("/{categoryMac}/delete/{num}")
	@ResponseBody
	public String delete(@PathVariable("num") int num,
									  @PathVariable("categoryMac") String categoryMac) {
		return String.format("{\"deleted\":\"%b\"}", svc.delete(num));
	}
	
//  게시글 업데이트폼
	@GetMapping("/{categoryMac}/update/{num}")
	public String update(@PathVariable("num") int num, 
						 HttpSession session,
						 Model model,
						 @PathVariable("categoryMac") String categoryMac) {
		model.addAttribute("idMac", (String)session.getAttribute("idMac"));
		model.addAttribute("board", svc.getDetail(num, categoryMac));
		model.addAttribute("filelist", svc.getFileList(num));
		model.addAttribute("fileindex", svc.getFileList(num).size());
		
		return String.format("thymeleaf/mac/board/%s_board_edit", categoryMac);
	}
	
//  게시글 수정
	@PostMapping("/{categoryMac}/edit")
	@ResponseBody
	public String edit(Board newBoard,
									@RequestParam("files") MultipartFile[] mfiles,
									@PathVariable("categoryMac") String categoryMac,
									HttpServletRequest request) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		boolean updated = svc.update(newBoard);
		if(mfiles[0].isEmpty() != true) {
			svc.fileupdate(newBoard, mfiles, request);
		}
			
		return String.format("{\"updated\":\"%b\"}", svc.update(newBoard));
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