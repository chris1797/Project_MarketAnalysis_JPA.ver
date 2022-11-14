package com.mac.demo.serviceImpl;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.Attach;
import com.mac.demo.dto.Board;
import com.mac.demo.dto.Comment;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface BoardServiceImpl {

    List<Board> findByCategory(String category);

    PageInfo<Board> getPageInfo(String category);

    Long save(Board board, MultipartFile[] mfiles, String savePath);
    Boolean update(Board board, MultipartFile[] mfiles, String savePath);
    Board getDetail(int board_num, String category);
    boolean delete(int board_num);

    List<Board> getListByKeyword(String keyword, String categorymac);
    List<Board> getListByNickName(String nickname, String categorymac);

    List<Comment> getCommentList(int board_num);
    boolean commentsave(Comment comment);
    boolean commentdelete(int comment_num);

    List<Attach> getFileList(int pcode);
    String getFname(int num);
    boolean filedelete(int file_Id);
    List<Attach> getFileSet(Board board, MultipartFile[] mfiles, String savePath);
    ResponseEntity<Resource> download (String contentType, int FileNum, Resource resource) throws UnsupportedEncodingException;
}
