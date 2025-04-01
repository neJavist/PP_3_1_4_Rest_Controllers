package com.example.PP_3_1_1_Spring_Boot_App.repository;

import com.example.PP_3_1_1_Spring_Boot_App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void deleteUserById(Long id);
}
