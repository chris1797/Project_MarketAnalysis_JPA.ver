package com.mac.demo.service;

import com.mac.demo.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO getComment(Long board_num, String user_id, String nickname);
    boolean commentSave(CommentDTO commentDTO);
    boolean commentDelete(Long comment_num);
    List<CommentDTO> getCommentList();
    List<CommentDTO> getCommentList(Long board_num);
    List<CommentDTO> getCommentListByComment(String keyword);
    List<CommentDTO> getCommentListByNickName(String Nickname);

}
