package com.mac.demo.admin;

import com.mac.demo.dto.Attach;
import com.mac.demo.dto.Board;
import com.mac.demo.dto.Comment;
import com.mac.demo.model.User;
import com.mac.demo.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;

	@Autowired
	ResourceLoader resourceLoader;
	


    //모든 유저
	public List<User> findAllUser() {
		return dao.findAllUser();	
	}
	
	//모든 자유게시물
	public List<Board> findAllFreeBord() {
		return dao.findAllFreeBoard();
	}

	//모든 광고게시물
	public List<Board>findAllAdsBoard() {
		return dao.findAllAdsBoard();
	}

	//자유게시물 삭제
	public boolean boardDeleted(int numMac) {
		return dao.boardDeleted(numMac);
	}

	
	//계정 삭제
	public boolean userDeleted(int numMac) {
		return dao.userDeleted(numMac);
	}

	//공지사항 저장
	public int save(Board board) {
		return dao.saveNotice(board);
	}
	
	public boolean attachinsert(List<Attach> attList) {
		System.out.println("BoardService : " + attList.get(0).getFilenamemac());
		int res = dao.insertNoticeMultiAttach(attList);
		System.out.println(res + "개 업로드성공");

		return res==attList.size();
	}


	//공지사항 리스트
	public List<Board> findAllNoticeBoard() {
		return dao.findAllNoticeBoard();
	}

	//공지사항 삭제
	public boolean noticeBordDeleted(int numMac) {
		return dao.noticeBoardDeleted(numMac);
	}

	//모든 댓글
	public List<Comment> findAllCommentBoard() {
		return dao.findAllCommentBoard();
	}

	//댓글 삭제
	public boolean commentBordDeleted(int numMac) {
		return dao.commentBoardDeleted(numMac);
	}
	

	//자유게시물 검색
	public List<Board> getFreeListByKeyword(String titleMac){
		return dao.getfreeListByKeyword(titleMac);
	}
	public List<Board> getFreeListByNickName(String nickNameMac) {
		return dao.getfreeListByNickName(nickNameMac);
	}

	//광고게시물 검색
	public List<Board> getAdsListByKeyword(String keyword) {

		return dao.getAdsListByKeyword(keyword);
	}
	public List<Board> getAdsListByNickName(String keyword) {
		
		return dao.getAdsListByNickName(keyword);
	}
	
	//공지사항 검색
	public List<Board> getNoticeListByKeyword(String keyword) {
		
		return dao.getNoticeListByKeyword(keyword);
	}


	//댓글 검색
	public List<Comment> getCommentListByKeyword(String keyword) {
		
		return dao.getCommentListByKeyword(keyword);
	}
	public List<Comment> getCommentListByNickName(String keyword) {
		
		return dao.getCommentListByNickName(keyword);
	}

	//계정 검색
	public List<User> getUserListByKeyword(String keyword) {
		return dao.getUserListByKeyword(keyword);
	}
	
	
	//메인서비스의 공지사항 서비스
	
	public Board getNoticeDetail(int num) {
		return dao.getNoticeDetail(num);
	}
	
	public List<Attach> getNoticeFileList(int pcodeMac) {
		return dao.getNoticeFileList(pcodeMac);
	}

	public ResponseEntity<Resource> noticeDownload(HttpServletRequest request, int fileNum) throws Exception {
		String filename = dao.getNoticeFname(fileNum);
		String originFilename = URLDecoder.decode(filename, "UTF-8");
		Resource resource = resourceLoader.getResource("WEB-INF/files/" + originFilename);
		System.out.println("파일명:" + resource.getFilename());
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//			System.out.println(contentType); // return : image/jpeg
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		ResponseEntity<Resource> file =  ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				// HttpHeaders.CONTENT_DISPOSITION는 http header를 조작하는 것, 화면에 띄우지 않고 첨부화면으로
				// 넘어가게끔한다
				// filename=\"" + resource.getFilename() + "\"" 는 http프로토콜의 문자열을 고대로 쓴 것
				.body(resource);

		return file;
	}


	
	

}