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
import com.mac.demo.repository.BoardRepository;
import com.mac.demo.repository.CommentRepository;
import com.mac.demo.repository.UserRepositroy;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BoardService {

	private BoardMapper boardDao;
	private AttachMapper attachDao;
	
	private final UserRepositroy userRepository;
	private final BoardRepository boardRepository;
	private final CommentRepository cmtRepository;
	
	ResourceLoader resourceLoader;
	
	/*
	생성자 주입, @RequiredArgsConstructor로 따로 생성자 안만들어도 됨
	public BoardService(BoardRepository boardRepository, 
						UserRepositroy userRepository,
						CommentRepository cmtRepository) {
		this.boardRepository = boardRepository;
		this.userRepository = userRepository;
		this.cmtRepository = cmtRepository;
	}
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
	public boolean save(Board board){
		return boardRepository.save(board) != null;
	}
	
//	------------------ Board Detail -------------------    
	public Board getDetail(int nummac, String categorymac) {
		return boardRepository.findByNummacAndCategorymac(nummac, categorymac);
	}
	
//	------------------ DELETE -------------------    
	public boolean delete(int nummac) {
		return 0 > boardRepository.deleteByNummac(nummac);
	}
	
//	------------------UPDATE-------------------
	public boolean update(Board board) {
		return 0 < boardRepository.update(board.getTitlemac(), board.getContentsmac(), board.getNummac());
	}
		
	
//  -----------------COMMENT-----------------
	
	public List<Comment> getCommentList(int boardnum) { // pcode
		return cmtRepository.findByPcodemac(boardnum);		
	}
	
	public boolean commentsave(Comment comment) {
		return 0 < boardDao.commentsave(comment);	
	}
	
	public boolean commentdelete(int numMac) {
		return 0 < boardDao.commentdelete(numMac);
	}
	
//	-----------------------SEARCH-----------------------	
	public List<Board> getFreeListByKeyword(String titleMac){
		return boardDao.getFreeListByKeyword(titleMac);
	}

	public List<Board> getFreeListByNickName(String nickNameMac) {
		return boardDao.getFreeListByNickName(nickNameMac);
	}
	
	public List<Board> getAdsListByKeyword(String titleMac){
		return boardDao.getAdsListByKeyword(titleMac);
	}

	public List<Board> getAdsListByNickName(String nickNameMac) {
		return boardDao.getAdsListByNickName(nickNameMac);
	}
	
	public List<Board> getNoticeListByKeyword(String titleMac) {
		
		return boardDao.getNoticeListByKeyword(titleMac);
	}

	public List<Board> getNoticeListByNickName(String nickNameMac) {
		return boardDao.getNoticeListByNickName(nickNameMac);
	}
	
//	------------------------File------------------------
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
	
	public List<Attach> getUpdateFileSet(Board board, MultipartFile[] mfiles, HttpServletRequest request) {
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
	
	public boolean fileinsert(Board board, MultipartFile[] mfiles, HttpServletRequest request) {
		int res = attachDao.insertMultiAttach(getFileSet(board, mfiles, request));
		System.out.println(res + "개 파일 업로드성공");

		return res==getFileSet(board, mfiles, request).size();
	}
	
	
	public boolean fileupdate(Board board, MultipartFile[] mfiles, HttpServletRequest request) {
		int res = attachDao.updateMultiAttach(getUpdateFileSet(board, mfiles, request));
		System.out.println(res + "개 파일 업로드성공(update)");

		return res==getFileSet(board, mfiles, request).size();
	}
	
	
	public List<Attach> getFileList(int pcodeMac){
		return attachDao.getFileList(pcodeMac);
	}


	public String getFname(int num) {
		String fname = attachDao.getFname(num);
		return fname;
	}


	public boolean filedelete(int num) {
		int removed = attachDao.filedelete(num);
		return removed > 0;
	}

	
	public boolean insertMultiAttach(Attach vo) {
		long pcodeMac = vo.getNummac();  // 자동 증가된 업로드 번호를 받음
		
		List<Attach> attList = vo.getAttListmac();

		int totalSuccess = 0;
		for(int i=0;i<attList.size();i++)
		{
			Map<String,Object> fmap = new HashMap<>();
			fmap.put("pcodeMac", Long.valueOf(pcodeMac));
			fmap.put("fileNameMac", attList.get(i).getFilenamemac());
			fmap.put("filepathMac", vo.getFilepathmac());
//			totalSuccess += attchDao.insertAttach(fmap);   // 첨부파일 정보 저장
		}
		return totalSuccess==attList.size();
	}
	
	public ResponseEntity<Resource> download (HttpServletRequest request, int FileNum) throws Exception{
	      String filename = getFname(FileNum);
	      String originFilename = URLDecoder.decode(filename, "UTF-8");
	      Resource resource = resourceLoader.getResource("WEB-INF/files/" + originFilename);
	      System.out.println("파일명:" + resource.getFilename());
	      String contentType = null;
	      try {
	         contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//	         System.out.println(contentType); // return : image/jpeg
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
