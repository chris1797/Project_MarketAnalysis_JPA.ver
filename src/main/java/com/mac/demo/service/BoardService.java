package com.mac.demo.service;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.mac.demo.mappers.AttachMapper;
import com.mac.demo.mappers.BoardMapper;
import com.mac.demo.model.Attach;
import com.mac.demo.model.Board;
import com.mac.demo.model.Comment;
import com.mac.demo.model.User;
import com.mac.demo.repository.AttachRepository;
import com.mac.demo.repository.BoardRepository;
import com.mac.demo.repository.CommentRepository;
import com.mac.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BoardService {

	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;
	private final AttachRepository attachRepository;
	
	ResourceLoader resourceLoader;
	
/*
 * 
 * 생성자 주입, @RequiredArgsConstructor로 따로 생성자 안만들어도 됨
 *
 * public BoardService(BoardRepository boardRepository, 
 *						UserRepositroy userRepository,
 *						CommentRepository cmtRepository) {
 *		this.boardRepository = boardRepository;
 *		this.userRepository = userRepository;
 *		this.cmtRepository = cmtRepository;
 * }
 * 
*/
	
//	------------------List-------------------
	public List<Board> findByCategorymac(String categoryMac){
		return boardRepository.findByCategorymac(categoryMac);
	}
	

//	------------------id로 유저정보 가져오기-------------------    
	public User getOne(String idmac) {
		return userRepository.findByIdmac(idmac);
	}
	
//	------------------ SAVE -------------------    
	public Long save(Board board, MultipartFile[] mfiles, HttpServletRequest request){
		Board _board = boardRepository.save(board);
		List<Attach> attlist = getFileSet(_board, mfiles, request);
		if(attlist!=null) attachRepository.saveAll(attlist);
		
		return _board.getNummac();
	}
	
//	------------------UPDATE-------------------
	public Boolean update(Board board, MultipartFile[] mfiles, HttpServletRequest request) {
		try {
			boardRepository.update(board.getTitlemac(), board.getContentsmac(), board.getNummac());
			List<Attach> attlist = getFileSet(board, mfiles, request);
			if(attlist!=null) attachRepository.saveAll(attlist);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	------------------ Board Detail -------------------    
	public Board getDetail(int nummac, String categorymac) {
		return boardRepository.findByNummacAndCategorymac(nummac, categorymac);
	}
	
//	------------------ DELETE -------------------    
	public boolean delete(int nummac) {
		return 0 > boardRepository.deleteByNummac(nummac);
	}
	
		
//	-----------------------SEARCH-----------------------	
	public List<Board> getListByKeyword(String keyword, String categorymac){
		return boardRepository.getListByKeyword(keyword, categorymac);
	}

	public List<Board> getListByNickName(String nickname, String categorymac) {
		return boardRepository.getListByNickname(nickname, categorymac);
	}

//	----------------------- Comment -----------------------
	public List<Comment> getCommentList(int boardnum) { // pcode
		return commentRepository.findByPcodemac(boardnum);		
	}
	
	public boolean commentsave(Comment comment) {
		return commentRepository.save(comment) != null;	
	}
	
	public boolean commentdelete(int numMac) {
		return 0 < commentRepository.deleteByNummac(numMac);
	}
	
//	------------------------File------------------------
	public List<Attach> getFileList(int pcodeMac){
		return attachRepository.findAllByPcodemac(pcodeMac);
	}

//	File Id로 파일이름 가져오기
	public String getFname(int num) {
		Attach attach = attachRepository.findById(num).get();
		String filename = attach.getFilenamemac();
		return filename;
	}
	
//	파일 지우기 Ajax
	public boolean filedelete(int file_Id) {
		try {
			attachRepository.deleteById(file_Id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	파일 리스트 구성
	public List<Attach> getFileSet(Board board, MultipartFile[] mfiles, HttpServletRequest request) {
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
					_att.setPcodemac(board.getNummac());
					_att.setIdmac(board.getIdmac());
					_att.setNicknamemac(getOne(board.getIdmac()).getNicknamemac());
					_att.setFilenamemac(fname_changed);
					_att.setFilepathmac(savePath);
				
				attList.add(_att);

//				메모리에 있는 파일을 저장경로에 옮기는 method, local 디렉토리에 있는 그 파일만 셀렉가능
				mfiles[i].transferTo(
						new File(savePath + "/" + fname_changed));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return attList;
	}
	
//	파일 다운로드
	public ResponseEntity<Resource> download (HttpServletRequest request, int FileNum) throws Exception{
	      String filename = getFname(FileNum);
	      String originFilename = URLDecoder.decode(filename, "UTF-8");
	      Resource resource = resourceLoader.getResource("WEB-INF/files/" + originFilename);
	      System.out.println("파일명:" + resource.getFilename());
	      String contentType = null;
	      try {
	         contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	      } catch (IOException e) {
	         e.printStackTrace();
	      }

	      if (contentType == null) {
	         contentType = "application/octet-stream";
	      }
	      
	      ResponseEntity<Resource> file =  ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(resource.getFilename().getBytes("UTF-8"), "ISO-8859-1") + "\"")
	                  
	            // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	            // HttpHeaders.CONTENT_DISPOSITION는 http header를 조작하는 것, 화면에 띄우지 않고 첨부화면으로
	            // 넘어가게끔한다ㄴ
	            // filename=\"" + resource.getFilename() + "\"" 는 http프로토콜의 문자열을 고대로 쓴 것
	            .body(resource);
	      
	      return file;
	   }
	
	
//	------------------------PAGE------------------------
	public PageInfo<Board> getPageInfo(String categoryMac) {
		PageInfo<Board> pageInfo = new PageInfo<>(findByCategorymac(categoryMac));;
		return pageInfo;
	}
	
}
