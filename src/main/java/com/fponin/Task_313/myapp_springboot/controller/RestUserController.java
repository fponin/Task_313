package com.fponin.Task_313.myapp_springboot.controller;

import com.fponin.Task_313.myapp_springboot.model.User;
import com.fponin.Task_313.myapp_springboot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestUserController {
    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/info")
    public User printUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

       return userService.getUserByName(auth.getName());
    }
}