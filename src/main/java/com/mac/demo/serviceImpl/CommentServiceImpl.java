package com.mac.demo.serviceImpl;

import com.mac.demo.dto.Comment;
import com.mac.demo.repository.CommentRepository;
import com.mac.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment getComment(Long board_num, String user_id, String nickname) {
        Comment comment = Comment.builder()
                .user_id(user_id)
                .pcode(board_num)
                .nickname(nickname)
                .build();
        return comment;
    }

    @Override
    public List<Comment> getCommentList(Long board_num) {
        return commentRepository.findByPcode(board_num);
    }

    @Override
    public List<Comment> getCommentListByComment(String comment) {
        return commentRepository.findByComment(comment);
    }

    @Override
    public List<Comment> getCommentListByNickName(String nickName) {
        return commentRepository.findByNickname(nickName);
    }

    @Override
    public List<Comment> getCommentList() {
        return commentRepository.findAll();
    }

    @Override
    public boolean commentSave(Comment comment) {
        Comment _comment = comment.toEntity();
        try {
            commentRepository.save(_comment);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean commentDelete(Long comment_num) {
        return 0 < commentRepository.deleteByComment_num(comment_num);
    }
}
