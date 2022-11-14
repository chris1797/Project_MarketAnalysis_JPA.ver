package com.mac.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.Board;
import com.mac.demo.dto.Comment;
import com.mac.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService svc;

	ResourceLoader resourceLoader;
	
//	생성자가 1개일 경우, @Autowired를 생략해도 주입가능

	
//	커뮤니티메인화면
	@GetMapping("/main")
	public String main(Model model, HttpSession session) {
		model.addAttribute((String)session.getAttribute("idMac"));
		return "thymeleaf/mac/board/board_main";
	}
	
//======================================== 자유게시판 ========================================
//	게시글작성폼
	@GetMapping("/{categoryMac}/input")
	public String input(Model model,
						HttpSession session,
						@PathVariable("categoryMac") String categorymac) {
		// login check
		String id = null;
		String idMac = null;
		try {
			idMac = (String)session.getAttribute("idMac");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(idMac == null){
			model.addAttribute("msg", "로그인 후 사용 가능합니다.");
			model.addAttribute("board", new Board());
			return "thymeleaf/mac/login/loginForm";
		} else {

			//닉네임 가져오기
			Board board = new Board();
			board.setNicknamemac(svc.getOne(idMac).getNicknamemac());
			board.setCategorymac(categorymac);
			model.addAttribute("board", board);
			
			// 현재 세션의 ID를 넘겨주고 inputform에서는 hidden으로 다시 넘겨받아서 save	 
			model.addAttribute("idMac", idMac);
		}
		
		return String.format("thymeleaf/mac/board/%s_board_input", categorymac);
	}
	

//	게시글 저장
	@PostMapping("/{categoryMac}/save")
	@ResponseBody
	public String save(@Valid Board board,
									@PathVariable("categoryMac") String categoryMac,
									@RequestParam("files") MultipartFile[] mfiles,
									@SessionAttribute(name = "idMac", required = false) String idMac,
									HttpServletRequest request) {
		log.trace(board.toString());

		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		
		//insert 후 해당 글의 num을 다시 폼으로 보내서, 글쓰기 완료 후 해당 글의 상세페이지로 이동되도록 구현
		return String.format("{\"savednum\":\"%d\"}", svc.save(board, mfiles, savePath));
	}
	
//	리스트
	@GetMapping("/{categoryMac}/list")
	public String getListByPage(@RequestParam(name="page", required = false,defaultValue = "1") int page,
								@PathVariable("categoryMac") String categoryMac,
								Model model,
								HttpSession session) {
		try {
			PageHelper.startPage(page, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		model.addAttribute("pageInfo", svc.getPageInfo(categoryMac));
		try {
			model.addAttribute("idMac",(String)session.getAttribute("idMac"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return String.format("thymeleaf/mac/board/%s_board_list", categoryMac);
	}
	
	
//  게시글 보기
	@GetMapping("/{categoryMac}/detail/{num}")
	public String getDetail(@PathVariable("num") int boardnum,
							@PathVariable("categoryMac") String categoryMac,
							@RequestParam(name="page", required = false,defaultValue = "1") int page, 
							Model model,
							HttpSession session) {
		
		//test용
		Comment comment = new Comment();
		if(session.getAttribute("idMac") != null) {
			comment.setIdmac((String) session.getAttribute("idMac"));
			comment.setNicknamemac(svc.getOne((String)session.getAttribute("idMac")).getNicknamemac());	
			comment.setPcodemac(boardnum);
			model.addAttribute("idMac", (String)session.getAttribute("idMac"));
		} else {
			model.addAttribute("msg", "로그인 후 작성 가능합니다.");
		}
		
//		게시판 분기
		model.addAttribute("board", svc.getDetail(boardnum, categoryMac));
		
//		Pagenation
		PageHelper.startPage(page, 7);
		model.addAttribute("pageInfo", new PageInfo<>(svc.getCommentList(boardnum)));
		model.addAttribute("page", page);
		
//		Comment
		model.addAttribute("comment", comment);
		
//		File
		model.addAttribute("filelist", svc.getFileList(boardnum));
		model.addAttribute("fileindex", svc.getFileList(boardnum).size());
		
		
		// 댓글 삭제를 위한 idMac체크
		
		return String.format("thymeleaf/mac/board/%s_board_detail", categoryMac);
	}
	
//  게시글 삭제
//	PostMapping 방식으로 form 밖에 있는 데이터를 넘기지 못해 get으로 우선 구현
	@DeleteMapping("/{categoryMac}/delete/{num}")
	@ResponseBody
	public String delete(@PathVariable("num") int num,
									  @PathVariable("categoryMac") String categoryMac) {
		return String.format("{\"deleted\":\"%b\"}", svc.delete(num));
	}
	
//  게시글 업데이트폼
	@PutMapping("/{categoryMac}/update/{num}")
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
	@PutMapping("/{categoryMac}/edit")
	@ResponseBody
	public String edit(@Valid Board newBoard,
						@RequestParam("files") MultipartFile[] mfiles,
						@PathVariable("categoryMac") String categoryMac,
						HttpServletRequest request) {
		log.trace(newBoard.toString());

		return String.format("{\"updated\":\"%b\"}", svc.update(newBoard, mfiles, request));
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
		
//		검색옵션(글제목+내용 or 닉네임)에 따른 List 분류
		if (category.equals("contents")) {
			pageInfo = new PageInfo<>(svc.getListByKeyword(keyword, categoryMac));
		} else {
			pageInfo = new PageInfo<>(svc.getListByNickName(keyword, categoryMac));
		}

		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("page", page);

		return String.format("thymeleaf/mac/board/%s_board_list", categoryMac);
	}
	
//======================================== 댓글 ========================================
	@PostMapping("/comment")
	@ResponseBody
	public Map<String, Object> comment(Comment comment, HttpSession session) {
		log.trace(comment.toString());
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if((String)session.getAttribute("idMac") == null){ //세션을 가져옴
			map.put("msg", "로그인 후 사용 가능합니다.");
		} else {
			map.put("commented", svc.commentsave(comment));
		}
		
		return map;
	}
	
	
	@DeleteMapping("/comment/delete/{numMac}")
	@ResponseBody
	public Map<String, Object> comment_delte(@PathVariable int numMac) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("삭제할 댓글 No. : " + numMac);
		map.put("deleted", svc.commentdelete(numMac));
		return map;
	}
//======================================== 파일 ========================================
	
	@DeleteMapping("/file/delete/{numMac}")
	@ResponseBody
	public Map<String, Object> file_delte(@PathVariable("numMac") int file_Id, 
										  HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("삭제할 파일 No. : " + file_Id);
		map.put("filedeleted", svc.filedelete(file_Id));
		return map;
	}
	
	@GetMapping("/file/download/{filenum}")
	@ResponseBody
	public ResponseEntity<Resource> download(HttpServletRequest request,
											 @PathVariable(name="filenum", required = false) int fileNum) throws Exception {
		/**
		 * 파일명 디코딩 작업
		 */
		String fileName = svc.getFname(fileNum);
		String originFilename = URLDecoder.decode(fileName, "UTF-8");

		Resource resource = resourceLoader.getResource("WEB-INF/files/" + originFilename);
		String contextType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		return svc.download(contextType, fileNum, resource);
	}
}