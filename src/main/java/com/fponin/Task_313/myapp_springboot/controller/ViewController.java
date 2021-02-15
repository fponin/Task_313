package com.fponin.Task_313.myapp_springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping(value = "login")
    public String login() {

        return "login";
    }

    @GetMapping(value = "/users")
    public String users() {

        return "users";
    }

}