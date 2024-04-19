package com.cms.mini_board.dto.PageDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class PageResultDTO <DTO, EN> {
    private List<DTO> dtoList;

    public PageResultDTO(Page<EN> en, Function<EN, DTO> fn) {
        dtoList = en.stream().map(fn).collect(Collectors.toList());
    }
}
