package com.example.test.dto.movie;

import com.example.test.entity.Movie;
import com.example.test.dto.filterDto.FilterDto;
import com.example.test.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovieFilterDto extends FilterDto {
    @NotEmpty
    private String name;
    private String description;
    @NotEmpty
    private UserDto user;
    private Movie creatorId;
    private LocalDateTime minCreatedDate;
    private LocalDateTime maxCreatedDate;
}
