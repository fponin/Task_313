package com.fponin.Task_313.myapp_springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @GetMapping(value = "/users")
    public String users() {

        return "users";
    }
    @RequestMapping(value = "/user_info")
    public String info() {

        return "user_info";
    }

}