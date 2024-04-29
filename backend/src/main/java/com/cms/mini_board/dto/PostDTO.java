package com.cms.mini_board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class PostDTO {
    private Long postId;
    private String title;
    private Long memberId;
    private String author;
    private String content;
    private LocalDateTime regDate;
    private Long views;

}
