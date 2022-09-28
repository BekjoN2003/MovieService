package com.example.test.dto.movie;

import com.example.test.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MovieDto {
    private Integer id;
    private String name;
    private String description;
    private UserDto user;

}
