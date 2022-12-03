package com.mac.demo.service;

import com.mac.demo.dto.Comment;


public interface CommentService {

    Comment getComment(Long board_num, String user_id, String nickname);
    boolean commentSave(Comment comment);
    boolean commentDelete(Long comment_num);

}
