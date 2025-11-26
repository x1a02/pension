package com.x1a02.pension.service.impl;

import com.x1a02.pension.entity.User;
import com.x1a02.pension.mapper.UserMapper;
import com.x1a02.pension.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean addUser(User user) {
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        int rows = userMapper.insert(user);
        return rows > 0;
    }

    public boolean updateUser(User user) {
        int rows = userMapper.update(user);
        return rows > 0;
    }

    public boolean deleteUser(Long id) {
        int rows = userMapper.deleteById(id);
        return rows > 0;
    }

    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    public List<User> getStaffUsers() {
        return userMapper.selectByUserType(2);
    }

    public List<User> getFamilyUsers() {
        return userMapper.selectByUserType(3);
    }

    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
