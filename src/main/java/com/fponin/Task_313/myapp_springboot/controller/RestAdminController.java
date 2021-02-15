package com.fponin.Task_313.myapp_springboot.controller;


import com.fponin.Task_313.myapp_springboot.model.Role;
import com.fponin.Task_313.myapp_springboot.model.User;
import com.fponin.Task_313.myapp_springboot.service.RoleService;
import com.fponin.Task_313.myapp_springboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


@RestController
@RequestMapping(value = "/admin/")
public class RestAdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    // List all @User
    @GetMapping(value = "users")
    public List<User> printUsers() {
        List<User> usersList = userService.getAllUsers();
        return usersList;
    }

    // Delete @User
    @DeleteMapping( value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUsers(@PathVariable("id") int id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update @User
    @PutMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUsers(@RequestBody @Validated User user) {
        try{
            userService.updateUser(user);
            return new ResponseEntity<> (user, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
    }

    // Save @User
    @PostMapping(value = "addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        try{
            userService.saveUser(user);
            return new ResponseEntity<> (user, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("id") int id) {
        User user = userService.findUser(id);
        return user;
    }

    @GetMapping(value = "roles")
    public List<Role> getRoles() {
        List<Role> roles = roleService.getAllRole();
        return roles;
    }

    private Set<Role> addRole(String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String s : role) {
            roleSet.add(roleService.getRoleByName(s));
        }
        return roleSet;
    }
}