package com.cms.mini_board.dto.PageDTO;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@ToString
@Getter
@AllArgsConstructor
public class PageRequestDTO {
    private int offset;
    private int size;

    public PageRequestDTO () {
        this.offset = 1;
        this.size = 10;
    }

    public Pageable getPageRequest(Sort sort) {
        return PageRequest.of(offset-1, size, sort);
    }

}
