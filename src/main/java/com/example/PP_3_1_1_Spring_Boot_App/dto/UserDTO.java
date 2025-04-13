package com.example.PP_3_1_1_Spring_Boot_App.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Не может быть пустым")
    @Size(max = 20, message = "Долнжо быть меньше 20 символов")
    private String name;

    @Min(value = 0, message = "Возраст не может быть меньше 0")
    @Max(value = 100, message = "Возраст не может быть больше 100")
    private int age;

    @NotEmpty(message = "Email не может быть пустым")
    @Email(message = "Email не валидный")
    private String email;

    @NotEmpty(message = "Не может быть пустым")
    private String password;

    private Set<RoleDTO> roles = new HashSet<>();
}
