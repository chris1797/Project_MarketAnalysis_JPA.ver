package com.mac.demo.serviceImpl;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.Board;
import org.springframework.web.multipart.MultipartFile;

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

}
