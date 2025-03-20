package com.guyu.sqlite_demo.controller;

import com.guyu.sqlite_demo.pojo.User;
import com.guyu.sqlite_demo.service.UserService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        userService.insertUser(user);
        return "User added!";
    }

    @GetMapping("/list")
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get")
    public User getUserById(Long id) {
        return userService.getUserById(id);
    }
}
