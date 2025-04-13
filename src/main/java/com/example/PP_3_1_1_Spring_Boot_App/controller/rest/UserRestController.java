package com.example.PP_3_1_1_Spring_Boot_App.controller.rest;

import com.example.PP_3_1_1_Spring_Boot_App.dto.UserDTO;
import com.example.PP_3_1_1_Spring_Boot_App.entity.User;
import com.example.PP_3_1_1_Spring_Boot_App.mapper.ObjectMapper;
import com.example.PP_3_1_1_Spring_Boot_App.service.RoleService;
import com.example.PP_3_1_1_Spring_Boot_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(objectMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/current-user")
    public UserDTO getCurrentUser(Principal principal) {
        return objectMapper.toUserDTO(userService.findUserByName(principal.getName()).get());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return objectMapper.toUserDTO(userService.getUserById(id));
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return HttpStatus.OK;
    }

    @PostMapping("/add")
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        User user = objectMapper.toUser(userDTO);
        user.setRoles(userDTO.getRoles().stream()
                .map(s -> roleService.findByName(s.getName()))
                .collect(Collectors.toSet()));

        userService.saveUser(user);
        return userDTO;
    }

    @PutMapping("/edit/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User newUser = userService.getUserById(id);
        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setAge(userDTO.getAge());
        newUser.setPassword(userDTO.getPassword());
        newUser.setRoles(
                userDTO.getRoles().stream()
                        .map(s -> roleService.findByName(s.getName()))
                        .collect(Collectors.toSet())
        );
        userService.saveUser(newUser);

        return userDTO;
    }
}
