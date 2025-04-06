package com.example.PP_3_1_1_Spring_Boot_App.controller;

import com.example.PP_3_1_1_Spring_Boot_App.entity.User;
import com.example.PP_3_1_1_Spring_Boot_App.repository.RoleRepository;
import com.example.PP_3_1_1_Spring_Boot_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/add")
    public String showSaveUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "user-save-form";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user-save-form";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "user-edit-form";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user-edit-form";
        }

        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
