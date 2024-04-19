package com.cms.mini_board.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardPageDTO {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime updatedAt;
    private Long views;
}
