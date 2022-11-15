package com.mac.demo.serviceImpl;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.Attach;
import com.mac.demo.dto.Board;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface BoardServiceImpl {

    List<Board> findBoardByCategory(String category);

    PageInfo<Board> getPageInfo(String category);

    Long save(Board board, MultipartFile[] mfiles, String savePath);
    Boolean update(Board board, MultipartFile[] mfiles, String savePath);
    Board getDetail(Long board_num, String category);
    boolean delete(Long board_num);

    List<Board> getListByKeyword(String keyword, String categorymac);
    List<Board> getListByNickName(String nickname, String categorymac);

    List<Attach> getFileList(Long pcode);
    String getFname(Long file_Id);
    boolean filedelete(Long file_Id);
    List<Attach> getFileSet(Board board, MultipartFile[] mfiles, String savePath);
    ResponseEntity<Resource> download (String contentType, Long file_Id, Resource resource) throws UnsupportedEncodingException;
}
