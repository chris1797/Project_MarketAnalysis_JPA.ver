package com.mac.demo.serviceImpl;

import com.mac.demo.dto.CommentDTO;
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
    public CommentDTO getComment(Long board_num, String user_id, String nickname) {
        CommentDTO commentDTO = CommentDTO.builder()
                .user_id(user_id)
                .pcode(board_num)
                .nickname(nickname)
                .build();
        return commentDTO;
    }

    @Override
    public List<CommentDTO> getCommentList(Long board_num) {
        return commentRepository.findByPcode(board_num);
    }

    @Override
    public List<CommentDTO> getCommentListByComment(String comment) {
        return commentRepository.findByComment(comment);
    }

    @Override
    public List<CommentDTO> getCommentListByNickName(String nickName) {
        return commentRepository.findByNickname(nickName);
    }

    @Override
    public List<CommentDTO> getCommentList() {
        return commentRepository.findAll();
    }

    @Override
    public boolean commentSave(CommentDTO commentDTO) {
        CommentDTO _commentDTO = commentDTO.toEntity();
        try {
            commentRepository.save(_commentDTO);
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
