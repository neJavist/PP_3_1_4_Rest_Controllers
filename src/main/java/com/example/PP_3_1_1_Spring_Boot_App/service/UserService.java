package com.example.PP_3_1_1_Spring_Boot_App.service;


import com.example.PP_3_1_1_Spring_Boot_App.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUserById(Long id);

    User getUserById(Long id);

    void updateUser(User user);

    Optional<User> findUserByName(String name);

    Optional<User> findUserByEmail(String email);
}
