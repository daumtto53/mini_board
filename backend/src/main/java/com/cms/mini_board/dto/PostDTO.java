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
    private Long id;
    private String title;
    private String author;
    private LocalDateTime regDate;
    private Long views;

}
