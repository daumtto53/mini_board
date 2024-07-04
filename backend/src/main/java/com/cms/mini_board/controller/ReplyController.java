package com.cms.mini_board.controller;

import com.cms.mini_board.dto.ReplyDTO;
import com.cms.mini_board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('USER')")
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

    @PreAuthorize("@replyServiceImpl.isAuthor(#replyId, principal.username)")
    @PutMapping("/{postId}/replies/{replyId}")
    public ResponseEntity<String> modifyReply(@RequestBody ReplyDTO replyDTO) {
        log.info("replyDTO={}", replyDTO);
        replyService.modifyReply(replyDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/replies/{replyId}")
    @PreAuthorize("@replyServiceImpl.isAuthor(#replyId, principal.username)")
    public ResponseEntity<String> deleteReply(@PathVariable String postId, @PathVariable String replyId) {
        log.info("replyId={}", replyId);
        replyService.deleteReply(Long.valueOf(replyId));
        //Authentication :
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
