package com.cms.mini_board.dto;

import com.cms.mini_board.entity.constants.SearchOption;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SearchCondition {
    private SearchOption searchOption;
    private String searchQuery;
}
