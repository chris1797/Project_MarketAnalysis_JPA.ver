package com.mac.demo.service;

import com.mac.demo.dto.Comment;

import java.util.List;

public interface CommentService {

    Comment getComment(Long board_num, String user_id, String nickname);
    boolean commentSave(Comment comment);
    boolean commentDelete(Long comment_num);
    List<Comment> getCommentList();
    List<Comment> getCommentList(Long board_num);
    List<Comment> getCommentListByComment(String keyword);
    List<Comment> getCommentListByNickName(String Nickname);

}
