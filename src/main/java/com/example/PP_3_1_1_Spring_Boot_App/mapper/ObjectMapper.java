package com.example.PP_3_1_1_Spring_Boot_App.mapper;

import com.example.PP_3_1_1_Spring_Boot_App.dto.RoleDTO;
import com.example.PP_3_1_1_Spring_Boot_App.dto.UserDTO;
import com.example.PP_3_1_1_Spring_Boot_App.entity.Role;
import com.example.PP_3_1_1_Spring_Boot_App.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjectMapper {
    public User toUser(UserDTO userDTO);
    public UserDTO toUserDTO(User user);

    public Role toRole(RoleDTO roleDTO);
    public RoleDTO toRoleDTO(Role role);
}
