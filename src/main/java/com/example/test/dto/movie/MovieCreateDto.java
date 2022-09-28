package com.example.test.dto.movie;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MovieCreateDto {
    @NotBlank
    private String name;
    @NotEmpty
    private String description;

    private Integer creatorId;
}
