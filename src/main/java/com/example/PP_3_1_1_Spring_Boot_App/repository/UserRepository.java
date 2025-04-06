package com.example.PP_3_1_1_Spring_Boot_App.repository;

import com.example.PP_3_1_1_Spring_Boot_App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteUserById(Long id);

    @Query(value = "SELECT u FROM User u JOIN FETCH u.roles r WHERE u.name = :name")
    Optional<User> findUserByName(String name);
}
