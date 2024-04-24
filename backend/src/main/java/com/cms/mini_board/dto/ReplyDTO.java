package com.cms.mini_board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReplyDTO {
    private Long replyId;
    private String replyAuthor;
    private String replyText;
    private Long memberId;
    private Long postId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime replyUpdatedAt;
}
