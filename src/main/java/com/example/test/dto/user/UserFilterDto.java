package com.example.test.dto.user;

import com.example.test.dto.filterDto.FilterDto;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
public class UserFilterDto extends FilterDto {
    private String name;
    private String email;
    private Integer minAge;
    private Integer maxAge;
    private LocalDateTime minCreatedDate;
    private LocalDateTime maxCreatedDate;
}
