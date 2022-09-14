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
		return "thymeleaf/mac/board/boardMain_copy";
	}
	
//======================================== 자유게시판 ========================================
//	게시글작성폼
	@GetMapping("/{board_kind}/input")
	public String input(Model model,
						HttpSession session,
						@PathVariable("board_kind") String board_kind) {
		
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
			model.addAttribute("board", board);
			
			// 현재 세션의 ID를 넘겨주고 inputform에서는 hidden으로 다시 넘겨받아서 save	 
			model.addAttribute("idMac", id);
		}
		
		String linkpath = null;
		if(board_kind.contentEquals("free")) {
			linkpath = "thymeleaf/mac/board/free_inputform";
		} else if(board_kind.contentEquals("ads")) {
			linkpath = "thymeleaf/mac/board/ads_inputform";
		} else if(board_kind.contentEquals("notice")) {
			linkpath = "thymeleaf/mac/board/notice_inputform";
		}
		
		return linkpath;
	}
	

//	게시글 저장
	@PostMapping("/{board_kind}/save")
	@ResponseBody
	public Map<String, Object> save(Board board,
									@PathVariable("board_kind") String board_kind,
									@RequestParam("files") MultipartFile[] mfiles,
									@SessionAttribute(name = "idMac", required = false) String idMac,
									HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		String fname_changed = null;
		
		// 파일 VO List
		List<Attach> attList = new ArrayList<>();
		
		// 업로드
		try {
			for (int i = 0; i < mfiles.length; i++) {
				// mfiles 파일명 수정
				String[] token = mfiles[i].getOriginalFilename().split("\\.");
				fname_changed = token[0] + "_" + System.nanoTime() + "." + token[1];
				
					// Attach 객체 만들어서 가공
					Attach _att = new Attach();
					_att.setIdMac(board.getIdMac());
					_att.setNickNameMac(svc.getOne(board.getIdMac()).getNickNameMac());
					_att.setFileNameMac(fname_changed);
					_att.setFilepathMac(savePath);
				
				attList.add(_att);

//				메모리에 있는 파일을 저장경로에 옮기는 method, local 디렉토리에 있는 그 파일만 셀렉가능
				mfiles[i].transferTo(
						new File(savePath + "/" + fname_changed));
			}
			svc.insert(attList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 경로를 변수로 받아서 그에 따른 테이블 insert 분기
		if (board_kind.contentEquals("free")) {
			svc.saveToFree(board);
		} else if (board_kind.contentEquals("ads")) {
			svc.saveToAds(board);
		}
		map.put("saved",board.getNumMac());
		//insert 후 시퀸스의 값을 가져와 map에 넣은뒤 다시 폼으로
		//그후 그 번호를 가지고 detail로 넘어가독
		//자세한건 form에 ajax 확인
		
		return map;
	}
	
//	자유게시판 리스트
	@GetMapping("/{board_kind}/list")
	public String getListByPage(@RequestParam(name="page", required = false,defaultValue = "1") int page,
								@PathVariable("board_kind") String board_kind,
								Model model,
								HttpSession session) {
		PageHelper.startPage(page, 10);
		PageInfo<Board> pageInfo = null;
		
		model.addAttribute("page", page);
		model.addAttribute("idMac",(String)session.getAttribute("idMac"));
		
		String linkpath = null;
		
		// board_kind == "free" 로 하면 오류남
		if(board_kind.contentEquals("free")) {
			pageInfo = new PageInfo<>(svc.getFreeList());
			linkpath = "thymeleaf/mac/board/free_boardList_copy";
		} else if (board_kind.contentEquals("ads")) {
			pageInfo = new PageInfo<>(svc.getAdsList());
			linkpath = "thymeleaf/mac/board/ads_boardList_copy";
		} else if (board_kind.contentEquals("notice")) {
			pageInfo = new PageInfo<>(svc.getNoticeList());
			linkpath = "thymeleaf/mac/board/notice_boardList_copy";
		}
		
		model.addAttribute("pageInfo", pageInfo);
		
		return linkpath;
	}
	
	
//  게시글 보기
	@GetMapping("/{board_kind}/detail/{num}")
	public String getDetail(@PathVariable("num") int num,
							@PathVariable("board_kind") String board_kind,
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
		if(board_kind.contentEquals("free")) {
			model.addAttribute("board", svc.getFreeDetail(num));
			linkpath = "thymeleaf/mac/board/free_board_detail_copy";
		} else if(board_kind.contentEquals("ads")) {
			model.addAttribute("board", svc.getAdsDetail(num));
			linkpath = "thymeleaf/mac/board/ads_board_detail_copy";
		} else if(board_kind.contentEquals("notice")) {
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
		
		return linkpath;
	}
	
//  게시글 삭제
//	PostMapping 방식으로 form 밖에 있는 데이터를 넘기지 못해 get으로 우선 구현
	@GetMapping("/{board_kind}/delete/{num}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("num") int num,
									  @PathVariable("board_kind") String board_kind) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (board_kind.equals("free")) {
			map.put("deleted", svc.Freedelete(num));
			map.put("commetdeleted", svc.freeCommentAllDelete(num));
		} else if (board_kind.equals("ads")) {
			map.put("deleted", svc.Adsdelete(num));
			map.put("commetdeleted", svc.adsCommentAllDelete(num));
		}
		return map;
	}
	
//  게시글 업데이트폼
	@GetMapping("/{board_kind}/update/{num}")
	public String update(@PathVariable("num") int num, 
						 Model model,
						 @PathVariable("board_kind") String board_kind) {
		
//		{board_kind}에 따른 html경로 변수 초기화
		String linkpath = null;
		
		if (board_kind.equals("free")) {
			model.addAttribute("board", svc.getFreeDetail(num));
			linkpath = "thymeleaf/mac/board/free_updateform";
		} else if (board_kind.equals("ads")) {
			model.addAttribute("board", svc.getAdsDetail(num));
			linkpath = "thymeleaf/mac/board/ads_updateform";
		}
		
		List<Attach> filelist = svc.getFileList(num);;
		
		model.addAttribute("filelist", filelist);
		model.addAttribute("fileindex", filelist.size());
		
		return linkpath;
	}
	
//  게시글 수정
	@PostMapping("/{board_kind}/edit")
	@ResponseBody
	public Map<String, Object> edit(Board newBoard,
									@RequestParam("files") MultipartFile[] mfiles,
									@PathVariable("board_kind") String board_kind,
									HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (board_kind.equals("free")) {
			map.put("updated", svc.Freeedit(newBoard));
		} else if (board_kind.equals("ads")) {
			map.put("updated", svc.Adsedit(newBoard));
		}
		
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		String fname_changed = null;
		
		// 파일 VO List
		List<Attach> attList = new ArrayList<>();
		
		// 업로드
		try {
			for (int i = 0; i < mfiles.length; i++) {
				// mfiles 파일명 수정
				String[] token = mfiles[i].getOriginalFilename().split("\\.");
				fname_changed = token[0] + "_" + System.nanoTime() + "." + token[1];
				
					// Attach 객체 만들어서 가공
					Attach _att = new Attach();
					_att.setPcodeMac(newBoard.getNumMac());
					_att.setIdMac(newBoard.getIdMac());
					_att.setNickNameMac(svc.getOne(newBoard.getIdMac()).getNickNameMac());
					_att.setFileNameMac(fname_changed);
					_att.setFilepathMac(savePath);
				
				attList.add(_att);

//				메모리에 있는 파일을 저장경로에 옮기는 method, local 디렉토리에 있는 그 파일만 셀렉가능
				mfiles[i].transferTo(
						new File(savePath + "/" + fname_changed));
			}
			svc.update(attList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	
//	게시글 타이틀 검색
	@GetMapping("/{board_kind}/search")
	public String getListByTitle(@RequestParam(name="page", required = false,defaultValue = "1") int page,
								 @RequestParam(name="category", required = false) String category,
								 @RequestParam(name="keyword", required = false) String keyword,
								 @PathVariable("board_kind") String board_kind,
								 Model model) {
		
		PageHelper.startPage(page, 10);
		
		String linkpath = null;
		PageInfo<Board> pageInfo = null;
		if(board_kind.contentEquals("free")) {
			linkpath = "thymeleaf/mac/board/free_boardList_copy";
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getFreeListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getFreeListByNickName(keyword));
			}
		} else if(board_kind.contentEquals("ads")) {
			linkpath = "thymeleaf/mac/board/ads_boardList_copy";
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getAdsListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getAdsListByNickName(keyword));
			}
		} else if(board_kind.contentEquals("notice")) {
			linkpath = "thymeleaf/mac/board/notice_boardList_copy";
			if(category.equals("contents")) {
				pageInfo = new PageInfo<>(svc.getAdsListByKeyword(keyword));
			} else {
				pageInfo = new PageInfo<>(svc.getAdsListByNickName(keyword));
			}
		}
		
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("page", page);
		
		return linkpath;
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