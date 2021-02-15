package com.fponin.Task_313.myapp_springboot.service;

import com.fponin.Task_313.myapp_springboot.dao.RoleDao;
import com.fponin.Task_313.myapp_springboot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }

    @Override
    @Transactional
    public List<Role> getAllRole() {
        return roleDao.getAllRole();
    }
}
