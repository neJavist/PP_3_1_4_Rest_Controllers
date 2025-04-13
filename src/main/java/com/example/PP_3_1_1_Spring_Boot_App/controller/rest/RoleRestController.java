package com.example.PP_3_1_1_Spring_Boot_App.controller.rest;

import com.example.PP_3_1_1_Spring_Boot_App.dto.RoleDTO;
import com.example.PP_3_1_1_Spring_Boot_App.mapper.ObjectMapper;
import com.example.PP_3_1_1_Spring_Boot_App.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {
    private final RoleService roleService;
    private final ObjectMapper objectMapper;

    @Autowired
    public RoleRestController(RoleService roleService, ObjectMapper objectMapper) {
        this.roleService = roleService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.findAll().stream()
                .map(objectMapper::toRoleDTO)
                .collect(Collectors.toList());
    }
}
