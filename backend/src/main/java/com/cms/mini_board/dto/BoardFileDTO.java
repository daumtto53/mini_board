package com.cms.mini_board.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class BoardFileDTO {
    private String fullPath;
    private String originalFileName;
    private String saveName;
    private String formattedCreatedDate;
    private byte[] byteData;
}
