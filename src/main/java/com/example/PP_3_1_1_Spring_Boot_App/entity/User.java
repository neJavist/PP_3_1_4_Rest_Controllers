package com.example.PP_3_1_1_Spring_Boot_App.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name can not be empty")
    @Size(max = 20, message = "Must be less than 20 characters")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    @Max(value = 100, message = "Age should be less than 100")
    private int age;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Email not valid")
    private String email;
}
