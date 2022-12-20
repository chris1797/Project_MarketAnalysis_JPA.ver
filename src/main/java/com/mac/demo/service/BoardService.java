package com.mac.demo.service;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.Board;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardService {

    List<Board> findBoardByCategory(String category);

    PageInfo<Board> getPageInfo(String category);

    Page<Board> findByUser_id(Pageable pageable);


    Long save(Board board, MultipartFile[] mfiles, String savePath);
    Boolean update(Board board, MultipartFile[] mfiles, String savePath);
    Board getDetail(Long board_num, String category);
    boolean delete(Long board_num);

    List<Board> getListByKeyword(String keyword, String categorymac);
    List<Board> getListByNickName(String nickname, String categorymac);

}
