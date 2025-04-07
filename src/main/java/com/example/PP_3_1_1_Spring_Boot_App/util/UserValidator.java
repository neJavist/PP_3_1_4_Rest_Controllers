package com.example.PP_3_1_1_Spring_Boot_App.util;

import com.example.PP_3_1_1_Spring_Boot_App.entity.User;
import com.example.PP_3_1_1_Spring_Boot_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        userService.findUserByName(user.getName()).ifPresent(existing -> {
            if (!existing.getId().equals(user.getId())) {
                errors.rejectValue("name", "", "Username is already exist");
            }
        });

        userService.findUserByEmail(user.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(user.getId())) {
                errors.rejectValue("email", "", "Email is already exist");
            }
        });
    }
}
