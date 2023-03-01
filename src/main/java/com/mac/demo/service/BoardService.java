package com.mac.demo.service;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardService {

    List<BoardDTO> findBoardByCategory(String category);

    PageInfo<BoardDTO> getPageInfo(String category);

    Page<BoardDTO> findByUser_id(Pageable pageable);


    Long save(BoardDTO boardDTO, MultipartFile[] mfiles, String savePath);
    Boolean update(BoardDTO boardDTO, MultipartFile[] mfiles, String savePath);
    BoardDTO getDetail(Long board_num, String category);
    boolean delete(Long board_num);

    List<BoardDTO> getListByKeyword(String keyword, String categorymac);
    List<BoardDTO> getListByNickName(String nickname, String categorymac);

}
