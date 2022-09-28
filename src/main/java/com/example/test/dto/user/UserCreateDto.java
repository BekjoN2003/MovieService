package com.example.test.dto.user;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserCreateDto {
    @NotBlank(message = ("name cannot empty or null"))
    private String name;
    @Email
    @NotBlank(message = ("email cannot empty or null"))
    private String email;
    @NotNull(message = ("age cannot null"))
    private Integer age;
}
