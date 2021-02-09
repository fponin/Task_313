package com.fponin.Task_312.myapp_springboot.service;

import com.fponin.Task_312.myapp_springboot.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String roleName);

    List<Role> getAllRole();
}
