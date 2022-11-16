package com.mac.demo.serviceImpl;

import com.mac.demo.dto.Comment;

import java.util.List;

public interface CommentServiceImpl {

    List<Comment> getCommentList(Long board_num);
    boolean commentsave(Comment comment);
    boolean commentdelete(Long comment_num);

}
