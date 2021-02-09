package com.fponin.Task_312.myapp_springboot.controller;


import com.fponin.Task_312.myapp_springboot.model.Role;
import com.fponin.Task_312.myapp_springboot.model.User;
import com.fponin.Task_312.myapp_springboot.service.RoleService;
import com.fponin.Task_312.myapp_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    //Список всех @User
    @GetMapping()
    public String printUsers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        List<User> usersList = userService.getAllUsers();

        model.addAttribute("authUser", userService.getUserByName(auth.getName()));
        model.addAttribute("usersList", usersList);
        model.addAttribute("roles", roleService.getAllRole());
        model.addAttribute("user", user);
//        return "admin_panel";
        return "admin_panel";
    }

    //Удаление @User
    @GetMapping("/rem/{id}")
    public String deleteUsers(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/delete-user/{id}")
    public String remUserModal(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("roles", roleService.getAllRole());
        return "delete-user :: delete-user";
    }

    //Сохранить @User
//    @GetMapping(value = "/addNewUser")
//    public String addNewUsers(Model model) {
//        User user = new User();
//        user.addRoletoUser(roleService.getRoleByName("ROLE_USER"));
//        model.addAttribute("roles", roleService.getAllRole());
//        model.addAttribute("user", user);
//        return "user";
//    }

    //    @GetMapping(value = "/user/{id}")
//    public String getUsers(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.findUser(id));
//        model.addAttribute("roles", roleService.getAllRole());
//        return "edit-user :: edituser";
//    }
    @GetMapping(value = "/edit-user/{id}")
    public String editUserModal(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("roles", roleService.getAllRole());
        return "edit-user :: edit-user";
    }

    @PostMapping("/{id}")
    public String saveUsers(@ModelAttribute("user") User user, @RequestParam("role") String[] role) {
        user.setRoles(addRole(role));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/addNewUser")
    public String editUser(@ModelAttribute User user, @RequestParam("role") String[] role) {
        user.setRoles(addRole(role));
        userService.saveUser(user);
        return "redirect:/admin";
    }


    private Set<Role> addRole(String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String s : role) {
            roleSet.add(roleService.getRoleByName(s));
        }
        return roleSet;
    }

}