package com.mac.demo.serviceImpl;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.AttachDTO;
import com.mac.demo.dto.BoardDTO;
import com.mac.demo.repository.BoardRepository;
import com.mac.demo.service.AttachService;
import com.mac.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final AttachService attachSvc;
	ResourceLoader resourceLoader;


	public BoardDTO getBoard(String user_id, String nickname, String category) {

		BoardDTO boardDTO = BoardDTO.builder()
						   .user_id(user_id)
						   .nickname(nickname)
						   .category(category)
						   .build();
		return boardDTO;
	}

	public List<BoardDTO> findBoardByCategory(String categoryMac){
		return boardRepository.findByCategory(categoryMac);
	}

	/**
	 *	게시글 저장
	 */
	public Long save(BoardDTO boardDTO, MultipartFile[] mfiles, String savePath){
		BoardDTO _boardDTO = boardRepository.save(boardDTO);
		List<AttachDTO> attlist = getFileSet(_boardDTO, mfiles, savePath);
		if(attlist!=null) attachSvc.saveAll(attlist);
		
		return _boardDTO.getBoard_num();
	}
	
	/**
	 *	게시글 수정
	 */
	public Boolean update(BoardDTO boardDTO, MultipartFile[] mfiles, String savePath) {
		try {
			boardRepository.update(boardDTO.getTitle(), boardDTO.getContents(), boardDTO.getBoard_num());
			List<AttachDTO> attlist = getFileSet(boardDTO, mfiles, savePath);
			if(attlist!=null) attachSvc.saveAll(attlist);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 *	게시글 상세보기
	 */
	public BoardDTO getDetail(Long board_num, String category) {
		return boardRepository.findByNummacAndCategorymac(board_num, category);
	}

	/**
	 * 게시글 삭제
	 */
	public boolean delete(Long board_num) {
		return 0 > boardRepository.deleteByboard_num(board_num);
	}
	
	/**
	 *	글검색 - 제목&글내용
	 */
	public List<BoardDTO> getListByKeyword(String keyword, String categorymac) {
		return boardRepository.getListByKeyword(keyword, categorymac);
	}

	/**
	 *	글검색 - 닉네임
	 */
	public List<BoardDTO> getListByNickName(String nickname, String category) {
		return boardRepository.getListByNickname(nickname, category);
	}


//	------------------------File------------------------
	public List<AttachDTO> getFileList(Long pcode){
		return attachSvc.findAllByPcode(pcode);
	}

//	File Id로 파일이름 가져오기
	public String getFname(Long file_id) {
		String filename = attachSvc.findFilenameById(file_id);
		return filename;
	}
	
//	파일 지우기 Ajax
	public boolean filedelete(Long file_Id) {
		try {
			attachSvc.deleteById(file_Id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	파일 리스트 구성
	public List<AttachDTO> getFileSet(BoardDTO boardDTO, MultipartFile[] mfiles, String savePath) {
		String fname_changed = null;
		List<AttachDTO> attList = new ArrayList<>();
		
		try {
			for (int i = 0; i < mfiles.length; i++) {
				String[] token = mfiles[i].getOriginalFilename().split("\\.");
				fname_changed = token[0] + "_" + System.nanoTime() + "." + token[1];

				AttachDTO _att = AttachDTO.builder()
						.pcode(boardDTO.getBoard_num())
						.user_id(boardDTO.getUser_id())
						.filename(fname_changed)
						.filepath(savePath)
						.build();

				attList.add(_att);
				/**
				 * 메모리에 있는 파일을 저장경로에 옮김, 로컬 경로에 있는 파일만 선택 가능
				 * 추후 AWS S3로 전환
				 */
				mfiles[i].transferTo(new File(savePath + "/" + fname_changed));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return attList;
	}

//	파일 다운로드
	public ResponseEntity<Resource> download (String contentType, Resource resource) throws UnsupportedEncodingException {

	      if (contentType == null) contentType = "application/octet-stream";

	      ResponseEntity<Resource> file =  ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(resource.getFilename().getBytes("UTF-8"), "ISO-8859-1") + "\"")
	                  
	            // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	            // HttpHeaders.CONTENT_DISPOSITION는 http header를 조작하는 것, 화면에 띄우지 않고 첨부화면으로
	            // 넘어가게끔한다
	            // filename=\"" + resource.getFilename() + "\"" 는 http프로토콜의 문자열을 고대로 쓴 것
	            .body(resource);

	      return file;
	   }
	
	
	public PageInfo<BoardDTO> getPageInfo(String categoryMac) {
		PageInfo<BoardDTO> pageInfo = new PageInfo<>(findBoardByCategory(categoryMac));
		return pageInfo;
	}

	@Override
	public Page<BoardDTO> findByUser_id(Pageable pageable) {
		return null;
	}

}
