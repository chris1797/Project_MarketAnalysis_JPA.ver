package com.mac.demo.controller;

import com.mac.demo.dto.Comment;
import com.mac.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentSvc;

    @PostMapping("/comment")
    public Map<String, Object> comment(Comment comment, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        log.trace(comment.toString());

        if((String)session.getAttribute("idMac") == null){
            map.put("msg", "로그인 후 사용 가능합니다.");
        } else {
            Comment _comment = comment.toEntity();
            map.put("commented", commentSvc.commentSave(_comment));
        }
        return map;
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comment/{numMac}")
    public Map<String, Object> comment_delte(@PathVariable Long comment_num) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("삭제할 댓글 No. : " + comment_num);
        map.put("deleted", commentSvc.commentDelete(comment_num));
        return map;
    }
}
