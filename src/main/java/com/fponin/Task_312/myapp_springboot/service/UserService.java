package com.fponin.Task_312.myapp_springboot.service;

import com.fponin.Task_312.myapp_springboot.model.User;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();

    User findUser(int id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    User getUserByName(String name);

}
