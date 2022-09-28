package com.example.test.dto.filterDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class FilterDto {
    private Integer size;
    private Integer page;
    private String sortBy;
    private Sort.Direction direction;
}
