package com.example.test.dto.auth;

import com.example.test.dto.movie.MovieDto;
import com.example.test.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDto {
    private UserDto userDto;
    private MovieDto movieDto;


}
