package com.example.PP_3_1_1_Spring_Boot_App.controller;

import com.example.PP_3_1_1_Spring_Boot_App.entity.User;
import com.example.PP_3_1_1_Spring_Boot_App.service.RoleService;
import com.example.PP_3_1_1_Spring_Boot_App.service.UserService;
import com.example.PP_3_1_1_Spring_Boot_App.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("admin", userService.findUserByName(principal.getName()).orElseThrow());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin";
    }

    @GetMapping("/add")
    public String addForm(Model model, Principal principal) {
        model.addAttribute("admin", userService.findUserByName(principal.getName()).get());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin-save-form";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute @Validated User user,
                           BindingResult bindingResult,
                           Model model,
                           Principal principal) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            model.addAttribute("admin",
                    userService.findUserByName(principal.getName()).orElseThrow());
            return "admin-save-form";
        }

        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "admin";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user) {

        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
