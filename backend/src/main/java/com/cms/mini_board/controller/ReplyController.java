package com.cms.mini_board.controller;

import com.cms.mini_board.dto.ReplyDTO;
import com.cms.mini_board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Long> registerReply(@RequestBody ReplyDTO replyDTO) {
        log.info("replyDTO={}", replyDTO);
        Long replyId = replyService.registerReply(replyDTO);
        return new ResponseEntity<>(replyId, HttpStatus.OK);
    }

    @GetMapping("/{postId}/replies/{replyId}")
    public ResponseEntity<Long> getReply(@PathVariable String postId, @PathVariable String replyId) {
        //Authentication :
        return new ResponseEntity<>(1L, HttpStatus.OK);
    }

    @PutMapping("/{postId}/replies/{replyId}")
    public ResponseEntity<String> modifyReply(@RequestBody ReplyDTO replyDTO) {
        log.info("replyDTO={}", replyDTO);
        //Authentication : UserID를 알아야 reply를 modify할 수 있지 않을까... 아닌가? Post 번호만 알아도 되나?
        replyService.modifyReply(replyDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/replies/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable String postId, @PathVariable String replyId) {
        log.info("replyId={}", replyId);
        replyService.deleteReply(Long.valueOf(replyId));
        //Authentication :
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
