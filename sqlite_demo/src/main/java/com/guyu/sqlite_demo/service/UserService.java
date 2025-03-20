package com.guyu.sqlite_demo.service;

import com.guyu.sqlite_demo.mapper.UserMapper;
import com.guyu.sqlite_demo.pojo.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    // 插入用户
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    // 查询所有用户
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    // 根据 ID 查询用户
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
}
