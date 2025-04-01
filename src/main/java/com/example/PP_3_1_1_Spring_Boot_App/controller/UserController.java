package com.example.PP_3_1_1_Spring_Boot_App.controller;


import com.example.PP_3_1_1_Spring_Boot_App.entity.User;
import com.example.PP_3_1_1_Spring_Boot_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user";
    }

    @GetMapping("/add")
    public String showSaveUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-save-form";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user-save-form";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user-edit-form";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user-edit-form";
        }

        userService.updateUser(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
