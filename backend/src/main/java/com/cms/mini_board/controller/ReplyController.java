package com.cms.mini_board.controller;

import com.cms.mini_board.dto.ReplyDTO;
import com.cms.mini_board.entity.Reply;
import com.cms.mini_board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{postId}/replies")
    public ResponseEntity<List<ReplyDTO>> getReplyList(@PathVariable String postId){
        //TODO
        List<ReplyDTO> replyList = replyService.getReplyList(Long.valueOf(postId));
        return new ResponseEntity<>(replyList, HttpStatus.OK);
    }

    @PostMapping("/{postId}/replies")
    public ResponseEntity<Long> registerReply(@PathVariable String postId) {
        //TODO
        return new ResponseEntity<>(1L, HttpStatus.OK);
    }

    @GetMapping("/{postId}/replies/{replyId}")
    public ResponseEntity<Long> getReply(@PathVariable String postId, @PathVariable String replyId) {
        //Authentication :
        return new ResponseEntity<>(1L, HttpStatus.OK);
    }

    @PutMapping("/{postId}/replies/{replyId}")
    public ResponseEntity<String> modifyReply(@PathVariable String postId, @PathVariable String replyId) {
        //Authentication : UserID를 알아야 reply를 modify할 수 있지 않을까... 아닌가? Post 번호만 알아도 되나?

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/replies/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable String deleteId, @PathVariable String replyId) {
        //Authentication :
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
